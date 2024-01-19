/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';
import {EdgeProps, EdgeText} from 'react-flow-renderer';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectRelationshipEdgeData} from '../types';
import ManyMarker from './ManyMarker';
import OneMarker from './OneMarker';

import './Edge.scss';

const labelStyle = {
	cursor: 'pointer',
	fill: '#FFF',
	fontSize: '12px',
	fontWeight: 600,
};

const DEFAULT_COLOR = '#80ACFF';
const HIGHLIGHT_COLOR = '#0B5FFF';

function getInitialObjectRelationshipEdgeStyle(edgeSelected: boolean) {
	return {
		stroke: edgeSelected ? HIGHLIGHT_COLOR : DEFAULT_COLOR,
		strokeWidth: '2px',
	};
}

function getInitialLabelBgStyle(edgeSelected: boolean) {
	return {
		fill: edgeSelected ? HIGHLIGHT_COLOR : DEFAULT_COLOR,
		height: '24px',
	};
}

interface ObjectRelationshipEdge
	extends Partial<EdgeProps<ObjectRelationshipEdgeData[]>> {
	edgeCenterX: number;
	edgeCenterY: number;
	edgePath: string;
	reverseEdgePath?: string;
}

export default function ObjectRelationshipEdge({
	edgePath,
	edgeCenterX,
	edgeCenterY,
	data,
	id: edgeId,
	reverseEdgePath,
	style = {},
}: ObjectRelationshipEdge) {
	const [{id, label, markerEndId, markerStartId, selected}] = data!;

	const [_, dispatch] = useObjectFolderContext();
	const [activePopover, setActivePopover] = useState(false);
	const [
		objectRelationshipEdgeStyle,
		setObjectRelationshipEdgeStyle,
	] = useState({
		...style,
		...getInitialObjectRelationshipEdgeStyle(selected),
	});
	const [labelBgStyle, setLabelBgStyle] = useState(
		getInitialLabelBgStyle(selected)
	);

	const hasManyObjectRelationships = data && data.length > 1;

	const menuElementRef = useRef(null);
	const triggerElementRef = useRef<HTMLElement | null>(null);

	useEffect(() => {
		if (activePopover || selected) {
			setObjectRelationshipEdgeStyle((style) => {
				return {...style, stroke: HIGHLIGHT_COLOR};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: HIGHLIGHT_COLOR,
				};
			});
		}
		else {
			setObjectRelationshipEdgeStyle((style) => {
				return {...style, stroke: DEFAULT_COLOR};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: DEFAULT_COLOR,
				};
			});
		}
	}, [activePopover, selected]);

	return hasManyObjectRelationships ? (
		<g className="react-flow__connection">
			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={edgeId}
				style={objectRelationshipEdgeStyle}
			/>

			<EdgeText
				label={data.length}
				labelBgBorderRadius={4}
				labelBgPadding={[8, 5]}
				labelBgStyle={labelBgStyle}
				labelShowBg
				labelStyle={labelStyle}
				onClick={(event) => {
					triggerElementRef.current = event.target as HTMLElement;

					setActivePopover(!activePopover);
				}}
				x={edgeCenterX}
				y={edgeCenterY}
			/>

			<ClayDropDown.Menu
				active={activePopover}
				alignElementRef={triggerElementRef}
				onActiveChange={() => {
					setActivePopover(!activePopover);
				}}
				ref={menuElementRef}
			>
				<ClayDropDown.ItemList>
					{data.map((objectRelationshipEdgeData, index) => (
						<ClayDropDown.Item
							key={index}
							onClick={() => {
								dispatch({
									payload: {
										selectedObjectRelationshipId:
											objectRelationshipEdgeData.id,
									},
									type:
										TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
								});
							}}
						>
							<div className="lfr-objects__model-builder-self-edge-dropdown-item">
								<div>
									<div>
										{objectRelationshipEdgeData.label}
									</div>

									<span className="text-small">
										{objectRelationshipEdgeData.type}
									</span>
								</div>

								<ClayIcon symbol="angle-right" />
							</div>
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</g>
	) : (
		<g className="react-flow__connection">
			<OneMarker objectRelationshipId={id.toString()} />

			<ManyMarker objectRelationshipId={id.toString()} />

			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={edgeId}
				markerEnd={`url(#${markerEndId})`}
				style={objectRelationshipEdgeStyle}
			/>

			<path
				className="react-flow__edge-path"
				d={reverseEdgePath}
				id={edgeId + 'reverse'}
				markerEnd={`url(#${markerStartId})`}
				style={objectRelationshipEdgeStyle}
			/>

			<EdgeText
				label={label}
				labelBgBorderRadius={4}
				labelBgPadding={[8, 5]}
				labelBgStyle={labelBgStyle}
				labelShowBg
				labelStyle={labelStyle}
				onClick={() => {
					dispatch({
						payload: {
							selectedObjectRelationshipId: id,
						},
						type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
					});
				}}
				x={edgeCenterX}
				y={edgeCenterY}
			/>
		</g>
	);
}

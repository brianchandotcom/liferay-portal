/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {
	EdgeProps,
	EdgeText,
	getEdgeCenter,
	useStoreState,
} from 'react-flow-renderer';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectRelationshipEdgeData} from '../types';
import {
	getInitialLabelBgStyle,
	getInitialObjectRelationshipEdgeStyle,
} from './DefaultObjectRelationshipEdge';

import './Edge.scss';

const labelStyle = {
	cursor: 'pointer',
	fill: '#FFF',
	fontSize: '12px',
	fontWeight: 600,
};

export default function SelfObjectRelationshipEdge({
	data,
	id,
	source,
	sourceX,
	sourceY,
	style = {},
	targetX,
	targetY,
}: EdgeProps<ObjectRelationshipEdgeData>) {
	const {
		defaultLanguageId,
		label,
		markerEndId,
		markerStartId,
		objectRelationshipId,
		selected,
		selfObjectRelationships,
	} = data!;

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
	const {edges, nodes} = useStoreState((state) => state);

	const sourceTargetNode = useMemo(
		() => nodes.find((node) => node.id === source),
		[source, nodes]
	);

	const menuElementRef = useRef(null);
	const triggerElementRef = useRef<HTMLElement | null>(null);

	useEffect(() => {
		if (activePopover || selected) {
			setObjectRelationshipEdgeStyle((style) => {
				return {...style, stroke: '#0B5FFF'};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: '#0B5FFF',
				};
			});
		}
		else {
			setObjectRelationshipEdgeStyle((style) => {
				return {...style, stroke: '#80ACFF'};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: '#80ACFF',
				};
			});
		}
	}, [activePopover, selected]);

	if (!sourceTargetNode) {
		return null;
	}

	const hasManySelfObjectRelationships =
		selfObjectRelationships && selfObjectRelationships.length > 1;

	const radiusX = (sourceX - targetX) * 0.6;
	const radiusY = 150;

	const edgePath = `M ${
		sourceX - (hasManySelfObjectRelationships ? 5 : 20)
	} ${sourceY} A ${radiusX} ${radiusY} 0 1 0 ${
		targetX + (hasManySelfObjectRelationships ? 2 : 8)
	} ${targetY}`;

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY: sourceY - (hasManySelfObjectRelationships ? 0 : 45),
		targetX,
		targetY,
	});

	return (
		<g className="react-flow__connection">
			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={id}
				markerEnd={
					!hasManySelfObjectRelationships
						? `url(#${markerEndId})`
						: ''
				}
				markerStart={
					!hasManySelfObjectRelationships
						? `url(#${markerStartId})`
						: ''
				}
				style={objectRelationshipEdgeStyle}
			/>

			{hasManySelfObjectRelationships ? (
				<>
					<EdgeText
						label={label}
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
						y={edgeCenterY + 230}
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
							{selfObjectRelationships.map(
								(selfObjectRelationship, index) => (
									<ClayDropDown.Item
										key={index}
										onClick={() => {
											dispatch({
												payload: {
													edges,
													nodes,
													selectedObjectRelationshipId: selfObjectRelationship.id.toString(),
												},
												type:
													TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
											});
										}}
									>
										<div className="lfr-objects__model-builder-self-edge-dropdown-item">
											<div>
												<div>
													{getLocalizableLabel(
														defaultLanguageId!,
														selfObjectRelationship.label,
														selfObjectRelationship.name
													)}
												</div>

												<span className="text-small">
													{
														selfObjectRelationship.type
													}
												</span>
											</div>

											<ClayIcon symbol="angle-right" />
										</div>
									</ClayDropDown.Item>
								)
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown.Menu>
				</>
			) : (
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
								edges,
								nodes,
								selectedObjectRelationshipId: objectRelationshipId.toString(),
							},
							type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
						});
					}}
					x={edgeCenterX}
					y={edgeCenterY + 230}
				/>
			)}
		</g>
	);
}

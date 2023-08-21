/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayList from '@clayui/list';
import ClayPopover from '@clayui/popover';
import {getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useEffect, useMemo, useState} from 'react';
import {
	EdgeProps,
	EdgeText,
	getEdgeCenter,
	useStoreState,
} from 'react-flow-renderer';

import {useFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectRelationshipEdgeData} from '../types';
import {getInitialEdgeStyle, getInitialLabelBgStyle} from './DefaultEdge';

import './Edge.scss';

const labelStyle = {
	cursor: 'pointer',
	fill: '#FFF',
	fontSize: '12px',
	fontWeight: 600,
};

export default function SelfEdge({
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
		edgeSelected,
		label,
		markerEndId,
		objectRelationshipId,
		selfRelationships,
	} = data!;

	const [_, dispatch] = useFolderContext();
	const [activePopover, setActivePopover] = useState(false);
	const [edgeStyle, setEdgeStyle] = useState({
		...style,
		...getInitialEdgeStyle(edgeSelected),
	});
	const [labelBgStyle, setLabelBgStyle] = useState(
		getInitialLabelBgStyle(edgeSelected)
	);
	const {edges, nodes} = useStoreState((state) => state);

	const sourceTargetNode = useMemo(
		() => nodes.find((node) => node.id === source),
		[source, nodes]
	);

	useEffect(() => {
		if (edgeSelected) {
			setEdgeStyle((style) => {
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
			setEdgeStyle((style) => {
				return {...style, stroke: '#80ACFF'};
			});
			setLabelBgStyle((style) => {
				return {
					...style,
					fill: '#80ACFF',
				};
			});
		}
	}, [edgeSelected]);

	if (!sourceTargetNode) {
		return null;
	}

	const radiusX = (sourceX - targetX) * 0.6;
	const radiusY = 150;

	const edgePath = `M ${
		sourceX - 5
	} ${sourceY} A ${radiusX} ${radiusY} 0 1 0 ${targetX + 2} ${targetY}`;

	const [edgeCenterX, edgeCenterY] = getEdgeCenter({
		sourceX,
		sourceY,
		targetX,
		targetY,
	});

	return (
		<g className="react-flow__connection">
			<path
				className="react-flow__edge-path"
				d={edgePath}
				id={id}
				markerEnd={`url(#${markerEndId})`}
				style={edgeStyle}
			/>

			{selfRelationships && selfRelationships.length > 1 ? (
				<ClayPopover
					alignPosition="bottom"
					header="Self Relationships"
					show={activePopover}
					size="lg"
					trigger={
						<EdgeText
							label={label}
							labelBgBorderRadius={4}
							labelBgPadding={[8, 5]}
							labelBgStyle={labelBgStyle}
							labelShowBg
							labelStyle={labelStyle}
							onClick={() => setActivePopover(!activePopover)}
							x={edgeCenterX}
							y={edgeCenterY + 230}
						/>
					}
				>
					<ClayList>
						{selfRelationships.map((selfRelationship, index) => (
							<ClayList.Item
								flex
								key={index}
								onClick={() => {
									dispatch({
										payload: {
											edges,
											nodes,
											selectedObjectRelationshipId: selfRelationship.id.toString(),
										},
										type: TYPES.SET_SELECTED_EDGE,
									});
								}}
							>
								<ClayList.ItemField>
									{getLocalizableLabel(
										defaultLanguageId!,
										selfRelationship.label,
										selfRelationship.name
									)}
								</ClayList.ItemField>

								<ClayList.ItemField>
									{selfRelationship.type}
								</ClayList.ItemField>
							</ClayList.Item>
						))}
					</ClayList>
				</ClayPopover>
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
							type: TYPES.SET_SELECTED_EDGE,
						});
					}}
					x={edgeCenterX}
					y={edgeCenterY + 230}
				/>
			)}
		</g>
	);
}

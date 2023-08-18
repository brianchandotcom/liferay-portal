/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayList from '@clayui/list';
import ClayPopover from '@clayui/popover';
import {getLocalizableLabel} from '@liferay/object-js-components-web';
import React, {useMemo, useState} from 'react';
import {
	EdgeProps,
	EdgeText,
	getEdgeCenter,
	useStoreState,
} from 'react-flow-renderer';

import {ObjectRelationshipEdgeData} from '../types';

import './Edge.scss';

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
	const nodes = useStoreState((state) => state.nodes);
	const [activePopover, setActivePopover] = useState(false);

	const sourceTargetNode = useMemo(
		() => nodes.find((node) => node.id === source),
		[source, nodes]
	);

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
				markerEnd={`url(#${data?.markerEndId})`}
				style={{
					...style,
					stroke: '#0B5FFF',
					strokeWidth: '2px',
				}}
			/>

			{data?.selfRelationships && data.selfRelationships.length > 1 ? (
				<ClayPopover
					alignPosition="bottom"
					header="Self Relationships"
					show={activePopover}
					size="lg"
					trigger={
						<EdgeText
							label={data.label}
							labelBgBorderRadius={4}
							labelBgPadding={[8, 5]}
							labelBgStyle={{
								fill: '#0B5FFF',
								height: '24px',
							}}
							labelShowBg
							labelStyle={{
								cursor: 'pointer',
								fill: '#FFF',
								fontSize: '12px',
								fontWeight: 600,
							}}
							onClick={() => setActivePopover(!activePopover)}
							x={edgeCenterX}
							y={edgeCenterY + 230}
						/>
					}
				>
					<ClayList>
						{data.selfRelationships.map(
							(selfRelationship, index) => (
								<ClayList.Item flex key={index}>
									<ClayList.ItemField>
										{getLocalizableLabel(
											data.defaultLanguageId!,
											selfRelationship.label,
											selfRelationship.name
										)}
									</ClayList.ItemField>

									<ClayList.ItemField>
										{selfRelationship.type}
									</ClayList.ItemField>
								</ClayList.Item>
							)
						)}
					</ClayList>
				</ClayPopover>
			) : (
				<EdgeText
					label={data?.label}
					labelBgBorderRadius={4}
					labelBgPadding={[8, 5]}
					labelBgStyle={{
						fill: '#0B5FFF',
						height: '24px',
					}}
					labelShowBg
					labelStyle={{
						cursor: 'pointer',
						fill: '#FFF',
						fontSize: '12px',
						fontWeight: 600,
					}}
					x={edgeCenterX}
					y={edgeCenterY + 230}
				/>
			)}
		</g>
	);
}

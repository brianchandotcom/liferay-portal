/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import classNames from 'classnames';
import React, {useRef} from 'react';
import {EdgeText} from 'react-flow-renderer';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import {ObjectRelationshipEdgeData} from '../types';
import {BaseObjectRepationShipEdgeProps} from './ObjectRelationshipEdge';

import './Edge.scss';

interface ManyObjectRelationshipEdgeProps
	extends BaseObjectRepationShipEdgeProps {
	activePopover: boolean;
	data: ObjectRelationshipEdgeData[];
	labelBgStyle: React.CSSProperties;
	labelStyle: React.CSSProperties;
	objectRelationshipEdgeStyle: React.CSSProperties;
	setActivePopover: (value: boolean) => void;
}

export function ManyObjectRelationshipEdge({
	activePopover,
	data,
	edgeCenterX,
	edgeCenterY,
	edgeId,
	edgePath,
	labelBgStyle,
	labelStyle,
	objectRelationshipEdgeStyle,
	setActivePopover,
}: ManyObjectRelationshipEdgeProps) {
	const [_, dispatch] = useObjectFolderContext();
	const menuElementRef = useRef(null);
	const triggerElementRef = useRef<HTMLElement | null>(null);
	const someObjectRelationshipInheritance = data.some(({edge}) => edge);

	return (
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
							className="lfr-objects__model-builder-many-edge-dropdown-item-button"
							key={index}
							onClick={() => {
								dispatch({
									payload: {
										selectedObjectRelationshipId:
											objectRelationshipEdgeData.id,
									},
									type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
								});
							}}
							symbolRight="angle-right"
							{...(objectRelationshipEdgeData.edge
								? {symbolLeft: 'organizations'}
								: {})}
						>
							<div
								className={classNames({
									'lfr-objects__model-builder-many-edge-dropdown-item-details':
										someObjectRelationshipInheritance &&
										!objectRelationshipEdgeData.edge,
								})}
							>
								<div>
									<Text size={4}>
										{objectRelationshipEdgeData.label}
									</Text>
								</div>

								<Text size={3}>
									{objectRelationshipEdgeData.type}
								</Text>
							</div>
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown.Menu>
		</g>
	);
}

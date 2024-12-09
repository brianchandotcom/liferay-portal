/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';

import {useObjectFolderContext} from '../ModelBuilderContext/objectFolderContext';
import {TYPES} from '../ModelBuilderContext/typesEnum';
import ManyMarker from './ManyMarker';
import {BaseObjectRepationShipEdgeProps} from './ObjectRelationshipEdge';
import OneMarker from './OneMarker';

import './Edge.scss';

interface SimpleObjectRelationshipEdgeProps
	extends BaseObjectRepationShipEdgeProps {
	id: number;
	isRootStructure: boolean;
	label: string;
	markerEndId: string;
	markerStartId: string;
	reverseEdgePath?: string;
}

export function SimpleObjectRelationshipEdge({
	edgeCenterX,
	edgeCenterY,
	edgeId,
	edgePath,
	id,
	isRootStructure,
	label,
	labelBgStyle,
	labelStyle,
	markerEndId,
	markerStartId,
	objectRelationshipEdgeStyle,
	reverseEdgePath,
}: SimpleObjectRelationshipEdgeProps) {
	const buttonRef = useRef(null);
	const [_, dispatch] = useObjectFolderContext();
	const [foreignObjectWidth, setForeignObjectWidth] = useState<number>(0);

	const foreignObjectHeight = 25;

	useEffect(() => {
		if (buttonRef.current) {
			const buttonWidth = (buttonRef.current as HTMLButtonElement)
				.offsetWidth;
			setForeignObjectWidth(buttonWidth);
		}
	}, [buttonRef, label]);

	return (
		<>
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
			<foreignObject
				height={foreignObjectHeight}
				width={foreignObjectWidth}
				x={edgeCenterX - foreignObjectWidth / 2}
				y={edgeCenterY - foreignObjectHeight / 2}
			>
				<ClayButton
					className="react-flow__edge-button"
					onClick={() => {
						dispatch({
							payload: {
								selectedObjectRelationshipId: id,
							},
							type: TYPES.SET_SELECTED_OBJECT_RELATIONSHIP_EDGE,
						});
					}}
					ref={buttonRef}
					style={{
						backgroundColor: labelBgStyle.fill,
						...labelBgStyle,
						...labelStyle,
					}}
				>
					{label}

					{isRootStructure && (
						<>
							&nbsp;
							<ClayIcon symbol="organizations" />
						</>
					)}
				</ClayButton>
			</foreignObject>
		</>
	);
}

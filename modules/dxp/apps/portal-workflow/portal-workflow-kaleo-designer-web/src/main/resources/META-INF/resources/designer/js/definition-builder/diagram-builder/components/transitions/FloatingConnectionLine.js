/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React from 'react';
import {getBezierPath} from 'react-flow-renderer';

import {getEdgeParams} from './utils';

const FloatingConnectionLine = ({
	sourceNode,
	sourcePosition,
	targetPosition,
	targetX,
	targetY,
}) => {
	if (!sourceNode) {
		return null;
	}

	const targetNode = {
		__rf: {height: 1, position: {x: targetX, y: targetY}, width: 1},
		id: 'connection-target',
	};

	const {sx, sy} = getEdgeParams(sourceNode, targetNode);
	const d = getBezierPath({
		sourcePosition,
		sourceX: sx,
		sourceY: sy,
		targetPosition,
		targetX,
		targetY,
	});

	return (
		<g>
			<path
				className="animated"
				d={d}
				fill="none"
				stroke="#222"
				strokeWidth={1.5}
			/>

			<circle
				cx={targetX}
				cy={targetY}
				fill="#fff"
				r={3}
				stroke="#222"
				strokeWidth={1.5}
			/>
		</g>
	);
};

export default FloatingConnectionLine;

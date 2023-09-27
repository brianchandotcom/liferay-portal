/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ArrowHeadType, Position} from 'react-flow-renderer';

function getNodeIntersection(intersectionNode, targetNode) {
	const {
		height: intersectionNodeHeight,
		position: intersectionNodePosition,
		width: intersectionNodeWidth,
	} = intersectionNode.__rf;
	const targetPosition = targetNode.__rf.position;

	const width = intersectionNodeWidth / 2;
	const height = intersectionNodeHeight / 2;

	const x2 = intersectionNodePosition.x + width;
	const y2 = intersectionNodePosition.y + height;
	const x1 = targetPosition.x + width;
	const y1 = targetPosition.y + height;

	const xx1 = (x1 - x2) / (2 * width) - (y1 - y2) / (2 * height);
	const yy1 = (x1 - x2) / (2 * width) + (y1 - y2) / (2 * height);
	const a = 1 / (Math.abs(xx1) + Math.abs(yy1));
	const xx3 = a * xx1;
	const yy3 = a * yy1;
	const x = width * (xx3 + yy3) + x2;
	const y = height * (-xx3 + yy3) + y2;

	return {x, y};
}

function getEdgePosition(node, intersectionPoint) {
	const n = {...node.__rf.position, ...node.__rf};
	const nx = Math.round(n.x);
	const ny = Math.round(n.y);
	const px = Math.round(intersectionPoint.x);
	const py = Math.round(intersectionPoint.y);

	if (px <= nx + 1) {
		return Position.Left;
	}
	if (px >= nx + n.width - 1) {
		return Position.Right;
	}
	if (py <= ny + 1) {
		return Position.Top;
	}
	if (py >= n.y + n.height - 1) {
		return Position.Bottom;
	}

	return Position.Top;
}

export function getEdgeParams(source, target) {
	const sourceIntersectionPoint = getNodeIntersection(source, target);
	const targetIntersectionPoint = getNodeIntersection(target, source);

	const sourcePos = getEdgePosition(source, sourceIntersectionPoint);
	const targetPos = getEdgePosition(target, targetIntersectionPoint);

	return {
		sourcePos,
		sx: sourceIntersectionPoint.x,
		sy: sourceIntersectionPoint.y,
		targetPos,
		tx: targetIntersectionPoint.x,
		ty: targetIntersectionPoint.y,
	};
}

export function createElements() {
	const elements = [];
	const center = {x: window.innerWidth / 2, y: window.innerHeight / 2};

	elements.push({data: {label: 'Target'}, id: 'target', position: center});

	for (let i = 0; i < 8; i++) {
		const degrees = i * (360 / 8);
		const radians = degrees * (Math.PI / 180);
		const x = 250 * Math.cos(radians) + center.x;
		const y = 250 * Math.sin(radians) + center.y;

		elements.push({data: {label: 'Source'}, id: `${i}`, position: {x, y}});

		elements.push({
			arrowHeadType: ArrowHeadType.Arrow,
			id: `edge-${i}`,
			source: `${i}`,
			target: 'target',
			type: 'floating',
		});
	}

	return elements;
}

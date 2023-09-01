/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ArrowHeadType, Node, Position, XYPosition} from 'react-flow-renderer';

// this helper function returns the intersection point
// of the line between the center of the intersectionNode and the target node

export function createElements() {
	const elements = [];
	const center = {x: window.innerWidth / 2, y: window.innerHeight / 2};

	elements.push({
		data: {label: 'Target'},
		id: 'target',
		position: center,
	});

	for (let i = 0; i < 8; i++) {
		const degrees = i * (360 / 8);
		const radians = degrees * (Math.PI / 180);
		const x = 250 * Math.cos(radians) + center.x;
		const y = 250 * Math.sin(radians) + center.y;

		elements.push({
			data: {label: 'Source'},
			id: `${i}`,
			position: {x, y},
		});

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

export function getEdgeParams(
	source: Node,
	sourceIncrementY: number,
	target: Node,
	targetIncrementY: number
) {
	const sourceIntersectionPoint = getNodeIntersection(
		source,
		sourceIncrementY,
		targetIncrementY,
		target
	);

	const targetIntersectionPoint = getNodeIntersection(
		target,
		sourceIncrementY,
		targetIncrementY,
		source
	);

	const sourcePos = getEdgePosition(
		sourceIntersectionPoint,
		source,
		sourceIncrementY
	);

	const targetPos = getEdgePosition(
		targetIntersectionPoint,
		target,
		targetIncrementY
	);

	return {
		sourcePos,
		sourceX: sourceIntersectionPoint.x,
		sourceY: sourceIntersectionPoint.y,
		targetPos,
		targetX: targetIntersectionPoint.x,
		targetY: targetIntersectionPoint.y,
	};
}

function getEdgePosition(
	intersectionPoint: XYPosition,
	node: Node,
	nodeIncrementY: number
) {
	const nodeProperties = {...node.__rf.position, ...node.__rf};
	const nodePositionX = Math.round(nodeProperties.x);
	const nodePositionY = Math.round(nodeProperties.y + nodeIncrementY);
	const intersectionPointX = Math.round(intersectionPoint.x);
	const intersectionPointY = Math.round(intersectionPoint.y);

	if (intersectionPointX <= nodePositionX + 1) {
		return Position.Left;
	}
	if (intersectionPointX >= nodePositionX + nodeProperties.width - 1) {
		return Position.Right;
	}
	if (intersectionPointY <= nodePositionY + 1) {
		return Position.Top;
	}
	if (intersectionPointY >= nodeProperties.y + nodeProperties.height - 1) {
		return Position.Bottom;
	}

	return Position.Top;
}

function getNodeIntersection(
	intersectionNode: Node,
	sourceIncrementY: number,
	targetIncrementY: number,
	targetNode: Node
): XYPosition {

	// https://math.stackexchange.com/questions/1724792/an-algorithm-for-finding-the-intersection-point-between-a-center-of-vision-and-a

	const {
		height: intersectionNodeHeight,
		position: intersectionNodePosition,
		width: intersectionNodeWidth,
	} = intersectionNode.__rf;
	const targetPosition = targetNode.__rf.position;

	const nodeHalfWidth = intersectionNodeWidth / 2;
	const nodeHalfHeight = intersectionNodeHeight / 2;

	const sourceCoordinateX = intersectionNodePosition.x + nodeHalfWidth;
	const sourceCoordinateY =
		intersectionNodePosition.y + sourceIncrementY + nodeHalfHeight;
	const targetCoordinateX = targetPosition.x + nodeHalfWidth;
	const targetCoordinateY =
		targetPosition.y + targetIncrementY + nodeHalfHeight;

	const sourceToTargetXDifference =
		(targetCoordinateX - sourceCoordinateX) / (2 * nodeHalfWidth) -
		(targetCoordinateY - sourceCoordinateY) / (2 * nodeHalfHeight);
	const sourceToTargetYDifference =
		(targetCoordinateX - sourceCoordinateX) / (2 * nodeHalfWidth) +
		(targetCoordinateY - sourceCoordinateY) / (2 * nodeHalfHeight);

	const normalizedScale =
		1 /
		(Math.abs(sourceToTargetXDifference) +
			Math.abs(sourceToTargetYDifference));

	const normalizedSourceToTargetXDifference =
		normalizedScale * sourceToTargetXDifference;
	const normalizedSourceToTargetYDifference =
		normalizedScale * sourceToTargetYDifference;

	const intersectionPointX =
		nodeHalfWidth *
			(normalizedSourceToTargetXDifference +
				normalizedSourceToTargetYDifference) +
		sourceCoordinateX;
	const intersectionPointY =
		nodeHalfHeight *
			(-normalizedSourceToTargetXDifference +
				normalizedSourceToTargetYDifference) +
		sourceCoordinateY;

	return {x: intersectionPointX, y: intersectionPointY};
}

export function getObjectFolderName(): string {
	const urlSearchParams = new URLSearchParams(window.location.search);

	return urlSearchParams.get('objectFolderName') || '';
}

export function updateURLParam(paramType: string, paramValue: string) {
	const currentURL = window.location.href;

	const newURL = currentURL.replace(
		new RegExp('(' + paramType + '=)([^&]*)'),
		paramType + '=' + paramValue
	);

	window.history.pushState({path: newURL}, '', newURL);
}

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

function hasPositionedNode(objectFolderItems: ObjectFolderItem[]) {
	return objectFolderItems.some(
		(objectFolderItem) =>
			objectFolderItem.positionX !== 0 || objectFolderItem.positionY !== 0
	);
}

interface handleUnplacedObjectDefinitionNode {
	index: number;
	objectFolderExternalReferenceCode: string;
	outdatedObjectFolderItems: ObjectFolderItem[];
	positionColumn: {x: number; y: number};
	updatedObjectFolderItems: ObjectFolderItem[];
}

function handleUnplacedObjectDefinitionNode({
	index,
	objectFolderExternalReferenceCode,
	outdatedObjectFolderItems,
	positionColumn,
	updatedObjectFolderItems,
}: handleUnplacedObjectDefinitionNode) {
	const hasNewPositionedNode = hasPositionedNode(updatedObjectFolderItems);

	if (objectFolderExternalReferenceCode === 'uncategorized') {
		const hasOldPositionedNode = hasPositionedNode(
			outdatedObjectFolderItems
		);

		if (hasOldPositionedNode) {
			return getObjectDefinitionNodeNextPosition(
				outdatedObjectFolderItems
			);
		}

		return getUncategorizedPredefinedPosition(positionColumn, index);
	}
	else if (hasNewPositionedNode) {
		return getObjectDefinitionNodeNextPosition(updatedObjectFolderItems);
	}

	return getObjectFolderDiagramCenterPosition();
}

interface getObjectDefinitionNodePosition {
	index: number;
	objectDefinition: ObjectDefinitionNodeData;
	objectFolderExternalReferenceCode: string;
	outdatedObjectFolderItems: ObjectFolderItem[];
	positionColumn: {x: number; y: number};
	updatedObjectFolderItems: ObjectFolderItem[];
}

export function getObjectDefinitionNodePosition({
	index,
	objectDefinition,
	objectFolderExternalReferenceCode,
	outdatedObjectFolderItems,
	positionColumn,
	updatedObjectFolderItems,
}: getObjectDefinitionNodePosition) {
	const objectFolderItem = outdatedObjectFolderItems.find(
		(objectFolderItem) =>
			objectFolderItem.objectDefinitionExternalReferenceCode ===
			objectDefinition.externalReferenceCode
	);

	const {positionX, positionY} = objectFolderItem as ObjectFolderItem;

	if (positionX === 0 && positionY === 0) {
		return handleUnplacedObjectDefinitionNode({
			index,
			objectFolderExternalReferenceCode,
			outdatedObjectFolderItems,
			positionColumn,
			updatedObjectFolderItems,
		});
	}

	return {x: positionX, y: positionY};
}

export function getObjectDefinitionNodeNextPosition(
	objectFolderItems: ObjectFolderItem[]
) {
	const yPositions = objectFolderItems.map(
		(objectDefinitionNode) => objectDefinitionNode.positionY
	);
	const maximumY = Math.max(...yPositions);
	const maximumNodesYPosition = objectFolderItems.filter(
		(objectDefinitionNode) => objectDefinitionNode.positionY === maximumY
	);
	const xPositions = maximumNodesYPosition.map(
		(objectDefinitionNode) => objectDefinitionNode.positionX
	);
	const maximumX = Math.max(...xPositions);
	const mostBottomRightNodePosition = maximumNodesYPosition.find(
		(objectDefinitionNode) => objectDefinitionNode.positionX === maximumX
	);

	return {
		x: mostBottomRightNodePosition!.positionX + 380,
		y: mostBottomRightNodePosition!.positionY,
	};
}

export function getObjectFolderDiagramCenterPosition() {
	const diagramAreaPositionInfo = document
		.querySelector('.lfr-objects__model-builder-diagram-area')
		?.getBoundingClientRect();
	const objectDefinitionNodeContainerInfo = {height: 352, width: 284};

	if (diagramAreaPositionInfo) {
		return {
			x:
				diagramAreaPositionInfo.width / 2 -
				objectDefinitionNodeContainerInfo.width / 2,
			y:
				diagramAreaPositionInfo.height / 2 -
				objectDefinitionNodeContainerInfo.height / 2,
		};
	}
	else {
		return {x: 0, y: 0};
	}
}

function getUncategorizedPredefinedPosition(
	positionColumn: {x: number; y: number},
	index: number
) {
	const x = positionColumn.x * 380 + 360;
	const y = positionColumn.y * 450 + 100;

	positionColumn.x++;

	if ((index + 1) % 4 === 0 && index !== 0) {
		positionColumn.y++;
		positionColumn.x = 0;
	}

	return {x, y};
}

export function updatePreviousURLParam(paramType: string, paramValue: string) {
	const previousPath = document.referrer;

	const newPreviousURL = new URL(previousPath);

	const objectFolderNameParam = newPreviousURL.searchParams.get(paramType);

	if (objectFolderNameParam) {
		newPreviousURL.searchParams.set(paramType, paramValue);

		window.history.pushState(null, '', newPreviousURL.toString());

		window.location.href = newPreviousURL.toString();
	}
}

export function updateURLParam(paramType: string, paramValue: string) {
	const currentURL = window.location.href;

	const newURL = currentURL.replace(
		new RegExp('(' + paramType + '=)([^&]*)'),
		paramType + '=' + paramValue
	);

	window.history.pushState({path: newURL}, '', newURL);
}

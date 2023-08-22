/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Edge} from 'react-flow-renderer';

import {ObjectRelationshipEdgeData} from '../types';

export function fieldsCustomSort(objectFields: ObjectFieldNode[]) {
	const fieldOrder = ['id', 'externalReferenceCode'];

	const compareFields = (
		fieldA: ObjectFieldNode,
		fieldB: ObjectFieldNode
	) => {
		const fieldAIndex = fieldOrder.indexOf(fieldA.name as string);
		const fieldBIndex = fieldOrder.indexOf(fieldB.name as string);

		if (fieldAIndex !== -1 && fieldBIndex !== -1) {
			return fieldAIndex - fieldBIndex;
		}
		else if (fieldAIndex !== -1) {
			return -1;
		}
		else if (fieldBIndex !== -1) {
			return 1;
		}

		if (fieldA.required && !fieldB.required) {
			return -1;
		}
		else if (!fieldA.required && fieldB.required) {
			return 1;
		}

		return 0;
	};

	return objectFields.sort(compareFields);
}

function separateEdgesBySourceAndTarget(
	edges: Edge<ObjectRelationshipEdgeData>[]
) {
	const edgeGroups: {[key: string]: Edge<ObjectRelationshipEdgeData>[]} = {};

	edges.forEach((edge) => {
		const key =
			edge.source <= edge.target
				? `${edge.source}-${edge.target}`
				: `${edge.target}-${edge.source}`;

		if (!edgeGroups[key]) {
			edgeGroups[key] = [];
		}

		edgeGroups[key].push(edge);
	});

	const groupedEdges = Object.values(edgeGroups);

	return groupedEdges;
}

export function incrementEdgesYPosition(
	edges: Edge<ObjectRelationshipEdgeData>[],
	initialYPosition: number,
	yIncrement: number
) {
	let sourceTargetYIncrement = initialYPosition;

	return edges.map((edge) => {
		const newEdge = {
			...edge,
			data: {
				...edge.data,
				sourceY: sourceTargetYIncrement,
				targetY: sourceTargetYIncrement,
			},
		} as Edge<ObjectRelationshipEdgeData>;

		sourceTargetYIncrement += yIncrement;

		return newEdge;
	});
}

export function getNonOverlappingEdges(
	allEdges: Edge<ObjectRelationshipEdgeData>[]
) {
	const groupedEdges = separateEdgesBySourceAndTarget(allEdges);

	const newEdges: Edge<ObjectRelationshipEdgeData>[] = [];

	function addIncrementedEdges(
		edges: Edge<ObjectRelationshipEdgeData>[],
		initialYPosition: number,
		yIncrement: number
	) {
		const incrementedEdges = incrementEdgesYPosition(
			edges,
			initialYPosition,
			yIncrement
		);
		newEdges.push(...incrementedEdges);
	}

	groupedEdges.forEach((edges) => {
		const edgeCount = edges.length;

		if (edgeCount <= 1) {
			addIncrementedEdges(edges, 0, 0);
		}
		else if (edgeCount <= 3) {
			addIncrementedEdges(edges, 0, 50);
		}
		else if (edgeCount <= 4) {
			addIncrementedEdges(edges, -50, 50);
		}
		else if (edgeCount <= 6) {
			addIncrementedEdges(edges, -100, 50);
		}
		else {
			addIncrementedEdges(edges, -100, 30);
		}
	});

	return newEdges;
}

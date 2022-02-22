/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

export function visit(nodes, callback) {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
}

/**
 * Map the node array prop in an understandable format for the Treeview component
 * @param {array} nodeArray - Array of nodes.
 * @return {array} A new array of nodes.
 */
export function nodeTreeArrayMapper({
	childrenPropertyKey,
	namePropertyKey,
	nodeArray,
}) {
	return nodeArray.map((node, index) => {
		const hasChildren = !!node[childrenPropertyKey]?.length;

		const _getNodeId = ({index, node}) =>
			hasChildren ? `_${index}` : `${node[namePropertyKey]}`;

		return {
			...node,
			children: hasChildren
				? nodeTreeArrayMapper({
						childrenPropertyKey,
						namePropertyKey,
						nodeArray: node[childrenPropertyKey],
				  })
				: null,
			expanded: !!(!index && hasChildren) || false,
			id: _getNodeId({index, node}),
			name: hasChildren
				? handleNodeName({
						childrenPropertyKey,
						node,
				  })
				: node[namePropertyKey],
		};
	});
}

/**
 * Adds to the node name the total count of children, if any
 * Each node also can have children to be filtered
 * @param {object} object
 * @param {array} object.node Tree node object.
 * @return {string} The node name with the total count.
 */
export function handleNodeName({childrenPropertyKey, node}) {
	const children = node.children || node[childrenPropertyKey];
	let count = children.length;

	if (!count) {
		count = node[childrenPropertyKey].length;
	}

	const langKey =
		count > 1
			? Liferay.Language.get('items')
			: Liferay.Language.get('item');

	return `${node.label} (${count} ${langKey})`;
}

/**
 * Handles the node filtering
 * Needed due to the recursivity of handleNodeFiltering function and the need of expanding first node
 * @param {object} object
 * @param {array} object.nodes - Array of nodes.
 * @param {string} object.query - string used in search input.
 * @return {array} A new array of nodes.
 */
export function filterNodes({
	childrenPropertyKey,
	namePropertyKey,
	nodes,
	query,
}) {
	const filteredNodes = handleNodeFiltering({
		childrenPropertyKey,
		namePropertyKey,
		nodes,
		query,
	});

	if (!filteredNodes.length) {
		return [];
	}

	filteredNodes[0].expanded = true;

	return filteredNodes;
}

/**
 * Filters the array of nodes using a string
 * Each node also can have children to be filtered
 * @param {object} object
 * @param {array} object.nodes - Array of nodes.
 * @param {string} object.query - string used in search input.
 * @return {array} A new array of nodes.
 */
const handleNodeFiltering = ({
	childrenPropertyKey,
	namePropertyKey,
	nodes,
	query,
}) => {
	const nodeHasChildren = (node) => Array.isArray(node) && node.length > 0;

	return nodes.filter((node) => {
		if (nodeHasChildren(node.children)) {
			node.children = [
				...handleNodeFiltering({
					childrenPropertyKey,
					nodes: node.children,
					query,
				}),
			];

			node.name = handleNodeName({
				childrenPropertyKey,
				node,
			});
		}

		const cleanNodeName = node.name
			.toLowerCase()
			.replace(/\(\d+ [a-z]+\)$/i, '')
			.trim();

		const nodeMatchesInQuery = cleanNodeName.includes(query);

		if (
			nodeMatchesInQuery &&
			!nodeHasChildren(node.children) &&
			node[childrenPropertyKey]?.length
		) {
			node.children = [
				...nodeTreeArrayMapper({
					childrenPropertyKey,
					namePropertyKey,
					nodeArray: node[childrenPropertyKey],
				}),
			];
		}

		return (
			nodeMatchesInQuery ||
			(!nodeMatchesInQuery && nodeHasChildren(node.children))
		);
	});
};

/**
 * Returns the selected object
 * Object properties are received via props using mandatoryFieldsForFiltering
 * @param {object} object
 * @param {array} object.mandatoryFieldsForFiltering - Array of strings referencing properties.
 * @param {object} object.node - the selected node from the tree
 * @return {object} A object with the proper keys from mandatoryFieldsForFiltering
 */
export function getSelectedNodeObject({mandatoryFieldsForFiltering, node}) {
	const nodeObject = {};

	mandatoryFieldsForFiltering.forEach((key) => {
		nodeObject[key] = node[key];
	});

	return nodeObject;
}

/**
 * Processes and returns the data in an understandable format for the backend
 * An exception occurs when mandatoryFieldsForFiltering contains only one key
 * @param {object} object
 * @param {array} object.data - array of objects coming from the selections made in the tree
 * @param {array} object.mandatoryFieldsForFiltering - Array of strings referencing properties.
 * @return {object, array} The data object when mandatoryFieldsForFiltering length is > 1, an array of strings otherwise
 */
export function selectedDataOutputTransfomer({
	data,
	mandatoryFieldsForFiltering,
}) {
	if (!data.length || mandatoryFieldsForFiltering.length > 1) {
		return data;
	}

	return data.map((node) => node[mandatoryFieldsForFiltering[0]]);
}

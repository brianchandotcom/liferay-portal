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

export const visit = (nodes, callback) => {
	nodes.forEach((node) => {
		callback(node);

		if (node.children) {
			visit(node.children, callback);
		}
	});
};

/**
 * Map the node array prop in an understandable format for the Treeview component
 * @param {array} nodeArray - Array of nodes.
 * @return {array} A new array of nodes.
 */
export const nodeTreeArrayMapper = ({
	childrenPropertyKey,
	namePropertyKey,
	nodeArray,
}) => {
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
};

/**
 * Adds to the node name the total count of children, if any
 * Each node also can have children to be filtered
 * @param {object} object
 * @param {array} object.node Tree node object.
 * @return {string} The node name with the total count.
 */
export const handleNodeName = ({childrenPropertyKey, node}) => {
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
};

/**
 * Filters the array of nodes using a string
 * Each node also can have children to be filtered
 * @param {object} object
 * @param {array} object.nodes - Array of nodes.
 * @param {string} object.query - string used in search input.
 * @return {array} A new array of nodes.
 */
export const filterNodes = ({childrenPropertyKey, nodes, query}) => {
	const nodeHasChildren = (node) => Array.isArray(node) && node.length > 0;

	return nodes.filter((node) => {
		if (nodeHasChildren(node.children)) {
			node.children = [
				...filterNodes({
					childrenPropertyKey,
					nodes: node.children,
					query,
				}),
			];

			node.expanded = true; // TODO: Expand only if children match in the query

			node.name = handleNodeName({
				childrenPropertyKey,
				node,
			});
		}

		const nodeMatchesInQuery = node.name.toLowerCase().includes(query);

		return (
			nodeMatchesInQuery ||
			(!nodeMatchesInQuery && nodeHasChildren(node.children))
		);
	});
};

export const restoreChildrenForEmptiedParent = ({
	childrenPropertyKey,
	filteredNodes,
	namePropertyKey,
}) => {
	visit(filteredNodes, (node) => {
		if (!node.children?.length && node[childrenPropertyKey]?.length) {
			node.children = [
				...nodeTreeArrayMapper({
					childrenPropertyKey,
					namePropertyKey,
					nodeArray: node[childrenPropertyKey],
				}),
			];
			node.expanded = false;
		}
	});

	return [...filteredNodes];
};

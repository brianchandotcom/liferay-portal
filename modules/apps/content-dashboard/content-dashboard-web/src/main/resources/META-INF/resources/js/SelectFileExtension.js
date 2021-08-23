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

import PropTypes from 'prop-types';
import React from 'react';

import TreeFilter from './components/TreeFilter';

/**
 * Map the node array prop in an understandable format for the Treeview component
 * @param {array} nodeArray - Array of nodes.
 * @return {array} A new array of nodes.
 */
const nodeTreeArrayMapper = (nodeArray) => {
	return nodeArray.map((node, index) => {
		const hasChildren = !!node.fileExtensions?.length;

		const _getNodeId = ({index, node}) =>
			hasChildren ? `_${index}` : `${node.fileExtension}`;

		return {
			...node,
			children: hasChildren
				? nodeTreeArrayMapper(node.fileExtensions)
				: null,
			expanded: !!(!index && hasChildren) || false,
			id: _getNodeId({index, node}),
			name: hasChildren ? node.label : node.fileExtension,
		};
	});
};

const SelectFileExtension = ({
	fileExtensionGroups,
	itemSelectorSaveEvent,
	portletNamespace,
}) => {
	return (
		<TreeFilter
			itemSelectorSaveEvent={itemSelectorSaveEvent}
			mandatoryFieldsForFiltering={['id']}
			nodes={nodeTreeArrayMapper(fileExtensionGroups)}
			portletNamespace={portletNamespace}
		/>
	);
};

SelectFileExtension.propTypes = {
	fileExtensionGroups: PropTypes.array.isRequired,
	itemSelectorSaveEvent: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};

export default SelectFileExtension;

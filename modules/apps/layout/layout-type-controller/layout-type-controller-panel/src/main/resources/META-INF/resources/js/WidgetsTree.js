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

import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export default function WidgetsTree({
	items,
	portletNamespace: namespace,
	selectedPortlets: initialSelectedIds,
}) {
	const [selectedIds, setSelectedIds] = useState(initialSelectedIds || []);

	return (
		<>
			<ClayTreeView
				defaultItems={items}
				onSelectionChange={(nextSelectedIds) => {
					setSelectedIds(Array.from(nextSelectedIds));
				}}
				selectedKeys={new Set(selectedIds)}
				selectionMode="multiple-recursive"
				showExpanderOnHover={false}
			>
				{(item) => <TreeItem item={item} namespace={namespace} />}
			</ClayTreeView>

			<input
				name={`${namespace}TypeSettingsProperties--panelSelectedPortlets--`}
				readOnly
				type="hidden"
				value={selectedIds.join(',')}
			></input>
		</>
	);
}

WidgetsTree.propTypes = {};

function TreeItem({item, namespace}) {
	return (
		<ClayTreeView.Item>
			<ClayTreeView.ItemStack>
				<ClayCheckbox
					aria-labelledby={getId(namespace, item.id)}
					containerProps={{className: 'mb-0'}}
					tabIndex={-1}
				/>

				<ClayIcon symbol="folder" />

				<span id={getId(namespace, item.id)}>{item.name}</span>
			</ClayTreeView.ItemStack>

			<ClayTreeView.Group items={item.children}>
				{(childItem) => (
					<ClayTreeView.Item>
						<ClayCheckbox
							aria-labelledby={getId(namespace, childItem.id)}
							containerProps={{className: 'mb-0'}}
							tabIndex={-1}
						/>

						<ClayIcon symbol="page" />

						<span id={getId(namespace, childItem.id)}>
							{childItem.name}
						</span>
					</ClayTreeView.Item>
				)}
			</ClayTreeView.Group>
		</ClayTreeView.Item>
	);
}

TreeItem.propTypes = {
	item: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
};

function getId(namespace, key) {
	return `${namespace}pages-tree-${key}`;
}

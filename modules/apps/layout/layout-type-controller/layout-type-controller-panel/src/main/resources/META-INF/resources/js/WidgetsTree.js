/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TreeView as ClayTreeView} from '@clayui/core';
import {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

export default function WidgetsTree({
	items,
	portletNamespace: namespace,
	selectedPortlets,
}) {
	const [selectedIds, setSelectedIds] = useState(selectedPortlets);

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

WidgetsTree.propTypes = {
	items: PropTypes.array.isRequired,
	portletNamespace: PropTypes.array.isRequired,
	selectedPortlets: PropTypes.array.isRequired,
};

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
	return `${namespace}widgets-tree-${key}`;
}

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
import ClayIcon from '@clayui/icon';
import {Treeview} from 'frontend-js-components-web';
import React from 'react';

function findCategory(categoryId, categories = []) {
	// eslint-disable-next-line no-for-of-loops/no-for-of-loops
	for (const category of categories) {
		if (category.id === categoryId) {
			return category;
		}

		const childrenCategory = findCategory(categoryId, category.children);

		if (childrenCategory) {
			return childrenCategory;
		}
	}

	return null;
}

const AssetCategoriesNavigationTreeView = ({
	selectedCategoryId,
	vocabularies,
}) => {
	const handleSelectionChange = (event, item) => {
		event.preventDefault();

		if (selectedCategoryId === item.id) {
			return;
		}

		Liferay.Util.navigate(item.url);
	};

	return (
		<ClayTreeView
			defaultItems={vocabularies}
			defaultSelectedKeys={
				new Set(selectedCategoryId ? [selectedCategoryId] : [])
			}
		>
			{(item) => (
				<ClayTreeView.Item>
					<ClayTreeView.ItemStack
						onClick={(event) => handleSelectionChange(event, item)}
					>
						<ClayIcon symbol={item.icon} />

						{item.name}
					</ClayTreeView.ItemStack>

					<ClayTreeView.Group items={item.children}>
						{(item) => (
							<ClayTreeView.Item
								onClick={(event) =>
									handleSelectionChange(event, item)
								}
							>
								<ClayIcon symbol={item.icon} />

								{item.name}
							</ClayTreeView.Item>
						)}
					</ClayTreeView.Group>
				</ClayTreeView.Item>
			)}
		</ClayTreeView>
	);
};

const OldAssetCategoriesNavigationTreeView = ({
	selectedCategoryId,
	vocabularies,
}) => {
	const handleSelectionChange = ([selectedNodeId]) => {
		if (selectedNodeId && selectedCategoryId !== selectedNodeId) {
			const category = findCategory(selectedNodeId, vocabularies);

			if (category) {
				Liferay.Util.navigate(category.url);
			}
		}
	};

	return (
		<Treeview
			NodeComponent={Treeview.Card}
			initialSelectedNodeIds={
				selectedCategoryId ? [selectedCategoryId] : []
			}
			multiSelection={false}
			nodes={vocabularies}
			onSelectedNodesChange={handleSelectionChange}
		/>
	);
};

export default Liferay.__FF__.enableClayTreeView
	? AssetCategoriesNavigationTreeView
	: OldAssetCategoriesNavigationTreeView;

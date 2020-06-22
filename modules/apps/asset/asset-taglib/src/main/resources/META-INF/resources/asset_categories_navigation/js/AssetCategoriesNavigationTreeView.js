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
			initialSelectedNodeIds={
				selectedCategoryId ? [selectedCategoryId] : []
			}
			multiSelection={false}
			NodeComponent={Treeview.Card}
			nodes={vocabularies}
			onSelectedNodesChange={handleSelectionChange}
		/>
	);
};

export default AssetCategoriesNavigationTreeView;

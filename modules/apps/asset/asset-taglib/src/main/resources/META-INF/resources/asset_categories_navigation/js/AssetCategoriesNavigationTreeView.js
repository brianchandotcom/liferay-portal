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

function buildNodes(vocabularies, categories) {
	return vocabularies.map((vocabulary) => ({
		...vocabulary,
		children: categories.filter((category) => {
			return category.vocabularyId === vocabulary.vocabularyId;
		}),
		id: vocabulary.vocabularyId,
	}));
}

const AssetCategoriesNavigationTreeView = ({categories, vocabularies}) => {
	const nodes = buildNodes(vocabularies, categories);

	const handleSelectionChange = (selectedNodeIds) => {
		const selectedNodeId = [...selectedNodeIds][0];

		if (selectedNodeId) {
			const {url} = nodes[0].children.find(
				(node) => node.id === selectedNodeId
			);

			if (url) {
				Liferay.Util.navigate(url);
			}
		}
	};

	return (
		<Treeview
			multiSelection={false}
			NodeComponent={Treeview.Card}
			nodes={nodes}
			onSelectedNodesChange={handleSelectionChange}
		/>
	);
};

export default AssetCategoriesNavigationTreeView;

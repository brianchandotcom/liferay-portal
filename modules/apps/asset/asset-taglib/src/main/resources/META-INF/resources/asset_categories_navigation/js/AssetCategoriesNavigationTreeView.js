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
		children: categories
			.map((category) => {
				if (category.vocabularyId === vocabulary.vocabularyId) {
					return {
						...category,
					};
				}
			})
			.filter(Boolean),
		id: vocabulary.vocabularyId,
	}));
}

const AssetCategoriesNavigationTreeView = ({categories, vocabularies}) => {
	const nodes = buildNodes(vocabularies, categories);

	return <Treeview NodeComponent={Treeview.Card} nodes={nodes} />;
};

export default AssetCategoriesNavigationTreeView;

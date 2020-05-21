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

import {AssetVocabularyCategoriesSelector} from 'asset-taglib';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

AssetCategoriesSelectorTag.propTypes = {
	categoryIds: PropTypes.array,
	groupIds: PropTypes.array,
	namespace: PropTypes.string,
	portletURL: PropTypes.string,
	selectedCategories: PropTypes.oneOfType([
		PropTypes.string,
		PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string,
				value: PropTypes.oneOfType([
					PropTypes.number,
					PropTypes.string,
				]),
			})
		),
	]),
	vocabularyIds: PropTypes.array,
};

function AssetCategoriesSelectorTag({
	categoryIds,
	groupIds,
	namespace,
	portletURL,
	selectedCategories,
	vocabularyIds,
}) {
	const [selectedItems, setSelectedItems] = useState(
		selectedCategories || []
	);

	return (
		<AssetVocabularyCategoriesSelector
			categoryIds={categoryIds}
			eventName={`${namespace}selectCategory`}
			groupIds={groupIds}
			id={`${namespace}parentCategorySelector`}
			inputName={`${namespace}parentCategoryId`}
			onSelectedItemsChange={setSelectedItems}
			portletURL={portletURL}
			selectedItems={selectedItems}
			singleSelect={true}
			sourceItemsVocabularyIds={vocabularyIds}
		/>
	);
}

export default AssetCategoriesSelectorTag;

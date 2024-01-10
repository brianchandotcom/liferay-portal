/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

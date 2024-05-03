/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import classnames from 'classnames';
import {
	normalizeFriendlyURL,
	openCategorySelectionModal,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function AssetVocabulariesCategoriesFriendlyUrlSelector({
	automaticURL: initialDisabled,
	inputAddon = '',
	portletNamespace,
	selectCategoryURL,
	selectedCategories = [],
}) {
	const [disabled, setDisabled] = useState(initialDisabled);
	const [selectedItems, setSelectedItems] = useState(selectedCategories);

	const inputAddonNodeRef = useRef(
		document.querySelector('.friendly-url .form-text')
	);

	const getUnique = (array, property) => {
		return array
			.map((element) => element[property])
			.map(
				(element, index, initialArray) =>
					initialArray.indexOf(element) === index && index
			)
			.filter((element) => array[element])
			.map((element) => array[element]);
	};

	const handleItemsChange = (items) => {
		const assetCategories = Object.entries(items).map(
			([id, {label, title}]) => ({
				label: label || title,
				value: id,
			})
		);

		const addedItems = getUnique(
			assetCategories.filter(
				(item) =>
					!selectedItems.find(
						(selectedItem) => selectedItem.value === item.value
					)
			),
			'label'
		);

		const removedItems = selectedItems.filter(
			(selectedItem) =>
				!assetCategories.find(
					(item) => item.value === selectedItem.value
				)
		);

		const current = [...selectedItems, ...addedItems].filter(
			(item) =>
				!removedItems.find(
					(removedItem) => removedItem.value === item.value
				)
		);

		setSelectedItems(current);
	};

	const handleSelectButtonClick = () => {
		openCategorySelectionModal({
			onSelect: handleItemsChange,
			portletNamespace,
			selectCategoryURL,
		});
	};

	useEffect(() => {
		if (inputAddonNodeRef.current) {
			inputAddonNodeRef.current.innerText =
				inputAddon +
				selectedItems
					.map(
						(category) => `${normalizeFriendlyURL(category.label)}/`
					)
					.join('');
		}
	}, [inputAddon, inputAddonNodeRef, selectedItems]);

	return (
		<ClayForm.Group>
			<label
				className={classnames({disabled})}
				htmlFor={`${portletNamespace}friendlyURLAssetCategoryIdsMultiSelect`}
			>
				{Liferay.Language.get('add-categories-to-url')}
			</label>

			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayMultiSelect
						disabled={disabled}
						id={`${portletNamespace}friendlyURLAssetCategoryIdsMultiSelect`}
						inputName={
							portletNamespace + 'friendlyURLAssetCategoryIds'
						}
						items={selectedItems}
						onItemsChange={handleItemsChange}
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem shrink>
					<ClayButton
						aria-haspopup="dialog"
						disabled={disabled}
						displayType="secondary"
						onClick={handleSelectButtonClick}
					>
						{Liferay.Language.get('select')}
					</ClayButton>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

AssetVocabulariesCategoriesFriendlyUrlSelector.propTypes = {
	automaticURL: PropTypes.bool,
	inputAddon: PropTypes.string,
	portletNamespace: PropTypes.string.isRequired,
	selectCategoryURL: PropTypes.string.isRequired,
	selectedCategories: PropTypes.array,
};

export default AssetVocabulariesCategoriesFriendlyUrlSelector;

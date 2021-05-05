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

import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect, {itemLabelFilter} from '@clayui/multi-select';
import {usePrevious} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import Lang from '../utils/lang.es';

function AssetVocabulariesCategoriesSelector({
	eventName,
	id,
	isValid = true,
	groupIds = [],
	inputName,
	label,
	onSelectedItemsChange = () => {},
	portletURL,
	required,
	selectedItems = [],
	singleSelect,
	sourceItemsVocabularyIds = [],
	useFallbackInput,
}) {
	const [inputValue, setInputValue] = useState('');

	const [invalidItems, setInvalidItems] = useState([]);

	const {refetch, resource} = useResource({
		fetchOptions: {
			body: new URLSearchParams({
				cmd: JSON.stringify({
					'/assetcategory/search': {
						'-obc': null,
						end: 20,
						groupIds,
						name: `%${inputValue.toLowerCase()}%`,
						start: 0,
						vocabularyIds: sourceItemsVocabularyIds,
					},
				}),
				p_auth: Liferay.authToken,
			}),
			credentials: 'include',
			method: 'POST',
			'x-csrf-token': Liferay.authToken,
		},
		link: `${window.location.origin}${themeDisplay.getPathContext()}
				/api/jsonws/invoke`,
	});

	const previousInputValue = usePrevious(inputValue);

	useEffect(() => {
		if (inputValue && inputValue !== previousInputValue) {
			refetch();
		}
	}, [inputValue, previousInputValue, refetch]);

	const getUnique = (arr, property) => {
		return arr
			.map((element) => element[property])
			.map(
				(element, index, array) =>
					array.indexOf(element) === index && index
			)
			.filter((element) => arr[element])
			.map((element) => arr[element]);
	};

	const handleItemsChange = (items) => {
		const addedItems = getUnique(
			items.filter(
				(item) =>
					!selectedItems.find(
						(selectedItem) => selectedItem.value === item.value
					)
			),
			'label'
		);

		const invalidAddedItems = [];

		const validAddedItems = [];

		addedItems.map((item) => {
			if (
				resource.find(
					(sourceItem) => sourceItem.titleCurrentValue === item.label
				)
			) {
				validAddedItems.push(item);
			}
			else {
				invalidAddedItems.push(item);
			}
		});

		const removedItems = selectedItems.filter(
			(selectedItem) =>
				!items.find((item) => item.value === selectedItem.value)
		);

		const current = [...selectedItems, ...validAddedItems].filter(
			(item) =>
				!removedItems.find(
					(removedItem) => removedItem.value === item.value
				)
		);

		setInvalidItems(invalidAddedItems);

		onSelectedItemsChange(current);
	};

	const handleSelectButtonClick = () => {
		const sub = (str, obj) => str.replace(/\{([^}]+)\}/g, (_, m) => obj[m]);

		const url = sub(decodeURIComponent(portletURL), {
			selectedCategories: selectedItems.map((item) => item.value).join(),
			singleSelect,
			vocabularyIds: sourceItemsVocabularyIds.concat(),
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems) {
					const newValues = Object.keys(selectedItems).reduce(
						(acc, itemKey) => {
							const item = selectedItems[itemKey];
							if (!item.unchecked) {
								acc.push({
									label: item.value,
									value: item.categoryId,
								});
							}

							return acc;
						},
						[]
					);

					onSelectedItemsChange(newValues);
				}
			},
			selectEventName: eventName,
			title: label
				? Liferay.Util.sub(Liferay.Language.get('select-x'), label)
				: Liferay.Language.get('select-categories'),
			url,
		});
	};

	return (
		<div className="field-content">
			<ClayForm.Group
				className={classNames({
					'has-error':
						(invalidItems && invalidItems.length > 0) || !isValid,
				})}
				id={id}
			>
				{useFallbackInput && (
					<input
						name={inputName}
						type="hidden"
						value={selectedItems.map((item) => item.value).join()}
					/>
				)}

				{label && (
					<label>
						{label}

						{required && (
							<span className="inline-item inline-item-after reference-mark">
								<ClayIcon symbol="asterisk" />

								<span className="hide-accessible">
									{Liferay.Language.get('required')}
								</span>
							</span>
						)}
					</label>
				)}

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							inputName={inputName}
							inputValue={inputValue}
							items={selectedItems}
							onChange={setInputValue}
							onItemsChange={handleItemsChange}
							sourceItems={
								resource
									? itemLabelFilter(
											resource.map((category) => {
												return {
													label:
														category.titleCurrentValue,
													value: category.categoryId,
												};
											}),
											inputValue
									  )
									: []
							}
						/>

						{invalidItems && invalidItems.length > 0 && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="info-circle" />

									{Lang.sub(
										Liferay.Language.get(
											`category-x-does-not-exist`
										),
										[
											invalidItems
												.map((item) => item.label)
												.join(','),
										]
									)}
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}

						{!isValid && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem>
									<ClayForm.FeedbackIndicator symbol="info-circle" />
									<span className="ml-2">
										{Liferay.Language.get(
											'this-field-is-required'
										)}
									</span>
								</ClayForm.FeedbackItem>
							</ClayForm.FeedbackGroup>
						)}
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButton
							displayType="secondary"
							onClick={handleSelectButtonClick}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}

AssetVocabulariesCategoriesSelector.propTypes = {
	eventName: PropTypes.string.isRequired,
	groupIds: PropTypes.array.isRequired,
	id: PropTypes.string,
	inputName: PropTypes.string.isRequired,
	label: PropTypes.string,
	onSelectedItemsChange: PropTypes.func,
	portletURL: PropTypes.string.isRequired,
	required: PropTypes.bool,
	selectedItems: PropTypes.array,
	singleSelect: PropTypes.bool,
	sourceItemsVocabularyIds: PropTypes.array,
	useFallbackInput: PropTypes.bool,
};

export default AssetVocabulariesCategoriesSelector;

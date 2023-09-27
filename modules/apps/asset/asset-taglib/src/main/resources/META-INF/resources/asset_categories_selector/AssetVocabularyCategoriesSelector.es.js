/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useResource} from '@clayui/data-provider';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {usePrevious} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {
	createPortletURL,
	fetch,
	openSelectionModal,
	sub,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

function AssetVocabulariesCategoriesSelector({
	eventName,
	id,
	isValid = true,
	formGroupClassName = '',
	groupIds = [],
	inputName,
	label,
	onSelectedItemsChange = () => {},
	portletURL,
	required,
	selectedItems = [],
	showVocabularyLabel = true,
	singleSelect,
	sourceItemsVocabularyIds = [],
	useFallbackInput,
}) {
	const [inputValue, setInputValue] = useState('');

	const [invalidItems, setInvalidItems] = useState([]);

	const [networkStatus, setNetworkStatus] = useState(4);
	const {refetch, resource} = useResource({
		fetch,
		fetchOptions: {
			body: new URLSearchParams({
				cmd: JSON.stringify({
					'/assetcategory/search': {
						'-obc': null,
						'end': 20,
						groupIds,
						'name': inputValue
							? `%${inputValue.toLowerCase()}%`
							: '',
						'start': 0,
						'vocabularyIds': sourceItemsVocabularyIds,
					},
				}),
				p_auth: Liferay.authToken,
			}),
			method: 'POST',
		},
		fetchPolicy: 'cache-first',
		link: `${
			window.location.origin
		}${themeDisplay.getPathContext()}/api/jsonws/invoke`,
		onNetworkStatusChange: setNetworkStatus,
	});

	const previousInputValue = usePrevious(inputValue);

	useEffect(() => {
		if (inputValue !== previousInputValue) {
			refetch();
		}

		// The intended `refetch` method has no reference stabilization, adding
		// this to deps will cause a loop and we only want to invoke the
		// `useEffect` when the value changes.
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [groupIds, inputValue, previousInputValue, sourceItemsVocabularyIds]);

	const selectButtonRef = useRef();

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
		const url = createPortletURL(portletURL, {
			p_p_id: Liferay.PortletKeys.ITEM_SELECTOR,
			selectedCategories: selectedItems.map((item) => item.value).join(),
			selectedCategoryIds: selectedItems.map((item) => item.value).join(),
			singleSelect,
			vocabularyIds: sourceItemsVocabularyIds.concat(),
		});

		openSelectionModal({
			buttonAddLabel: Liferay.Language.get('done'),
			height: '70vh',
			iframeBodyCssClass: '',
			multiple: true,
			onSelect: (selectedItems) => {
				if (selectedItems) {
					const newValues = Object.keys(selectedItems).reduce(
						(acc, itemKey) => {
							const item = selectedItems[itemKey];
							if (!item.unchecked) {
								acc.push({
									label: item.title,
									value: item.classPK,
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
			size: 'md',
			title: label
				? sub(Liferay.Language.get('select-x'), label)
				: Liferay.Language.get('select-categories'),
			url: url.toString(),
		});
	};

	return (
		<div className="field-content">
			<ClayForm.Group
				className={classNames(formGroupClassName, {
					'has-error':
						(invalidItems && !!invalidItems.length) || !isValid,
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
					<label
						className={showVocabularyLabel ? '' : 'sr-only'}
						htmlFor={inputName + '_MultiSelect'}
					>
						{label}

						{required && (
							<span className="inline-item inline-item-after reference-mark">
								<ClayIcon symbol="asterisk" />

								<span className="hide-accessible sr-only">
									{Liferay.Language.get('required')}
								</span>
							</span>
						)}
					</label>
				)}

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							id={inputName + '_MultiSelect'}
							inputName={inputName}
							items={selectedItems}
							loadingState={networkStatus}
							onChange={setInputValue}
							onItemsChange={handleItemsChange}
							sourceItems={
								resource
									? resource.map((category) => {
											return {
												label:
													category.titleCurrentValue,
												value: category.categoryId,
											};
									  })
									: []
							}
							value={inputValue}
						/>

						{invalidItems && !!invalidItems.length && (
							<ClayForm.FeedbackGroup>
								<ClayForm.FeedbackItem aria-live="polite">
									<ClayForm.FeedbackIndicator symbol="info-circle" />

									{sub(
										Liferay.Language.get(
											`category-x-does-not-exist`
										),
										invalidItems
											.map((item) => item.label)
											.join(',')
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
							aria-haspopup="dialog"
							aria-label={sub(
								Liferay.Language.get('select-x'),
								label
							)}
							displayType="secondary"
							onClick={handleSelectButtonClick}
							ref={selectButtonRef}
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

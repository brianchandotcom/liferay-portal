/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput, ClayRadio, ClayRadioGroup} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import ClayMultiSelect from '@clayui/multi-select';
import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {
	normalizeFriendlyURL,
	openCategorySelectionModal,
	sub,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useRef, useState} from 'react';

const DEFAULT_URL = 'default-url';
const CUSTOM_URL = 'custom-url';
function AssetVocabulariesCategoriesFriendlyUrlSelector({
	automaticURL,
	customFriendlyURL = '',
	formGroupClassName = '',
	friendlyUrlInfo,
	friendlyURLSeparatorCompanyConfigurationURL,
	id,
	inputAddon,
	isValid = true,
	label = Liferay.Language.get('add-categories-to-url'),
	portletNamespace,
	required = false,
	selectCategoryURL,
	selectedCategories = [],
	showVocabularyLabel = true,
	useFallbackInput = true,
}) {
	const [customUrlCheckboxValue, setCustomUrlCheckboxValue] = useState(
		automaticURL ? DEFAULT_URL : CUSTOM_URL
	);

	const [friendlyUrlValue, setFriendlyUrlValue] = useState(customFriendlyURL);

	const [selectedItems, setSelectedItems] = useState(selectedCategories);

	const selectButtonRef = useRef();
	const editedRef = useRef(!!customFriendlyURL);

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
		const assetCategories = Object.keys(items).map((key) => {
			return {...items[key], label: items[key].title};
		});

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

	const friendlyUrlAddon = useMemo(
		() =>
			selectedItems
				.map((category) => category.label.replace(/ /g, '-') + '/')
				.join(''),
		[selectedItems]
	);

	useEventListener(
		'change',
		(event) => {
			if (!editedRef.current) {
				setFriendlyUrlValue(normalizeFriendlyURL(event.target.value));
			}
		},
		true,
		document.getElementById(portletNamespace + 'title')
	);

	const handleChange = (event) => {
		editedRef.current = true;
		setFriendlyUrlValue(normalizeFriendlyURL(event.target.value));
	};

	return (
		<div className="field-content">
			<div className="c-mb-3">
				<div className="c-mb-4">
					{Liferay.Language.get(
						'customize-the-url-of-this-blog-entry-to-your-preference-or-stick-to-the-default-setting-based-on-the-entry-title'
					)}

					{friendlyURLSeparatorCompanyConfigurationURL && (
						<ClayLink
							href={friendlyURLSeparatorCompanyConfigurationURL}
						>
							{' ' +
								Liferay.Language.get(
									'check-instance-settings-for-more-url-separator-configurations'
								)}
						</ClayLink>
					)}
				</div>

				<ClayRadioGroup
					name={portletNamespace + 'automaticURL'}
					onChange={setCustomUrlCheckboxValue}
					value={customUrlCheckboxValue}
				>
					<ClayRadio
						label={
							<strong>
								{Liferay.Language.get('use-the-default-url')}
							</strong>
						}
						value={DEFAULT_URL}
					/>

					<ClayRadio
						label={
							<strong>
								{Liferay.Language.get('use-a-customized-url')}
							</strong>
						}
						value={CUSTOM_URL}
					/>
				</ClayRadioGroup>
			</div>

			<ClayForm.Group
				className={classNames(formGroupClassName, {
					'has-error': !isValid,
				})}
				id={id}
			>
				{useFallbackInput && (
					<input
						disabled={!selectedItems.length}
						id={portletNamespace + 'friendlyURLAssetCategoryIds'}
						name={portletNamespace + 'friendlyURLAssetCategoryIds'}
						readOnly={true}
						type="hidden"
						value={selectedItems
							.map((item) => item.categoryId)
							.join()}
					/>
				)}

				{label && (
					<label
						className={showVocabularyLabel ? '' : 'sr-only'}
						htmlFor={portletNamespace + '_MultiSelect'}
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
							disabled={!selectedItems.length}
							id={portletNamespace + '_MultiSelect'}
							inputName={portletNamespace}
							items={selectedItems}
							onItemsChange={handleItemsChange}
							readOnly={true}
						/>

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
							disabled={customUrlCheckboxValue === DEFAULT_URL}
							displayType="secondary"
							onClick={handleSelectButtonClick}
							ref={selectButtonRef}
						>
							{Liferay.Language.get('select')}
						</ClayButton>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor="urlTitle">
					{Liferay.Language.get('friendly-url')}

					<span
						className="lfr-portal-tooltip ml-1 taglib-icon-help"
						title={friendlyUrlInfo}
					>
						<span className="c-inner">
							<ClayIcon symbol="question-circle-full"></ClayIcon>
						</span>
					</span>
				</label>

				<div className="form-text">{inputAddon + friendlyUrlAddon}</div>

				<ClayInput.Group>
					<ClayInput.GroupItem prepend shrink>
						<ClayInput.GroupText>/</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem append>
						<ClayInput
							disabled={customUrlCheckboxValue === DEFAULT_URL}
							id={portletNamespace + 'urlTitle'}
							name={portletNamespace + 'urlTitle'}
							onChange={handleChange}
							placeholder={Liferay.Language.get('friendly-url')}
							type="text"
							value={friendlyUrlValue}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>
		</div>
	);
}

AssetVocabulariesCategoriesFriendlyUrlSelector.propTypes = {
	formGroupClassName: PropTypes.string,
	id: PropTypes.string.isRequired,
	inputAddon: PropTypes.string.isRequired,
	isValid: PropTypes.bool,
	label: PropTypes.string,
	portletNamespace: PropTypes.string.isRequired,
	required: PropTypes.bool,
	selectCategoryURL: PropTypes.string.isRequired,
	selectedCategories: PropTypes.array,
	showVocabularyLabel: PropTypes.bool,
	useFallbackInput: PropTypes.bool,
};

export default AssetVocabulariesCategoriesFriendlyUrlSelector;

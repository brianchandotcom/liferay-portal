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
import classnames from 'classnames';
import {
	normalizeFriendlyURL,
	openCategorySelectionModal,
} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useRef, useState} from 'react';

function AssetVocabulariesCategoriesFriendlyUrlSelector({
	automaticURL: initialAutomaticURL,
	customFriendlyURL = '',
	friendlyURLSeparatorCompanyConfigurationURL,
	friendlyUrlInfo,
	inputAddon,
	portletNamespace,
	selectCategoryURL,
	selectedCategories = [],
}) {
	const [automaticURL, setAutomaticURL] = useState(initialAutomaticURL);

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
		const assetCategories = Object.entries(items).map(([id, {title}]) => ({
			label: title,
			value: id,
		}));

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
					onChange={setAutomaticURL}
					value={automaticURL}
				>
					<ClayRadio
						label={
							<strong>
								{Liferay.Language.get('use-the-default-url')}
							</strong>
						}
						value={true}
					/>

					<ClayRadio
						label={
							<strong>
								{Liferay.Language.get('use-a-customized-url')}
							</strong>
						}
						value={false}
					/>
				</ClayRadioGroup>
			</div>

			<ClayForm.Group>
				<label
					className={classnames({disabled: automaticURL})}
					htmlFor={`${portletNamespace}friendlyURLAssetCategoryIdsMultiSelect`}
				>
					{Liferay.Language.get('add-categories-to-url')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayMultiSelect
							disabled={automaticURL}
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
							disabled={automaticURL}
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
				<label
					className={classnames({disabled: automaticURL})}
					htmlFor="urlTitle"
				>
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
							disabled={automaticURL}
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
	automaticURL: PropTypes.bool,
	customFriendlyURL: PropTypes.string,
	friendlyURLSeparatorCompanyConfigurationURL: PropTypes.string,
	friendlyUrlInfo: PropTypes.string,
	inputAddon: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	selectCategoryURL: PropTypes.string.isRequired,
	selectedCategories: PropTypes.array,
};

export default AssetVocabulariesCategoriesFriendlyUrlSelector;

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

import ClayAlert from '@clayui/alert';
import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {TranslationAdminSelector} from 'frontend-js-components-web';
import {fetch, objectToFormData, openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function AssetVocabularyContextualSidebar({
	assetVocabulary,
	chooseAssetVocabularyProps,
	defaultLanguageId,
	locales,
	localizedNames,
	namespace,
	numberOfCategories: initialNumberOfCategories,
	showAssetVocabularyLevel,
	siteName: initialSiteName,
	useCustomName,
}) {
	const [translations, setTranslations] = useState(localizedNames);
	const [customNameEnabled, setCustomNameEnabled] = useState(useCustomName);
	const [
		assetVocabularyLevelEnabled,
		setAssetVocabularyLevelEnabled,
	] = useState(showAssetVocabularyLevel);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);
	const [selectedVocabulary, setSelectedVocabulary] = useState(
		assetVocabulary
	);
	const [numberOfCategories, setNumberOfCategories] = useState(
		initialNumberOfCategories
	);
	const [siteName, setSiteName] = useState(initialSiteName);

	const {
		assetVocabularySelectorURL,
		eventName,
		getAssetVocabularyDetailsURL,
	} = chooseAssetVocabularyProps;

	const openChooseItemModal = () =>
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					let item = {
						...selectedItem,
					};

					let value;

					if (typeof selectedItem.value === 'string') {
						try {
							value = JSON.parse(selectedItem.value);
						}
						catch (error) {}
					}
					else if (
						selectedItem.value &&
						typeof selectedItem.value === 'object'
					) {
						value = selectedItem.value;
					}

					if (value) {
						delete item.value;
						item = {...value};
					}

					setSelectedVocabulary({
						...item,
						classPK: item.assetVocabularyId,
					});

					const namespacedItem = Liferay.Util.ns(namespace, item);

					fetch(getAssetVocabularyDetailsURL, {
						body: objectToFormData(namespacedItem),
						method: 'POST',
					})
						.then((response) => response.json())
						.then((jsonResponse) => {
							setNumberOfCategories(
								jsonResponse.numberOfCategories
							);
							setSiteName(jsonResponse.siteName);
						})
						.catch(() => {});
				}
			},
			selectEventName: eventName,
			title: Liferay.Language.get('select-vocabulary'),
			url: assetVocabularySelectorURL,
		});

	return (
		<>
			<ClayForm.Group className="align-items-center d-flex mb-2">
				<ClayCheckbox
					checked={assetVocabularyLevelEnabled}
					label={Liferay.Language.get('display-name-as-first-level')}
					onChange={() =>
						setAssetVocabularyLevelEnabled(
							!assetVocabularyLevelEnabled
						)
					}
				/>

				<span
					className="mb-3 ml-1"
					data-tooltip-align="top"
					title={Liferay.Language.get('use-vocabulary-level-help')}
				>
					<ClayIcon symbol="question-circle" />
				</span>
			</ClayForm.Group>

			<ClayForm.Group className="align-items-center d-flex mb-2">
				<ClayCheckbox
					checked={customNameEnabled}
					label={Liferay.Language.get('use-custom-name')}
					onChange={() => setCustomNameEnabled(!customNameEnabled)}
				/>

				<span
					className="mb-3 ml-1"
					data-tooltip-align="top"
					title={Liferay.Language.get('use-custom-name-help')}
				>
					<ClayIcon symbol="question-circle" />
				</span>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={`${namespace}_nameInput`}>
					{Liferay.Language.get('name')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							disabled={!customNameEnabled}
							id={`${namespace}_nameInput`}
							onChange={(event) =>
								setTranslations({
									...translations,
									[selectedLocaleId]: event.target.value,
								})
							}
							type="text"
							value={translations[selectedLocaleId] || ''}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem
						className="site-navigation-language-selector"
						shrink
					>
						<TranslationAdminSelector
							activeLanguageIds={locales.map(
								(locale) => locale.id
							)}
							availableLocales={locales}
							defaultLanguageId={defaultLanguageId}
							onSelectedLanguageIdChange={setSelectedLocaleId}
							selectedLanguageId={selectedLocaleId}
							translations={translations}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayForm.Group
				className={classNames({
					'has-warning': !numberOfCategories,
				})}
			>
				<label htmlFor={`${namespace}_itemInput`}>
					{Liferay.Language.get('item')}
				</label>

				<ClayInput.Group className="site-navigation-item-selector">
					<ClayInput.GroupItem>
						<ClayInput
							className="text-secondary"
							id={`${namespace}_itemInput`}
							onChange={() => {}}
							onClick={openChooseItemModal}
							type="text"
							value={selectedVocabulary.title}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('change-item')}
							displayType="secondary"
							onClick={openChooseItemModal}
							symbol="change"
							title={Liferay.Language.get('change-item')}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				{!numberOfCategories && (
					<>
						<ClayAlert
							className="mt-1"
							displayType="warning"
							title={Liferay.Language.get('no-categories-inside')}
							variant="feedback"
						/>

						<p className="small text-secondary">
							{Liferay.Language.get(
								'vocabularies-without-categories-are-hidden-from-navigation-menus'
							)}
						</p>
					</>
				)}
			</ClayForm.Group>

			<ClayForm.Group className="pt-2">
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('type')}
					</p>

					<div className="d-flex">
						<ClayLabel displayType="secondary">
							{Liferay.Language.get('vocabulary')}
						</ClayLabel>

						<ClayLabel displayType="info">
							{Liferay.Language.get('dynamic')}
						</ClayLabel>
					</div>
				</div>
			</ClayForm.Group>

			<ClayForm.Group>
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('categories')}
					</p>

					<p className="list-group-text">{numberOfCategories}</p>
				</div>
			</ClayForm.Group>

			<ClayForm.Group>
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('site')}
					</p>

					<p className="list-group-text">{siteName}</p>
				</div>
			</ClayForm.Group>

			<FormValues
				localizedNames={translations}
				namespace={namespace}
				selectedVocabulary={selectedVocabulary}
				showAssetVocabularyLevel={assetVocabularyLevelEnabled}
				useCustomName={customNameEnabled}
			/>
		</>
	);
}

AssetVocabularyContextualSidebar.propTypes = {
	assetVocabulary: PropTypes.shape({
		classPK: PropTypes.string,
		groupId: PropTypes.string,
		title: PropTypes.string,
		type: PropTypes.string,
		uuid: PropTypes.string,
	}).isRequired,
	chooseAssetVocabularyProps: PropTypes.shape({
		assetVocabularySelectorURL: PropTypes.string,
		eventName: PropTypes.string,
		getAssetVocabularyDetailsURL: PropTypes.string,
	}).isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	locales: PropTypes.array.isRequired,
	localizedNames: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	numberOfCategories: PropTypes.number.isRequired,
	showAssetVocabularyLevel: PropTypes.bool.isRequired,
	siteName: PropTypes.string.isRequired,
	useCustomName: PropTypes.bool.isRequired,
};

function FormValues({
	localizedNames,
	namespace,
	selectedVocabulary,
	showAssetVocabularyLevel,
	useCustomName,
}) {
	return (
		<>
			<input
				hidden
				name={getFieldName(namespace, 'classPK')}
				readOnly
				value={selectedVocabulary.classPK || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'groupId')}
				readOnly
				value={selectedVocabulary.groupId || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'title')}
				readOnly
				value={selectedVocabulary.title || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'type')}
				readOnly
				value={selectedVocabulary.type || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'localizedNames')}
				readOnly
				value={useCustomName ? JSON.stringify(localizedNames) : '{}'}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'showAssetVocabularyLevel')}
				readOnly
				value={showAssetVocabularyLevel}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'useCustomName')}
				readOnly
				value={useCustomName}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'uuid')}
				readOnly
				value={selectedVocabulary.uuid || ''}
			/>
		</>
	);
}

FormValues.propTypes = {
	localizedNames: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	selectedVocabulary: PropTypes.shape({
		classPK: PropTypes.string,
		groupId: PropTypes.string,
		title: PropTypes.string,
		type: PropTypes.string,
		uuid: PropTypes.string,
	}).isRequired,
	showAssetVocabularyLevel: PropTypes.bool,
	useCustomName: PropTypes.bool,
};

function getFieldName(namespace, fieldName) {
	return `${namespace}TypeSettingsProperties--${fieldName}--`;
}

export default AssetVocabularyContextualSidebar;

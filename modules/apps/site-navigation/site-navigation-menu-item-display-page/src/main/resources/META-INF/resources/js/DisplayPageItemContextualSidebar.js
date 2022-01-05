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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {TranslationAdminSelector} from 'frontend-js-components-web';
import {fetch, objectToFormData, openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function DisplayPageItemContextualSidebar({
	chooseItemProps,
	defaultLanguageId,
	item,
	itemSubtype,
	itemType,
	locales,
	localizedNames,
	namespace,
	useCustomName,
}) {
	const [translations, setTranslations] = useState(localizedNames);
	const [customNameEnabled, setCustomNameEnabled] = useState(useCustomName);
	const [selectedLocaleId, setSelectedLocaleId] = useState(defaultLanguageId);
	const [selectedItem, setSelectedItem] = useState(item);

	const [type, setType] = useState(itemType);
	const [subtype, setSubtype] = useState(itemSubtype);
	const [itemData, setItemData] = useState(item.data);

	const {
		eventName,
		getItemTypeURL,
		itemSelectorURL,
		modalTitle,
	} = chooseItemProps;

	const openChooseItemModal = () =>
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					let infoItem = {
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
						delete infoItem.value;
						infoItem = {...value};
					}

					setSelectedItem(infoItem);

					const namespacedInfoItem = Liferay.Util.ns(
						namespace,
						infoItem
					);

					fetch(getItemTypeURL, {
						body: objectToFormData(namespacedInfoItem),
						method: 'POST',
					})
						.then((response) => response.json())
						.then((jsonResponse) => {
							setType(jsonResponse.itemType);
							setSubtype(jsonResponse.itemSubtype);
							setItemData(jsonResponse.data);
						})
						.catch(() => {});
				}
			},
			selectEventName: eventName,
			title: modalTitle,
			url: itemSelectorURL,
		});

	return (
		<>
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
					<ClayIcon symbol="question-circle-full" />
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

					<ClayInput.GroupItem shrink>
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

			<ClayForm.Group>
				<label htmlFor={`${namespace}_itemInput`}>
					{Liferay.Language.get('item')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							className="text-secondary"
							disabled={!customNameEnabled}
							id={`${namespace}_itemInput`}
							readOnly
							type="text"
							value={selectedItem.title}
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
			</ClayForm.Group>

			<ClayForm.Group>
				<div className="list-group">
					<p className="list-group-title">
						{Liferay.Language.get('type')}
					</p>

					<p className="list-group-text">{type}</p>
				</div>
			</ClayForm.Group>

			{subtype && (
				<ClayForm.Group>
					<div className="list-group">
						<p className="list-group-title">
							{Liferay.Language.get('subtype')}
						</p>

						<p className="list-group-text">{subtype}</p>
					</div>
				</ClayForm.Group>
			)}

			{Boolean(itemData.length) &&
				itemData.map(({title, value}) => (
					<ClayForm.Group key={title}>
						<div className="list-group">
							<p className="list-group-title">{title}</p>

							<p className="list-group-text">{value}</p>
						</div>
					</ClayForm.Group>
				))}

			<FormValues
				localizedNames={translations}
				namespace={namespace}
				selectedItem={selectedItem}
				useCustomName={customNameEnabled}
			/>
		</>
	);
}

DisplayPageItemContextualSidebar.propTypes = {
	chooseItemProps: PropTypes.object.isRequired,
	defaultLanguageId: PropTypes.string.isRequired,
	item: PropTypes.shape({
		classNameId: PropTypes.string,
		classPK: PropTypes.string,
		classTypeId: PropTypes.string,
		data: PropTypes.array,
		title: PropTypes.string,
		type: PropTypes.string,
	}).isRequired,
	itemSubtype: PropTypes.string,
	itemType: PropTypes.string.isRequired,
	locales: PropTypes.array.isRequired,
	localizedNames: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	useCustomName: PropTypes.bool.isRequired,
};

function FormValues({localizedNames, namespace, selectedItem, useCustomName}) {
	return (
		<>
			<input
				hidden
				name={getFieldName(namespace, 'classNameId')}
				readOnly
				value={selectedItem.classNameId || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'classPK')}
				readOnly
				value={selectedItem.classPK || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'classTypeId')}
				readOnly
				value={selectedItem.classTypeId || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'title')}
				readOnly
				value={selectedItem.title || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'type')}
				readOnly
				value={selectedItem.type || ''}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'localizedNames')}
				readOnly
				value={useCustomName ? JSON.stringify(localizedNames) : '{}'}
			/>
			<input
				hidden
				name={getFieldName(namespace, 'useCustomName')}
				readOnly
				value={useCustomName}
			/>
		</>
	);
}

FormValues.propTypes = {
	localizedNames: PropTypes.object.isRequired,
	namespace: PropTypes.string.isRequired,
	selectedItem: PropTypes.shape({
		classNameId: PropTypes.string,
		classPK: PropTypes.string,
		classTypeId: PropTypes.string,
		data: PropTypes.array,
		title: PropTypes.string,
		type: PropTypes.string,
	}).isRequired,
};

function getFieldName(namespace, fieldName) {
	return `${namespace}TypeSettingsProperties--${fieldName}--`;
}

export default DisplayPageItemContextualSidebar;

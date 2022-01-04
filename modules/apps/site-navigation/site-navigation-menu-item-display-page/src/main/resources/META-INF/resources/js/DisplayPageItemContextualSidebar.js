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
import ClayForm, {ClayInput} from '@clayui/form';
import {openSelectionModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

function DisplayPageItemContextualSidebar({
	chooseItemProps,
	item,
	itemSubtype,
	itemType,
	namespace,
}) {
	const [selectedItem, setSelectedItem] = useState(item);

	const {eventName, itemSelectorURL, modalTitle} = chooseItemProps;

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
				}
			},
			selectEventName: eventName,
			title: modalTitle,
			url: itemSelectorURL,
		});

	return (
		<>
			<ClayForm.Group>
				<label htmlFor={`${namespace}_nameInput`}>
					{Liferay.Language.get('name')}
				</label>

				<ClayInput
					disabled
					id={`${namespace}_nameInput`}
					readOnly
					type="text"
					value={selectedItem.title}
				/>
			</ClayForm.Group>

			<ClayForm.Group>
				<label htmlFor={`${namespace}_itemInput`}>
					{Liferay.Language.get('item')}
				</label>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<ClayInput
							className="text-secondary"
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

					<p className="list-group-text">{itemType}</p>
				</div>
			</ClayForm.Group>

			{itemSubtype && (
				<ClayForm.Group>
					<div className="list-group">
						<p className="list-group-title">
							{Liferay.Language.get('subtype')}
						</p>

						<p className="list-group-text">{itemSubtype}</p>
					</div>
				</ClayForm.Group>
			)}

			<FormValues namespace={namespace} selectedItem={selectedItem} />
		</>
	);
}

DisplayPageItemContextualSidebar.propTypes = {
	chooseItemProps: PropTypes.object.isRequired,
	item: PropTypes.shape({
		classNameId: PropTypes.string,
		classPK: PropTypes.string,
		classTypeId: PropTypes.string,
		title: PropTypes.string,
		type: PropTypes.string,
	}).isRequired,
	itemSubtype: PropTypes.string,
	itemType: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
};

function FormValues({namespace, selectedItem}) {
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
		</>
	);
}

FormValues.propTypes = {
	namespace: PropTypes.string.isRequired,
	selectedItem: PropTypes.shape({
		classNameId: PropTypes.string,
		classPK: PropTypes.string,
		classTypeId: PropTypes.string,
		title: PropTypes.string,
		type: PropTypes.string,
	}).isRequired,
};

function getFieldName(namespace, fieldName) {
	return `${namespace}TypeSettingsProperties--${fieldName}--`;
}

export default DisplayPageItemContextualSidebar;

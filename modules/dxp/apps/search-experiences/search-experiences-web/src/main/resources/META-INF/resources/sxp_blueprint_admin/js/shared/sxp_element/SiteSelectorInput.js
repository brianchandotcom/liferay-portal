/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {openSelectionModal} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import removeDuplicates from '../../utils/functions/remove_duplicates';
import toNumber from '../../utils/functions/to_number';
import ThemeContext from '../ThemeContext';

/**
 * SiteSelectorInput uses fetchSitesURL in order for the user to quickly
 * find sites. Click on the 'select' button to open a modal with
 * the full list of sites.
 *
 * The site selector will render when a field has the 'number' type or
 * 'multiselect' type and their name contains 'group_id' or 'group_ids',
 * respectively. For the multiselect, IDs are formatted as an array of
 * stringified IDs for 'View Element JSON'. For the number type, the ID
 * is formatted as a number.
 */
function SiteSelectorInput({
	disabled,
	id,
	label,
	multiple = false,
	name,
	setFieldTouched,
	setFieldValue,
	value,
}) {
	const [inputValue, setInputValue] = useState(
		multiple ? '' : value.label || String(value) || ''
	);

	const {namespace} = useContext(ThemeContext);
	const {selectSitesURL} = useContext(ThemeContext);

	const _getLabel = (item) =>
		`${item.groupdescriptivename} (ID: ${item.groupid})`;

	const _handleBlur = () => {
		setFieldTouched(name);
	};

	const _handleFieldValueChange = (newFieldValue) => {
		if (!multiple) {
			_handleSingleItemChange(newFieldValue);
		}
		else {
			_handleMultiItemsChange(newFieldValue);
		}
	};

	const _handleMultiItemsChange = (items) => {
		setFieldValue(
			name,

			// Saved selections are formatted into {label, value} objects,
			// and the value is validated to be a number. All duplicates
			// from the array are removed.

			removeDuplicates(
				items
					.map((item) => ({
						label: item.label || _getLabel(item),
						value: item.value || String(item.groupid),
					}))
					.filter(({value}) => typeof toNumber(value) === 'number'),
				'value'
			)
		);
	};

	const _handleSingleInputValueChange = (event) => {
		const newValue = event.target.value;

		setInputValue(newValue);

		if (newValue.trim()) {
			const newValueNumber = toNumber(newValue);

			setFieldValue(
				name,
				typeof newValueNumber === 'number' ? newValueNumber : ''
			);
		}
		else {
			setFieldValue(name, '');
		}
	};

	const _handleSingleItemChange = (item) => {
		setFieldValue(name, {
			label: _getLabel(item),
			value: String(item.groupid),
		});
		setInputValue(_getLabel(item));
	};

	return (
		<ClayInput.Group className="item-selector-input" small>
			<ClayInput.GroupItem>
				{multiple ? (
					<ClayMultiSelect
						aria-label={label}
						disabled={disabled}
						id={id}
						items={value || []}
						loadingState={4}
						onBlur={_handleBlur}
						onChange={setInputValue}
						onItemsChange={_handleMultiItemsChange}
						value={inputValue}
					/>
				) : (
					<ClayInput
						aria-label={label}
						disabled={disabled}
						id={id}
						onBlur={_handleBlur}
						onChange={_handleSingleInputValueChange}
						value={inputValue}
					/>
				)}
			</ClayInput.GroupItem>

			<ClayInput.GroupItem shrink>
				<ClayButton
					aria-label={Liferay.Language.get('select')}
					disabled={disabled}
					displayType="secondary"
					onClick={() => {
						openSelectionModal({
							id: `${namespace}selectSite`,
							onSelect: (selectedItem) => {
								if (!selectedItem) {
									return;
								}

								_handleFieldValueChange([selectedItem]);
							},
							selectEventName: `${namespace}selectSite`,
							title: Liferay.Language.get('select-site'),
							url: selectSitesURL,
						});
					}}
					small
					type="button"
				>
					{Liferay.Language.get('select')}
				</ClayButton>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}

export default SiteSelectorInput;

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {openSelectionModal} from 'frontend-js-web';
import React, {useContext, useMemo, useState} from 'react';

import {
	GROUP_EXTERNAL_REFERENCE_CODE,
	GROUP_EXTERNAL_REFERENCE_CODES,
} from '../../utils/constants';
import removeDuplicates from '../../utils/functions/remove_duplicates';
import toNumber from '../../utils/functions/to_number';
import {IDENTIFIER_TYPES} from '../../utils/types/identifierTypes';
import ThemeContext from '../ThemeContext';

/**
 * SiteSelectorInput uses selectSitesURL and openSelectionModal so users can
 * quickly find sites. Click on the 'select' button to open the modal.
 *
 * The site selector renders automatically when:
 *
 * - The field has the 'number' type and its name contains 'group_id'.
 *   For this case, the ID is formatted as a number for 'View Element JSON'.
 *
 * - The field has the 'text' type and its name contains
 *   'group_external_reference_code'.
 *   For this case, the ERC is formatted as a string for 'View Element JSON'.
 *
 * - The field has the 'multiselect' type and its name contains 'group_ids' or
 *  'group_external_reference_codes'.
 *   For this case, the IDs/ERCs are formatted as an array of stringified IDs for
 *   'View Element JSON'.
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

	const identifierType = useMemo(() => {
		return id.includes(GROUP_EXTERNAL_REFERENCE_CODE) ||
			id.includes(GROUP_EXTERNAL_REFERENCE_CODES)
			? IDENTIFIER_TYPES.EXTERNAL_REFERENCE_CODE
			: IDENTIFIER_TYPES.ID;
	}, [id]);

	const locator =
		identifierType === IDENTIFIER_TYPES.EXTERNAL_REFERENCE_CODE
			? {label: 'ERC', value: 'groupexternalreferencecode'}
			: {label: 'ID', value: 'groupid'};

	const _getLabel = (item) =>
		`${item.groupdescriptivename} (${locator.label}: ${item[locator.value]})`;

	const _handleBlur = () => {
		setFieldTouched(name);
	};

	const _handleFieldValueChange = (newFieldValue) => {
		if (!multiple) {
			_handleSingleItemChange(newFieldValue);
		}
		else {
			_handleMultiItemsChange([...value, newFieldValue]);
		}
	};

	const _handleMultiItemsChange = (items) => {
		setFieldValue(
			name,

			// Saved selections are formatted into {label, value} objects,
			// and the value is validated to be a number (if ID). All duplicates
			// from the array are removed.

			removeDuplicates(
				items
					.map((item) => ({
						label: item.label || _getLabel(item),
						value: item.value || String(item[locator.value]),
					}))
					.filter(
						({value}) =>
							identifierType ===
								IDENTIFIER_TYPES.EXTERNAL_REFERENCE_CODE ||
							typeof toNumber(value) === 'number'
					),
				'value'
			)
		);
	};

	const _handleSingleInputValueChange = (event) => {
		let newValue = event.target.value;

		setInputValue(newValue);

		if (newValue.trim()) {
			if (identifierType === IDENTIFIER_TYPES.ID) {
				const newValueNumber = toNumber(newValue);

				newValue =
					typeof newValueNumber === 'number' ? newValueNumber : '';
			}

			setFieldValue(name, newValue);
		}
		else {
			setFieldValue(name, '');
		}
	};

	const _handleSingleItemChange = (item) => {
		if (identifierType === IDENTIFIER_TYPES.EXTERNAL_REFERENCE_CODE) {
			setFieldValue(name, item[locator.value]);
			setInputValue(item[locator.value]);
		}
		else {
			setFieldValue(name, {
				label: _getLabel(item),
				value: String(item[locator.value]),
			});
			setInputValue(_getLabel(item));
		}
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

								_handleFieldValueChange(selectedItem);
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

/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {useId} from 'frontend-js-components-web';
import React from 'react';

import {config} from '../../config';
import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectPublishedChildren from '../../selectors/selectPublishedChildren';
import {Field, PhoneNumberField} from '../../utils/field';
import MaxLengthInput from '../MaxLengthInput';
import PhonePrefixSelector from '../settings/PhonePrefixSelector';

const PREFIX_TYPE_OPTIONS = [
	{
		label: Liferay.Language.get('defined-by-user'),
		value: 'definedByUser',
	},
	{
		label: Liferay.Language.get('fixed'),
		value: 'fixed',
	},
];

export default function getPhoneNumberFieldComponents(): {
	FirstSectionComponent?: React.FC<{disabled?: boolean; field: Field}>;
	SecondSectionComponent?: React.FC<{disabled?: boolean; field: Field}>;
} {
	return {
		SecondSectionComponent,
	};
}

function SecondSectionComponent({
	disabled,
	field,
}: {
	disabled?: boolean;
	field: Field;
}) {
	const phoneNumberField = field as PhoneNumberField;

	const dispatch = useStateDispatch();
	const publishedChildren = useSelector(selectPublishedChildren);

	const isPublished = publishedChildren.has(field.uuid);

	const prefixTypeId = useId();
	const prefixId = useId();

	return (
		<>
			<ClayForm.Group className="mb-3">
				<label htmlFor={prefixTypeId}>
					{Liferay.Language.get('prefix-type')}

					<ClayIcon
						className="ml-1 reference-mark"
						focusable="false"
						role="presentation"
						symbol="asterisk"
					/>
				</label>

				<Picker
					aria-label={Liferay.Language.get('prefix-type')}
					disabled={disabled || isPublished}
					id={prefixTypeId}
					items={PREFIX_TYPE_OPTIONS}
					onSelectionChange={(value: React.Key) => {
						const settings: PhoneNumberField['settings'] = {
							...phoneNumberField.settings,
							prefixType:
								value as PhoneNumberField['settings']['prefixType'],
						};

						if (value === 'fixed' && !settings.fixedCountryCode) {
							settings.fixedCountryCode = config.countries[0]?.a2;
						}
						else if (value === 'definedByUser') {
							delete settings.fixedCountryCode;
						}

						dispatch({
							settings,
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
					selectedKey={phoneNumberField.settings.prefixType}
				>
					{(item) => <Option key={item.value}>{item.label}</Option>}
				</Picker>
			</ClayForm.Group>

			{phoneNumberField.settings.prefixType === 'fixed' && (
				<ClayForm.Group className="mb-3">
					<label htmlFor={prefixId}>
						{Liferay.Language.get('prefix')}

						<ClayIcon
							className="ml-1 reference-mark"
							focusable="false"
							role="presentation"
							symbol="asterisk"
						/>
					</label>

					<PhonePrefixSelector
						disabled={disabled || isPublished}
						id={prefixId}
						onChange={(fixedCountryCode) => {
							dispatch({
								settings: {
									...phoneNumberField.settings,
									fixedCountryCode,
								},
								type: 'update-field',
								uuid: field.uuid,
							});
						}}
						value={phoneNumberField.settings.fixedCountryCode}
					/>
				</ClayForm.Group>
			)}

			<ClayForm.Group className="mb-3">
				<ClayCheckbox
					checked={phoneNumberField.settings.uniqueValues || false}
					disabled={disabled || isPublished}
					label={Liferay.Language.get('accept-unique-values-only')}
					onChange={(event) => {
						dispatch({
							settings: {
								...phoneNumberField.settings,
								uniqueValues: event.target.checked,
							},
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
				/>
			</ClayForm.Group>

			<MaxLengthInput disabled={disabled} field={field} />
		</>
	);
}

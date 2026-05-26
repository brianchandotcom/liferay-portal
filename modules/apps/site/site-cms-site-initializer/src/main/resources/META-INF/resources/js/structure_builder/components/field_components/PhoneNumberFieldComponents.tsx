/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {CountryCodePicker} from '@liferay/object-js-components-web';
import {useId} from 'frontend-js-components-web';
import React from 'react';

import {config} from '../../config';
import {useSelector, useStateDispatch} from '../../contexts/StateContext';
import selectPublishedChildren from '../../selectors/selectPublishedChildren';
import {Field, PhoneNumberField} from '../../utils/field';

const COUNTRY_SOURCE_OPTIONS = [
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
		FirstSectionComponent,
		SecondSectionComponent,
	};
}

function FirstSectionComponent({
	disabled,
	field,
}: {
	disabled?: boolean;
	field: Field;
}) {
	const phoneNumberField = field as PhoneNumberField;

	const dispatch = useStateDispatch();

	const countryId = useId();
	const countrySourceId = useId();

	return (
		<>
			<ClayForm.Group className="mb-3">
				<label htmlFor={countrySourceId}>
					{Liferay.Language.get('country-source')}

					<ClayIcon
						className="ml-1 reference-mark"
						focusable="false"
						role="presentation"
						symbol="asterisk"
					/>
				</label>

				<Picker
					aria-label={Liferay.Language.get('country-source')}
					disabled={disabled}
					id={countrySourceId}
					items={COUNTRY_SOURCE_OPTIONS}
					onSelectionChange={(value: React.Key) => {
						const settings: PhoneNumberField['settings'] = {
							...phoneNumberField.settings,
							countrySource:
								value as PhoneNumberField['settings']['countrySource'],
						};

						if (value === 'fixed' && !settings.country) {
							settings.country = config.countries[0]?.a2;
						}
						else if (value === 'definedByUser') {
							delete settings.country;
						}

						dispatch({
							settings,
							type: 'update-field',
							uuid: field.uuid,
						});
					}}
					selectedKey={phoneNumberField.settings.countrySource}
				>
					{(item) => <Option key={item.value}>{item.label}</Option>}
				</Picker>
			</ClayForm.Group>

			{phoneNumberField.settings.countrySource === 'fixed' && (
				<div className="form-group-autofit">
					<ClayForm.Group className="form-group-item-shrink mb-3">
						<label htmlFor={countryId}>
							{Liferay.Language.get('country')}

							<ClayIcon
								className="ml-1 reference-mark"
								focusable="false"
								role="presentation"
								symbol="asterisk"
							/>
						</label>

						<CountryCodePicker
							aria-label={Liferay.Language.get('country')}
							countries={config.countries}
							disabled={disabled}
							id={countryId}
							onSelectionChange={(country) => {
								dispatch({
									settings: {
										...phoneNumberField.settings,
										country: country.a2,
									},
									type: 'update-field',
									uuid: field.uuid,
								});
							}}
							selectedKey={phoneNumberField.settings.country}
						/>
					</ClayForm.Group>
				</div>
			)}
		</>
	);
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

	return (
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
	);
}

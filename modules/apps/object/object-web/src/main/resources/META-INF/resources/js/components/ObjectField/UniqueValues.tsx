/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {Toggle} from '@liferay/object-js-components-web';
import React from 'react';

import {
	removeFieldSettings,
	updateFieldSettings,
} from '../../utils/fieldSettings';

import './ObjectFieldFormBase.scss';

interface UniqueValuesProps {
	disabled?: boolean;
	objectField: Partial<ObjectField>;
	setValues: (values: Partial<ObjectField>) => void;
}

export function UniqueValues({
	disabled,
	objectField: values,
	setValues,
}: UniqueValuesProps) {
	const isUniqueValue = values.objectFieldSettings?.some(
		(setting) => setting.name === 'uniqueValues'
	);

	const handleUniqueValuesToggle = (toggled: boolean) => {
		if (toggled) {
			setValues({
				objectFieldSettings: updateFieldSettings(
					values.objectFieldSettings,
					{
						name: 'uniqueValues',
						value: true,
					}
				),
			});
		}
		else {
			setValues({
				objectFieldSettings: removeFieldSettings(
					['uniqueValues'],
					values
				),
			});
		}
	};

	return (
		<>
			<ClayForm.Group>
				<Toggle
					disabled={disabled}
					label={Liferay.Language.get('accept-unique-values-only')}
					name="enableUniqueValues"
					onToggle={handleUniqueValuesToggle}
					toggled={isUniqueValue}
					tooltip={Liferay.Language.get(
						'users-will-only-be-able-to-add-unique-values-for-this-field'
					)}
				/>
			</ClayForm.Group>
		</>
	);
}

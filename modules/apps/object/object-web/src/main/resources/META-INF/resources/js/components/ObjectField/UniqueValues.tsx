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
						'users-will-only-be-able-to-add-unique-values-for-that-field'
					)}
				/>
			</ClayForm.Group>
		</>
	);
}

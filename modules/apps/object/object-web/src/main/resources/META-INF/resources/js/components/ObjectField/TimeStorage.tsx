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
import {SingleSelect} from '@liferay/object-js-components-web';
import React from 'react';

import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../../utils/fieldSettings';

import './ObjectFieldFormBase.scss';

interface TimeStorageProps {
	disabled?: boolean;
	objectFieldSettings: ObjectFieldSetting[];
	setValues: (values: Partial<ObjectField>) => void;
}

const timeStorageOptions = [
	{
		label: Liferay.Language.get('convert-to-utc'),
		value: 'convertToUTC',
	},
	{
		label: Liferay.Language.get('use-input-as-entered'),
		value: 'useInputAsEntered',
	},
];

export function TimeStorage({
	disabled,
	objectFieldSettings,
	setValues,
}: TimeStorageProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	const timeStorageOption = timeStorageOptions.find(
		({value}) => value === settings.timeStorage
	);

	const handleValueChange = ({value}: {value: string}) =>
		setValues({
			objectFieldSettings: updateFieldSettings(objectFieldSettings, {
				name: 'timeStorage',
				value,
			}),
		});

	return (
		<ClayForm.Group>
			<SingleSelect
				disabled={disabled}
				label={Liferay.Language.get('time-storage')}
				onChange={handleValueChange}
				options={timeStorageOptions}
				required
				value={timeStorageOption?.label}
			/>
		</ClayForm.Group>
	);
}

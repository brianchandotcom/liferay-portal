/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

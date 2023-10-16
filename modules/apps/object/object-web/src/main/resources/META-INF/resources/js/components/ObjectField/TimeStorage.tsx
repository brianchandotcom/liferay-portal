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
	onSubmit?: (value: Partial<ObjectField>) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
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
	onSubmit,
	setValues,
	values,
}: TimeStorageProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	const timeStorageOption = timeStorageOptions.find(
		({value}) => value === settings.timeStorage
	);

	const handleValueChange = (value: string) => {
		setValues({
			objectFieldSettings: updateFieldSettings(objectFieldSettings, {
				name: 'timeStorage',
				value,
			}),
		});

		if (onSubmit) {
			onSubmit({
				...values,
				objectFieldSettings: updateFieldSettings(objectFieldSettings, {
					name: 'timeStorage',
					value,
				}),
			});
		}
	};

	return (
		<ClayForm.Group>
			<SingleSelect
				disabled={disabled}
				items={timeStorageOptions}
				label={Liferay.Language.get('time-storage')}
				onSelectionChange={(value) =>
					handleValueChange(value as string)
				}
				required
				selectedKey={timeStorageOption?.value}
			/>
		</ClayForm.Group>
	);
}

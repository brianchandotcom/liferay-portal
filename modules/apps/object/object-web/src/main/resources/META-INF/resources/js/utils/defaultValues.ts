/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {removeFieldSettings} from './fieldSettings';

export function getDefaultValueFieldSettings(values: Partial<ObjectField>) {
	const defaultValueTypeSetting = values.objectFieldSettings!.find(
		(setting) => setting.name === 'defaultValueType'
	);

	let defaultValueType;

	if (defaultValueTypeSetting) {
		defaultValueType = defaultValueTypeSetting.value;
	}

	let defaultValue;
	let defaultValueSetting;

	if (defaultValueType) {
		defaultValueSetting = values.objectFieldSettings!.find(
			(setting) => setting.name === 'defaultValue'
		);
		if (defaultValueSetting) {
			defaultValue = defaultValueSetting.value;
		}
	}

	return {defaultValue, defaultValueType};
}

export function getUpdatedDefaultValueFieldSettings(
	values: Partial<ObjectField>,
	newDefaultValue: ObjectFieldSettingValue,
	newDefaultValueType: string
) {
	const newDefaultValueFieldSettings: ObjectFieldSetting[] | null = [
		{
			name: 'defaultValueType',
			value: newDefaultValueType,
		},
		{
			name: 'defaultValue',
			value: newDefaultValue,
		},
	];

	const filteredObjectFieldSettings = removeFieldSettings(
		['defaultValueType', 'defaultValue'],
		values
	);

	return filteredObjectFieldSettings.concat(newDefaultValueFieldSettings);
}

export function getUpdatedDefaultValueType(
	values: Partial<ObjectField>,
	newDefaultValueType: string
) {
	const filteredObjectFieldSettings = removeFieldSettings(
		['defaultValueType', 'defaultValue'],
		values
	);

	return [
		...filteredObjectFieldSettings,
		{
			name: 'defaultValueType',
			value: newDefaultValueType,
		},
	] as ObjectFieldSetting[];
}

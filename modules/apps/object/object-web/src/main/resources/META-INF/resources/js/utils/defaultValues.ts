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

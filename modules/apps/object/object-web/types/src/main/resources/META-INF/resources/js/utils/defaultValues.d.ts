/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function getDefaultValueFieldSettings(
	values: Partial<ObjectField>
): {
	defaultValue: ObjectFieldSettingValue | undefined;
	defaultValueType:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
};
export declare function getUpdatedDefaultValueFieldSettings(
	values: Partial<ObjectField>,
	newDefaultValue: ObjectFieldSettingValue,
	newDefaultValueType: string
): ObjectFieldSetting[];
export declare function getUpdatedDefaultValueType(
	values: Partial<ObjectField>,
	newDefaultValueType: string
): ObjectFieldSetting[];

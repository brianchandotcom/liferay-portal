/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function normalizeFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] | undefined
) {
	const settings: {
		[key in ObjectFieldSettingName]?:
			| LocalizedValue<string>
			| NameValueObject[]
			| ObjectFieldFilterSetting[]
			| ObjectFieldPicklistSetting
			| boolean
			| number
			| string;
	} = {};

	objectFieldSettings?.forEach(({name, value}) => {
		settings[name] = value;
	});

	return settings;
}

export function removeFieldSettings(
	settingsToRemove: ObjectFieldSettingName[],
	values: Partial<ObjectField>
): ObjectFieldSetting[] {
	return values.objectFieldSettings!.filter(
		(setting) => !settingsToRemove.includes(setting.name)
	);
}

export function updateFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] = [],
	{name, value}: ObjectFieldSetting
) {
	let isNewSetting = true;

	const settings = objectFieldSettings.map((setting) => {
		if (setting.name === name) {
			isNewSetting = false;

			return {...setting, value};
		}

		return setting;
	});

	return isNewSetting ? [...settings, {name, value}] : settings;
}

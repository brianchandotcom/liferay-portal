/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare function normalizeFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] | undefined
): {
	function?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	output?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	script?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	maxLength?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	defaultValue?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	prefix?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	filters?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	acceptedFileExtensions?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	defaultValueType?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	fileSource?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	initialValue?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	maximumFileSize?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	objectDefinition1ShortName?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	objectFieldName?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	objectRelationshipName?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	showCounter?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	showFilesInDocumentsAndMedia?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	stateFlow?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	storageDLFolderPath?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	suffix?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	timeStorage?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	uniqueValues?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
	uniqueValuesErrorMessage?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| NameValueObject[]
		| ObjectFieldPicklistSetting
		| ObjectFieldFilterSetting[]
		| undefined;
};
export declare function removeFieldSettings(
	settingsToRemove: ObjectFieldSettingName[],
	values: Partial<ObjectField>
): ObjectFieldSetting[];
export declare function updateFieldSettings(
	objectFieldSettings: ObjectFieldSetting[] | undefined,
	{name, value}: ObjectFieldSetting
): ObjectFieldSetting[];

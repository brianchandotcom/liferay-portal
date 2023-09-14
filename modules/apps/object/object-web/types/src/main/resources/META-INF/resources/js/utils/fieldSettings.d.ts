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
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	defaultValue?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	acceptedFileExtensions?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	defaultValueType?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	fileSource?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	filters?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	maxLength?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	maximumFileSize?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	objectDefinition1ShortName?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	objectFieldName?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	objectRelationshipName?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	output?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	script?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	showCounter?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	showFilesInDocumentsAndMedia?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	stateFlow?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	storageDLFolderPath?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	timeStorage?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	uniqueValues?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	uniqueValuesErrorMessage?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
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

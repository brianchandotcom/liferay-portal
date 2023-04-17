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
	acceptedFileExtensions?:
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
	readOnly?:
		| string
		| number
		| boolean
		| Partial<Liferay.Language.FullyLocalizedValue<string>>
		| ObjectFieldPicklistSetting
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| undefined;
	readOnlyScript?:
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

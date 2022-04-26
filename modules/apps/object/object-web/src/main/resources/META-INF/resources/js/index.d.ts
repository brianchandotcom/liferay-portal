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

type ObjectFieldBusinessType = 'Attachment' | 'LongText' | 'Picklist' | 'Text';
interface ObjectFieldType {
	businessType: ObjectFieldBusinessType;
	dbType: string;
	description: string;
	label: string;
}
interface ObjectField {
	DBType: string;
	businessType: ObjectFieldBusinessType;
	id?: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: Locale | null;
	label: LocalizedValue<string>;
	listTypeDefinitionId: number;
	name?: string;
	objectFieldSettings?: ObjectFieldSetting[];
	relationshipType?: unknown;
	required: boolean;
}

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	value: string | number | boolean;
}

type ObjectFieldSettingName =
	| 'acceptedFileExtensions'
	| 'fileSource'
	| 'maximumFileSize'
	| 'maxLength'
	| 'showCounter'
	| 'showFilesInDocumentsAndMedia'
	| 'storageDLFolderPath';

interface ObjectValidation {
	active: boolean;
	description?: string;
	engine: string;
	errorLabel: LocalizedValue<string>;
	id: number;
	name: any;
	script: string;
}

interface ObjectValidationRuleElement {
	items: ObjectValidationRuleElementItem[];
	label: string;
}

interface ObjectValidationRuleElementItem {
	content?: string;
	label: string;
	tooltip: string;
}

interface ObjectValidationType {
	label: string;
}

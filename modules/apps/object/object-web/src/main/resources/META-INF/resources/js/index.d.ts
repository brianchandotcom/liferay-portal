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

type Locale = Liferay.Language.Locale;
type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

interface ObjectAction {
	active: boolean;
	conditionExpression?: string;
	description?: string;
	id?: number;
	name: string;
	objectActionExecutorKey: string;
	objectActionTriggerKey: string;
	objectDefinitionsRelationshipsURL: string;
	parameters?: ObjectActionParameters;
	predefinedValues: Map<string, string>[];
	script?: string;
}

interface ObjectActionParameters {
	notificationTemplateId?: number;
	objectDefinitionId?: number;
	predefinedValues?: PredefinedValue[];
	relatedObjectEntries?: boolean;
	script?: string;
	secret?: string;
	url?: string;
}

type ObjectFieldBusinessType =
	| 'Attachment'
	| 'LongText'
	| 'Picklist'
	| 'Relationship'
	| 'Text'
	| 'Aggregation'
	| 'LongInteger'
	| 'Integer'
	| 'Decimal'
	| 'PrecisionDecimal'
	| 'Workflow Status'
	| 'Date';
interface ObjectFieldType {
	businessType: ObjectFieldBusinessType;
	dbType: string;
	description: string;
	label: string;
}
interface ObjectField {
	DBType: string;
	businessType: ObjectFieldBusinessType | string;
	defaultValue?: string;
	externalReferenceCode?: string;
	id: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: Locale | null;
	label: LocalizedValue<string>;
	listTypeDefinitionId: number;
	name: string;
	objectFieldSettings?: ObjectFieldSetting[];
	relationshipType?: unknown;
	required: boolean;
	state: boolean;
	system?: boolean;
}

interface ObjectFieldView extends ObjectField {
	checked?: boolean;
	filtered?: boolean;
	hasFilter?: boolean;
	type?: string;
}

interface ObjectDefinition {
	active: boolean;
	dateCreated: string;
	dateModified: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectActions: [];
	objectFields: ObjectField[];
	objectLayouts: [];
	objectViews: [];
	panelCategoryKey: string;
	parameterRequired?: boolean;
	pluralLabel: LocalizedValue<string>;
	portlet: boolean;
	scope: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	storageType?: string;
	system: boolean;
	titleObjectFieldId: number;
}

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	objectFieldId?: number;
	value: string | number | boolean | ObjectFieldFilterSetting[];
}

type ObjectFieldFilterSetting = {
	filterBy?: string;
	filterType?: string;
	json?: {
		[key: string]:
			| string
			| string[]
			| ObjectFieldDateRangeFilterSettings
			| undefined;
	};
};

type ObjectFieldDateRangeFilterSettings = {
	[key: string]: string;
};

interface IItem extends LabelValueObject {
	checked?: boolean;
}

type ObjectFieldSettingName =
	| 'acceptedFileExtensions'
	| 'fileSource'
	| 'maximumFileSize'
	| 'maxLength'
	| 'showCounter'
	| 'showFilesInDocumentsAndMedia'
	| 'storageDLFolderPath'
	| 'relationship'
	| 'function'
	| 'summarizeField'
	| 'filters'
	| 'stateFlow';

interface ObjectValidation {
	active: boolean;
	description?: string;
	engine: string;
	engineLabel: string;
	errorLabel: LocalizedValue<string>;
	ffUseMetadataAsSystemFields?: boolean;
	id: number;
	name: LocalizedValue<string>;
	script: string;
}

interface ObjectRelationship {
	deletionType: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	objectRelationshipId: number;
	parameterObjectFieldId?: number;
	reverse?: boolean;
	type: ObjectRelationshipType;
}

interface ObjectDefinitionsRelationship {
	id: number;
	label: string;
	related?: boolean;
}

type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';

type ObjectValidationType = {
	label: string;
	name: string;
};

interface PickList {
	id: number;
	listTypeEntries: PickListItem[];
	name: string;
}

interface PickListItem {
	id: number;
	key: string;
	name: string;
}

interface PredefinedValue {
	inputAsValue: boolean;
	name: string;
	value: string;
}

interface LabelValueObject {
	label: string;
	value: string;
}

interface ObjectDefinitionsRelationship {
	id: number;
	label: string;
	related?: boolean;
}

interface ObjectState {
	key: string;
	nextObjectStates: string[];
}

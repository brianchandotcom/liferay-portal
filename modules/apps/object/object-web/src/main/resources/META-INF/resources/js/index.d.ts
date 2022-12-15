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

type NotificationTemplate = {
	attachmentObjectFieldIds: string[] | number[];
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	from: string;
	fromName: LocalizedValue<string>;
	id: number;
	name: string;
	objectDefinitionId: number | null;
	subject: LocalizedValue<string>;
	to: LocalizedValue<string>;
	type: 'email' | 'userNotification';
};

interface ObjectAction {
	active: boolean;
	conditionExpression?: string;
	description?: string;
	errorMessage: LocalizedValue<string>;
	id?: number;
	label: LocalizedValue<string>;
	name: string;
	objectActionExecutorKey: string;
	objectActionTriggerKey: string;
	objectDefinitionId?: number;
	objectDefinitionsRelationshipsURL: string;
	parameters: ObjectActionParameters;
	script?: string;
}

interface ObjectActionParameters {
	lineCount?: number;
	notificationTemplateId?: number;
	objectDefinitionExternalReferenceCode?: string;
	objectDefinitionId?: number;
	predefinedValues?: PredefinedValue[];
	relatedObjectEntries?: boolean;
	script?: string;
	secret?: string;
	url?: string;
}

type ObjectFieldBusinessType =
	| 'Aggregation'
	| 'Attachment'
	| 'Date'
	| 'Decimal'
	| 'Formula'
	| 'Integer'
	| 'LongInteger'
	| 'LongText'
	| 'MultiselectPicklist'
	| 'Picklist'
	| 'PrecisionDecimal'
	| 'Relationship'
	| 'Text'
	| 'Workflow Status';
interface ObjectFieldType {
	businessType: ObjectFieldBusinessType;
	dbType: string;
	description: string;
	label: string;
}
interface ObjectField {
	DBType: string;
	businessType: ObjectFieldBusinessType;
	defaultValue?: string;
	externalReferenceCode?: string;
	id: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: Locale | null;
	label: LocalizedValue<string>;
	listTypeDefinitionExternalReferenceCode: string;
	listTypeDefinitionId?: number;
	name: string;
	objectFieldSettings?: ObjectFieldSetting[];
	relationshipId?: number;
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
	enableCategorization: boolean;
	externalReferenceCode: string;
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
	restContextPath: string;
	scope: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	storageType?: string;
	system: boolean;
	titleObjectFieldName: string;
}

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	objectFieldId?: number;
	value:
		| string
		| number
		| boolean
		| NameValueObject[]
		| ObjectFieldFilterSetting[]
		| ObjectFieldPicklistSetting;
}

interface ObjectEntry {
	creator: {
		additionalName: string;
		contentType: string;
		familyName: string;
		givenName: string;
		id: number;
		name: string;
	};
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	name: string;
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	[key: string]: string | number | unknown;
}

type ObjectFieldPicklistSetting = {
	id: number;
	objectStates: ObjectState[];
};

type ObjectFieldFilterSetting = {
	filterBy?: string;
	filterType?: string;
	json:
		| {
				[key: string]:
					| string
					| string[]
					| ObjectFieldDateRangeFilterSettings
					| undefined;
		  }
		| ExcludesFilterOperator
		| IncludesFilterOperator
		| string;
};

type ExcludesFilterOperator = {
	not: {
		in: string[] | number[];
	};
};

type IncludesFilterOperator = {
	in: string[] | number[];
};

type ObjectFieldDateRangeFilterSettings = {
	[key: string]: string;
};

interface IItem extends LabelValueObject {
	checked?: boolean;
}

type TFilterOperators = {
	dateOperators: LabelValueObject[];
	numericOperators: LabelValueObject[];
	picklistOperators: LabelValueObject[];
};

type ObjectFieldSettingName =
	| 'acceptedFileExtensions'
	| 'fileSource'
	| 'filters'
	| 'function'
	| 'maxLength'
	| 'maximumFileSize'
	| 'objectDefinition1ShortName'
	| 'objectFieldName'
	| 'objectRelationshipName'
	| 'output'
	| 'readOnly'
	| 'readOnlyScript'
	| 'script'
	| 'showCounter'
	| 'showFilesInDocumentsAndMedia'
	| 'stateFlow'
	| 'storageDLFolderPath';

interface ObjectValidation {
	active: boolean;
	description?: string;
	engine: string;
	engineLabel: string;
	errorLabel: LocalizedValue<string>;
	id: number;
	lineCount?: number;
	name: LocalizedValue<string>;
	script: string;
}

interface ObjectRelationship {
	deletionType: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	objectRelationshipId: number;
	parameterObjectFieldName?: string;
	reverse: boolean;
	type: ObjectRelationshipType;
}

interface ObjectDefinitionsRelationship {
	externalReferenceCode: string;
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
	actions: Actions;
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeEntries: PickListItem[];
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface PickListItem {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface Actions {
	delete: HTTPMethod;
	get: HTTPMethod;
	permissions: HTTPMethod;
	update: HTTPMethod;
}

interface HTTPMethod {
	href: string;
	method: string;
}

interface PredefinedValue {
	inputAsValue: boolean;
	label: LocalizedValue<string>;
	name: string;
	value: string;
}

interface LabelValueObject {
	label: string;
	value: string;
}

interface LabelNameObject {
	label: string;
	name: string;
}

interface NameValueObject {
	name: string;
	value: string;
}

interface ObjectDefinitionsRelationship {
	id: number;
	label: string;
	related?: boolean;
}

interface ObjectState {
	key: string;
	objectStateTransitions: {key: string}[];
}

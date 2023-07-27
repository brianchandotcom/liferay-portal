/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

interface Folder {
	actions: {};
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
}

type NotificationTemplate = {
	attachmentObjectFieldIds: string[] | number[];
	bcc: string;
	body: LocalizedValue<string>;
	cc: string;
	description: string;
	externalReferenceCode: string;
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
	notificationTemplateExternalReferenceCode?: string;
	notificationTemplateId?: number;
	objectDefinitionExternalReferenceCode?: string;
	objectDefinitionId?: number;
	predefinedValues?: PredefinedValue[];
	relatedObjectEntries?: boolean;
	script?: string;
	secret?: string;
	system?: boolean;
	url?: string;
}

type ObjectFieldBusinessType =
	| 'Aggregation'
	| 'Attachment'
	| 'Date'
	| 'DateTime'
	| 'Decimal'
	| 'Encrypted'
	| 'Formula'
	| 'Integer'
	| 'LongInteger'
	| 'LongText'
	| 'MultiselectPicklist'
	| 'Picklist'
	| 'PrecisionDecimal'
	| 'Relationship'
	| 'RichText'
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
	indexedLanguageId: Liferay.Language.Locale | null;
	label: LocalizedValue<string>;
	listTypeDefinitionExternalReferenceCode: string;
	listTypeDefinitionId?: number;
	localized: boolean;
	name: string;
	objectFieldSettings?: ObjectFieldSetting[];
	readOnly: ReadOnlyFieldValue;
	readOnlyConditionExpression: string;
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
	accountEntryRestricted: boolean;
	accountEntryRestrictedObjectFieldId: string;
	accountEntryRestrictedObjectFieldName: string;
	active: boolean;
	dateCreated: string;
	dateModified: string;
	dbTableName?: string;
	defaultLanguageId: Liferay.Language.Locale;
	enableCategorization: boolean;
	enableComments: boolean;
	enableLocalization: boolean;
	enableObjectEntryHistory: boolean;
	externalReferenceCode: string;
	id: number;
	label: LocalizedValue<string>;
	modifiable?: boolean;
	name: string;
	objectActions: [];
	objectFields: ObjectField[];
	objectFolderExternalReferenceCode: string;
	objectLayouts: [];
	objectRelationships: [];
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
	titleObjectFieldId: number | string;
	titleObjectFieldName: string;
}

type ReadOnlyFieldValue = '' | 'conditional' | 'false' | 'true';

type ObjectFieldSettingValue =
	| LocalizedValue<string>
	| NameValueObject[]
	| ObjectFieldFilterSetting[]
	| ObjectFieldPicklistSetting
	| boolean
	| number
	| string;

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	objectFieldId?: number;
	value: ObjectFieldSettingValue;
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
	| 'defaultValue'
	| 'defaultValueType'
	| 'fileSource'
	| 'filters'
	| 'function'
	| 'maxLength'
	| 'maximumFileSize'
	| 'objectDefinition1ShortName'
	| 'objectFieldName'
	| 'objectRelationshipName'
	| 'output'
	| 'script'
	| 'showCounter'
	| 'showFilesInDocumentsAndMedia'
	| 'stateFlow'
	| 'storageDLFolderPath'
	| 'timeStorage'
	| 'uniqueValues'
	| 'uniqueValuesErrorMessage';

interface ObjectValidation {
	active: boolean;
	description?: string;
	engine: string;
	engineLabel: string;
	errorLabel: LocalizedValue<string>;
	id: number;
	lineCount?: number;
	name: LocalizedValue<string>;
	objectValidationRuleSettings?: {
		name: 'objectFieldExternalReferenceCode';
		value: string;
	}[];
	outputType?: string;
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

interface AddObjectEntryDefinitions {
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
	businessType: ObjectFieldBusinessType;
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

interface LabelTypeObject {
	label: string;
	type: string;
}

interface NameValueObject {
	name: string;
	value: string;
}

interface AddObjectEntryDefinitions {
	id: number;
	label: string;
	related?: boolean;
	system?: boolean;
}

interface ObjectState {
	key: string;
	objectStateTransitions: {key: string}[];
}

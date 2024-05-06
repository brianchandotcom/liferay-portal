/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface Actions {
	create?: HTTPMethod;
	delete?: HTTPMethod;
	get?: HTTPMethod;
	permissions?: HTTPMethod;
	update?: HTTPMethod;
}

interface CreateObjectField {
	listTypeDefinitionName?: string;
	mandatory: boolean;
	objectDefinitionName: string;
	objectFieldBusinessType: string;
	objectFieldLabel: string;
}

interface DataObject {
	[K: string]: unknown;
}

type ExcludesFilterOperator = {
	not: {
		in: string[] | number[];
	};
};

interface HTTPMethod {
	href: string;
	method: string;
}

type IncludesFilterOperator = {
	in: string[] | number[];
};

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

interface NameValueObject {
	name: string;
	value: string;
}

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
	system: boolean;
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

interface PredefinedValue {
	businessType: ObjectFieldBusinessTypeName;
	inputAsValue: boolean;
	label: LocalizedValue<string>;
	name: string;
	value: string;
}
interface ObjectDefinition {
	accountEntryRestricted: boolean;
	accountEntryRestrictedObjectFieldId: string;
	accountEntryRestrictedObjectFieldName: string;
	actions: Actions;
	active: boolean;
	dateCreated: string;
	dateModified: string;
	dbTableName?: string;
	defaultLanguageId: Liferay.Language.Locale;
	enableCategorization: boolean;
	enableComments: boolean;
	enableLocalization: boolean;
	enableObjectEntryDraft: boolean;
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
	objectRelationships: ObjectRelationship[];
	objectViews: [];
	panelCategoryKey: string;
	parameterRequired?: boolean;
	pluralLabel: LocalizedValue<string>;
	portlet: boolean;
	restContextPath: string;
	rootObjectDefinitionExternalReferenceCode: string;
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

interface ObjectDefinitions {
	actions: Actions;
	items: ObjectDefinition[];
}

interface ObjectDefinitionNodeData
	extends Omit<ObjectDefinition, 'objectFields'> {
	hasObjectDefinitionDeleteResourcePermission: boolean;
	hasObjectDefinitionManagePermissionsResourcePermission: boolean;
	hasObjectDefinitionUpdateResourcePermission: boolean;
	hasObjectDefinitionViewResourcePermission: boolean;
	linkedObjectDefinition: boolean;
	objectFields: ObjectFieldNodeRow[];
	selected: boolean;
	showAllObjectFields: boolean;
}

interface ObjectField {
	DBType: string;
	businessType: ObjectFieldBusinessTypeName;
	defaultValue?: string;
	externalReferenceCode: string;
	id?: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId?: Liferay.Language.Locale | string;
	label: LocalizedValue<string>;
	listTypeDefinitionExternalReferenceCode?: string;
	listTypeDefinitionId?: number;
	localized: boolean;
	name: string;
	objectFieldSettings?: ObjectFieldSetting[];
	readOnly: ReadOnlyFieldValue;
	readOnlyConditionExpression?: string;
	relationshipId?: number;
	relationshipType?: unknown;
	required: boolean;
	state: boolean;
	system?: boolean;
}

type ObjectFieldBusinessTypeName =
	| 'Aggregation'
	| 'Attachment'
	| 'AutoIncrement'
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

type ObjectFieldDateRangeFilterSettings = {
	[key: string]: string;
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

interface ObjectFieldNodeRow extends Partial<ObjectField> {
	primaryKey: boolean;
	required: boolean;
	selected: boolean;
}

type ObjectFieldPicklistSetting = {
	id: number;
	objectStates: ObjectState[];
};

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	objectFieldId?: number;
	value: ObjectFieldSettingValue;
}

type ObjectFieldSettingName =
	| 'acceptedFileExtensions'
	| 'defaultValue'
	| 'defaultValueType'
	| 'fileSource'
	| 'filters'
	| 'function'
	| 'initialValue'
	| 'maxLength'
	| 'maximumFileSize'
	| 'objectDefinition1ShortName'
	| 'objectFieldName'
	| 'objectRelationshipName'
	| 'output'
	| 'prefix'
	| 'script'
	| 'showCounter'
	| 'showFilesInDocumentsAndMedia'
	| 'stateFlow'
	| 'storageDLFolderPath'
	| 'suffix'
	| 'timeStorage'
	| 'uniqueValues'
	| 'uniqueValuesErrorMessage';

type ObjectFieldSettingValue =
	| LocalizedValue<string>
	| NameValueObject[]
	| ObjectFieldFilterSetting[]
	| ObjectFieldPicklistSetting
	| boolean
	| number
	| string;

interface ObjectFieldBusinessType {
	businessType: ObjectFieldBusinessTypeName;
	dbType: string;
	description: string;
	label: string;
}

interface ObjectFolder {
	actions: Actions;
	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitions?: ObjectDefinitionNodeData[];
	objectFolderItems: ObjectFolderItem[];
}

interface ObjectFolderItem {
	linkedObjectDefinition: boolean;
	objectDefinitionExternalReferenceCode: string;
	positionX: number;
	positionY: number;
}

interface ObjectRelationship {
	deletionType: string;
	edge?: boolean;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	parameterObjectFieldName?: string;
	reverse: boolean;
	system?: boolean;
	type: ObjectRelationshipType;
}

type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';

interface ObjectState {
	key: string;
	objectStateTransitions: {key: string}[];
}

interface ObjectValidation {
	active: boolean;
	description?: string;
	engine: string;
	engineLabel: string;
	errorLabel: LocalizedValue<string>;
	id?: number;
	lineCount?: number;
	name: LocalizedValue<string>;
	objectValidationRuleSettings?: ObjectValidationRuleSetting[];
	outputType?: string;
	script: string;
	system?: boolean;
}

interface ObjectValidationRuleSetting {
	name:
		| 'compositeKeyObjectFieldExternalReferenceCode'
		| 'outputObjectFieldExternalReferenceCode';
	value: string;
}

interface PickListItem {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}

interface PickList {
	actions: Actions;
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeEntries: PickListItem[];
	name: string;
	name_i18n: LocalizedValue<string>;
}

type ReadOnlyFieldValue = '' | 'conditional' | 'false' | 'true';

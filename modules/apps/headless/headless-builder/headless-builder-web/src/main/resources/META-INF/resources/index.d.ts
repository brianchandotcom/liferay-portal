/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface APIApplicationItem extends BaseItem {
	applicationStatus: ApplicationStatus;
	baseURL: string;
	title: string;
	version: string;
}

interface APIEndpointItem extends BaseItem {
	httpMethod: APIListType;
	path: string;
	r_apiApplicationToAPIEndpoints_c_apiApplicationId: string;
	scope: APIListType;
}

interface APIListType {
	key: string;
	name?: string;
}

interface APIProperty {
	description?: string;
	name: string;
	objectFieldERC: string;
	objectRelationshipNames?: string;
}

interface APISchemaItem extends BaseItem {
	apiSchemaToAPIProperties?: APIProperty[];
	mainObjectDefinitionERC: string;
	name: string;
	r_apiApplicationToAPISchemas_c_apiApplicationId?: string;
}

interface APISchemaPropertyItem {
	actions: Actions;
	apiSchemaToAPIPropertiesERC: string;
	dateCreated: string;
	dateModified: string;
	description?: string;
	externalReferenceCode: string;
	id: number;
	keywords: string[];
	name: string;
	objectFieldERC: string;
	objectFieldId: number;
	objectRelationshipNames: string;
	r_apiSchemaToAPIProperties_c_apiSchemaERC: string;
	r_apiSchemaToAPIProperties_c_apiSchemaId: number;
}

interface APISchemaUIData {
	description: string;
	mainObjectDefinitionERC: string;
	name: string;
	schemaProperties?: TreeViewItemData[];
}

interface APIURLPaths {
	applications: string;
	endpoints: string;
	filters: string;
	properties: string;
	schemas: string;
	sorts: string;
}

interface Action {
	href: string;
	method: string;
}

interface Actions {
	delete: Action;
	get: Action;
	permissions: Action;
	update: Action;
}

interface AddedObjectDefinition extends ObjectDefinition {
	aggregatedObjectRelationshipNames?: string;
}

interface ApplicationStatus {
	key: ApplicationStatusKeys;
	name?: 'Published' | 'Unpublished';
}

interface BaseItem {
	actions: Actions;
	createDate: string;
	creator: string;
	dateCreated: string;
	dateModified: string;
	description: string;
	externalReferenceCode: string;
	id: number;
	keywords: string[];
	modifiedDate: string;
	scopeKey: string;
	status: string;
}

interface FDSActionData {
	id: string;
}

interface FDSItem<T> {
	action: {data: FDSActionData; id: string};
	id: number;
	itemData: T;
	loadData: voidReturn;
	value: string;
}

interface FetchedData {
	apiApplication?: APIApplicationItem;
	apiEndpoint?: APIEndpointItem;
	apiSchema?: APISchemaItem;
}

interface FetchedListType {
	listTypeEntries: APIListType[];
}

interface ManagementButton {
	onClick: voidReturn;
	visible: boolean;
}

interface ManagementButtonsProps {
	cancel: ManagementButton;
	publish: ManagementButton;
	save: ManagementButton;
}

interface NameValueObject {
	name: string;
	value: string;
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
	objectLayouts: [];
	objectRelationshipName?: string;
	objectRelationships: ObjectRelationship[];
	objectViews: [];
	panelCategoryKey: string;
	parameterRequired?: boolean;
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

interface ObjectDefinitionsRelationshipTree {
	definition: AddedObjectDefinition;
	relatedDefinitions?: ObjectDefinitionsRelationshipTree[];
}

interface ObjectField {
	DBType: string;
	businessType: ObjectFieldBusinessType;
	defaultValue?: string;
	externalReferenceCode: string;
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

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	objectFieldId?: number;
	value: ObjectFieldSettingValue;
}

interface ObjectRelationship {
	name: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId2: number;
}

interface ObjectState {
	key: string;
	objectStateTransitions: {key: string}[];
}

interface SelectOption {
	label: string;
	value: string;
}

interface TreeViewItemData {
	businessType: ObjectFieldBusinessType;
	children?: TreeViewItemData[];
	description?: string;
	id?: number;
	name: string;
	objectDefinitionName: string;
	objectFieldERC: string;
	objectFieldId: number;
	objectFieldName: string;
	objectRelationshipNames?: string;
	r_apiSchemaToAPIProperties_c_apiSchemaId: number;
	type: string;
}

type APIApplicationUIData = Pick<
	APIApplicationItem,
	'baseURL' | 'description' | 'title'
>;

type APIEndpointUIData = Pick<
	APIEndpointItem,
	'description' | 'path' | 'scope'
>;

type ActiveNav = 'details' | 'endpoints' | 'schemas';

type ApplicationDataError = {
	baseURL: boolean;
	title: boolean;
};

type ApplicationStatusKeys = 'published' | 'unpublished';

type EndpointDataError = {
	description: boolean;
	path: boolean;
	scope: boolean;
};

type ExcludesFilterOperator = {
	not: {
		in: string[] | number[];
	};
};

type FetchedSchemaData = {
	apiSchema?: APISchemaItem;
	objectDefinitions?: ObjectDefinitionsRelationshipTree;
	schemaProperties?: APISchemaPropertyItem[];
};

type IncludesFilterOperator = {
	in: string[] | number[];
};

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

type MainNav = 'list' | {edit: number};

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

type ObjectFieldPicklistSetting = {
	id: number;
	objectStates: ObjectState[];
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

type ObjectFieldSettingValue =
	| LocalizedValue<string>
	| NameValueObject[]
	| ObjectFieldFilterSetting[]
	| ObjectFieldPicklistSetting
	| boolean
	| number
	| string;

type ReadOnlyFieldValue = '' | 'conditional' | 'false' | 'true';

type SchemaDataError = {
	description: boolean;
	mainObjectDefinitionERC: boolean;
	name: boolean;
};

type voidReturn = () => void;

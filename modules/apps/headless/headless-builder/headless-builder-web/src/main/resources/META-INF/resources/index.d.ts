/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type ActiveNav = 'details' | 'endpoints' | 'schemas';

interface APIURLPaths {
	applications: string;
	endpoints: string;
	filters: string;
	properties: string;
	schemas: string;
	sorts: string;
}

interface HTTPMethod {
	href: string;
	method: string;
}

interface Actions {
	delete: HTTPMethod;
	get: HTTPMethod;
	permissions: HTTPMethod;
	update: HTTPMethod;
}

type voidReturn = () => void;

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
	apiSchema?: APIApplicationSchemaItem;
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

type ApplicationStatusKeys = 'published' | 'unpublished';

interface ApplicationStatus {
	key: ApplicationStatusKeys;
	name?: 'Published' | 'Unpublished';
}
interface APIApplicationItem extends BaseItem {
	applicationStatus: ApplicationStatus;
	baseURL: string;
	title: string;
	version: string;
}

type APIApplicationUIData = Pick<
	APIApplicationItem,
	'baseURL' | 'description' | 'title'
>;

interface APIEndpointItem extends BaseItem {
	name: string;
	path: string;
}

interface APIApplicationSchemaItem extends BaseItem {
	mainObjectDefinitionERC: string;
	name: string;
	r_apiApplicationToAPISchemas_c_apiApplicationId: string;
}

type APISchemaUIData = Pick<
	APIApplicationSchemaItem,
	'description' | 'name' | 'mainObjectDefinitionERC'
>;

interface ManagementButton {
	onClick: voidReturn;
	visible: boolean;
}

interface ManagementButtonsProps {
	cancel: ManagementButton;
	publish: ManagementButton;
	save: ManagementButton;
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
	modifiable?: boolean;
	name: string;
	objectActions: [];
	objectLayouts: [];
	objectRelationships: [];
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

type MainSchemaNav = 'list' | {edit: number};

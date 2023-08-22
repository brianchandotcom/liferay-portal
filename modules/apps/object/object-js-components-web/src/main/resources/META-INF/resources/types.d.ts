/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;
interface ObjectDefinition {
	actions: DefinitionActions;
	active: boolean;
	dateCreated: string;
	dateModified: string;
	defaultLanguageId: string;
	externalReferenceCode: string;
	id: number;
	label: LocalizedValue<string>;
	modifiable?: boolean;
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
	titleObjectFieldName: string;
}

type DefinitionAction = {
	href: string;
	method: string;
};

type DefinitionActions = {
	delete: DefinitionAction;
	get: DefinitionAction;
	permissions: DefinitionAction;
	update: DefinitionAction;
};

interface ObjectField {
	DBType: string;
	businessType: ObjectFieldBusinessType | string;
	defaultValue?: string;
	externalReferenceCode?: string;
	id: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: Liferay.Language.Locale | null;
	label: LocalizedValue<string>;
	listTypeDefinitionId: number;
	localized: boolean;
	name: string;
	objectFieldSettings?: ObjectFieldSetting[];
	relationshipType?: unknown;
	required: boolean;
	state: boolean;
	system?: boolean;
}
type LabelValueObject = {label: string; value: string};
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

interface ObjectFieldSetting {
	name: ObjectFieldSettingName;
	objectFieldId?: number;
	value: string | number | boolean | ObjectFieldFilterSetting[];
}

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
		| string;
};

type ObjectFieldDateRangeFilterSettings = {
	[key: string]: string;
};

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

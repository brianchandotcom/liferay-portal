/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectField,
	ObjectRelationship,
} from '../../../apps/object/object-admin-rest-client-js/src/main/resources/META-INF/resources/node/api';

interface Actions {
	create?: HTTPMethod;
	delete?: HTTPMethod;
	get?: HTTPMethod;
	permissions?: HTTPMethod;
	update?: HTTPMethod;
}

interface CreateObjectField {
	attachmentSource?: string;
	listTypeDefinitionName?: string;
	mandatory?: boolean;
	objectDefinitionLabel?: string;

	objectDefinitionNodes: unknown;
	objectFieldBusinessType: ObjectField.BusinessTypeEnum;
	objectFieldLabel: string;
}

interface CreateObjectRelationship {
	manyRecordsOf: string;
	objectDefinitionLabel: string;
	objectDefinitionNodes: unknown;
	objectRelationshipLabel: string;
	objectRelationshipType: ObjectRelationship.TypeEnum;
}

interface DataObject {
	[K: string]: unknown;
}

type Direction = 'bottom' | 'left' | 'right' | 'top';

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

interface LabelNameObject {
	label: string;
	name: string;
}

interface ListTypeDefinition {
	actions: Actions;
	externalReferenceCode: string;
	id: number;
	key: string;
	listTypeEntries: ListTypeEntry[];
	name: string;
	name_i18n: LocalizedValue<string>;
	system: boolean;
}

interface ListTypeDefinitions {
	actions: Actions;
	items: ListTypeDefinition[];
}

interface ListTypeEntry {
	externalReferenceCode: string;
	id: number;
	key: string;
	name: string;
	name_i18n: LocalizedValue<string>;
}

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

interface NameValueObject {
	name: string;
	value: string;
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

interface ObjectEntry {

	// replace with model generated with object-rest-impl

	dateCreated: string;
	dateModified: string;
	externalReferenceCode: string;
	id: number;
	keywords: [];
	status: {
		code: number;
		label: string;
		label_i18n: string;
	};
	textField: string;
	[key: string]: any;
}

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

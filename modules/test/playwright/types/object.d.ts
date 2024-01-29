/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface DataObject {
	[K: string]: unknown;
}

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

interface ObjectRelationship {
	deletionType: string;
	edge: boolean;
	id: number;
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionExternalReferenceCode1: string;
	objectDefinitionExternalReferenceCode2: string;
	objectDefinitionId1: number;
	objectDefinitionId2: number;
	readonly objectDefinitionName2: string;
	parameterObjectFieldId?: number;
	reverse: boolean;
	type: ObjectRelationshipType;
}

type ObjectRelationshipType = 'manyToMany' | 'oneToMany' | 'oneToOne';

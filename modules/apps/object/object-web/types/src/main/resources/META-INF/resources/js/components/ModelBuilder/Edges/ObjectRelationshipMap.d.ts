/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export declare class ObjectRelationshipMap extends Map<
	string,
	ObjectRelationship[]
> {
	private _delete;
	private _getValue;
	private _setValue;
	deleteByExternalReferenceCodes(
		objectDefinitionExternalReferenceCode1: string,
		objectDefinitionExternalReferenceCode2: string
	): boolean;
	getKey(
		objectDefinitionExternalReferenceCode1: string,
		objectDefinitionExternalReferenceCode2: string
	): string;
	getValueByExternalReferenceCodes(
		objectDefinitionExternalReferenceCode1: string,
		objectDefinitionExternalReferenceCode2: string
	): ObjectRelationship[] | undefined;
	getValueByKey(key: string): ObjectRelationship[] | undefined;
	setValue(objectRelationship: ObjectRelationship): this;
}

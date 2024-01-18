/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export class ObjectRelationshipMap extends Map<string, ObjectRelationship[]> {
	private _delete(key: string) {
		return this.delete(key);
	}

	private _getValue(key: string): ObjectRelationship[] | undefined {
		return this.get(key);
	}

	private _setValue(key: string, objectRelationship: ObjectRelationship[]) {
		return this.set(key, objectRelationship);
	}

	public deleteByExternalReferenceCodes(
		objectDefinitionExternalReferenceCode1: string,
		objectDefinitionExternalReferenceCode2: string
	) {
		const key = this.getKey(
			objectDefinitionExternalReferenceCode1,
			objectDefinitionExternalReferenceCode2
		);

		return this._delete(key);
	}

	public getKey(
		objectDefinitionExternalReferenceCode1: string,
		objectDefinitionExternalReferenceCode2: string
	): string {
		return `${objectDefinitionExternalReferenceCode1}#${objectDefinitionExternalReferenceCode2}`;
	}

	public getValueByExternalReferenceCodes(
		objectDefinitionExternalReferenceCode1: string,
		objectDefinitionExternalReferenceCode2: string
	): ObjectRelationship[] | undefined {
		const key = this.getKey(
			objectDefinitionExternalReferenceCode1,
			objectDefinitionExternalReferenceCode2
		);

		return this._getValue(key);
	}

	public getValueByKey(key: string): ObjectRelationship[] | undefined {
		return this._getValue(key);
	}

	public setValue(objectRelationship: ObjectRelationship) {
		const key = this.getKey(
			objectRelationship.objectDefinitionExternalReferenceCode1,
			objectRelationship.objectDefinitionExternalReferenceCode2
		);
		const value = this.getValueByKey(key);

		if (value) {
			return this._setValue(key, [...value, objectRelationship]);
		}
		else {
			return this._setValue(key, [objectRelationship]);
		}
	}
}

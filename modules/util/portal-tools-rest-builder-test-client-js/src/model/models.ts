/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

	import {ChildTestEntity1} from './childTestEntity1';
	import {ChildTestEntity2} from './childTestEntity2';
	import {ChildTestEntity3} from './childTestEntity3';
	import {CompanyTestEntity} from './companyTestEntity';
	import {EntityModelResourceTestEntity1} from './entityModelResourceTestEntity1';
	import {EntityModelResourceTestEntity2} from './entityModelResourceTestEntity2';
	import {EnumTestEntity} from './enumTestEntity';
	import {Facet} from './facet';
	import {FacetValue} from './facetValue';
	import {Filter} from './filter';
	import {JSONMapAttributeTestEntity} from './jSONMapAttributeTestEntity';
	import {NestedArrayItemsTestEntity} from './nestedArrayItemsTestEntity';
	import {NestedTestEntity} from './nestedTestEntity';
	import {PageCompanyTestEntity} from './pageCompanyTestEntity';
	import {PageEntityModelResourceTestEntity1} from './pageEntityModelResourceTestEntity1';
	import {PageFilter} from './pageFilter';
	import {PagePermission} from './pagePermission';
	import {PageSchema} from './pageSchema';
	import {PageSiteTestEntity} from './pageSiteTestEntity';
	import {PageSort} from './pageSort';
	import {PageTestEntity} from './pageTestEntity';
	import {Permission} from './permission';
	import {Schema} from './schema';
	import {SiteTestEntity} from './siteTestEntity';
	import {Sort} from './sort';
	import {TestEntity} from './testEntity';
	import {TestEntityAddress} from './testEntityAddress';
	import {UnreferencedTestEntity} from './unreferencedTestEntity';

	export * from './childTestEntity1';
	export * from './childTestEntity2';
	export * from './childTestEntity3';
	export * from './companyTestEntity';
	export * from './entityModelResourceTestEntity1';
	export * from './entityModelResourceTestEntity2';
	export * from './enumTestEntity';
	export * from './facet';
	export * from './facetValue';
	export * from './filter';
	export * from './jSONMapAttributeTestEntity';
	export * from './nestedArrayItemsTestEntity';
	export * from './nestedTestEntity';
	export * from './pageCompanyTestEntity';
	export * from './pageEntityModelResourceTestEntity1';
	export * from './pageFilter';
	export * from './pagePermission';
	export * from './pageSchema';
	export * from './pageSiteTestEntity';
	export * from './pageSort';
	export * from './pageTestEntity';
	export * from './permission';
	export * from './schema';
	export * from './siteTestEntity';
	export * from './sort';
	export * from './testEntity';
	export * from './testEntityAddress';
	export * from './unreferencedTestEntity';

/**
 * @author Alejandro Tardín
 * @generated
 */

/* tslint:disable:no-unused-variable */
const primitives = [
	'string',
	'boolean',
	'double',
	'integer',
	'long',
	'float',
	'number',
	'any',
];

const typeMap: {[index: string]: any} = {
	ChildTestEntity1,
	ChildTestEntity2,
	ChildTestEntity3,
	CompanyTestEntity,
	EntityModelResourceTestEntity1,
	EntityModelResourceTestEntity2,
	EnumTestEntity,
	Facet,
	FacetValue,
	Filter,
	JSONMapAttributeTestEntity,
	NestedArrayItemsTestEntity,
	NestedTestEntity,
	PageCompanyTestEntity,
	PageEntityModelResourceTestEntity1,
	PageFilter,
	PagePermission,
	PageSchema,
	PageSiteTestEntity,
	PageSort,
	PageTestEntity,
	Permission,
	Schema,
	SiteTestEntity,
	Sort,
	TestEntity,
	TestEntityAddress,
	UnreferencedTestEntity,
};

function startsWith(str: string, match: string): boolean {
	return str.substring(0, match.length) === match;
}

function endsWith(str: string, match: string): boolean {
	return (
		str.length >= match.length &&
		str.substring(str.length - match.length) === match
	);
}

const nullableSuffix = ' | null';
const optionalSuffix = ' | undefined';
const arrayPrefix = 'Array<';
const arraySuffix = '>';
const mapPrefix = '{ [key: string]: ';
const mapSuffix = '; }';

export class ObjectSerializer {
	public static findCorrectType(data: any, expectedType: string) {
		if (data === undefined) {
			return expectedType;
		}
		else if (primitives.indexOf(expectedType.toLowerCase()) !== -1) {
			return expectedType;
		}
		else if (expectedType === 'Date') {
			return expectedType;
		}
		else {
			if (!typeMap[expectedType]) {
				return expectedType;
			}

			const discriminatorProperty = typeMap[expectedType].discriminator;
			if (discriminatorProperty === null) {
				return expectedType;
			}
			else {
				if (data[discriminatorProperty]) {
					const discriminatorType = data[discriminatorProperty];
					if (typeMap[discriminatorType]) {
						return discriminatorType;
					}
					else {
						return expectedType;
					}
				}
				else {
					return expectedType;
				}
			}
		}
	}

	public static serialize(data: any, type: string): any {
		if (data === undefined) {
			return data;
		}
		else if (primitives.indexOf(type.toLowerCase()) !== -1) {
			return data;
		}
		else if (endsWith(type, nullableSuffix)) {
			const subType: string = type.slice(0, -nullableSuffix.length);

			return ObjectSerializer.serialize(data, subType);
		}
		else if (endsWith(type, optionalSuffix)) {
			const subType: string = type.slice(0, -optionalSuffix.length);

			return ObjectSerializer.serialize(data, subType);
		}
		else if (startsWith(type, arrayPrefix)) {
			const subType: string = type.slice(
				arrayPrefix.length,
				-arraySuffix.length
			);
			const transformedData: any[] = [];
			for (let index = 0; index < data.length; index++) {
				const datum = data[index];
				transformedData.push(
					ObjectSerializer.serialize(datum, subType)
				);
			}

			return transformedData;
		}
		else if (startsWith(type, mapPrefix)) {
			const subType: string = type.slice(
				mapPrefix.length,
				-mapSuffix.length
			);
			const transformedData: {[key: string]: any} = {};
			for (const key in data) {
				transformedData[key] = ObjectSerializer.serialize(
					data[key],
					subType
				);
			}

			return transformedData;
		}
		else if (type === 'Date') {
			return data.toISOString();
		}
		else {
			if (!typeMap[type]) {
				return data;
			}

			type = this.findCorrectType(data, type);

			const attributeTypes = typeMap[type].getAttributeTypeMap();
			const instance: {[index: string]: any} = {};
			for (let index = 0; index < attributeTypes.length; index++) {
				const attributeType = attributeTypes[index];
				instance[attributeType.baseName] = ObjectSerializer.serialize(
					data[attributeType.name],
					attributeType.type
				);
			}

			return instance;
		}
	}

	public static deserialize(data: any, type: string): any {
		type = ObjectSerializer.findCorrectType(data, type);
		if (data === undefined) {
			return data;
		}
		else if (primitives.indexOf(type.toLowerCase()) !== -1) {
			return data;
		}
		else if (endsWith(type, nullableSuffix)) {
			const subType: string = type.slice(0, -nullableSuffix.length);

			return ObjectSerializer.deserialize(data, subType);
		}
		else if (endsWith(type, optionalSuffix)) {
			const subType: string = type.slice(0, -optionalSuffix.length);

			return ObjectSerializer.deserialize(data, subType);
		}
		else if (startsWith(type, arrayPrefix)) {
			const subType: string = type.slice(
				arrayPrefix.length,
				-arraySuffix.length
			);
			const transformedData: any[] = [];
			for (let index = 0; index < data.length; index++) {
				const datum = data[index];
				transformedData.push(
					ObjectSerializer.deserialize(datum, subType)
				);
			}

			return transformedData;
		}
		else if (startsWith(type, mapPrefix)) {
			const subType: string = type.slice(
				mapPrefix.length,
				-mapSuffix.length
			);
			const transformedData: {[key: string]: any} = {};
			for (const key in data) {
				transformedData[key] = ObjectSerializer.deserialize(
					data[key],
					subType
				);
			}

			return transformedData;
		}
		else if (type === 'Date') {
			return new Date(data);
		}
		else {
			if (!typeMap[type]) {
				return data;
			}
			const instance = new typeMap[type]();
			const attributeTypes = typeMap[type].getAttributeTypeMap();
			for (let index = 0; index < attributeTypes.length; index++) {
				const attributeType = attributeTypes[index];
				instance[attributeType.name] = ObjectSerializer.deserialize(
					data[attributeType.baseName],
					attributeType.type
				);
			}

			return instance;
		}
	}
}
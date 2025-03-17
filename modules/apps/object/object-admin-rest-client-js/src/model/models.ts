/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

	import {Facet} from './facet';
	import {FacetValue} from './facetValue';
	import {ObjectAction} from './objectAction';
	import {ObjectDefinition} from './objectDefinition';
	import {ObjectDefinitionSetting} from './objectDefinitionSetting';
	import {ObjectField} from './objectField';
	import {ObjectFieldSetting} from './objectFieldSetting';
	import {ObjectFolder} from './objectFolder';
	import {ObjectFolderItem} from './objectFolderItem';
	import {ObjectLayout} from './objectLayout';
	import {ObjectLayoutBox} from './objectLayoutBox';
	import {ObjectLayoutColumn} from './objectLayoutColumn';
	import {ObjectLayoutRow} from './objectLayoutRow';
	import {ObjectLayoutTab} from './objectLayoutTab';
	import {ObjectRelationship} from './objectRelationship';
	import {ObjectState} from './objectState';
	import {ObjectStateFlow} from './objectStateFlow';
	import {ObjectStateTransition} from './objectStateTransition';
	import {ObjectValidationRule} from './objectValidationRule';
	import {ObjectValidationRuleSetting} from './objectValidationRuleSetting';
	import {ObjectView} from './objectView';
	import {ObjectViewColumn} from './objectViewColumn';
	import {ObjectViewFilterColumn} from './objectViewFilterColumn';
	import {ObjectViewSortColumn} from './objectViewSortColumn';
	import {PageObjectAction} from './pageObjectAction';
	import {PageObjectDefinition} from './pageObjectDefinition';
	import {PageObjectField} from './pageObjectField';
	import {PageObjectFolder} from './pageObjectFolder';
	import {PageObjectLayout} from './pageObjectLayout';
	import {PageObjectRelationship} from './pageObjectRelationship';
	import {PageObjectValidationRule} from './pageObjectValidationRule';
	import {PageObjectView} from './pageObjectView';
	import {Status} from './status';

	export * from './facet';
	export * from './facetValue';
	export * from './objectAction';
	export * from './objectDefinition';
	export * from './objectDefinitionSetting';
	export * from './objectField';
	export * from './objectFieldSetting';
	export * from './objectFolder';
	export * from './objectFolderItem';
	export * from './objectLayout';
	export * from './objectLayoutBox';
	export * from './objectLayoutColumn';
	export * from './objectLayoutRow';
	export * from './objectLayoutTab';
	export * from './objectRelationship';
	export * from './objectState';
	export * from './objectStateFlow';
	export * from './objectStateTransition';
	export * from './objectValidationRule';
	export * from './objectValidationRuleSetting';
	export * from './objectView';
	export * from './objectViewColumn';
	export * from './objectViewFilterColumn';
	export * from './objectViewSortColumn';
	export * from './pageObjectAction';
	export * from './pageObjectDefinition';
	export * from './pageObjectField';
	export * from './pageObjectFolder';
	export * from './pageObjectLayout';
	export * from './pageObjectRelationship';
	export * from './pageObjectValidationRule';
	export * from './pageObjectView';
	export * from './status';

/**
 * @author Javier Gamarra
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
	Facet,
	FacetValue,
	ObjectAction,
	ObjectDefinition,
	ObjectDefinitionSetting,
	ObjectField,
	ObjectFieldSetting,
	ObjectFolder,
	ObjectFolderItem,
	ObjectLayout,
	ObjectLayoutBox,
	ObjectLayoutColumn,
	ObjectLayoutRow,
	ObjectLayoutTab,
	ObjectRelationship,
	ObjectState,
	ObjectStateFlow,
	ObjectStateTransition,
	ObjectValidationRule,
	ObjectValidationRuleSetting,
	ObjectView,
	ObjectViewColumn,
	ObjectViewFilterColumn,
	ObjectViewSortColumn,
	PageObjectAction,
	PageObjectDefinition,
	PageObjectField,
	PageObjectFolder,
	PageObjectLayout,
	PageObjectRelationship,
	PageObjectValidationRule,
	PageObjectView,
	Status,
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
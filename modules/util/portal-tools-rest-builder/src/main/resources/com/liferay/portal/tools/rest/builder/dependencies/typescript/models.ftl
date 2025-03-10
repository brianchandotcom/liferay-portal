import localVarRequest from 'request';

<#list schemaMap?keys?sort as key>
	import {${key}} from './${key?uncap_first}';
</#list>

<#list schemaMap?keys?sort as key>
	export * from './${key?uncap_first}';
</#list>

/**
 * @author ${configYAML.author}
 * @generated
 */

export interface RequestDetailedFile {
	options?: {
		contentType?: string;
		filename?: string;
	};
	value: Buffer;
}

export type RequestFile = string | Buffer | fs.ReadStream | RequestDetailedFile;

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
<#list schemaMap?keys?sort as key>
	${key},
</#list>
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
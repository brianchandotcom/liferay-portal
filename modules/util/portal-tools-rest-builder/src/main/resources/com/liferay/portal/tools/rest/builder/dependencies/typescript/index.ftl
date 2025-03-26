<#list schemas?keys?sort as schema>
	import {${schema}} from './models/${schema}';
</#list>

/**
 * @author ${configYAML.author}
 * @generated
 */

<#list apiContexts?sort_by("className") as apiContext>
	export {${apiContext.className}} from './apis/${apiContext.className}';
</#list>

<#list schemas?keys?sort as schema>
	export {${schema}} from './models/${schema}';
</#list>

export class HttpError extends Error {
	constructor(
		public body: any,
		public response: Response,
		public statusCode: number
	) {
		super('HTTP request failed');
		this.name = 'HttpError';
	}
}

function endsWith(str: string, match: string): boolean {
	return (
		str.length >= match.length &&
		str.substring(str.length - match.length) === match
	);
}

function startsWith(str: string, match: string): boolean {
	return str.substring(0, match.length) === match;
}

const arrayPrefix = 'Array<';
const arraySuffix = '>';
const mapPrefix = '{ [key: string]: ';
const mapSuffix = '; }';
const nullableSuffix = ' | null';
const optionalSuffix = ' | undefined';
const primitives = new Set([
	'string',
	'boolean',
	'double',
	'integer',
	'long',
	'float',
	'number',
	'any',
]);
const typeMap: {[index: string]: any} = {
<#list schemas?keys?sort as schema>
	${schema},
</#list>
};

export class ObjectSerializer {
	public static deserialize(data: any, type: string): any {
		type = ObjectSerializer.findCorrectType(data, type);
		if (data === undefined) {
			return data;
		}
		else if (primitives.has(type.toLowerCase())) {
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

	public static findCorrectType(data: any, expectedType: string) {
		if (data === undefined) {
			return expectedType;
		}
		else if (primitives.has(expectedType.toLowerCase())) {
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
		else if (primitives.has(type.toLowerCase())) {
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
}
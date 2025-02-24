import localVarRequest from 'request';

<#list schemaMap?keys?sort as key>
	import {${key}} from './${key?uncap_first}';
</#list>

<#list schemaMap?keys?sort as key>
	export * from './${key?uncap_first}';
</#list>

export interface RequestDetailedFile {
	options?: {
		contentType?: string;
		filename?: string;
	};
	value: Buffer;
}

/* tslint:disable:no-unused-variable */
const primitives = [
	'string',
	'boolean',
	'double',
	'integer',
	'long',
	'float',
	'number',
	'any'
];

const typeMap: {[index: string]: any} = {
	<#list schemaMap?keys?sort as key>
		'${key}': ${key}<#if !key?is_last>,</#if>
	</#list>
}

// Check if a string starts with another string without using es6 features
function startsWith(str: string, match: string): boolean {
	return str.substring(0, match.length) === match;
}

// Check if a string ends with another string without using es6 features
function endsWith(str: string, match: string): boolean {
	return str.length >= match.length && str.substring(str.length - match.length) === match;
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
		} else if (primitives.indexOf(expectedType.toLowerCase()) !== -1) {
			return expectedType;
		} else if (expectedType === 'Date') {
			return expectedType;
		} else {
			if (!typeMap[expectedType]) {
				return expectedType; // w/e we don't know the type
			}

			// Check the discriminator
			const discriminatorProperty = typeMap[expectedType].discriminator;
			if (discriminatorProperty === null) {
				return expectedType; // the type does not have a discriminator. use it.
			} else {
				if (data[discriminatorProperty]) {
					var discriminatorType = data[discriminatorProperty];
					if(typeMap[discriminatorType]){
						return discriminatorType; // use the type given in the discriminator
					} else {
						return expectedType; // discriminator did not map to a type
					}
				} else {
					return expectedType; // discriminator was not present (or an empty string)
				}
			}
		}
	}

	public static serialize(data: any, type: string): any {
		if (data === undefined) {
			return data;
		} else if (primitives.indexOf(type.toLowerCase()) !== -1) {
			return data;
		} else if (endsWith(type, nullableSuffix)) {
			const subType: string = type.slice(0, -nullableSuffix.length); // Type | null => Type
			return ObjectSerializer.serialize(data, subType);
		} else if (endsWith(type, optionalSuffix)) {
			const subType: string = type.slice(0, -optionalSuffix.length); // Type | undefined => Type
			return ObjectSerializer.serialize(data, subType);
		} else if (startsWith(type, arrayPrefix)) {
			const subType: string = type.slice(arrayPrefix.length, -arraySuffix.length); // Array<Type> => Type
			const transformedData: any[] = [];
			for (let index = 0; index < data.length; index++) {
				const datum = data[index];
				transformedData.push(ObjectSerializer.serialize(datum, subType));
			}
			return transformedData;
		} else if (startsWith(type, mapPrefix)) {
			const subType: string = type.slice(mapPrefix.length, -mapSuffix.length); // { [key: string]: Type; } => Type
			const transformedData: { [key: string]: any } = {};
			for (const key in data) {
				transformedData[key] = ObjectSerializer.serialize(
					data[key],
					subType,
				);
			}
			return transformedData;
		} else if (type === 'Date') {
			return data.toISOString();
		} else {
			if (!typeMap[type]) { // in case we dont know the type
				return data;
			}

			// Get the actual type of this object
			type = this.findCorrectType(data, type);

			// get the map for the correct type.
			const attributeTypes = typeMap[type].getAttributeTypeMap();
			const instance: {[index: string]: any} = {};
			for (let index = 0; index < attributeTypes.length; index++) {
				const attributeType = attributeTypes[index];
				instance[attributeType.baseName] = ObjectSerializer.serialize(data[attributeType.name], attributeType.type);
			}
			return instance;
		}
	}

	public static deserialize(data: any, type: string): any {
		// polymorphism may change the actual type.
		type = ObjectSerializer.findCorrectType(data, type);
		if (data === undefined) {
			return data;
		} else if (primitives.indexOf(type.toLowerCase()) !== -1) {
			return data;
		} else if (endsWith(type, nullableSuffix)) {
			const subType: string = type.slice(0, -nullableSuffix.length); // Type | null => Type
			return ObjectSerializer.deserialize(data, subType);
		} else if (endsWith(type, optionalSuffix)) {
			const subType: string = type.slice(0, -optionalSuffix.length); // Type | undefined => Type
			return ObjectSerializer.deserialize(data, subType);
		} else if (startsWith(type, arrayPrefix)) {
			const subType: string = type.slice(arrayPrefix.length, -arraySuffix.length); // Array<Type> => Type
			const transformedData: any[] = [];
			for (let index = 0; index < data.length; index++) {
				const datum = data[index];
				transformedData.push(ObjectSerializer.deserialize(datum, subType));
			}
			return transformedData;
		} else if (startsWith(type, mapPrefix)) {
			const subType: string = type.slice(mapPrefix.length, -mapSuffix.length); // { [key: string]: Type; } => Type
			const transformedData: { [key: string]: any } = {};
			for (const key in data) {
				transformedData[key] = ObjectSerializer.deserialize(
					data[key],
					subType,
				);
			}
			return transformedData;
		} else if (type === 'Date') {
			return new Date(data);
		} else {
			if (!typeMap[type]) { // dont know the type
				return data;
			}
			const instance = new typeMap[type]();
			const attributeTypes = typeMap[type].getAttributeTypeMap();
			for (let index = 0; index < attributeTypes.length; index++) {
				const attributeType = attributeTypes[index];
				instance[attributeType.name] = ObjectSerializer.deserialize(data[attributeType.baseName], attributeType.type);
			}
			return instance;
		}
	}
}

export interface Authentication {
	/**
	* Apply authentication settings to header and query params.
	*/
	applyToRequest(requestOptions: localVarRequest.Options): Promise<void> | void;
}

export class HttpBasicAuth implements Authentication {
	public password: string = '';
	public username: string = '';

	applyToRequest(requestOptions: localVarRequest.Options): void {
		requestOptions.auth = {
			password: this.password, username: this.username
		}
	}
}

export class HttpBearerAuth implements Authentication {
	public accessToken: string | (() => string) = '';

	applyToRequest(requestOptions: localVarRequest.Options): void {
		if (requestOptions && requestOptions.headers) {
			const accessToken = typeof this.accessToken === 'function'
							? this.accessToken()
							: this.accessToken;
			requestOptions.headers['Authorization'] = 'Bearer ' + accessToken;
		}
	}
}

export class ApiKeyAuth implements Authentication {
	public apiKey: string = '';

	constructor(private location: string, private paramName: string) {
	}

	applyToRequest(requestOptions: localVarRequest.Options): void {
		if (this.location === 'query') {
			(<any>requestOptions.qs)[this.paramName] = this.apiKey;
		} else if (this.location === 'header' && requestOptions && requestOptions.headers) {
			requestOptions.headers[this.paramName] = this.apiKey;
		} else if (this.location === 'cookie' && requestOptions && requestOptions.headers) {
			if (requestOptions.headers['Cookie']) {
				requestOptions.headers['Cookie'] += '; ' + this.paramName + '=' + encodeURIComponent(this.apiKey);
			}
			else {
				requestOptions.headers['Cookie'] = this.paramName + '=' + encodeURIComponent(this.apiKey);
			}
		}
	}
}

export class OAuth implements Authentication {
	public accessToken: string = '';

	applyToRequest(requestOptions: localVarRequest.Options): void {
		if (requestOptions && requestOptions.headers) {
			requestOptions.headers['Authorization'] = 'Bearer ' + this.accessToken;
		}
	}
}

export class VoidAuth implements Authentication {
	public password: string = '';
	public username: string = '';

	applyToRequest(_: localVarRequest.Options): void {
		// Do nothing
	}
}

export type Interceptor = (requestOptions: localVarRequest.Options) => (Promise<void> | void);
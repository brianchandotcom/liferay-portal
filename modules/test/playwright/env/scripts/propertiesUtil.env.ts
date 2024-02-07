/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {error} from 'node:console';
import {existsSync, PathOrFileDescriptor, readFileSync, writeFile} from 'node:fs'
import {KeyValuePairObject, getProperties} from 'properties-file'

export function getPropertiesFromFile(propertiesFilePath: PathOrFileDescriptor): KeyValuePairObject {
	if ((propertiesFilePath === null) || (propertiesFilePath === undefined) || !existsSync(propertiesFilePath.toString())) {
		return null;
	}

	return getProperties(readFileSync(propertiesFilePath));
}

export function getPropertiesFromFiles(propertiesFilePaths: PathOrFileDescriptor[]): KeyValuePairObject {
	const properties = [];

	for (const propertiesFilePath of propertiesFilePaths) {
		properties.push(getPropertiesFromFile(propertiesFilePath));
	}

	return _mergeProperties(properties);
}

export function getPropertyValue(properties: KeyValuePairObject, propertyName: string): string {
	return _getPropertyValue(properties, propertyName, []);
}

export function writePropertiesFile(propertiesFilePath: PathOrFileDescriptor, properties: KeyValuePairObject) {
	let propertiesFileContent = '';

	for (const propertyName in properties) {
		propertiesFileContent += propertyName + '=' + properties[propertyName] + "\n";
	}

	writeFile(propertiesFilePath, propertiesFileContent, (error) => {
		if (error) {
			throw error;
		}
	});
}

function _getPropertyValue(properties: KeyValuePairObject, propertyName: string, previousPropertyNames: string[]): string {
	if (previousPropertyNames.includes(propertyName)) {
		if (previousPropertyNames.length > 1) {
			throw new error("Circular property reference chain found");
		}

		return "${" + propertyName + "}";
	}
	
	previousPropertyNames.push(propertyName);

	const propertyValue = properties[propertyName];

	if ((propertyValue === null) || (propertyValue === undefined)) {
		return "${" + propertyName + "}";
	}

	const filteredPropertyValue = _removeCommentsFromPropertyValues(propertyValue);

	let replacedPropertyValue = filteredPropertyValue;

	const regex = /(\$\{([^\}]+)\})/g;

	let results;

	while ((results = regex.exec(filteredPropertyValue)) !== null) {
		const foundPropertyName = results[2];

		if (properties[foundPropertyName] != null) {
			replacedPropertyValue = replacedPropertyValue.replace(results[0], _getPropertyValue(properties, foundPropertyName, previousPropertyNames));
		}
	}

	return replacedPropertyValue;
}

function _mergeProperties(propertiesArray: KeyValuePairObject[]): KeyValuePairObject {
	let properties;

	for (const i in propertiesArray) {
		const propertiesItem = propertiesArray[i];

		if ((propertiesItem == null) || (propertiesItem == undefined)) {
			continue;
		}

		if ((properties === null) || (properties === undefined)) {
			properties = propertiesItem;
		}
		else {
			for (const newPropertyName in propertiesItem) {
				properties[newPropertyName] = propertiesItem[newPropertyName];
			}
		}
	}

	return properties;
}

function _removeCommentsFromPropertyValues(propertyValue: string): string {
	if ((propertyValue === null) || (propertyValue === undefined)) {
		return null;
	}

	const propertyValues = [];

	const propertyValueArray = propertyValue.split(",");

	for (const i in propertyValueArray) {
		const propertyValueItem = propertyValueArray[i].trim();

		if ((!propertyValueItem.length) || (propertyValueItem.startsWith("#"))) {
			continue;
		}

		propertyValues.push(propertyValueItem);
	}

	return propertyValues.join(",");
}
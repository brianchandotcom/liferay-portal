/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {fetch, openToast} from 'frontend-js-web';

const HEADERS = new Headers({
	'content-type': 'application/json',
	'x-csrf-token': window.Liferay.authToken,
});

function getOptionElement(label, schemaName, value) {
	const optionElement = document.createElement('option');

	optionElement.innerHTML = label;
	optionElement.value = value;

	if (schemaName) {
		optionElement.setAttribute('schemaName', schemaName);
	}

	return optionElement;
}

function showImportMapping(components, internalClassNameValue) {
	const schemas = components.schemas;

	const schemaEntry = schemas[internalClassNameValue];

	var mappingArea = document.querySelector('.plan-mappings');
	var mappingRowTemplate = document.querySelector('.plan-mappings-template')
		.innerHTML;

	mappingArea.innerHTML = '';

	let curId = 1;

	for (const key in schemaEntry.properties) {
		const object = schemaEntry.properties[key];

		if (object.readOnly) {
			continue;
		}

		const mappingRow = mappingRowTemplate
			.replaceAll('ID_TEMPLATE', curId)
			.replace('VALUE_TEMPLATE', key);

		mappingArea.innerHTML += mappingRow;

		curId++;
	}

	document.querySelector('.import-mapping-table').classList.remove('hide');

	document
		.querySelector('form button[type="submit"]')
		.removeAttribute('disabled');
}

export default function ({importMapping, namespace}) {
	const headlessEnpointSelect = document.querySelector(
		`#${namespace}headlessEndpoint`
	);
	const internalClassNameSelect = document.querySelector(
		`#${namespace}internalClassName`
	);

	const taskItemDelegateNameInput = document.querySelector(
		`#${namespace}taskItemDelegateName`
	);

	headlessEnpointSelect.addEventListener('change', async (event) => {
		event.target.disabled = true;

		const headlessEnpoint = event.target.value;

		if (!headlessEnpoint) {
			internalClassNameSelect.innerHTML = '';

			return;
		}

		try {
			const response = await fetch(headlessEnpoint, {
				credentials: 'include',
				headers: HEADERS,
			});

			if (!response.ok) {
				throw new Error(`Failed to fetch: '${headlessEnpointSelect}'`);
			}

			const {components} = await response.json();
			internalClassNameSelect.innerHTML = '';

			internalClassNameSelect.appendChild(getOptionElement('', '', ''));

			const keys = Object.keys(components.schemas).sort();

			keys.forEach((key) => {
				const properties = components.schemas[key].properties;

				if (!properties || !properties['x-class-name']) {
					return;
				}

				const className = properties['x-class-name'].default;

				const schemaName = properties['x-schema-name']?.default;

				const optionElement = getOptionElement(
					trimPackage(className),
					schemaName,
					className
				);

				internalClassNameSelect.appendChild(optionElement);
			});

			Liferay.fire('schema-selected', {
				schema: null,
			});

			internalClassNameSelect.disabled = false;
		}
		catch (error) {
			openToast({
				message: Liferay.Language.get('your-request-has-failed'),
				type: 'danger',
			});

			console.error('Failed to fetch ' + error);
		}
		finally {
			event.target.disabled = false;
		}
	});

	internalClassNameSelect.addEventListener(
		'change',
		handleClassNameSelectChange
	);

	async function handleClassNameSelectChange() {
		const headlessEnpointValue = headlessEnpointSelect.value;

		const selectedOption =
			internalClassNameSelect.options[
				internalClassNameSelect.selectedIndex
			];

		const schemaName = selectedOption.getAttribute('schemaName');

		taskItemDelegateNameInput.value = schemaName || 'DEFAULT';

		const internalClassNameValue = trimPackage(
			schemaName || selectedOption.value
		);

		if (!headlessEnpointValue || !internalClassNameValue) {
			Liferay.fire('schema-selected', {
				schema: null,
			});

			return;
		}

		try {
			const response = await fetch(headlessEnpointValue, {
				credentials: 'include',
				headers: HEADERS,
			});

			if (!response.ok) {
				throw new Error(`Failed to fetch: '${headlessEnpointValue}'`);
			}

			const {components} = await response.json();

			const schemaEntry = components.schemas[internalClassNameValue];

			Liferay.fire('schema-selected', {
				schema: schemaEntry.properties,
			});

			if (importMapping) {
				showImportMapping(components, internalClassNameValue);
			}
		}
		catch (error) {
			openToast({
				message: Liferay.Language.get('your-request-has-failed'),
				type: 'danger',
			});

			console.error(`Failed to fetch ${error}`);
		}
	}

	function trimPackage(name) {
		if (!name || name.lastIndexOf('.') < 0) {
			return name;
		}

		return name.substr(name.lastIndexOf('.') + 1);
	}
}

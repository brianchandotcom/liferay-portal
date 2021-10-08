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

function getOptionElement(label, value) {
	const optionElement = document.createElement('option');

	optionElement.value = value;
	optionElement.innerHTML = label;

	return optionElement;
}

export default function ({namespace}) {
	const headlessEnpointSelect = document.querySelector(
		`#${namespace}headlessEndpoint`
	);
	const internalClassNameSelect = document.querySelector(
		`#${namespace}internalClassName`
	);

	headlessEnpointSelect.addEventListener('change', (event) => {
		event.target.disabled = true;

		const headlessEnpoint = event.target.value;

		if (!headlessEnpoint) {
			internalClassNameSelect.innerHTML = '';

			return;
		}

		fetch(headlessEnpoint, {
			credentials: 'include',
			headers: HEADERS,
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error(
						`Failed to fetch: '${headlessEnpointSelect}'`
					);
				}

				return response.json();
			})
			.then(({components}) => {
				internalClassNameSelect.innerHTML = '';

				internalClassNameSelect.appendChild(getOptionElement('', ''));

				const keys = Object.keys(components.schemas).sort();

				keys.forEach((key) => {
					const properties = components.schemas[key].properties;

					if (!properties || !properties['x-class-name']) {
						return;
					}

					const path = properties['x-class-name'].default;

					const value = path.substr(path.lastIndexOf('.') + 1);

					const optionElement = getOptionElement(key, value);

					internalClassNameSelect.appendChild(optionElement);
				});

				Liferay.fire('schema-selected', {
					schema: null,
				});

				internalClassNameSelect.disabled = false;
			})
			.catch((response) => {
				openToast({
					message: Liferay.Language.get('your-request-has-failed'),
					type: 'danger',
				});

				console.error('Failed to fetch ' + response);
			})
			.finally(() => {
				event.target.disabled = false;
			});
	});

	internalClassNameSelect.addEventListener(
		'change',
		handleClassNameSelectChange
	);

	function handleClassNameSelectChange() {
		const headlessEnpointValue = headlessEnpointSelect.value;
		const internalClassNameValue = internalClassNameSelect.value;

		if (!headlessEnpointValue || !internalClassNameValue) {
			Liferay.fire('schema-selected', {
				schema: null,
			});

			return;
		}

		fetch(headlessEnpointValue, {
			credentials: 'include',
			headers: HEADERS,
		})
			.then((response) => {
				if (!response.ok) {
					throw new Error(
						`Failed to fetch: '${headlessEnpointValue}'`
					);
				}

				return response.json();
			})
			.then(({components}) => {
				const schemaEntry = components.schemas[internalClassNameValue];

				Liferay.fire('schema-selected', {
					schema: schemaEntry.properties,
				});
			})
			.catch((response) => {
				openToast({
					message: Liferay.Language.get('your-request-has-failed'),
					type: 'danger',
				});

				console.error(`Failed to fetch ${response}`);
			});
	}
}

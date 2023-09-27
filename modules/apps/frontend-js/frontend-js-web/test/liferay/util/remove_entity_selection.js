/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import removeEntitySelection from '../../../src/main/resources/META-INF/resources/liferay/util/remove_entity_selection';

describe('Liferay.Util.removeEntitySelection', () => {
	const namespace = 'fooNamespace';
	const entityIdString = 'fooId';
	const entityNameString = 'fooName';
	const removeEntityButton = document.createElement('button');

	const elementByEntityId = document.createElement('input');

	elementByEntityId.id = `${namespace}${entityIdString}`;
	elementByEntityId.value = '1';

	document.body.appendChild(elementByEntityId);

	const elementByEntityName = document.createElement('input');

	elementByEntityName.id = `${namespace}${entityNameString}`;
	elementByEntityName.value = '1';

	document.body.appendChild(elementByEntityName);

	it('sets the value of the elementByEntityId to 0', () => {
		removeEntitySelection(
			entityIdString,
			entityNameString,
			removeEntityButton,
			namespace
		);

		expect(elementByEntityId.value).toBe('0');
	});

	it('sets the value of the elementByEntityName to an empty string', () => {
		removeEntitySelection(
			entityIdString,
			entityNameString,
			removeEntityButton,
			namespace
		);

		expect(elementByEntityName.value).toBe('');
	});

	it('disables the provided button', () => {
		removeEntitySelection(
			entityIdString,
			entityNameString,
			removeEntityButton,
			namespace
		);

		expect(removeEntityButton.disabled).toBe(true);
	});
});

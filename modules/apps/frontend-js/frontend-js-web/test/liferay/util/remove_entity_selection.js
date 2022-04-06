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

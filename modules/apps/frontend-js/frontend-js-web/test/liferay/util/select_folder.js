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

import selectFolder from '../../../src/main/resources/META-INF/resources/liferay/util/select_folder';

describe('Liferay.Util.selectFolder', () => {
	const folderData = {
		idString: 'fooId',
		idValue: '1',
		nameString: 'fooName',
		nameValue: 'fooNameValue',
	};
	const namespace = 'foo';

	Liferay = {
		Util: {
			unescape: jest.fn(() => folderData.nameValue),
		},
	};

	it('sets the value of the folderNameElement to match the name of the selected folder', () => {
		const folderNameElement = document.createElement('input');

		folderNameElement.id = `${namespace}${folderData.nameString}`;

		document.body.appendChild(folderNameElement);

		selectFolder(folderData, namespace);

		expect(folderNameElement.value).toBe(folderData.nameValue);
	});

	it('sets the value of the folderDataElement to match the id of the selected folder', () => {
		const folderDataElement = document.createElement('input');

		folderDataElement.id = `${namespace}${folderData.idString}`;

		document.body.appendChild(folderDataElement);

		selectFolder(folderData, namespace);

		expect(folderDataElement.value).toBe(folderData.idValue);
	});

	it('enables the removeFolderButton', () => {
		const mockButton = document.createElement('button');

		mockButton.id = `${namespace}removeFolderButton`;

		selectFolder(folderData, namespace);

		expect(mockButton.disabled).toBe(false);
	});
});

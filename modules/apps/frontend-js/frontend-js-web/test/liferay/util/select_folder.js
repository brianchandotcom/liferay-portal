/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

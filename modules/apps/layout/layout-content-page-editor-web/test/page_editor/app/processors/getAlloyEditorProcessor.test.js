/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getAlloyEditorProcessor from '../../../../src/main/resources/META-INF/resources/page_editor/app/processors/getAlloyEditorProcessor';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
	() => ({
		config: {
			defaultEditorConfigurations: {
				text: {
					editorConfig: {
						documentBrowseLinkUrl: '/',
						filebrowserImageBrowseLinkUrl: '/',
						filebrowserImageBrowseUrl: '/',
					},
				},
			},
		},
	})
);

describe('getAlloyEditorProcessor', () => {
	it('destroys previous editor before creating a new one', async () => {
		const editors = [];

		global.AlloyEditor = {
			editable: jest.fn(() => {
				const nativeEditor = {
					getData: jest.fn(() => '<p>value</p>'),
					on: jest.fn(() => ({removeListener: jest.fn()})),
				};

				const editor = {
					destroy: jest.fn(),
					get: jest.fn((key) =>
						key === 'nativeEditor' ? nativeEditor : null
					),
				};

				editors.push(editor);

				return editor;
			}),
		};

		const processor = getAlloyEditorProcessor('text');

		const elementA = document.createElement('div');
		const elementB = document.createElement('div');

		elementA.id = 'editableA';
		elementB.id = 'editableB';

		await processor.createEditor(elementA, jest.fn(), jest.fn(), null);

		await processor.createEditor(elementB, jest.fn(), jest.fn(), null);

		expect(global.AlloyEditor.editable).toHaveBeenCalledTimes(2);

		expect(editors[0].destroy).toHaveBeenCalledTimes(1);

		expect(editors[0].destroy.mock.invocationCallOrder[0]).toBeLessThan(
			global.AlloyEditor.editable.mock.invocationCallOrder[1]
		);
	});
});

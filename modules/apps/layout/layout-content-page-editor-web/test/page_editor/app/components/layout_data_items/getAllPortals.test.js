/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getAllPortals from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout_data_items/getAllPortals';

describe('getAllPortals', () => {
	it('returns all drop zones for a given Element', () => {
		const element = document.createElement('div');

		element.innerHTML = `
        <lfr-drop-zone>Drop zone 1</lfr-drop-zone>
        <lfr-drop-zone data-lfr-drop-zone-id="dropzone">Drop zone 2</lfr-drop-zone>
        <lfr-drop-zone uuid="123">Drop zone 3</lfr-drop-zone>
        `;

		const dropzones = element.querySelectorAll('lfr-drop-zone');

		expect(getAllPortals(element)).toEqual([
			{
				Component: expect.any(Function),
				dropZoneId: '',
				element: dropzones[0],
				mainItemId: '',
				priority: Infinity,
			},
			{
				Component: expect.any(Function),
				dropZoneId: 'dropzone',
				element: dropzones[1],
				mainItemId: '',
				priority: Infinity,
			},
			{
				Component: expect.any(Function),
				dropZoneId: '',
				element: dropzones[2],
				mainItemId: '123',
				priority: Infinity,
			},
		]);
	});

	it('takes into account the priority attribute', () => {
		const element = document.createElement('div');

		element.innerHTML = `
        <lfr-drop-zone data-lfr-priority="1">Drop zone</lfr-drop-zone>
        <lfr-drop-zone data-lfr-priority="2">Drop zone</lfr-drop-zone>
        `;

		expect(getAllPortals(element)).toEqual([
			expect.objectContaining({priority: '1'}),
			expect.objectContaining({priority: '2'}),
		]);
	});
});

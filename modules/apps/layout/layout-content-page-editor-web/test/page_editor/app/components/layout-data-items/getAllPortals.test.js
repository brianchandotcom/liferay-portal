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

import getAllPortals from '../../../../../src/main/resources/META-INF/resources/page_editor/app/components/layout-data-items/getAllPortals';

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

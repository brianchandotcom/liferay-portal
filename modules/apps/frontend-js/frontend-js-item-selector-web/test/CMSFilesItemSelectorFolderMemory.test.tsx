/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch} from 'frontend-js-web';

import {
	getLastBreadcrumbFolders,
	setLastBreadcrumbFolders,
} from '../src/main/resources/META-INF/resources/item_selector/lastBreadcrumbFoldersMemory';
import openCMSFileSelectorModal from '../src/main/resources/META-INF/resources/item_selector/openCMSFileSelectorModal';

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(),
}));

// Replace the modal with a portaled stub that confirms a selection on click, so
// the test can exercise the folder-memory wiring without driving the data set.

jest.mock(
	'../src/main/resources/META-INF/resources/item_selector/ItemSelectorModal',
	() => ({
		__esModule: true,
		default: ({onItemsChange}: any) => {
			const React = jest.requireActual('react');
			const {createPortal} = jest.requireActual('react-dom');

			return createPortal(
				React.createElement(
					'button',
					{
						onClick: () =>
							onItemsChange([{embedded: {id: 1}, id: 1}]),
						type: 'button',
					},
					'confirm-selection'
				),
				globalThis.document.body
			);
		},
	})
);

const mockedFetch = fetch as jest.Mock;

const FOLDER_MEMORY_KEY = 'test-field';

describe('CMS files item selector folder memory', () => {
	let root: {unmount?: () => void} | undefined;

	beforeAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: () => false,
		});
	});

	beforeEach(() => {
		setLastBreadcrumbFolders(FOLDER_MEMORY_KEY, null);

		mockedFetch.mockImplementation(() =>
			Promise.resolve({
				json: () => Promise.resolve({items: [], totalCount: 0}),
				ok: true,
			})
		);
	});

	afterEach(() => {
		root?.unmount?.();
		root = undefined;

		document.body.innerHTML = '';

		jest.clearAllMocks();
	});

	afterAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: undefined,
		});
	});

	it('stores the current folder path when a selection is confirmed', async () => {
		const user = userEvent.setup();

		root = openCMSFileSelectorModal({
			folderMemoryKey: FOLDER_MEMORY_KEY,
			groupId: 123,
			onSelect: jest.fn(),
		}) as {unmount?: () => void};

		const button = await screen.findByRole('button', {
			name: 'confirm-selection',
		});

		await user.click(button);

		expect(getLastBreadcrumbFolders(FOLDER_MEMORY_KEY)).toEqual([
			{id: null, label: 'files'},
		]);
	});

	it('forwards the confirmed selection to the caller', async () => {
		const user = userEvent.setup();
		const onSelect = jest.fn();

		root = openCMSFileSelectorModal({
			groupId: 123,
			onSelect,
		}) as {unmount?: () => void};

		const button = await screen.findByRole('button', {
			name: 'confirm-selection',
		});

		await user.click(button);

		expect(onSelect).toHaveBeenCalledWith([{embedded: {id: 1}, id: 1}]);
	});
});

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {screen, waitFor, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {fetch} from 'frontend-js-web';

import openCMSFileSelectorModal from '../src/main/resources/META-INF/resources/item_selector/openCMSFileSelectorModal';

const fileItem = {
	embedded: {id: 1, title: 'File'},
	id: 1,
};

function jsonResponse(items: object[]) {
	return Promise.resolve({
		json: () =>
			Promise.resolve({
				items,
				lastPage: 1,
				page: 1,
				totalCount: items.length,
			}),
		ok: true,
	});
}

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(),
}));

const mockedFetch = fetch as jest.Mock;

function getDecodedSearchURLs() {
	return mockedFetch.mock.calls
		.map(([url]) =>
			decodeURIComponent(url?.toString?.() ?? '').replace(/\+/g, ' ')
		)
		.filter((url) => url.includes('/o/search/v1.0/search'));
}

describe('openCMSFileSelectorModal', () => {
	beforeAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: () => false,
		});
	});

	beforeEach(() => {
		mockedFetch.mockImplementation(() => jsonResponse([fileItem]));
	});

	afterEach(() => {
		jest.clearAllMocks();
	});

	afterAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: undefined,
		});
	});

	it('renders cards and table options in the view-toggle picker', async () => {
		const user = userEvent.setup();

		openCMSFileSelectorModal({
			groupId: 123,
			onSelect: jest.fn(),
		});

		const dialog = await screen.findByRole('dialog');

		const viewToggle = await within(dialog).findByRole('combobox', {
			name: 'x-view-selected',
		});

		await user.click(viewToggle);

		const options = await screen.findAllByRole('option');

		expect(options).toHaveLength(2);

		expect(options[0]).toHaveTextContent('cards');

		expect(options[1]).toHaveTextContent('table');
	});

	it('fetches with a folder-inclusive root filter', async () => {
		openCMSFileSelectorModal({
			groupId: 123,
			onSelect: jest.fn(),
		});

		await screen.findByRole('dialog');

		await waitFor(() => {
			expect(getDecodedSearchURLs().length).toBeGreaterThan(0);
		});

		const [firstURL] = getDecodedSearchURLs();

		expect(firstURL).toContain("(cmsSection eq 'files')");

		expect(firstURL).toContain('(rootDescendantNode eq false)');

		expect(firstURL).not.toContain("cmsKind eq 'object'");

		expect(firstURL).not.toContain('folderId eq ');
	});

	it("widens the extension filter so folders pass through with cmsKind eq 'folder'", async () => {
		openCMSFileSelectorModal({
			allowedExtensions: 'jpg,png',
			groupId: 123,
			onSelect: jest.fn(),
		});

		await screen.findByRole('dialog');

		await waitFor(() => {
			expect(getDecodedSearchURLs().length).toBeGreaterThan(0);
		});

		const [firstURL] = getDecodedSearchURLs();

		expect(firstURL).toContain("extension in ('jpg','png')");

		expect(firstURL).toContain("cmsKind eq 'folder'");
	});
});

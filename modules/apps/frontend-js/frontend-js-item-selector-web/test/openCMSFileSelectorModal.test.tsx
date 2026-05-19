/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {screen, within} from '@testing-library/react';
import userEvent from '@testing-library/user-event';

import openCMSFileSelectorModal from '../src/main/resources/META-INF/resources/item_selector/openCMSFileSelectorModal';

jest.mock('frontend-js-web', () => ({
	...(jest.requireActual('frontend-js-web') as any),
	fetch: jest.fn(() =>
		Promise.resolve({
			json: () =>
				Promise.resolve({
					items: [{embedded: {id: 1, title: 'File'}, id: 1}],
					lastPage: 1,
					page: 1,
					totalCount: 1,
				}),
			ok: true,
		})
	),
}));

describe('openCMSFileSelectorModal', () => {
	beforeAll(() => {
		Object.assign(Liferay.ThemeDisplay, {
			isImpersonated: () => false,
		});
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
});

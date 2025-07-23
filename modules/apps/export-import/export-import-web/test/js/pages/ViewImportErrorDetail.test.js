/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import '@testing-library/jest-dom/extend-expect';

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import {openModal} from 'frontend-js-components-web';

import {ViewImportErrorDetail} from '../../../src/main/resources/META-INF/resources/js/pages/ViewImportErrorDetail';

const _getComponent = (props) => {
	return <ViewImportErrorDetail {...props} />;
};

const mockData = (data) => {
	global.fetch = jest.fn(() =>
		Promise.resolve({
			json: () => Promise.resolve(data),
			ok: true,
		})
	);
};

jest.mock('frontend-js-components-web', () => ({
	...jest.requireActual('frontend-js-components-web'),
	openModal: jest.fn(),
}));

describe('ViewImportErrorDetail', () => {
	afterEach(() => {
		jest.restoreAllMocks();
		jest.clearAllMocks();
	});

	it('renders loading state initially', async () => {
		mockData({});

		render(
			_getComponent({
				apiURL: '/group/__mocks__/get-import-error-detail',
				backURL: 'www.localhost:8080',
			})
		);

		expect(
			screen.getByTitle('Loading import error details...')
		).toBeInTheDocument();
	});

	it('renders error details when loaded', async () => {
		mockData({
			actions: {},
			creator: {
				name: 'John Doe',
			},
			dateCreated: '2025-06-05T08:51:54Z',
			dateModified: '2025-06-05T08:51:54Z',
			entityExternalReferenceCode: 'ERC-1001',
			entityId: 1001,
			entityScope: 'global',
			entitySite: 'default-site',
			entityType: 'Employees',
			errorId: 12345,
			errorMessage: `An essential process has failed to complete successfully.`,
			errorStackTrace: `Error stack trace details...`,
			errorType: 'An unexpected error occurred',
			externalReferenceCode: 'ERC-1',
			id: 51949,
			status: {
				code: 0,
				label: 'approved',
				label_i18n: 'Approved',
			},
		});

		const {getByRole} = render(
			_getComponent({
				apiURL: '/group/__mocks__/get-import-error-detail',
				backURL: 'www.localhost:8080',
			})
		);

		expect(await screen.findByText('error-id')).toBeInTheDocument();
		expect(await screen.findByText('error-message')).toBeInTheDocument();
		expect(await screen.findByText('entity-id')).toBeInTheDocument();

		fireEvent.click(getByRole('button', {name: 'view-stack-trace'}));

		expect(openModal).toHaveBeenCalledWith(
			expect.objectContaining({
				size: 'full-screen',
				title: 'stack-trace',
			})
		);
	});
});

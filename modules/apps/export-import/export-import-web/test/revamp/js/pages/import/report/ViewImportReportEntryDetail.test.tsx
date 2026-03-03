/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetch from 'jest-fetch-mock';
import React from 'react';

import '@testing-library/jest-dom';
import {fireEvent, render, screen} from '@testing-library/react';
import {openModal} from 'frontend-js-components-web';

import {ViewImportReportEntryDetail} from '../../../../../../src/main/resources/META-INF/resources/revamp/js/pages/import/report/ViewImportReportEntryDetail';

const renderComponent = (props: {apiURL: string; backURL: string}) => {
	return <ViewImportReportEntryDetail {...props} />;
};

jest.mock('frontend-js-components-web', () => ({
	...(jest.requireActual('frontend-js-components-web') as any),
	openModal: jest.fn(),
}));

describe('ViewImportReportEntryDetail', () => {
	afterEach(() => {
		jest.clearAllMocks();
	});

	it('renders loading state initially', async () => {
		fetch.mockResponseOnce(JSON.stringify({}));

		const {container} = render(
			renderComponent({
				apiURL: '/group/__mocks__/get-import-error-detail',
				backURL: 'www.localhost:8080',
			})
		);

		const loadingSpinner = container.querySelector('.loading-animation');

		expect(loadingSpinner).toBeInTheDocument();
	});

	it('renders error details when loaded and shows stack trace modal', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({
				classExternalReferenceCode: '',
				classPK: 123,
				creator: {
					name: 'John Doe',
				},
				dateCreated: '2025-06-05T08:51:54Z',
				dateModified: '2025-06-05T08:51:54Z',
				errorMessage: 'Error message',
				errorStacktrace: 'Error stack trace',
				id: 456,
				modelName: 'ModelName',
				scope: {
					label: 'Scope label',
					type: 'Scope Type',
				},
				type: {
					code: 1,
					label: 'Type label',
				},
			})
		);

		const {getByRole} = render(
			renderComponent({
				apiURL: '/group/__mocks__/get-import-error-detail',
				backURL: 'www.localhost:8080',
			})
		);

		expect(await screen.findByText('error-message')).toBeInTheDocument();
		expect(await screen.findAllByText('id')).toHaveLength(2);

		fireEvent.click(getByRole('button', {name: 'view-stack-trace'}));

		expect(openModal).toHaveBeenCalledWith(
			expect.objectContaining({
				size: 'full-screen',
				title: 'stack-trace',
			})
		);
	});

	it('shows "no stack trace" info modal when errorStacktrace is missing', async () => {
		fetch.mockResponseOnce(
			JSON.stringify({
				classExternalReferenceCode: '',
				classPK: 123,
				creator: {
					name: 'John Doe',
				},
				dateCreated: '2025-06-05T08:51:54Z',
				dateModified: '2025-06-05T08:51:54Z',
				errorMessage: 'Error message',
				errorStacktrace: null,
				id: 456,
				modelName: 'ModelName',
				scope: {
					label: 'Scope label',
					type: 'Scope Type',
				},
				type: {
					code: 1,
					label: 'Type label',
				},
			})
		);

		const {findByRole} = render(
			renderComponent({
				apiURL: '/group/__mocks__/get-import-error-detail',
				backURL: 'www.localhost:8080',
			})
		);

		const viewStackTraceButton = await findByRole('button', {
			name: 'view-stack-trace',
		});

		fireEvent.click(viewStackTraceButton);

		expect(openModal).toHaveBeenCalledWith(
			expect.objectContaining({
				bodyHTML:
					'this-error-was-detected-as-a-controlled-validation-error-during-the-import-process-and-did-not-originate-from-a-system-exception.-because-of-this-no-stack-trace-was-generated',
				status: 'info',
				title: 'no-stack-trace-available',
			})
		);
	});
});

/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {fireEvent, render, screen, waitFor} from '@testing-library/react';
import {openToast} from 'frontend-js-components-web';
import React from 'react';

import GooglePageSpeedConfiguration from '../../../js/google_pagespeed/GooglePageSpeedConfiguration';

jest.mock('frontend-js-components-web', () => ({
	openToast: jest.fn(),
}));

type ConfigurationProps = React.ComponentProps<
	typeof GooglePageSpeedConfiguration
>;

function renderConfiguration(props: Partial<ConfigurationProps> = {}) {
	return render(
		<GooglePageSpeedConfiguration
			backURL="/web/seo-studio/configurations"
			description="Connect Google PageSpeed Insights"
			domainsURL="/o/seo-studio/domains"
			instancesURL="/o/seo-studio/instances"
			title="Google PageSpeed"
			{...props}
		/>
	);
}

function getApiKeyInput() {
	return screen.getByPlaceholderText('enter-key');
}

function getCancelLink() {
	return screen.getByRole('link', {name: 'cancel'});
}

function getSaveButton() {
	return screen.getByRole('button', {name: /save|validating/});
}

function getToggleButton() {
	return screen.getByLabelText('toggle-api-key-visibility');
}

function mockInitialLoad({
	domains = [],
	instances = [],
}: {
	domains?: Array<{id: number}>;
	instances?: Array<{googlePageSpeedAPIKey?: string; id: number}>;
} = {}) {
	const fetchMock = Liferay.Util.fetch as jest.Mock;

	fetchMock.mockResolvedValueOnce({
		json: () => Promise.resolve({items: domains}),
		ok: true,
		status: 200,
	});
	fetchMock.mockResolvedValueOnce({
		json: () => Promise.resolve({items: instances}),
		ok: true,
		status: 200,
	});
}

beforeEach(() => {
	(openToast as jest.Mock).mockClear();

	(Liferay.Util as unknown) = {
		fetch: jest.fn(),
	};

	delete (window as any).location;

	(window as any).location = {assign: jest.fn(), href: ''};

	sessionStorage.clear();
});

describe('GooglePageSpeedConfiguration', () => {
	describe('render', () => {
		it('renders the title and description from props', async () => {
			mockInitialLoad();

			renderConfiguration({
				description: 'Custom description',
				title: 'Custom title',
			});

			expect(screen.getByText('Custom title')).toBeInTheDocument();
			expect(screen.getByText('Custom description')).toBeInTheDocument();
		});

		it('links Cancel to the provided backURL', async () => {
			mockInitialLoad();

			renderConfiguration({backURL: '/back/here'});

			expect(getCancelLink()).toHaveAttribute('href', '/back/here');
		});

		it('starts with the API key input hidden as a password', async () => {
			mockInitialLoad();

			renderConfiguration();

			expect(getApiKeyInput()).toHaveAttribute('type', 'password');
		});

		it('toggles the API key input visibility on click', async () => {
			mockInitialLoad();

			renderConfiguration();

			fireEvent.click(getToggleButton());

			expect(getApiKeyInput()).toHaveAttribute('type', 'text');

			fireEvent.click(getToggleButton());

			expect(getApiKeyInput()).toHaveAttribute('type', 'password');
		});
	});

	describe('initial load', () => {
		it('populates the API key from an existing instance', async () => {
			mockInitialLoad({
				domains: [{id: 42}],
				instances: [
					{
						googlePageSpeedAPIKey: 'EXISTING-KEY',
						id: 100,
					},
				],
			});

			renderConfiguration();

			await waitFor(() => {
				expect(getApiKeyInput()).toHaveValue('EXISTING-KEY');
			});
		});

		it('leaves the input empty when no instance has a key', async () => {
			mockInitialLoad({
				domains: [{id: 42}],
				instances: [{id: 100}],
			});

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeInTheDocument();
			});

			expect(getApiKeyInput()).toHaveValue('');
		});

		it('shows a danger toast when the initial fetch returns an HTTP error', async () => {
			const fetchMock = Liferay.Util.fetch as jest.Mock;

			fetchMock.mockResolvedValueOnce({
				json: () => Promise.resolve({}),
				ok: false,
				status: 500,
			});
			fetchMock.mockResolvedValueOnce({
				json: () => Promise.resolve({}),
				ok: false,
				status: 500,
			});

			renderConfiguration();

			await waitFor(() => {
				expect(openToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'failed-to-load-configuration',
						type: 'danger',
					})
				);
			});
		});
	});

	describe('save flow', () => {
		it('disables Save while the API key input is empty', async () => {
			mockInitialLoad({domains: [{id: 42}]});

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeDisabled();
			});
		});

		it('enables Save once the user types an API key', async () => {
			mockInitialLoad({domains: [{id: 42}]});

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeDisabled();
			});

			fireEvent.change(getApiKeyInput(), {target: {value: 'KEY123'}});

			expect(getSaveButton()).toBeEnabled();
		});

		it('shows a danger toast when no domains exist', async () => {
			mockInitialLoad();

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeInTheDocument();
			});

			fireEvent.change(getApiKeyInput(), {target: {value: 'KEY123'}});
			fireEvent.click(getSaveButton());

			await waitFor(() => {
				expect(openToast).toHaveBeenCalledWith(
					expect.objectContaining({type: 'danger'})
				);
			});
		});

		it('patches each instance with the new API key and navigates back on success', async () => {
			mockInitialLoad({
				domains: [{id: 42}],
				instances: [{id: 1}, {id: 2}],
			});

			const fetchMock = Liferay.Util.fetch as jest.Mock;

			fetchMock.mockResolvedValueOnce({
				json: () => Promise.resolve({}),
			});
			fetchMock.mockResolvedValueOnce({ok: true});
			fetchMock.mockResolvedValueOnce({ok: true});

			renderConfiguration({backURL: '/back/here'});

			await waitFor(() => {
				expect(getSaveButton()).toBeInTheDocument();
			});

			fireEvent.change(getApiKeyInput(), {target: {value: 'KEY123'}});
			fireEvent.click(getSaveButton());

			await waitFor(() => {
				expect(window.location.assign).toHaveBeenCalledWith(
					'/back/here'
				);
			});

			const patchCalls = fetchMock.mock.calls.filter(
				([, options]) => options?.method === 'PATCH'
			);

			expect(patchCalls).toHaveLength(2);

			const patchedKeys = patchCalls.map(
				([, options]) => JSON.parse(options.body).googlePageSpeedAPIKey
			);

			expect(patchedKeys).toEqual(['KEY123', 'KEY123']);

			expect(sessionStorage.getItem('seoStudioToast')).toBe(
				'google-pagespeed-api-key-added'
			);
		});

		it('shows an inline validation error when the key fails Google validation', async () => {
			mockInitialLoad({domains: [{id: 42}]});

			const fetchMock = Liferay.Util.fetch as jest.Mock;

			fetchMock.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({
						error: {status: 'PERMISSION_DENIED'},
					}),
				ok: false,
				status: 403,
			});

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeInTheDocument();
			});

			fireEvent.change(getApiKeyInput(), {target: {value: 'KEY123'}});
			fireEvent.click(getSaveButton());

			await waitFor(() => {
				expect(
					screen.getByText(
						'google-pagespeed-connection-failed-please-verify-your-configuration-and-try-again'
					)
				).toBeInTheDocument();
			});

			expect(window.location.assign).not.toHaveBeenCalled();
		});

		it('shows an inline validation error when Google returns a malformed body', async () => {
			mockInitialLoad({domains: [{id: 42}]});

			const fetchMock = Liferay.Util.fetch as jest.Mock;

			fetchMock.mockResolvedValueOnce({
				json: () => Promise.reject(new SyntaxError('Unexpected token')),
				ok: false,
				status: 500,
			});

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeInTheDocument();
			});

			fireEvent.change(getApiKeyInput(), {target: {value: 'KEY123'}});
			fireEvent.click(getSaveButton());

			await waitFor(() => {
				expect(
					screen.getByText(
						'google-pagespeed-connection-failed-please-verify-your-configuration-and-try-again'
					)
				).toBeInTheDocument();
			});

			expect(window.location.assign).not.toHaveBeenCalled();
		});

		it('shows an inline validation error when an instance save fails', async () => {
			mockInitialLoad({
				domains: [{id: 42}],
				instances: [{id: 1}],
			});

			const fetchMock = Liferay.Util.fetch as jest.Mock;

			fetchMock.mockResolvedValueOnce({
				json: () => Promise.resolve({}),
			});
			fetchMock.mockResolvedValueOnce({ok: false});

			renderConfiguration();

			await waitFor(() => {
				expect(getSaveButton()).toBeInTheDocument();
			});

			fireEvent.change(getApiKeyInput(), {target: {value: 'KEY123'}});
			fireEvent.click(getSaveButton());

			await waitFor(() => {
				expect(
					screen.getByText('failed-to-save-api-key')
				).toBeInTheDocument();
			});

			expect(window.location.assign).not.toHaveBeenCalled();
		});
	});
});

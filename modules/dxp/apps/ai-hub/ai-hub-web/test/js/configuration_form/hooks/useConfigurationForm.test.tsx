/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook, waitFor} from '@testing-library/react';

import {useConfigurationForm} from '../../../../src/main/resources/META-INF/resources/js/configuration_form/hooks/useConfigurationForm';

const mockGetConfiguration = jest.fn();
const mockPutConfiguration = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/configuration_form/services/ConfigurationService',
	() => ({
		getConfiguration: (...args: any[]) => mockGetConfiguration(...args),
		putConfiguration: (...args: any[]) => mockPutConfiguration(...args),
	})
);

jest.mock('frontend-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

const ACCOUNT_ENTRY_ID = 12345;

function renderConfigurationHook({
	accountEntryId = ACCOUNT_ENTRY_ID,
	externalReferenceCode = '',
}: {
	accountEntryId?: number;
	externalReferenceCode?: string;
} = {}) {
	return renderHook(() =>
		useConfigurationForm({accountEntryId, externalReferenceCode})
	);
}

function fillFields(result: any) {
	result.current.setField('environmentUrls', 'https://www.example.com');
	result.current.setField('recipientEmailAddress', 'admin@example.com');
}

describe('fetch lifecycle', () => {
	beforeEach(() => {
		mockGetConfiguration.mockReset();
		mockOpenToast.mockReset();
	});

	it('loads the configuration and replaces values on mount in edit mode', async () => {
		mockGetConfiguration.mockResolvedValueOnce({
			environmentUrls: 'https://loaded.example.com',
			externalReferenceCode: 'CONFIG_X',
			recipientEmailAddress: 'loaded@example.com',
		});

		const {result} = renderConfigurationHook({
			externalReferenceCode: 'CONFIG_X',
		});

		await waitFor(() => {
			expect(result.current.values.environmentUrls).toBe(
				'https://loaded.example.com'
			);
		});

		expect(result.current.values.recipientEmailAddress).toBe(
			'loaded@example.com'
		);

		expect(result.current.values.externalReferenceCode).toBe('CONFIG_X');

		expect(result.current.loading).toBe(false);
	});

	it('shows an error toast when the fetch rejects', async () => {
		mockGetConfiguration.mockRejectedValueOnce(new Error('Boom'));

		renderConfigurationHook({externalReferenceCode: 'CONFIG_X'});

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message: 'failed-to-load-configuration',
					type: 'danger',
				})
			);
		});
	});

	it('skips the fetch when externalReferenceCode is empty', async () => {
		const {result} = renderConfigurationHook({externalReferenceCode: ''});

		await waitFor(() => {
			expect(mockGetConfiguration).not.toHaveBeenCalled();
		});

		expect(result.current.loading).toBe(false);
	});
});

describe('useConfigurationForm', () => {
	beforeEach(() => {
		mockGetConfiguration.mockReset();
		mockPutConfiguration.mockReset();
		mockOpenToast.mockReset();
	});

	describe('initial values', () => {
		it('starts with empty fields in create mode', () => {
			const {result} = renderConfigurationHook({
				externalReferenceCode: '',
			});

			expect(result.current.values.environmentUrls).toBe('');

			expect(result.current.values.recipientEmailAddress).toBe('');

			expect(result.current.values.externalReferenceCode).toBe('');
		});
	});

	describe('submit', () => {
		it('falls back to the localized error when the thrown error has no message', async () => {
			mockPutConfiguration.mockRejectedValueOnce(new Error());

			const {result} = renderConfigurationHook();

			await act(async () => {
				fillFields(result);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'failed-to-save-configuration',
						type: 'danger',
					})
				);
			});
		});

		it('saves even when both fields are left empty', async () => {
			mockPutConfiguration.mockResolvedValueOnce({});

			const {result} = renderConfigurationHook({
				externalReferenceCode: '',
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockPutConfiguration).toHaveBeenCalledWith(
					'',
					expect.objectContaining({
						environmentUrls: '',
						recipientEmailAddress: '',
					})
				);
			});
		});

		it('sends the field values and the account relationship to the API', async () => {
			mockPutConfiguration.mockResolvedValueOnce({});

			const {result} = renderConfigurationHook({
				externalReferenceCode: '',
			});

			await act(async () => {
				fillFields(result);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockPutConfiguration).toHaveBeenCalledWith(
					'',
					expect.objectContaining({
						environmentUrls: 'https://www.example.com',
						r_accountToAIHubConfigurations_accountEntryId:
							ACCOUNT_ENTRY_ID,
						recipientEmailAddress: 'admin@example.com',
					})
				);
			});
		});

		it('shows a success toast when the save resolves', async () => {
			mockPutConfiguration.mockResolvedValueOnce({});

			const {result} = renderConfigurationHook();

			await act(async () => {
				fillFields(result);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'configuration-was-saved-successfully',
						type: 'success',
					})
				);
			});
		});

		it("surfaces the thrown error's message in a danger toast", async () => {
			mockPutConfiguration.mockRejectedValueOnce(
				new Error('Environment URL is invalid')
			);

			const {result} = renderConfigurationHook();

			await act(async () => {
				fillFields(result);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'Environment URL is invalid',
						type: 'danger',
					})
				);
			});
		});
	});
});

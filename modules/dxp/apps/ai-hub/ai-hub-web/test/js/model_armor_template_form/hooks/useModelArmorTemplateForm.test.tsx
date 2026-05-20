/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook, waitFor} from '@testing-library/react';

import {useModelArmorTemplateForm} from '../../../../src/main/resources/META-INF/resources/js/model_armor_template_form/hooks/useModelArmorTemplateForm';

const mockGetModelArmorTemplate = jest.fn();
const mockPutModelArmorTemplate = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/model_armor_template_form/services/ModelArmorTemplateService',
	() => ({
		getModelArmorTemplate: (...args: any[]) =>
			mockGetModelArmorTemplate(...args),
		putModelArmorTemplate: (...args: any[]) =>
			mockPutModelArmorTemplate(...args),
	})
);

jest.mock('@liferay/object-js-components-web', () => ({
	openToast: (...args: any[]) => mockOpenToast(...args),
}));

(global as any).Liferay = {
	Language: {
		get: (key: string) => key,
	},
};

function renderModelArmorHook({
	accountEntryExternalReferenceCode = 'ACCOUNT',
	externalReferenceCode = '',
}: {
	accountEntryExternalReferenceCode?: string;
	externalReferenceCode?: string;
} = {}) {
	return renderHook(() =>
		useModelArmorTemplateForm({
			accountEntryExternalReferenceCode,
			externalReferenceCode,
		})
	);
}

describe('fetch lifecycle', () => {
	beforeEach(() => {
		mockGetModelArmorTemplate.mockReset();
		mockOpenToast.mockReset();
	});

	it('loads the template and replaces values on mount in edit mode', async () => {
		mockGetModelArmorTemplate.mockResolvedValueOnce({
			active: false,
			description: 'Loaded from API',
			externalReferenceCode: 'TEMPLATE_X',
			guardrailType: 'output',
			location: 'us-central1',
			maliciousUriFilterEnabled: true,
			multiLanguageDetectionEnabled: false,
			piAndJailbreakConfidenceLevel: 'high',
			piAndJailbreakFilterEnabled: true,
			r_accountToAIHubModelArmorTemplates_accountEntryERC: 'ACCOUNT',
			raiDangerousLevel: 'lowAndAbove',
			raiHarassmentLevel: 'mediumAndAbove',
			raiHateSpeechLevel: 'high',
			raiSexuallyExplicitLevel: 'none',
			sdpFilterEnabled: false,
			title_i18n: {en_US: 'Loaded Title'},
		});

		const {result} = renderModelArmorHook({
			externalReferenceCode: 'TEMPLATE_X',
		});

		await waitFor(() => {
			expect(result.current.values.title_i18n).toEqual({
				en_US: 'Loaded Title',
			});
		});

		expect(result.current.values.active).toBe(false);
		expect(result.current.values.guardrailType).toBe('output');
		expect(result.current.values.location).toBe('us-central1');
		expect(result.current.values.raiDangerousLevel).toBe('lowAndAbove');
	});

	it('shows an error toast when the fetch rejects', async () => {
		mockGetModelArmorTemplate.mockRejectedValueOnce(new Error('Boom'));

		renderModelArmorHook({externalReferenceCode: 'TEMPLATE_X'});

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message: 'failed-to-load-model-armor-template-data',
					type: 'danger',
				})
			);
		});
	});

	it('skips the fetch when externalReferenceCode is empty', async () => {
		renderModelArmorHook({externalReferenceCode: ''});

		await waitFor(() => {
			expect(mockGetModelArmorTemplate).not.toHaveBeenCalled();
		});
	});
});

describe('useModelArmorTemplateForm', () => {
	beforeEach(() => {
		mockGetModelArmorTemplate.mockReset();
		mockPutModelArmorTemplate.mockReset();
		mockOpenToast.mockReset();
	});

	describe('initial values', () => {
		it('applies sensible defaults for picker and boolean fields', () => {
			const {result} = renderModelArmorHook();

			expect(result.current.values.active).toBe(true);
			expect(result.current.values.guardrailType).toBe('input');
			expect(result.current.values.piAndJailbreakConfidenceLevel).toBe(
				'mediumAndAbove'
			);
			expect(result.current.values.raiDangerousLevel).toBe('none');
			expect(result.current.values.maliciousUriFilterEnabled).toBe(false);
		});

		it('seeds the account-relationship field from the prop', () => {
			const {result} = renderModelArmorHook({
				accountEntryExternalReferenceCode: 'ACCOUNT_42',
			});

			expect(
				result.current.values
					.r_accountToAIHubModelArmorTemplates_accountEntryERC
			).toBe('ACCOUNT_42');
		});
	});

	describe('setField', () => {
		it('preserves type safety across boolean fields', async () => {
			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('multiLanguageDetectionEnabled', true);
			});

			expect(result.current.values.multiLanguageDetectionEnabled).toBe(
				true
			);
		});

		it('updates one field without touching the rest', async () => {
			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {en_US: 'New Title'});
			});

			expect(result.current.values.title_i18n).toEqual({
				en_US: 'New Title',
			});
			expect(result.current.values.active).toBe(true);
			expect(result.current.values.guardrailType).toBe('input');
		});
	});

	describe('submit', () => {
		it('shows a danger toast when the API response lacks an ERC', async () => {
			mockPutModelArmorTemplate.mockResolvedValueOnce({});

			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
				result.current.setField('location', 'us-central1');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'failed-to-save-model-armor-template',
						type: 'danger',
					})
				);
			});
		});

		it('shows a generic error toast when the PUT throws', async () => {
			mockPutModelArmorTemplate.mockRejectedValueOnce(new Error('Boom'));

			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
				result.current.setField('location', 'us-central1');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'an-unexpected-error-occurred',
						type: 'danger',
					})
				);
			});
		});

		it('shows a success toast when the API echoes back the ERC', async () => {
			mockPutModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
			});

			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
				result.current.setField('location', 'us-central1');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'model-armor-template-saved-successfully',
						type: 'success',
					})
				);
			});
		});
	});

	describe('validate', () => {
		it('clears errors once required fields are filled', async () => {
			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBe('required');
			});

			mockPutModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
			});

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
				result.current.setField('location', 'us-central1');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBeUndefined();
				expect(
					result.current.errors.externalReferenceCode
				).toBeUndefined();
				expect(result.current.errors.location).toBeUndefined();
			});
		});

		it('flags missing required fields on submit', async () => {
			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBe('required');
				expect(result.current.errors.externalReferenceCode).toBe(
					'required'
				);
				expect(result.current.errors.location).toBe('required');
			});

			expect(mockPutModelArmorTemplate).not.toHaveBeenCalled();
		});
	});
});

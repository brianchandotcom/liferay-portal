/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook, waitFor} from '@testing-library/react';

import {useModelArmorTemplateForm} from '../../../../src/main/resources/META-INF/resources/js/model_armor_template_form/hooks/useModelArmorTemplateForm';

const mockGetModelArmorTemplate = jest.fn();
const mockPostModelArmorTemplate = jest.fn();
const mockPutModelArmorTemplate = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/model_armor_template_form/services/ModelArmorTemplateService',
	() => ({
		getModelArmorTemplate: (...args: any[]) =>
			mockGetModelArmorTemplate(...args),
		postModelArmorTemplate: (...args: any[]) =>
			mockPostModelArmorTemplate(...args),
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
	externalReferenceCode = '',
}: {
	externalReferenceCode?: string;
} = {}) {
	return renderHook(() =>
		useModelArmorTemplateForm({
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
			maliciousUriFilterEnabled: true,
			multilanguageDetectionEnabled: false,
			piAndJailbreakConfidenceLevel: 'high',
			piAndJailbreakFilterEnabled: true,
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
		expect(result.current.values.raiDangerousLevel).toBe('lowAndAbove');
	});

	it('shows an error toast when the fetch rejects', async () => {
		mockGetModelArmorTemplate.mockRejectedValueOnce(new Error('Boom'));

		renderModelArmorHook({externalReferenceCode: 'TEMPLATE_X'});

		await waitFor(() => {
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message: 'failed-to-load-guardrail-data',
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
		mockPostModelArmorTemplate.mockReset();
		mockPutModelArmorTemplate.mockReset();
		mockOpenToast.mockReset();
	});

	describe('initial values', () => {
		it('applies sensible defaults for picker and boolean fields', () => {
			const {result} = renderModelArmorHook();

			expect(result.current.values.active).toBe(true);
			expect(result.current.values.guardrailType).toBe('');
			expect(result.current.values.piAndJailbreakConfidenceLevel).toBe(
				'mediumAndAbove'
			);
			expect(result.current.values.raiDangerousLevel).toBe('none');
			expect(result.current.values.maliciousUriFilterEnabled).toBe(false);
		});
	});

	describe('setField', () => {
		it('preserves type safety across boolean fields', async () => {
			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('multilanguageDetectionEnabled', true);
			});

			expect(result.current.values.multilanguageDetectionEnabled).toBe(
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
			expect(result.current.values.guardrailType).toBe('');
		});
	});

	describe('submit', () => {
		it("surfaces the thrown error's message in a danger toast", async () => {
			mockPostModelArmorTemplate.mockRejectedValueOnce(
				new Error('External reference code already in use')
			);

			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'External reference code already in use',
						type: 'danger',
					})
				);
			});
		});

		it('falls back to the localized error when the thrown error has no message', async () => {
			mockPostModelArmorTemplate.mockRejectedValueOnce(new Error());

			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockOpenToast).toHaveBeenCalledWith(
					expect.objectContaining({
						message: 'failed-to-save-guardrail',
						type: 'danger',
					})
				);
			});
		});

		it('calls postModelArmorTemplate in create mode', async () => {
			mockPostModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
			});

			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockPostModelArmorTemplate).toHaveBeenCalled();
			});

			expect(mockPutModelArmorTemplate).not.toHaveBeenCalled();
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message: 'guardrail-saved-successfully',
					type: 'success',
				})
			);
		});

		it('calls putModelArmorTemplate in edit mode', async () => {
			mockGetModelArmorTemplate.mockResolvedValueOnce({
				active: true,
				description: '',
				externalReferenceCode: 'TEMPLATE_X',
				guardrailType: 'input',
				location: 'us-central1',
				maliciousUriFilterEnabled: false,
				multilanguageDetectionEnabled: false,
				piAndJailbreakConfidenceLevel: 'mediumAndAbove',
				piAndJailbreakFilterEnabled: false,
				raiDangerousLevel: 'none',
				raiHarassmentLevel: 'none',
				raiHateSpeechLevel: 'none',
				raiSexuallyExplicitLevel: 'none',
				sdpFilterEnabled: false,
				title_i18n: {en_US: 'My Template'},
			});
			mockPutModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
			});

			const {result} = renderModelArmorHook({
				externalReferenceCode: 'TEMPLATE_X',
			});

			await waitFor(() => {
				expect(result.current.values.externalReferenceCode).toBe(
					'TEMPLATE_X'
				);
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(mockPutModelArmorTemplate).toHaveBeenCalled();
			});

			expect(mockPostModelArmorTemplate).not.toHaveBeenCalled();
			expect(mockOpenToast).toHaveBeenCalledWith(
				expect.objectContaining({
					message: 'guardrail-saved-successfully',
					type: 'success',
				})
			);
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

			mockPostModelArmorTemplate.mockResolvedValueOnce({
				externalReferenceCode: 'TEMPLATE_X',
			});

			await act(async () => {
				result.current.setField('title_i18n', {
					en_US: 'My Template',
				});
				result.current.setField('externalReferenceCode', 'TEMPLATE_X');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBeUndefined();
				expect(
					result.current.errors.externalReferenceCode
				).toBeUndefined();
			});
		});

		it('flags missing required fields on submit', async () => {
			const {result} = renderModelArmorHook();

			await act(async () => {
				result.current.setField('externalReferenceCode', '');
			});

			await act(async () => {
				result.current.handleSubmit();
			});

			await waitFor(() => {
				expect(result.current.errors.title_i18n).toBe('required');
				expect(result.current.errors.externalReferenceCode).toBe(
					'required'
				);
			});

			expect(mockPostModelArmorTemplate).not.toHaveBeenCalled();
			expect(mockPutModelArmorTemplate).not.toHaveBeenCalled();
		});
	});
});

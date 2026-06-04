/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	getGuardrail,
	postGuardrail,
	putGuardrail,
} from '../../../../src/main/resources/META-INF/resources/js/guardrail_form/services/GuardrailService';

const mockFetch = jest.fn();

jest.mock('frontend-js-web', () => ({
	fetch: (...args: any[]) => mockFetch(...args),
}));

const GET_BASE_URI = '/o/ai-hub/guardrails';
const POST_PUT_BASE_URI = '/o/ai-hub/v1.0/guardrails';

describe('GuardrailService', () => {
	beforeEach(() => {
		mockFetch.mockReset();
	});

	const fullPickerFields = {
		guardrailType: {key: 'input', name: 'Input'},
		piAndJailbreakConfidenceLevel: {
			key: 'mediumAndAbove',
			name: 'Medium and Above',
		},
		raiDangerousLevel: {key: 'none', name: 'None'},
		raiHarassmentLevel: {key: 'none', name: 'None'},
		raiHateSpeechLevel: {key: 'none', name: 'None'},
		raiSexuallyExplicitLevel: {key: 'none', name: 'None'},
	};

	describe('getGuardrail', () => {
		it('targets the by-external-reference-code endpoint', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () => Promise.resolve(fullPickerFields),
				ok: true,
			});

			await getGuardrail('TEMPLATE_X');

			expect(mockFetch).toHaveBeenCalledWith(
				`${GET_BASE_URI}/by-external-reference-code/TEMPLATE_X`,
				expect.objectContaining({method: 'GET'})
			);
		});

		it('throws when the response is not ok', async () => {
			mockFetch.mockResolvedValueOnce({ok: false});

			await expect(getGuardrail('TEMPLATE_X')).rejects.toThrow();
		});

		it('unwraps picker fields shaped as {key, name} into bare keys', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({
						active: true,
						externalReferenceCode: 'TEMPLATE_X',
						guardrailType: {key: 'output', name: 'Output'},
						piAndJailbreakConfidenceLevel: {
							key: 'high',
							name: 'High',
						},
						raiDangerousLevel: {key: 'lowAndAbove', name: 'Low'},
						raiHarassmentLevel: {
							key: 'mediumAndAbove',
							name: 'Med',
						},
						raiHateSpeechLevel: {key: 'high', name: 'High'},
						raiSexuallyExplicitLevel: {key: 'none', name: 'None'},
						title_i18n: {en_US: 'My Template'},
					}),
				ok: true,
			});

			const result = await getGuardrail('TEMPLATE_X');

			expect(result.guardrailType).toBe('output');
			expect(result.piAndJailbreakConfidenceLevel).toBe('high');
			expect(result.raiDangerousLevel).toBe('lowAndAbove');
			expect(result.raiHarassmentLevel).toBe('mediumAndAbove');
			expect(result.raiHateSpeechLevel).toBe('high');
			expect(result.raiSexuallyExplicitLevel).toBe('none');
		});
	});

	describe('postGuardrail', () => {
		it('returns the parsed response body', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({
						externalReferenceCode: 'TEMPLATE_X',
						title_i18n: {en_US: 'Saved'},
					}),
				ok: true,
			});

			const result = await postGuardrail({
				externalReferenceCode: 'TEMPLATE_X',
			} as any);

			expect(result.title_i18n.en_US).toBe('Saved');
		});

		it('sends a POST with the template serialized as JSON', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({externalReferenceCode: 'TEMPLATE_X'}),
				ok: true,
			});

			const template = {
				active: true,
				externalReferenceCode: 'TEMPLATE_X',
				title_i18n: {en_US: 'My Template'},
			} as any;

			await postGuardrail(template);

			expect(mockFetch).toHaveBeenCalledWith(
				POST_PUT_BASE_URI,
				expect.objectContaining({
					body: JSON.stringify(template),
					headers: {'Content-Type': 'application/json'},
					method: 'POST',
				})
			);
		});

		it("throws with the server's detail when the response is not ok", async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({
						detail: 'External reference code already in use',
					}),
				ok: false,
			});

			await expect(
				postGuardrail({
					externalReferenceCode: 'TEMPLATE_X',
				} as any)
			).rejects.toThrow('External reference code already in use');
		});
	});

	describe('putGuardrail', () => {
		it('returns the parsed response body', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({
						externalReferenceCode: 'TEMPLATE_X',
						title_i18n: {en_US: 'Saved'},
					}),
				ok: true,
			});

			const result = await putGuardrail({
				externalReferenceCode: 'TEMPLATE_X',
			} as any);

			expect(result.title_i18n.en_US).toBe('Saved');
		});

		it('sends a PUT with the template serialized as JSON', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({externalReferenceCode: 'TEMPLATE_X'}),
				ok: true,
			});

			const template = {
				active: true,
				externalReferenceCode: 'TEMPLATE_X',
				title_i18n: {en_US: 'My Template'},
			} as any;

			await putGuardrail(template);

			expect(mockFetch).toHaveBeenCalledWith(
				`${POST_PUT_BASE_URI}/by-external-reference-code/TEMPLATE_X`,
				expect.objectContaining({
					body: JSON.stringify(template),
					headers: {'Content-Type': 'application/json'},
					method: 'PUT',
				})
			);
		});

		it("throws with the server's detail when the response is not ok", async () => {
			mockFetch.mockResolvedValueOnce({
				json: () =>
					Promise.resolve({
						detail: 'External reference code already in use',
					}),
				ok: false,
			});

			await expect(
				putGuardrail({
					externalReferenceCode: 'TEMPLATE_X',
				} as any)
			).rejects.toThrow('External reference code already in use');
		});

		it('throws with an empty message when the response body is not JSON', async () => {
			mockFetch.mockResolvedValueOnce({
				json: () => Promise.reject(new Error('not json')),
				ok: false,
			});

			await expect(
				putGuardrail({
					externalReferenceCode: 'TEMPLATE_X',
				} as any)
			).rejects.toThrow();
		});
	});
});

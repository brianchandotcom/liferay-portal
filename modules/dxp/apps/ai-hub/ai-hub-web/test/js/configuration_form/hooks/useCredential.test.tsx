/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react';

import {useCredential} from '../../../../src/main/resources/META-INF/resources/js/configuration_form/hooks/useCredential';

const mockGetCredential = jest.fn();
const mockOpenToast = jest.fn();

jest.mock(
	'../../../../src/main/resources/META-INF/resources/js/configuration_form/services/ConfigurationService',
	() => ({
		getCredential: (...args: any[]) => mockGetCredential(...args),
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

describe('useCredential', () => {
	beforeEach(() => {
		mockGetCredential.mockReset();
		mockOpenToast.mockReset();
	});

	it('hides the client secret', async () => {
		mockGetCredential.mockResolvedValueOnce({clientSecret: 'SECRET_X'});

		const {result} = renderHook(() => useCredential());

		await act(async () => {
			await result.current.revealClientSecret();
		});

		expect(result.current.clientSecret).toBe('SECRET_X');

		act(() => {
			result.current.hideClientSecret();
		});

		expect(result.current.clientSecret).toBeNull();
	});

	it('reveals the client secret', async () => {
		mockGetCredential.mockResolvedValueOnce({clientSecret: 'SECRET_X'});

		const {result} = renderHook(() => useCredential());

		await act(async () => {
			await result.current.revealClientSecret();
		});

		expect(mockGetCredential).toHaveBeenCalledTimes(1);
		expect(result.current.clientSecret).toBe('SECRET_X');
		expect(result.current.revealing).toBe(false);
	});

	it('shows an error toast and keeps the secret hidden when revealing fails', async () => {
		mockGetCredential.mockRejectedValueOnce(new Error('Boom'));

		const {result} = renderHook(() => useCredential());

		await act(async () => {
			await result.current.revealClientSecret();
		});

		expect(mockOpenToast).toHaveBeenCalledWith(
			expect.objectContaining({
				message: 'failed-to-load-credentials',
				type: 'danger',
			})
		);
		expect(result.current.clientSecret).toBeNull();
	});

	it('starts with the secret hidden and no request made', () => {
		const {result} = renderHook(() => useCredential());

		expect(result.current.clientSecret).toBeNull();
		expect(result.current.revealing).toBe(false);
		expect(mockGetCredential).not.toHaveBeenCalled();
	});
});

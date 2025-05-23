/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {callWindowGlobalFunction} from '../../utils/callWindowGlobalFunction';

describe('callWindowGlobalFunction', () => {
	afterEach(() => {
		delete (window as any).registeredFn;
	});

	it('calls the passed function if it is registered on the window', () => {
		const mockFn = jest.fn();

		(window as any).registeredFn = mockFn;

		callWindowGlobalFunction('registeredFn');

		expect(mockFn).toHaveBeenCalled();

		mockFn.mockReset();
	});

	it('does not call the passed function if it is not registered on the window', () => {
		const mockFn = jest.fn();

		callWindowGlobalFunction('mockFn');

		expect(mockFn).not.toHaveBeenCalled();
	});
});

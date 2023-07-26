/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {act, renderHook} from '@testing-library/react-hooks';

import useSessionState from '../../src/main/resources/META-INF/resources/hooks/useSessionState';

describe('useSessionState', () => {
	beforeEach(() => {
		jest.spyOn(sessionStorage.__proto__, 'getItem');
		jest.spyOn(sessionStorage.__proto__, 'setItem');
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('gets initial value from session storage', () => {
		sessionStorage.getItem.mockImplementation(() => JSON.stringify('hey!'));
		sessionStorage.setItem.mockImplementation(() => {});

		const {result} = renderHook(() => useSessionState('key'));
		const [value] = result.current;

		expect(value).toBe('hey!');
	});

	it('uses given default value if there is nothing in sessionStorage', () => {
		const {result} = renderHook(() => useSessionState('key', 'default'));
		const [value] = result.current;

		expect(value).toBe('default');
	});

	it('updates sessionStorage when value is updated', () => {
		const {result} = renderHook(() => useSessionState('key'));
		const [, setValue] = result.current;

		act(() => {
			setValue(1234);
		});

		expect(window.sessionStorage.setItem).toHaveBeenCalledWith(
			'key',
			JSON.stringify(1234)
		);
	});
});

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {act, renderHook} from '@testing-library/react-hooks';

import {useSessionState} from '../../../../src/main/resources/META-INF/resources/page_editor/core/hooks/useSessionState';

describe('useSessionState', () => {
	beforeEach(() => {
		jest.spyOn(window.sessionStorage.__proto__, 'getItem');
		jest.spyOn(window.sessionStorage.__proto__, 'setItem');
	});

	afterEach(() => {
		jest.restoreAllMocks();
	});

	it('gets initial value from session storage', () => {
		window.sessionStorage.getItem.mockImplementation(() =>
			JSON.stringify('hey!')
		);

		const {result} = renderHook(() => useSessionState('key'));
		const [value] = result.current;

		expect(value).toBe('hey!');
	});

	it('uses given default value is there is nothing in sessionStorage', () => {
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

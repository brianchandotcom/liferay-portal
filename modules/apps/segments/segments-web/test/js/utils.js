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

import {fireEvent} from '@testing-library/react';

export function testControlledInput({
	element,
	mockFunc,
	newValue = 'Liferay',
	newValueExpected,
	value
}) {
	expect(element.value).toBe(value);

	fireEvent.change(element, {
		target: {value: newValue}
	});

	expect(mockFunc.mock.calls.length).toBe(1);

	expect(mockFunc.mock.calls[0][0]).toMatchObject(
		newValueExpected ? {value: newValueExpected} : {value: newValue}
	);

	expect(element.value).toBe(value);
}

export function testControlledDateInput({
	element,
	mockOnChangeFunc,
	newValue = 'Liferay',
	newValueExpected,
	newValueOnChange,
	value
}) {
	expect(element.value).toBe(value);

	fireEvent.change(element, {
		target: {value: newValue}
	});

	expect(mockOnChangeFunc.mock.calls.length).toBe(0);

	fireEvent.blur(element);

	expect(mockOnChangeFunc.mock.calls.length).toBe(1);

	expect(mockOnChangeFunc.mock.calls[0][0]).toMatchObject({
		value: newValueOnChange
	});

	expect(element.value).toBe(newValueExpected);
}

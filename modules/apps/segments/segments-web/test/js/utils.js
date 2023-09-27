/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fireEvent, waitFor} from '@testing-library/react';

export function testControlledInput({
	element,
	mockFunc,
	newValue = 'Liferay',
	newValueExpected,
	value,
}) {
	expect(element.value).toBe(value);

	fireEvent.change(element, {
		target: {value: newValue},
	});

	expect(mockFunc.mock.calls.length).toBe(1);

	expect(mockFunc.mock.calls[0][0]).toMatchObject(
		newValueExpected ? {value: newValueExpected} : {value: newValue}
	);

	waitFor(() => expect(element.value).toBe(value));
}

export function testControlledDateInput({
	element,
	inputChange = true,
	mockOnChangeFunc,
	newValue = 'Liferay',
	newValueExpected,
	newValueOnChange,
	value,
}) {
	expect(element.value).toBe(value);

	fireEvent.change(element, {
		target: {value: newValue},
	});

	expect(mockOnChangeFunc.mock.calls.length).toBe(0);

	fireEvent.blur(element);

	if (inputChange) {
		if (value === newValueExpected) {
			expect(mockOnChangeFunc.mock.calls.length).toBe(0);
		}
		else {
			expect(mockOnChangeFunc.mock.calls.length).toBe(1);

			expect(mockOnChangeFunc.mock.calls[0][0]).toMatchObject({
				value: newValueOnChange,
			});
		}
	}
	else {
		expect(mockOnChangeFunc.mock.calls.length).toBe(0);
	}
	expect(element.value).toBe(newValueExpected);
}

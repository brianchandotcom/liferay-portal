import {fireEvent} from 'react-testing-library';

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

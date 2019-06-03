import React from 'react';
import SelectEntityInput from 'components/inputs/SelectEntityInput.es';
import {cleanup, render} from 'react-testing-library';

const ENTITY_SELECT_INPUT_TESTID = 'entity-select-input';

describe('SelectEntityInput', () => {
	afterEach(cleanup);

	it('should render type id', () => {
		const mockOnChange = jest.fn();

		const defaultNumberValue = '12345';

		const {getByTestId} = render(
			<SelectEntityInput
				onChange={mockOnChange}
				selectEntity={{
					id: 'entitySelect',
					title: 'Select Entity Test',
					url: ''
				}}
				value={defaultNumberValue}
			/>
		);

		const element = getByTestId(ENTITY_SELECT_INPUT_TESTID);

		expect(element.value).toBe(defaultNumberValue);
	});
});

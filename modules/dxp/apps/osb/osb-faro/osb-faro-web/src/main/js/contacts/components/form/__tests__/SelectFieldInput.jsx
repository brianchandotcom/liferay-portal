import * as data from 'test/data';
import React from 'react';
import {FormSelectFieldInput} from '../SelectFieldInput';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SelectFieldInput', () => {
	it('should render', () => {
		const {container} = render(
			<FormSelectFieldInput
				field={{name: 'foo'}}
				form={data.mockForm()}
				groupId='23'
				name='foo'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with a label', () => {
		const label = 'bar';

		const {getByText} = render(
			<FormSelectFieldInput
				field={{name: 'foo'}}
				form={data.mockForm()}
				groupId='23'
				label={label}
				name='foo'
			/>
		);

		expect(getByText(label)).toBeTruthy();
	});
});

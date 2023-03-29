import DateInput from '../DateInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Property} from 'shared/util/records';

jest.unmock('react-dom');

describe('DateInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DateInput
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render with data', () => {
		const {container} = render(
			<DateInput
				displayValue='Start Date'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				value='12/12/12'
			/>
		);

		expect(container).toMatchSnapshot();
	});
});

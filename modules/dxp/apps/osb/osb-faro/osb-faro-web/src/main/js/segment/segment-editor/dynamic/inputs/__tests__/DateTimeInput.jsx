import DateTimeInput from '../DateTimeInput';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {Property} from 'shared/util/records';

jest.unmock('react-dom');

describe('DateTimeInput', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DateTimeInput
				displayValue='Start Date Time'
				operatorRenderer={() => <div>{'operator'}</div>}
				property={new Property()}
				value='2012-12-12T00:00:00.000Z'
			/>
		);

		expect(container).toMatchSnapshot();
	});
});

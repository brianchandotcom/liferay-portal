import DateFilter from '../DateFilter';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DateFilter', () => {
	it('should render', () => {
		const {container} = render(<DateFilter onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});

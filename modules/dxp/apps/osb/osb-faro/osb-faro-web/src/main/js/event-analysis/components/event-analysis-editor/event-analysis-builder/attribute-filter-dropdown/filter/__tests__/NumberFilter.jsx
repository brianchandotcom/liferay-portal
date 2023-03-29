import NumberFilter from '../NumberFilter';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('NumberFilter', () => {
	it('should render', () => {
		const {container} = render(<NumberFilter onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});

import DurationFilter from '../DurationFilter';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DurationFilter', () => {
	it('should render', () => {
		const {container} = render(<DurationFilter onSubmit={jest.fn()} />);

		expect(container).toMatchSnapshot();
	});
});

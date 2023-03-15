import ListGroup from '../index';
import React from 'react';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('ListGroup', () => {
	it('should render', () => {
		const {container} = render(<ListGroup />);

		expect(container).toMatchSnapshot();
	});
});

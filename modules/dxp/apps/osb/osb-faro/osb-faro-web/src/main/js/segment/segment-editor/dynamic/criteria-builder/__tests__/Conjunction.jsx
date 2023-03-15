import Conjunction from '../Conjunction';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Conjunction', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Conjunction conjunctionName='and' />);

		expect(container).toMatchSnapshot();
	});
});

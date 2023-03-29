import Icon from '../Icon';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('Icon', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<Icon symbol='file' />);
		expect(container).toMatchSnapshot();
	});
});

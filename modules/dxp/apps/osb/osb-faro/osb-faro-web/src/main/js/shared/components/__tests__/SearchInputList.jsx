import React from 'react';
import SearchInputList from '../SearchInputList';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('SearchInputList', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(<SearchInputList items={[]} />);

		expect(container).toMatchSnapshot();
	});
});

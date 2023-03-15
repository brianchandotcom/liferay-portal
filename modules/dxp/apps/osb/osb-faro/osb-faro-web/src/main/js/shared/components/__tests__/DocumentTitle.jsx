jest.unmock('../DocumentTitle');

import DocumentTitle from '../DocumentTitle';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DocumentTitle', () => {
	afterEach(cleanup);

	it('should change the document title with analytics cloud appended', () => {
		render(<DocumentTitle title='test' />);

		expect(document.title).toEqual('test - Analytics Cloud');
	});
});

import AddDataSource from '../AddDataSource';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('DistributionCard AddDataSource', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<BrowserRouter>
				<AddDataSource groupId='123' />
			</BrowserRouter>
		);

		expect(container).toMatchSnapshot();
	});
});

import InterestDetails from '../InterestDetails';
import mockDate from 'test/mock-date';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

describe('InterestDetails', () => {
	beforeAll(() => mockDate());
	afterAll(() => jest.restoreMocks());

	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<BrowserRouter>
				<InterestDetails
					account={{}}
					groupId='123'
					id='123'
					interestId='123'
				/>
			</BrowserRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

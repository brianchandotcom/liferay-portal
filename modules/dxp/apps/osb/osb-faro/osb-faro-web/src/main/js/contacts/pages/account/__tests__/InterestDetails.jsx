import InterestDetails from '../InterestDetails';
import mockDate from 'test/mock-date';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

describe('InterestDetails', () => {
	beforeAll(() => mockDate());
	afterAll(() => jest.restoreMocks());

	afterEach(cleanup);

	it('should render', () => {
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

		expect(container).toMatchSnapshot();
	});
});

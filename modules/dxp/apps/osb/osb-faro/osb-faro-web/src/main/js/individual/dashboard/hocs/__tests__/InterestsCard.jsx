import InterestsCard from '../InterestsCard';
import React from 'react';
import {BrowserRouter} from 'react-router-dom';
import {cleanup, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockIndividualInterestsReq} from 'test/graphql-data';
import {omit} from 'lodash';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '123'
	})
}));

const WrappedComponent = ({data}) => (
	<MockedProvider
		mocks={[
			mockIndividualInterestsReq(
				variables => omit(variables, 'keywords'),
				data && {data}
			)
		]}
	>
		<BrowserRouter>
			<InterestsCard />
		</BrowserRouter>
	</MockedProvider>
);

describe('InterestsCard', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(<WrappedComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('renders with empty data', () => {
		const {container} = render(<WrappedComponent data={[]} />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

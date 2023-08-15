import client from 'shared/apollo/client';
import ExperimentOverviewPage from '../ExperimentOverviewPage';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockExperimentRootReq, mockTimeRangeReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const WrappedComponent = ({status}) => (
	<ApolloProvider client={client}>
		<Provider store={mockStore() as any}>
			<MemoryRouter
				initialEntries={['/workspace/1000/2000/tests/overview/123']}
			>
				<Route path={Routes.TESTS_OVERVIEW}>
					<MockedProvider
						mocks={[
							mockTimeRangeReq(),
							mockExperimentRootReq({status})
						]}
					>
						<ExperimentOverviewPage
							router={{
								params: {
									channelId: '2000',
									groupId: '1000',
									id: '123'
								},
								query: {}
							}}
						/>
					</MockedProvider>
				</Route>
			</MemoryRouter>
		</Provider>
	</ApolloProvider>
);

describe('ExperimentOverviewPage', () => {
	it('renders review and delete button to experiment to status DRAFT', async () => {
		const {container, getByRole} = render(
			<WrappedComponent status='DRAFT' />
		);

		await waitForLoadingToBeRemoved(container);

		const reviewButton = getByRole('link', {
			name: /review/i
		}) as HTMLAnchorElement;
		const deleteButton = getByRole('link', {
			name: /delete/i
		}) as HTMLAnchorElement;

		expect(reviewButton).toBeInTheDocument();
		expect(reviewButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=reviewAndRun'
		);

		expect(deleteButton).toBeInTheDocument();
		expect(deleteButton.href).toEqual(
			'https://www.beryl.com/experiment-test?segmentsExperimentKey=123&segmentsExperimentAction=delete'
		);
	});
});

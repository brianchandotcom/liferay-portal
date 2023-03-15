import BasePage from 'shared/components/base-page';
import client from 'shared/apollo/client';
import React from 'react';
import SessionCard from '../SessionCard';
import {ApolloProvider} from '@apollo/react-components';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockExperimentSessionHistogramReq,
	mockExperimentSessionVariantsHistogramReq
} from 'test/graphql-data';
import {StateProvider} from 'experiments/state';
import {StaticRouter} from 'react-router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

const PER_VARIANT = 'Per Variant';

const MOCK_CONTEXT = {
	filters: {},
	router: {
		params: {
			channelId: '456',
			groupId: '2000',
			id: '123'
		},
		query: {
			rangeKey: '30'
		}
	}
};

describe('SessionCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<StaticRouter>
				<ApolloProvider client={client}>
					<MockedProvider
						mocks={[mockExperimentSessionHistogramReq()]}
					>
						<BasePage.Context.Provider value={MOCK_CONTEXT}>
							<StateProvider>
								<SessionCard />
							</StateProvider>
						</BasePage.Context.Provider>
					</MockedProvider>
				</ApolloProvider>
			</StaticRouter>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render a per variant', async () => {
		const {container, getByText} = render(
			<StaticRouter>
				<ApolloProvider client={client}>
					<MockedProvider
						mocks={[
							mockExperimentSessionHistogramReq(),
							mockExperimentSessionVariantsHistogramReq()
						]}
					>
						<StateProvider>
							<SessionCard />
						</StateProvider>
					</MockedProvider>
				</ApolloProvider>
			</StaticRouter>
		);

		await waitForLoading(container);

		expect(getByText(PER_VARIANT).className).not.toContain('active');

		fireEvent.click(getByText(PER_VARIANT));

		expect(getByText(PER_VARIANT).className).toContain('active');
	});
});

import BasePage from 'shared/components/base-page';
import PerDayChart from '../PerDayChart';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockExperimentVariantsHistogramReq} from 'test/graphql-data';
import {StateProvider} from 'experiments/state';
import {StaticRouter} from 'react-router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

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

describe('PerDayChart', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<StaticRouter>
				<MockedProvider mocks={[mockExperimentVariantsHistogramReq()]}>
					<BasePage.Context.Provider value={MOCK_CONTEXT}>
						<StateProvider>
							<PerDayChart />
						</StateProvider>
					</BasePage.Context.Provider>
				</MockedProvider>
			</StaticRouter>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

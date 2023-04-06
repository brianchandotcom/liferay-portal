import BasePage from 'shared/components/base-page';
import MediansChart from '../MediansChart';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {ExperimentResolver as Experiment} from 'shared/apollo/resolvers';
import {MockedProvider} from '@apollo/react-testing';
import {mockExperimentReq} from 'test/graphql-data';
import {StateProvider} from 'experiments/state';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');
jest.useRealTimers();

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

describe('MediansChart', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<StaticRouter>
				<MockedProvider
					mocks={[mockExperimentReq()]}
					resolvers={{Experiment}}
				>
					<BasePage.Context.Provider value={MOCK_CONTEXT}>
						<StateProvider>
							<MediansChart />
						</StateProvider>
					</BasePage.Context.Provider>
				</MockedProvider>
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

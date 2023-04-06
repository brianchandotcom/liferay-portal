import BasePage from 'shared/components/base-page';
import React from 'react';
import SummaryCard from '../SummaryCard';
import {cleanup, render} from '@testing-library/react';
import {ExperimentResolver as Experiment} from 'shared/apollo/resolvers';
import {MockedProvider} from '@apollo/react-testing';
import {mockExperimentReq} from 'test/graphql-data';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {StateProvider} from 'experiments/state';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

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
			rangeKey: RangeKeyTimeRanges.Last30Days
		}
	}
};

describe('SummaryCard', () => {
	afterEach(cleanup);

	it('should render a running SummaryCard', async () => {
		const {container} = render(
			<StaticRouter>
				<MockedProvider
					mocks={[mockExperimentReq()]}
					resolvers={{Experiment}}
				>
					<BasePage.Context.Provider value={MOCK_CONTEXT}>
						<StateProvider>
							<SummaryCard status='RUNNING' timeZoneId='UTC' />
						</StateProvider>
					</BasePage.Context.Provider>
				</MockedProvider>
			</StaticRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

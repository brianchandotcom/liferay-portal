import BasePage from 'shared/components/base-page';
import React from 'react';
import VariantCard from '../index';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {ExperimentResolver as Experiment} from 'shared/apollo/resolvers';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockExperimentReq,
	mockExperimentVariantsHistogramReq
} from 'test/graphql-data';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {Routes} from 'shared/util/router';
import {StateProvider} from 'experiments/state';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');
jest.useRealTimers();

const PER_DAY = 'Per day';

const MOCK_CONTEXT = {
	filters: {},
	router: {
		params: {
			channelId: '123123',
			groupId: '23',
			id: '123'
		},
		query: {
			rangeKey: RangeKeyTimeRanges.Last30Days
		}
	}
};

describe('VariantCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/123123/tests/overview/123']}
			>
				<Route path={Routes.TESTS_OVERVIEW}>
					<MockedProvider
						mocks={[
							mockExperimentReq(),
							mockExperimentVariantsHistogramReq()
						]}
						resolvers={{Experiment}}
					>
						<BasePage.Context.Provider value={MOCK_CONTEXT}>
							<StateProvider>
								<VariantCard label='test' />
							</StateProvider>
						</BasePage.Context.Provider>
					</MockedProvider>
				</Route>
			</MemoryRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render a Per day chart', async () => {
		const {container, getAllByText} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/123123/tests/overview/321321']}
			>
				<Route path={Routes.TESTS_OVERVIEW}>
					<MockedProvider
						mocks={[
							mockExperimentReq(),
							mockExperimentVariantsHistogramReq()
						]}
						resolvers={{Experiment}}
					>
						<BasePage.Context.Provider value={MOCK_CONTEXT}>
							<StateProvider>
								<VariantCard label='test' />
							</StateProvider>
						</BasePage.Context.Provider>
					</MockedProvider>
				</Route>
			</MemoryRouter>
		);

		await waitForLoadingToBeRemoved(container);

		expect(getAllByText(PER_DAY)[0].className).not.toContain('active');

		fireEvent.click(getAllByText(PER_DAY)[0]);

		expect(getAllByText(PER_DAY)[1].className).toContain('active');
	});
});

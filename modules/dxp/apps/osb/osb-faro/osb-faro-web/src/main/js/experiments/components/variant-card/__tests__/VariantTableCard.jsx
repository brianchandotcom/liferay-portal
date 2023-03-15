import BasePage from 'shared/components/base-page';
import React from 'react';
import VariantTableCard from '../VariantTableCard';
import {cleanup, render} from '@testing-library/react';
import {ExperimentResolver as Experiment} from 'shared/apollo/resolvers';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockExperimentReq} from 'test/graphql-data';
import {Routes} from 'shared/util/router';
import {StateProvider} from 'experiments/state';
import {waitForTable} from 'test/helpers';

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

describe('VariantTableCard', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(
			<MemoryRouter
				initialEntries={['/workspace/23/123123/tests/overview/123']}
			>
				<Route path={Routes.TESTS_OVERVIEW}>
					<MockedProvider
						mocks={[mockExperimentReq()]}
						resolvers={{Experiment}}
					>
						<BasePage.Context.Provider value={MOCK_CONTEXT}>
							<StateProvider>
								<VariantTableCard />
							</StateProvider>
						</BasePage.Context.Provider>
					</MockedProvider>
				</Route>
			</MemoryRouter>
		);

		await waitForTable(container);

		expect(container).toMatchSnapshot();
	});
});

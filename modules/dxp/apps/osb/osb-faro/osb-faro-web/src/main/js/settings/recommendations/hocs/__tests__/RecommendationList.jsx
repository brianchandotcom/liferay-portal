import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import RecommendationList from '../RecommendationList';
import RecommendationListQuery from '../../queries/RecommendationListQuery';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockJobBag} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {range} from 'lodash';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

export function mockRecommendationListReq() {
	return {
		request: {
			query: RecommendationListQuery,
			variables: {
				keywords: '',
				size: 10,
				sort: {column: 'name', type: 'DESC'},
				start: 0
			}
		},
		result: {
			data: mockJobBag(range(10).map(i => data.mockRecommendationJob(i)))
		}
	};
}

const defaultProps = {
	router: {params: {groupId: '23'}, query: {delta: '10', page: '1'}}
};

const DefaultComponent = props => (
	<MockedProvider mocks={[mockRecommendationListReq()]}>
		<Provider store={mockStore()}>
			<MemoryRouter
				initialEntries={[
					'/workspace/23/settings/recommendations?delta=10&field=name&sortOrder=DESC'
				]}
			>
				<Route path={Routes.SETTINGS_RECOMMENDATIONS}>
					<RecommendationList
						groupId='23'
						{...defaultProps}
						{...props}
					/>
				</Route>
			</MemoryRouter>
		</Provider>
	</MockedProvider>
);

describe('RecommendationList', () => {
	it('should render', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

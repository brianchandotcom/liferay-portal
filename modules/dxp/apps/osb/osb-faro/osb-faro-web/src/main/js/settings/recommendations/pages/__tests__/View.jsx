import * as data from 'test/data';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import View from '../View';
import {ApolloProvider} from '@apollo/react-components';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockRecommendationJobRunsReq,
	mockRecommendationReq
} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router-dom';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

const defaultProps = {
	router: {params: {groupId: '23'}, query: {delta: '10', page: '1'}}
};

const DefaultComponent = props => (
	<ApolloProvider client={client}>
		<Provider store={mockStore()}>
			<StaticRouter>
				<MockedProvider
					mocks={[
						mockRecommendationJobRunsReq([
							data.mockRecommendationJobRun(0)
						]),
						mockRecommendationReq(data.mockRecommendationJob('321'))
					]}
				>
					<View
						{...defaultProps}
						{...props}
						router={{params: {groupId: '123', jobId: '321'}}}
					/>
				</MockedProvider>
			</StaticRouter>
		</Provider>
	</ApolloProvider>
);

describe('View', () => {
	it('should render', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import RecommendationStepCard from '../index';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router-dom';

jest.unmock('react-dom');

describe('RecommendationStepCard', () => {
	it('should render', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<StaticRouter>
						<RecommendationStepCard
							router={{params: {groupId: '123'}}}
						/>
					</StaticRouter>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});

import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import RecommendationStepCard from '../index';
import {ApolloProvider} from '@apollo/client';
import {createMemoryRouter, RouterProvider} from 'react-router-dom';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';

jest.unmock('react-dom');

describe('RecommendationStepCard', () => {
	it('should render', () => {
		const router = createMemoryRouter([
			{
				element: (
					<ApolloProvider client={client}>
						<Provider store={mockStore()}>
							<RecommendationStepCard
								router={{params: {groupId: '123'}}}
							/>
						</Provider>
					</ApolloProvider>
				),
				path: '/'
			}
		]);

		const {container} = render(<RouterProvider router={router} />);

		expect(container).toMatchSnapshot();
	});
});

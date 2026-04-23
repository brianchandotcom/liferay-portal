import mockStore from 'test/mock-store';
import React from 'react';
import Search from '../Search';
import {InMemoryCache} from '@apollo/client';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {mockSearchStringListReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const WrappedComponent = props => (
	<Provider store={mockStore()}>
		<MemoryRouter
			initialEntries={['/workspace/23/settings/definitions/search']}
		>
			<Route path='/workspace/:groupId/settings/definitions/search'>
				<MockedProvider
					cache={
						new InMemoryCache({
							addTypename: false,
							freezeResults: false
						})
					}
					mocks={[mockSearchStringListReq()]}
				>
					<Search groupId='23' {...props} />
				</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe('Search', () => {
	it('should render', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

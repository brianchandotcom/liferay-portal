import mockStore from 'test/mock-store';
import React from 'react';
import Search from '../Search';
import {MockedProvider} from '@apollo/react-testing';
import {mockSearchStringListReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.useRealTimers();

describe('Search', () => {
	it('should render', async () => {
		const {container} = render(
			<MockedProvider mocks={[mockSearchStringListReq()]}>
				<Provider store={mockStore()}>
					<StaticRouter>
						<Search groupId='23' />
					</StaticRouter>
				</Provider>
			</MockedProvider>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});
});

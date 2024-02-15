import client from 'shared/apollo/client';
import DateRangeInput from '../DateRangeInput';
import mockStore from 'test/mock-store';
import moment from 'moment';
import React from 'react';
import {ApolloProvider} from '@apollo/react-hooks';
import {cleanup, render} from '@testing-library/react';
import {MockedProvider} from '@apollo/react-testing';
import {mockPreferenceReq} from 'test/graphql-data';
import {Provider} from 'react-redux';

jest.unmock('react-dom');

jest.mock('shared/hooks/useTimeZone', () => ({
	useTimeZone: () => ({timeZoneId: 'UTC'})
}));

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '2000',
		query: {
			rangeKey: '30'
		}
	})
}));

describe('DateRangeInput', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {container} = render(
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider mocks={[mockPreferenceReq()]}>
						<DateRangeInput
							value={{
								end: moment(100000000000),
								start: moment(0)
							}}
						/>
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		);

		expect(container).toMatchSnapshot();
	});
});

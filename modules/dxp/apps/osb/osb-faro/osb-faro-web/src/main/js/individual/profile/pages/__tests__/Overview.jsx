import * as data from 'test/data';
import mockStore from 'test/mock-store';
import Overview from '../Overview';
import React from 'react';
import {Individual} from 'shared/util/records';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockEventMetrics,
	mockSessions,
	mockTimeRangeReq
} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const variables = {channelId: undefined};

describe('IndividualOverview', () => {
	it('should render', async () => {
		const {container} = render(
			<MockedProvider
				mocks={[
					mockEventMetrics(variables),
					mockTimeRangeReq(),
					mockSessions(variables)
				]}
			>
				<Provider store={mockStore()}>
					<StaticRouter>
						<Overview
							groupId='23'
							id='test'
							individual={data.getImmutableMock(
								Individual,
								data.mockIndividual
							)}
						/>
					</StaticRouter>
				</Provider>
			</MockedProvider>
		);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

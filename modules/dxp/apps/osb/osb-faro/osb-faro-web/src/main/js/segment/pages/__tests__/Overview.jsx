import * as data from 'test/data';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import Overview from '../Overview';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {Provider} from 'react-redux';
import {render, waitForElementToBeRemoved} from '@testing-library/react';
import {Segment} from 'shared/util/records';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

describe('SegmentOverview', () => {
	it('should render', async () => {
		const {container} = render(
			<Provider store={mockStore()}>
				<ApolloProvider client={client}>
					<StaticRouter>
						<Overview
							groupId='23'
							id='test'
							segment={data.getImmutableMock(
								Segment,
								data.mockSegment
							)}
						/>
					</StaticRouter>
				</ApolloProvider>
			</Provider>
		);

		await waitForElementToBeRemoved(() =>
			container.querySelector('.spinner-root')
		).then(() => {
			expect(container).toMatchSnapshot();
		});
	});
});

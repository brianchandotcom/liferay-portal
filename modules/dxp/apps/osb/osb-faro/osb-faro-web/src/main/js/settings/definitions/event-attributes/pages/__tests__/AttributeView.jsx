import * as data from 'test/data';
import AttributeView from '../AttributeView';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {getISODate} from 'shared/util/date';
import {MemoryRouter} from 'react-router';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventAttributeDefinitionWithRecentValuesReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Route} from 'react-router-dom';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

const RenderWithRouter = ({children, recentValue}) => (
	<MemoryRouter
		initialEntries={[
			'/workspace/23/settings/definitions/event-attributes/0'
		]}
	>
		<Route path={Routes.SETTINGS_DEFINITIONS_EVENT_ATTRIBUTES_VIEW}>
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MockedProvider
						mocks={[
							mockEventAttributeDefinitionWithRecentValuesReq(
								data.mockEventAttributeDefinition(0, {
									recentValues: [
										{
											__typename: 'EventAttributeValue',
											lastSeenDate: getISODate(
												data.getTimestamp()
											),
											value: 'RecentValue',
											...recentValue
										}
									]
								}),
								{id: '0'}
							)
						]}
					>
						{children}
					</MockedProvider>
				</Provider>
			</ApolloProvider>
		</Route>
	</MemoryRouter>
);

describe('AttributeView', () => {
	it('should render', async () => {
		const {container} = render(
			<RenderWithRouter>
				<AttributeView attributeId='0' groupId='23' />
			</RenderWithRouter>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render with a table', () => {
		const {getByText} = render(
			<RenderWithRouter>
				<AttributeView attributeId='0' groupId='23' />
			</RenderWithRouter>
		);

		jest.runAllTimers();

		expect(getByText('Sample Raw Data')).toBeTruthy();
	});

	it('should render table with empty data if there is no column accessor value', () => {
		const {getByText} = render(
			<RenderWithRouter recentValue={{value: ''}}>
				<AttributeView attributeId='0' groupId='23' />
			</RenderWithRouter>
		);

		jest.runAllTimers();

		expect(getByText('-')).toBeTruthy();
	});
});

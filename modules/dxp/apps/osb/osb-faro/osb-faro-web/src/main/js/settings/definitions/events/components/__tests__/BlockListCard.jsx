import * as data from 'test/data';
import BlockListCard from '../BlockListCard';
import client from 'shared/apollo/client';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockBlockedCustomEventDefinitionsReq} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
import {Provider} from 'react-redux';
import {render} from '@testing-library/react';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

describe('BlockListCard', () => {
	const WrappedComponent = props => (
		<ApolloProvider client={client}>
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={[
						'/workspace/23/settings/definitions/events/block-list'
					]}
				>
					<Route path={Routes.SETTINGS_DEFINITIONS_EVENTS_BLOCK_LIST}>
						<MockedProvider
							mocks={[
								mockBlockedCustomEventDefinitionsReq(
									[
										data.mockBlockedCustomEventDefinition(
											0
										),
										data.mockBlockedCustomEventDefinition(1)
									],
									{keyword: '', size: 2}
								)
							]}
						>
							<BlockListCard groupId='23' {...props} />
						</MockedProvider>
					</Route>
				</MemoryRouter>
			</Provider>
		</ApolloProvider>
	);

	it('should render', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render when the state is empty', async () => {
		const WrappedComponentEmptyState = props => (
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MemoryRouter
						initialEntries={[
							'/workspace/23/settings/definitions/events/block-list'
						]}
					>
						<Route
							path={Routes.SETTINGS_DEFINITIONS_EVENTS_BLOCK_LIST}
						>
							<MockedProvider
								mocks={[
									mockBlockedCustomEventDefinitionsReq([], {
										keyword: '',
										size: 2
									})
								]}
							>
								<BlockListCard groupId='23' {...props} />
							</MockedProvider>
						</Route>
					</MemoryRouter>
				</Provider>
			</ApolloProvider>
		);

		const {container, getByText} = render(<WrappedComponentEmptyState />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(getByText('There are no events blocked.')).toBeInTheDocument();

		expect(
			getByText("To block events, select one from the events' table.")
		).toBeInTheDocument();

		expect(
			getByText(
				'Access our documentation to learn how to manage custom events.'
			)
		).toBeInTheDocument();
	});

	it('should render when the state is empty and with the autofit class', async () => {
		const WrappedComponentEmptyState = props => (
			<ApolloProvider client={client}>
				<Provider store={mockStore()}>
					<MemoryRouter
						initialEntries={[
							'/workspace/23/settings/definitions/events/block-list'
						]}
					>
						<Route
							path={Routes.SETTINGS_DEFINITIONS_EVENTS_BLOCK_LIST}
						>
							<MockedProvider
								mocks={[
									mockBlockedCustomEventDefinitionsReq([], {
										keyword: '',
										size: 2
									})
								]}
							>
								<BlockListCard groupId='23' {...props} />
							</MockedProvider>
						</Route>
					</MemoryRouter>
				</Provider>
			</ApolloProvider>
		);

		const {container} = render(<WrappedComponentEmptyState />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(
			container.querySelector('.no-results-title').textContent
		).toEqual('There are no events blocked.');
		expect(
			container.querySelector('.no-results-description').textContent
		).toEqual(
			"To block events, select one from the events' table.Access our documentation to learn how to manage custom events."
		);
	});
});

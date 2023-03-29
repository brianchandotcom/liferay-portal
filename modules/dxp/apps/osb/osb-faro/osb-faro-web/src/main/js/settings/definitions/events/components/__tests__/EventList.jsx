import * as data from 'test/data';
import client from 'shared/apollo/client';
import EventList from '../EventList';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {fireEvent, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockEventDefinitionsReq} from 'test/graphql-data';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {waitForLoading} from 'test/helpers';

jest.unmock('react-dom');

describe('EventList', () => {
	const WrappedComponent = ({event, ...otherProps}) => (
		<ApolloProvider client={client}>
			<Provider store={mockStore()}>
				<MemoryRouter
					initialEntries={[
						'/workspace/23/settings/definitions/events/default?delta=1'
					]}
				>
					<Route path={Routes.SETTINGS_DEFINITIONS_EVENTS}>
						<MockedProvider
							mocks={[
								mockEventDefinitionsReq([
									data.mockEventDefinition(0, {
										__typename: 'EventDefinition',
										...event
									})
								])
							]}
						>
							<EventList groupId='23' {...otherProps} />
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

	it('should render hide/unhide icon and not move to the right side border of the table when row is clicked', async () => {
		const {container} = render(
			<WrappedComponent
				event={{
					hidden: true
				}}
			/>
		);

		await waitForLoading(container);

		jest.runAllTimers();

		const firstTr = document.querySelector('.clickable');

		fireEvent.click(firstTr);

		expect(
			firstTr.querySelector('.custom-control-input').value
		).toBeTruthy();

		expect(firstTr.querySelector('.row-actions')).toBeTruthy();
	});
});

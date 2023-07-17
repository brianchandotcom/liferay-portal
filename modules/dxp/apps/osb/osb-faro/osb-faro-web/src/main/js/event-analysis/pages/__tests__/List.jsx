import 'test/mock-modal';

import * as useDataSource from 'shared/hooks/useDataSource';
import client from 'shared/apollo/client';
import EventAnalysisList from '../List';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {fireEvent, render} from '@testing-library/react';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {mockEmptyState, mockSuccessState} from 'test/__mocks__/mock-objects';
import {mockEventAnalysisListReq} from 'test/graphql-data';
import {open} from 'shared/actions/modals';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '456',
		groupId: '123'
	})
}));

const eventAnalysis = [
	{
		__typename: 'EventAnalysis',
		dateModified: '2022-01-10T19:49:28.589Z',
		id: '1',
		name: 'My first event analysis',
		userName: 'Test Test'
	},
	{
		__typename: 'EventAnalysis',
		dateModified: '2022-01-11T19:49:28.589Z',
		id: '1',
		name: 'My second event analysis',
		userName: 'Test Test'
	}
];

const WrappedComponent = ({eventAnalyses}) => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<MockedProvider mocks={[mockEventAnalysisListReq(eventAnalyses)]}>
				<MemoryRouter
					initialEntries={['/workspace/123/456/event-analysis']}
				>
					<Route path={Routes.EVENT_ANALYSIS}>
						<EventAnalysisList />
					</Route>
				</MemoryRouter>
			</MockedProvider>
		</ApolloProvider>
	</Provider>
);
const mockUseDataSource = useDataSource;

describe('Event Analysis List', () => {
	it('should render', async () => {
		mockUseDataSource.useDataSource = jest.fn(() => mockSuccessState);

		const {container} = render(
			<WrappedComponent eventAnalyses={eventAnalysis} />
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render empty state', async () => {
		const {container} = render(<WrappedComponent eventAnalyses={[]} />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		const noResults = container.querySelector('.no-results-root');

		expect(noResults).toBeInTheDocument();
		expect(
			container.querySelector('.no-results-description')
		).toHaveTextContent(
			'Create an analysis to get started.Access our documentation to learn more.'
		);
	});

	xit('should open modal to delete the event analysis when clicking on trash button', async () => {
		const {container} = render(
			<WrappedComponent eventAnalyses={eventAnalysis} />
		);

		await waitForLoadingToBeRemoved(container);

		const deleteButton = container.querySelector(
			'table tbody tr button[title="Delete"]'
		);

		fireEvent.click(deleteButton);

		expect(open).toBeCalled();
	});

	it('should render a trash icon when item is checked', async () => {
		const {container, getByTestId} = render(
			<WrappedComponent eventAnalyses={eventAnalysis} />
		);

		await waitForLoadingToBeRemoved(container);

		const managementBar = container.querySelector('.management-bar');

		expect(managementBar.querySelector('.lexicon-icon-trash')).toBeFalsy();

		const selectAllCheckbox = getByTestId('select-all-checkbox');

		fireEvent.click(selectAllCheckbox);

		jest.runAllTimers();

		expect(managementBar.querySelector('.lexicon-icon-trash')).toBeTruthy();
	});
});

describe('EventAnalysisList with no Data Source', () => {
	it('should render EmptyState', () => {
		mockUseDataSource.useDataSource = jest.fn(() => mockEmptyState);

		const {getByText} = render(
			<WrappedComponent eventAnalyses={eventAnalysis} />
		);

		expect(getByText('No Data Sources Connected')).toBeInTheDocument();
		expect(
			getByText('Connect a data source to get started.')
		).toBeInTheDocument();
		expect(
			getByText('Access our documentation to learn more.')
		).toBeInTheDocument();
	});
});

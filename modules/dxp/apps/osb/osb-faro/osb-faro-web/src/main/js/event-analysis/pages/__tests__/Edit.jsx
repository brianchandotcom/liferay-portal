import * as data from 'test/data';
import client from 'shared/apollo/client';
import EventAnalysisEdit from '../Edit';
import mockStore from 'test/mock-store';
import React from 'react';
import {ApolloProvider} from '@apollo/react-components';
import {
	cleanup,
	fireEvent,
	render,
	waitForElement
} from '@testing-library/react';
import {DISPLAY_NAME} from 'shared/util/pagination';
import {DndProvider} from 'react-dnd';
import {EventTypes} from 'event-analysis/utils/types';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/react-testing';
import {
	mockEventAnalysisReq,
	mockEventAttributeDefinitionsReq,
	mockEventDefinitionsReq,
	mockTimeRangeReq
} from 'test/graphql-data';
import {OrderByDirections} from 'shared/util/constants';
import {Provider} from 'react-redux';
import {range} from 'lodash';
import {Routes} from 'shared/util/router';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => ({
		channelId: '123',
		groupId: '456',
		id: '1'
	})
}));

const WrappedComponent = () => (
	<Provider store={mockStore()}>
		<ApolloProvider client={client}>
			<MockedProvider
				mocks={[
					mockTimeRangeReq(),
					mockEventAnalysisReq(),
					mockEventAttributeDefinitionsReq(
						range(10).map(i =>
							data.mockEventAttributeDefinition(i, {
								__typename: 'EventAttributeDefinition'
							})
						),
						{
							eventDefinitionId: '1',
							size: 200,
							sort: {
								column: DISPLAY_NAME,
								type: OrderByDirections.Ascending
							}
						}
					),
					mockEventDefinitionsReq(
						range(10).map(i =>
							data.mockEventDefinition(i, {
								__typename: 'EventDefinition'
							})
						),
						{
							eventType: EventTypes.All,
							hidden: false,
							page: 0,
							size: 200,
							sort: {
								column: DISPLAY_NAME,
								type: OrderByDirections.Ascending
							}
						}
					)
				]}
			>
				<MemoryRouter
					initialEntries={['/workspace/123/456/event-analysis/1']}
				>
					<Route path={Routes.EVENT_ANALYSIS_EDIT}>
						<DndProvider backend={HTML5Backend}>
							<EventAnalysisEdit />
						</DndProvider>
					</Route>
				</MemoryRouter>
			</MockedProvider>
		</ApolloProvider>
	</Provider>
);

describe('Event Analysis Edit', () => {
	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(<WrappedComponent />);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render event analysis with data', async () => {
		const {container, getByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('My First Event Analysis')).toBeTruthy();
		expect(
			container.querySelector('.dropdown-range-key-root button')
				.textContent
		).toEqual('Last 90 days');
		expect(container.querySelector('.event-list').textContent).toBe(
			'assetClicked'
		);
		expect(
			container.querySelectorAll(
				'.attribute-breakdown-section-root .attribute-list .dropdown'
			).length
		).toBe(2);
		expect(
			container.querySelectorAll(
				'.attribute-filter-section-root .attribute-list .dropdown'
			).length
		).toBe(1);
		expect(
			container.querySelector('.compare-to-previous-checkbox input')
				.checked
		).toBeTruthy();
	});

	it('should enable the save button when name is changed', async () => {
		const {container, getByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		const inputName = container.querySelector('input.title-input');

		fireEvent.change(inputName, {
			target: {
				value: 'My First Event Analysis Updated'
			}
		});

		expect(getByText('My First Event Analysis Updated')).toBeTruthy();

		expect(getByText('Save Analysis')).toBeEnabled();
	});

	it('should enable the save button when a new breakdown is added', async () => {
		const {container, getByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Save Analysis')).toBeDisabled();

		const addAttributeButton = container.querySelector(
			'.attribute-breakdown-section-root .add-attribute'
		);

		fireEvent.click(addAttributeButton);

		jest.runAllTimers();

		const dropdown = document.querySelector(
			'.base-dropdown-menu-root.show'
		);

		await waitForElement(() => dropdown);

		const hrefAttributeButton = dropdown.querySelector(
			'.base-dropdown-list li:nth-child(3) button'
		);

		fireEvent.click(hrefAttributeButton);

		jest.runAllTimers();

		expect(
			container.querySelectorAll(
				'.attribute-breakdown-section-root .attribute-list .dropdown'
			).length
		).toBe(3);
		expect(getByText('Save Analysis')).toBeEnabled();
	});

	it('should enable the save button when compareToPrevious checkbox is changed', async () => {
		const {container, getByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Save Analysis')).toBeDisabled();

		const compareToPreviousCheckbox = container.querySelector(
			'.compare-to-previous-checkbox input'
		);

		expect(compareToPreviousCheckbox.checked).toBeTruthy();

		fireEvent.click(compareToPreviousCheckbox);

		expect(compareToPreviousCheckbox.checked).toBeFalsy();

		expect(getByText('Save Analysis')).toBeEnabled();
	});

	it('should enable the save button when range selector is changed', async () => {
		const {container, getByText} = render(<WrappedComponent />);

		jest.runAllTimers();

		await waitForLoadingToBeRemoved(container);

		expect(getByText('Save Analysis')).toBeDisabled();

		const rangeKeyButton = container.querySelector(
			'.dropdown-range-key-root > button'
		);

		expect(rangeKeyButton.textContent).toEqual('Last 90 days');

		fireEvent.click(rangeKeyButton);

		jest.runAllTimers();

		const dropdown = document.querySelector(
			'.dropdown-range-key-menu-root.show'
		);

		await waitForElement(() => dropdown);

		fireEvent.click(dropdown.querySelector('ul > li button'));

		jest.runAllTimers();

		expect(rangeKeyButton.textContent).toEqual('Last 24 hours');

		expect(getByText('Save Analysis')).toBeEnabled();
	});
});

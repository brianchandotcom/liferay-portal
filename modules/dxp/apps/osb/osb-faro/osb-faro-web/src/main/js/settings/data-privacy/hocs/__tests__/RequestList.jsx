import * as RequestList from '../RequestList';
import mockStore from 'test/mock-store';
import moment from 'moment';
import React from 'react';
import RequestListQuery from '../../queries/RequestListQuery';
import {cleanup, render} from '@testing-library/react';
import {GDPRRequestStatuses, GDPRRequestTypes} from 'shared/util/constants';
import {Map, Set} from 'immutable';
import {MemoryRouter, Route} from 'react-router-dom';
import {mockDataControlTaskBag} from 'test/graphql-data';
import {MockedProvider} from '@apollo/react-testing';
import {Provider} from 'react-redux';
import {Routes} from 'shared/util/router';
import {
	selectAllAndToggle,
	selectFilterDropdownItem,
	waitForLoading
} from 'test/helpers';
import {waitForLoadingToBeRemoved} from 'test/helpers';

const mockItems = [
	{
		batchId: '1',
		completeDate: null,
		createDate: '2019-10-09T00:00',
		emailAddress: 't.smith@nosaints.com',
		id: '10',
		status: GDPRRequestStatuses.Running,
		type: GDPRRequestTypes.Delete
	},
	{
		batchId: '2',
		completeDate: null,
		createDate: '2019-10-09T00:00',
		emailAddress: 'alice.bryant@example.com',
		id: '20',
		status: GDPRRequestStatuses.Running,
		type: GDPRRequestTypes.Unsuppress
	},
	{
		batchId: '3',
		completeDate: '2019-10-05T00:00',
		createDate: '2019-09-09T00:00',
		emailAddress: 'scott.gilbert@example.com',
		id: '30',
		status: GDPRRequestStatuses.Expired,
		type: GDPRRequestTypes.Suppress
	},
	{
		batchId: '4',
		completeDate: '2019-11-10T00:00',
		createDate: '2019-11-09T00:00',
		emailAddress: 'foo@bar.com',
		id: '4',
		status: GDPRRequestStatuses.Completed,
		type: GDPRRequestTypes.Suppress
	},
	{
		batchId: '5',
		completeDate: null,
		createDate: '2019-09-09T00:00',
		emailAddress: 'lillie.foster@example.com',
		id: '50',
		status: GDPRRequestStatuses.Error,
		type: GDPRRequestTypes.Access
	},
	{
		batchId: '6',
		completeDate: null,
		createDate: '2019-09-09T00:00',
		emailAddress: 'bazbuz@example.com',
		id: '60',
		status: GDPRRequestStatuses.Pending,
		type: GDPRRequestTypes.Delete
	},
	{
		batchId: '7',
		completeDate: '2019-12-05T00:00',
		createDate: '2019-12-05T00:00',
		emailAddress: 'scott.gilbert@example.com',
		id: '70',
		status: GDPRRequestStatuses.Completed,
		type: GDPRRequestTypes.Access
	}
];

export function mockRequestListReq() {
	return {
		request: {
			query: RequestListQuery,
			variables: {
				keywords: '',
				size: 10,
				sort: {column: 'createDate', type: 'DESC'},
				start: 0
			}
		},
		result: {
			data: mockDataControlTaskBag(mockItems)
		}
	};
}

jest.unmock('react-dom');

const defaultProps = {
	filterBy: new Map({types: Set()}),
	router: {params: {groupId: '23'}, query: {delta: '10', page: '1'}}
};

const DefaultComponent = props => (
	<MockedProvider mocks={[mockRequestListReq()]}>
		<Provider store={mockStore()}>
			<MemoryRouter
				initialEntries={[
					'/workspace/23/settings/data-privacy/request-log/?delta=10'
				]}
			>
				<Route path={Routes.SETTINGS_DATA_PRIVACY_REQUEST_LOG}>
					<RequestList.default {...defaultProps} {...props} />
				</Route>
			</MemoryRouter>
		</Provider>
	</MockedProvider>
);

describe('RequestList', () => {
	beforeAll(() => {
		RequestList.getTodaysDate = jest.fn(() =>
			moment(new Date('December 09, 2019'))
		);
	});

	afterEach(cleanup);

	it('should render', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});

	it('should render a request row as checkable with a download button if the status is "DONE"', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		const rowElement = container.querySelector(
			'.table > tbody > tr:nth-of-type(4)'
		);

		expect(
			rowElement.querySelector('input[type=checkbox]').disabled
		).toBeFalse();

		expect(
			rowElement.querySelector('.row-inline-actions .button-root')
		).toHaveTextContent('Download');
	});

	it('should render a request row as disabled with no download button if the status is not "DONE"', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		const rowElement = container.querySelector(
			'.table > tbody > tr:nth-of-type(1)'
		);

		expect(
			rowElement.querySelector('input[type=checkbox]').disabled
		).toBeTrue();

		expect(
			rowElement.querySelector('.row-inline-actions button')
		).toBeNull();
	});

	it('should render a request row as disabled with a "download expired" message if the request status is EXPIRED', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoadingToBeRemoved(container);

		jest.runAllTimers();

		const rowElement = container.querySelector(
			'.table > tbody > tr:nth-of-type(3)'
		);

		expect(
			rowElement.querySelector('input[type=checkbox]').disabled
		).toBeTrue();

		expect(
			rowElement.querySelector('.row-inline-actions')
		).toHaveTextContent('Download Expired');
	});

	xit('should filter selected results by request type', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		selectAllAndToggle(container);

		selectFilterDropdownItem(container, 'Access');

		const tableRows = container.querySelectorAll('tbody > tr');

		expect(tableRows.length).toBe(1);

		expect(tableRows[0]).toHaveTextContent('scott.gilbert@example.com');
	});

	xit('should filter selected results by time period', async () => {
		const {container} = render(<DefaultComponent />);

		await waitForLoading(container);

		jest.runAllTimers();

		selectAllAndToggle(container);

		selectFilterDropdownItem(container, 'Last 7 days');

		const tableRows = container.querySelectorAll('tbody > tr');

		expect(tableRows.length).toBe(1);

		expect(tableRows[0]).toHaveTextContent('scott.gilbert@example.com');
	});
});

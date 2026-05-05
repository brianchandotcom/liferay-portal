import * as API from 'shared/api';
import * as data from 'test/data';
import * as NotificationAlertList from 'shared/components/NotificationAlertList';
import DataSourceList, {
	DataSourceName,
	disableRow,
	isDataSourceVisible,
	StatusRenderer
} from '../DataSourceList';
import mockStore, {mockStoreData} from 'test/mock-store';
import React from 'react';
import {cleanup, fireEvent, render, screen} from '@testing-library/react';
import {DataSourceStates, DataSourceTypes} from 'shared/util/constants';
import {MemoryRouter, Route} from 'react-router-dom';
import {MockedProvider} from '@apollo/client/testing';
import {Provider} from 'react-redux';
import {RemoteData} from 'shared/util/records';
import {Routes} from 'shared/util/router';
import {SubscriptionNames} from 'shared/util/subscriptions';
import {waitForLoadingToBeRemoved} from 'test/helpers';

jest.unmock('react-dom');

const defaultProps = {
	groupId: '23'
};

const Wrapper = ({children, queryString = '', store = mockStore()}) => (
	<Provider store={store}>
		<MemoryRouter
			initialEntries={[
				`/workspace/23/settings/data-source${queryString}`
			]}
		>
			<Route path={Routes.SETTINGS_DATA_SOURCE_LIST}>
				<MockedProvider addTypename={false}>{children}</MockedProvider>
			</Route>
		</MemoryRouter>
	</Provider>
);

describe.skip('DataSourceList', () => {
	beforeEach(() => {
		jest.spyOn(
			NotificationAlertList,
			'useNotificationsAPI'
		).mockReturnValue({
			data: [],
			loading: false,
			refetch: jest.fn()
		});
	});

	afterEach(() => {
		API.dataSource.search.mockReset();
		cleanup();
	});

	it('should render', async () => {
		API.dataSource.search.mockReturnValue(
			Promise.resolve({items: [data.mockLiferayDataSource(1)], total: 1})
		);

		const {container} = render(
			<Wrapper>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container).toMatchSnapshot();
	});

	it('should render with an empty state', async () => {
		API.dataSource.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<Wrapper queryString='?query=foo'>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});

	it('should render with a message to connect datasources if there are none', async () => {
		API.dataSource.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const {container} = render(
			<Wrapper>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});

	it('should open a dropdown with "Liferay DXP" and "Salesforce" when clicking the "Add Data Source" button', async () => {
		API.dataSource.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		render(
			<Wrapper>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved();

		fireEvent.click(screen.getByText('Add Data Source'));

		expect(screen.getByText('Liferay DXP')).toBeInTheDocument();
		expect(screen.getByText('Salesforce')).toBeInTheDocument();
	});

	it('should render toast for one data source with invalid credentials', async () => {
		const mockDS = data.mockLiferayDataSource(1, {
			credentials: {
				oAuthOwner: {emailAddress: 'test@liferay.com'}
			},
			state: DataSourceStates.CredentialsInvalid
		});

		API.dataSource.search.mockReturnValue(
			Promise.resolve({
				items: [mockDS],
				total: 1
			})
		);

		const {container} = render(
			<Wrapper>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('.embedded-alert-list-root')
		).toMatchSnapshot();
	});

	it('should render without an "add data source" button if the user role is member', async () => {
		API.dataSource.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const memberStore = mockStore(
			mockStoreData.setIn(
				['currentUser'],
				new RemoteData({data: '24', loading: false})
			)
		);

		render(
			<Wrapper store={memberStore}>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved();

		expect(screen.queryByText('Add Data Source')).toBeNull();
	});

	it('should render with a member-specific message to connect datasources if there are none', async () => {
		API.dataSource.search.mockReturnValue(
			Promise.resolve({items: [], total: 0})
		);

		const memberStore = mockStore(
			mockStoreData.setIn(
				['currentUser'],
				new RemoteData({data: '24', loading: false})
			)
		);

		const {container} = render(
			<Wrapper store={memberStore}>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(container.querySelector('.no-results-root')).toMatchSnapshot();
	});

	it("should render toast for one data source with invalid credentials for a member's view", async () => {
		const mockDS = data.mockLiferayDataSource(1, {
			credentials: {
				oAuthOwner: {emailAddress: 'test@liferay.com'}
			},
			state: DataSourceStates.CredentialsInvalid
		});

		API.dataSource.search.mockReturnValue(
			Promise.resolve({
				items: [mockDS],
				total: 1
			})
		);

		const memberStore = mockStore(
			mockStoreData.setIn(
				['currentUser'],
				new RemoteData({data: '24', loading: false})
			)
		);

		const {container} = render(
			<Wrapper store={memberStore}>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('.embedded-alert-list-root')
		).toMatchSnapshot();
	});

	it('should render toast for multiple data sources with invalid credentials', async () => {
		const mockDS = data.mockLiferayDataSource(1, {
			credentials: {
				oAuthOwner: {emailAddress: 'test@liferay.com'}
			},
			state: DataSourceStates.CredentialsInvalid
		});

		API.dataSource.search.mockReturnValue(
			Promise.resolve({
				items: [mockDS],
				total: 2
			})
		);

		const {container} = render(
			<Wrapper>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('.embedded-alert-list-root')
		).toMatchSnapshot();
	});

	it("should render toast for multiple data sources with invalid credentials for a member's view", async () => {
		const mockDS = data.mockLiferayDataSource(1, {
			credentials: {
				oAuthOwner: {emailAddress: 'test@liferay.com'}
			},
			state: DataSourceStates.CredentialsInvalid
		});

		API.dataSource.search.mockReturnValue(
			Promise.resolve({
				items: [mockDS],
				total: 2
			})
		);

		const memberStore = mockStore(
			mockStoreData.setIn(
				['currentUser'],
				new RemoteData({data: '24', loading: false})
			)
		);

		const {container} = render(
			<Wrapper store={memberStore}>
				<DataSourceList {...defaultProps} />
			</Wrapper>
		);

		await waitForLoadingToBeRemoved(container);

		expect(
			container.querySelector('.embedded-alert-list-root')
		).toMatchSnapshot();
	});
});

describe('CellRenderers', () => {
	afterEach(cleanup);

	it('should show data-source as not configured', () => {
		const {getByText} = render(<StatusRenderer data={{state: null}} />);

		expect(getByText(/Not Configured/)).toBeInTheDocument();
	});

	it('should render as disabled if the datasource is in the process of being deleted', () => {
		const {container} = render(
			<MemoryRouter>
				<DataSourceName
					data={{
						name: 'Test DS',
						state: DataSourceStates.InProgressDeleting
					}}
					hrefFormatter={() => '/test'}
				/>
			</MemoryRouter>
		);

		expect(container.querySelector('a')).toBeNull();
		expect(screen.getByText('Test DS')).toBeInTheDocument();
	});
});

describe('disableRow', () => {
	it('should return true if datasource state is inProgressDeleting', () => {
		expect(disableRow({state: DataSourceStates.InProgressDeleting})).toBe(
			true
		);
	});

	it('should return false if datasource state is NOT inProgressDeleting', () => {
		expect(disableRow({state: DataSourceStates.Ready})).toBe(false);
	});
});

describe('isDataSourceVisible', () => {
	it('should always show data source types with no rule, regardless of subscription', () => {
		expect(
			isDataSourceVisible(
				DataSourceTypes.Liferay,
				SubscriptionNames.LiferayDataPlatform
			)
		).toBe(true);

		expect(
			isDataSourceVisible(
				DataSourceTypes.Csv,
				SubscriptionNames.LiferayAnalyticsCloudEnterprise
			)
		).toBe(true);
	});

	it('should return true when the subscription name is null', () => {
		expect(isDataSourceVisible(DataSourceTypes.Demandbase, null)).toBe(
			true
		);
	});

	it('should show Demandbase, Hubspot and Salesforce only when the subscription is Liferay Data Platform', () => {
		[
			DataSourceTypes.Demandbase,
			DataSourceTypes.Hubspot,
			DataSourceTypes.Salesforce
		].forEach(type => {
			expect(
				isDataSourceVisible(
					type,
					SubscriptionNames.LiferayDataPlatform
				)
			).toBe(true);
		});
	});

	it('should hide Demandbase, Hubspot and Salesforce for any subscription other than Liferay Data Platform', () => {
		const nonLDPSubscriptions = [
			SubscriptionNames.LiferayAnalyticsCloudBasic,
			SubscriptionNames.LiferayAnalyticsCloudBusiness,
			SubscriptionNames.LiferayAnalyticsCloudEnterprise,
			SubscriptionNames.LiferaySaasEnterprisePlan,
			SubscriptionNames.LxcBusinessPlan
		];

		[
			DataSourceTypes.Demandbase,
			DataSourceTypes.Hubspot,
			DataSourceTypes.Salesforce
		].forEach(type => {
			nonLDPSubscriptions.forEach(subscription => {
				expect(isDataSourceVisible(type, subscription)).toBe(false);
			});
		});
	});
});

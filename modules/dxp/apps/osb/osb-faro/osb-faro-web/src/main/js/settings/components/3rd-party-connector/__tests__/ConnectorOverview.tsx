import ConnectorOverview from '../ConnectorOverview';
import mockStore from 'test/mock-store';
import React from 'react';
import {ConnectorConfig} from '../types';
import {DataSource} from 'shared/util/records';
import {DataSourceStatuses} from 'shared/util/constants';
import {fromJS} from 'immutable';
import {generateConnectorToken, updateConnector} from 'shared/api/connector';
import {Provider} from 'react-redux';
import {render, waitFor} from '@testing-library/react';

jest.unmock('react-dom');

const useParamsMock = jest.fn();
const useCurrentUserMock = jest.fn();
const useDisconnectDataSourceMock = jest.fn();
const useRequestMock = jest.fn();

jest.mock('react-router-dom', () => ({
	...jest.requireActual('react-router-dom'),
	useParams: () => useParamsMock()
}));

jest.mock('shared/hooks/useCurrentUser', () => ({
	useCurrentUser: () => useCurrentUserMock()
}));

jest.mock('shared/hooks/useRequest', () => ({
	useRequest: (args: any) => useRequestMock(args)
}));

jest.mock('settings/components/data-source/utils', () => ({
	useDisconnectDataSource: (args: any) => useDisconnectDataSourceMock(args)
}));

jest.mock('shared/api/connector', () => ({
	generateConnectorToken: jest.fn(() =>
		Promise.resolve({token: 'fake-token'})
	),
	updateConnector: jest.fn(() => Promise.resolve({}))
}));

jest.mock('shared/api/data-source', () => ({
	fetch: jest.fn(() => Promise.resolve({}))
}));

jest.mock('settings/components/base-page/BasePage', () => ({
	__esModule: true,
	default: ({children}: any) => <div>{children}</div>
}));

jest.mock('settings/components/AssignedPropertiesTable', () => ({
	AssignedPropertiesTable: () => <div data-testid='assigned-properties' />
}));

jest.mock('settings/components/data-source/DataSourceEditableTitle', () => ({
	DataSourceEditableTitle: ({label}: any) => (
		<div data-testid='data-source-title'>{label}</div>
	)
}));

jest.mock('settings/components/CopyInputValue', () => ({
	CopyInputValue: ({title, value}: any) => (
		<div data-testid='copy-input'>
			<span data-testid='copy-input-title'>{title}</span>
			<span data-testid='copy-input-value'>{value}</span>
		</div>
	)
}));

const buildConfig = (
	overrides: Partial<ConnectorConfig> = {}
): ConnectorConfig => ({
	columns: [],
	displayName: 'Acme',
	endpointPath: '/api/acme',
	entities: [],
	languages: {
		connectDescription: 'connectDescription',
		connectTitle: 'connectTitle',
		disconnectedAlert: 'DISCONNECTED_ALERT',
		endpointHelper: 'endpointHelper',
		endpointLabel: 'endpointLabel',
		reconnectHelper: 'reconnectHelper',
		successAlert: 'SUCCESS_ALERT',
		syncHelper: 'syncHelper',
		tokenLabel: 'tokenLabel'
	},
	singleton: false,
	slug: 'acme',
	type: 'ACME',
	...overrides
});

const buildDataSource = (status: string, providerData: any = null) =>
	new DataSource({
		id: 'ds-1',
		name: 'My Data Source',
		provider: providerData ? fromJS(providerData) : undefined,
		status
	});

const renderOverview = (
	props: Partial<{
		config: ConnectorConfig;
		dataSource: DataSource;
	}> = {}
) =>
	render(
		<Provider store={mockStore()}>
			<ConnectorOverview
				config={props.config ?? buildConfig()}
				dataSource={
					props.dataSource ??
					buildDataSource(DataSourceStatuses.Active)
				}
			/>
		</Provider>
	);

describe('ConnectorOverview', () => {
	beforeEach(() => {
		(generateConnectorToken as jest.Mock).mockClear();
		(updateConnector as jest.Mock).mockClear();

		useParamsMock.mockReturnValue({groupId: '23', id: 'ds-1'});
		useCurrentUserMock.mockReturnValue({isAdmin: () => true});
		useDisconnectDataSourceMock.mockReturnValue({
			handleDisconnect: jest.fn()
		});
		useRequestMock.mockReturnValue({
			data: undefined,
			error: false,
			loading: false
		});
	});

	it('renders the success alert when the data source is active and no account error is present', () => {
		const {getByText} = renderOverview();

		expect(getByText('SUCCESS_ALERT')).toBeTruthy();
	});

	it('renders the disconnected warning alert when the data source is inactive', () => {
		const {getByText} = renderOverview({
			dataSource: buildDataSource(DataSourceStatuses.Inactive)
		});

		expect(getByText('DISCONNECTED_ALERT')).toBeTruthy();
	});

	it('renders the up-to-date alert when the account status is reported back', () => {
		const dataSource = buildDataSource(DataSourceStatuses.Active, {
			accountsConfiguration: {accountsStatus: 'connected'}
		});

		const {getByText} = renderOverview({dataSource});

		expect(
			getByText(
				'All data coming from this data source is up to date. There are no errors to report.'
			)
		).toBeTruthy();
	});

	it('fetches a token using the connector slug when the data source is inactive', async () => {
		renderOverview({
			dataSource: buildDataSource(DataSourceStatuses.Inactive)
		});

		await waitFor(() =>
			expect(generateConnectorToken).toHaveBeenCalledWith({
				groupId: '23',
				type: 'acme'
			})
		);
	});

	it('does not fetch a token when the data source is already active', async () => {
		renderOverview({
			dataSource: buildDataSource(DataSourceStatuses.Active)
		});

		await Promise.resolve();

		expect(generateConnectorToken).not.toHaveBeenCalled();
	});

	it('shows the disconnect button when the user is admin and the data source is active', () => {
		const {getByLabelText} = renderOverview();

		expect(getByLabelText('Disconnect Data Source')).toBeTruthy();
	});

	it('hides the disconnect button when the data source is not active', () => {
		const {queryByLabelText} = renderOverview({
			dataSource: buildDataSource(DataSourceStatuses.Inactive)
		});

		expect(queryByLabelText('Disconnect Data Source')).toBeNull();
	});

	it('hides the disconnect button when the user is not an admin', () => {
		useCurrentUserMock.mockReturnValue({isAdmin: () => false});

		const {queryByLabelText} = renderOverview();

		expect(queryByLabelText('Disconnect Data Source')).toBeNull();
	});

	it('renders the configured display name as the data source type', () => {
		const config = buildConfig({displayName: 'Acme'});

		const {container} = renderOverview({config});

		const typeInput = container.querySelector(
			'#dataSourceType'
		) as HTMLInputElement | null;

		expect(typeInput).toBeTruthy();
		expect(typeInput?.value).toBe('Acme');
	});

	it('builds the endpoint URL using the window origin and config endpoint path', () => {
		const config = buildConfig({endpointPath: '/api/custom'});

		const {getAllByTestId} = renderOverview({config});

		const values = getAllByTestId('copy-input-value').map(
			node => node.textContent
		);

		expect(values).toContain(`${window.location.origin}/api/custom`);
	});
});

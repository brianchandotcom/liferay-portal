import * as data from 'test/data';
import mockStore from 'test/mock-store';
import React from 'react';
import SalesforceOverview from '../SalesforceOverview';
import {cleanup, fireEvent, render} from '@testing-library/react';
import {DataSource, User} from 'shared/util/records';
import {DataSourceStates} from 'shared/util/constants';
import {Provider} from 'react-redux';
import {StaticRouter} from 'react-router';

jest.unmock('react-dom');

const defaultProps = {
	currentUser: data.getImmutableMock(User, data.mockUser),
	dataSource: data.getImmutableMock(
		DataSource,
		data.mockSalesforceDataSource
	),
	groupId: '23',
	id: 'test'
};

const DefaultComponent = props => (
	<Provider store={mockStore()}>
		<StaticRouter>
			<SalesforceOverview {...defaultProps} {...props} />
		</StaticRouter>
	</Provider>
);

describe('SalesforceOverview', () => {
	afterEach(cleanup);

	it('should render with CONNECTED (NOT SYNCED) status', () => {
		const {container, getByText} = render(<DefaultComponent />);

		const label = container.querySelector('.label-item.label-item-expand');

		expect(label).toHaveTextContent('Connected');

		expect(
			getByText(
				'You have successfully authenticated your token with Liferay Analytics Cloud. You can now select the data to sync.'
			)
		).toBeInTheDocument();

		expect(
			getByText(
				'The data source setup is almost complete. Sync data to start seeing results as activities occur on your sites.'
			)
		).toBeInTheDocument();
	});

	it('should render with DISCONNECTED status', () => {
		const {container, getByText} = render(
			<DefaultComponent
				dataSource={data.getImmutableMock(
					DataSource,
					data.mockSalesforceDataSource,
					0,
					{
						state: DataSourceStates.Disconnected
					}
				)}
			/>
		);

		const label = container.querySelector('.label-item.label-item-expand');

		expect(label).toHaveTextContent('Disconnected');

		expect(
			getByText(
				'The Data Source is disconnected. Data is no longer being synced from DXP, but you can reconnect to resume syncing.'
			)
		).toBeInTheDocument();
	});

	it('should render with DISCONNECTED then click on Connect button to display CONNECTED (ALL SYNCED) status', () => {
		const {container, getByText} = render(
			<DefaultComponent
				dataSource={data.getImmutableMock(
					DataSource,
					data.mockSalesforceDataSource,
					0,
					{
						state: DataSourceStates.Disconnected
					}
				)}
			/>
		);

		const label = container.querySelector('.label-item.label-item-expand');

		expect(label).toHaveTextContent('Disconnected');

		fireEvent.click(getByText('Connect'));

		expect(label).toHaveTextContent('Connected');

		expect(
			getByText(
				'All data coming from this data source is up to date. There are no errors to report.'
			)
		).toBeInTheDocument();
	});

	it('should render with CONNECTED (NOT SYNCED) status, toggle the switches and then display CONNECTED (ALL SYNCED) status', () => {
		const {container, getByText} = render(<DefaultComponent />);

		const label = container.querySelector('.label-item.label-item-expand');

		expect(label).toHaveTextContent('Connected');

		expect(
			getByText(
				'You have successfully authenticated your token with Liferay Analytics Cloud. You can now select the data to sync.'
			)
		).toBeInTheDocument();

		expect(
			getByText(
				'The data source setup is almost complete. Sync data to start seeing results as activities occur on your sites.'
			)
		).toBeInTheDocument();

		const togglesSwitches = container.querySelectorAll(
			'.toggle-switch-check-bar'
		);

		fireEvent.click(togglesSwitches[0]);
		fireEvent.click(togglesSwitches[1]);

		expect(
			getByText(
				'All data coming from this data source is up to date. There are no errors to report.'
			)
		).toBeInTheDocument();
	});
});

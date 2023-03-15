import * as data from 'test/data';
import SalesforceConfigurationStatus from '../ConfigurationStatus';
import {DataSource, RemoteData} from 'shared/util/records';
import {fromJS, Map} from 'immutable';
import {renderWithStore} from 'test/mock-store';

describe.skip('SalesforceConfigurationStatus', () => {
	it('should render', () => {
		const component = renderWithStore(SalesforceConfigurationStatus, {
			groupId: '23',
			id: '23'
		});

		expect(component).toMatchSnapshot();
	});

	it('should render as read-only if the user is not authorized to make changes', () => {
		const component = renderWithStore(
			SalesforceConfigurationStatus,
			{
				groupId: '23',
				id: '23'
			},
			store => store.setIn(['currentUser', 'data'], '24')
		);

		expect(component).toMatchSnapshot();
	});

	// TODO: should be rewritten when new endpoint to replace connectionStatuses is added
	xit('should render already configured items with the button text "pause" instead of "enable"', () => {
		const component = renderWithStore(
			SalesforceConfigurationStatus,
			{
				groupId: '23',
				id: '23'
			},
			store =>
				store.setIn(
					['dataSources', '23'],
					new RemoteData({
						data: new DataSource(
							data.mockLiferayDataSource(23, {
								connectionStatuses: fromJS({
									accounts: {
										modifiedDate: data.getTimestamp(),
										status: 2
									},
									contacts: {
										modifiedDate: data.getTimestamp(),
										status: 2
									},
									leads: {
										modifiedDate: data.getTimestamp(),
										status: 2
									}
								}),
								provider: new Map({
									accountsConfiguration: {},
									contactsConfiguration: {},
									leadsConfiguration: {},
									type: 'SALESFORCE'
								})
							})
						),
						loading: false
					})
				)
		);

		expect(component).toMatchSnapshot();
	});

	// TODO: should be rewritten when new endpoint to replace connectionStatuses is added
	xit('should render as configuring items with a metric bar instead of a button', () => {
		const component = renderWithStore(
			SalesforceConfigurationStatus,
			{
				groupId: '23',
				id: '23'
			},
			store =>
				store.setIn(
					['dataSources', '23'],
					new RemoteData({
						data: new DataSource(
							data.mockLiferayDataSource(23, {
								connectionStatuses: fromJS({
									accounts: {
										count: 123,
										modifiedDate: data.getTimestamp(),
										status: 1,
										totalCount: 1234
									},
									contacts: {
										count: 573,
										modifiedDate: data.getTimestamp(),
										status: 1,
										totalCount: 5433
									},
									leads: {
										count: 573,
										modifiedDate: data.getTimestamp(),
										status: 1,
										totalCount: 5433
									}
								}),
								provider: new Map({
									type: 'SALESFORCE'
								})
							})
						),
						loading: false
					})
				)
		);

		expect(component).toMatchSnapshot();
	});
});

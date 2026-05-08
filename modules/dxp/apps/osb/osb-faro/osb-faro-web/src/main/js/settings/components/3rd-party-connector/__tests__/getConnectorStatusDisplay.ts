import {DataSource} from 'shared/util/records';
import {DataSourceStates, DataSourceStatuses} from 'shared/util/constants';
import {getConnectorStatusDisplay} from '../getConnectorStatusDisplay';

describe('getConnectorStatusDisplay', () => {
	it('returns success display with Active label when status is ACTIVE', () => {
		const dataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Active
		});

		expect(getConnectorStatusDisplay(dataSource)).toEqual({
			display: 'success',
			label: 'Active'
		});
	});

	it('returns warning display with Inactive label when status is INACTIVE', () => {
		const dataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Inactive
		});

		expect(getConnectorStatusDisplay(dataSource)).toEqual({
			display: 'warning',
			label: 'Inactive'
		});
	});

	it('returns secondary display with Disconnected label for a manually disconnected data source', () => {
		const dataSource = new DataSource({
			state: DataSourceStates.Disconnected,
			status: DataSourceStatuses.Inactive
		});

		expect(getConnectorStatusDisplay(dataSource)).toEqual({
			display: 'secondary',
			label: 'Disconnected'
		});
	});

	it('falls back to Inactive when dataSource is undefined', () => {
		expect(getConnectorStatusDisplay(undefined)).toEqual({
			display: 'warning',
			label: 'Inactive'
		});
	});
});

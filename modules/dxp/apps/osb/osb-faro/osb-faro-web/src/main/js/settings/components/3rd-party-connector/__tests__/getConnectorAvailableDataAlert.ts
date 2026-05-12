import {DataSource} from 'shared/util/records';
import {DataSourceStates, DataSourceStatuses} from 'shared/util/constants';
import {getConnectorAvailableDataAlert} from '../getConnectorAvailableDataAlert';

const PREVIOUSLY_SYNCED_MESSAGE =
	'Previously synced data remains available. Reconnect or check your data source connection to resume data syncing.';

const SYNCING_COMPLETES_MESSAGE =
	'Your data may take some time to appear as syncing completes.';

describe('getConnectorAvailableDataAlert', () => {
	describe('ACTIVE', () => {
		const activeDataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Active
		});

		it('zero data: no alert', () => {
			expect(
				getConnectorAvailableDataAlert(activeDataSource, false)
			).toBeNull();
		});

		it('with data: dismissible info / "Your data may take some time to appear" message', () => {
			expect(
				getConnectorAvailableDataAlert(activeDataSource, true)
			).toEqual({
				dismissible: true,
				displayType: 'info',
				message: SYNCING_COMPLETES_MESSAGE
			});
		});
	});

	describe('INACTIVE (not disconnected)', () => {
		const inactiveDataSource = new DataSource({
			state: DataSourceStates.CredentialsValid,
			status: DataSourceStatuses.Inactive
		});

		it('without data: no alert', () => {
			expect(
				getConnectorAvailableDataAlert(inactiveDataSource, false)
			).toBeNull();
		});

		it('with data (90d): non-dismissible info / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(inactiveDataSource, true)
			).toEqual({
				dismissible: false,
				displayType: 'info',
				message: PREVIOUSLY_SYNCED_MESSAGE
			});
		});
	});

	describe('DISCONNECTED', () => {
		const disconnectedDataSource = new DataSource({
			state: DataSourceStates.Disconnected,
			status: DataSourceStatuses.Inactive
		});

		it('with data: non-dismissible info / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(disconnectedDataSource, true)
			).toEqual({
				dismissible: false,
				displayType: 'info',
				message: PREVIOUSLY_SYNCED_MESSAGE
			});
		});

		it('without data: still shows non-dismissible / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(disconnectedDataSource, false)
			).toEqual({
				dismissible: false,
				displayType: 'info',
				message: PREVIOUSLY_SYNCED_MESSAGE
			});
		});
	});
});

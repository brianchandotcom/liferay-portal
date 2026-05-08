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

		it('with data: info / "Your data may take some time to appear" message', () => {
			expect(
				getConnectorAvailableDataAlert(activeDataSource, true)
			).toEqual({
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

		it('with data (90d): info / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(inactiveDataSource, true)
			).toEqual({
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

		it('with data: info / "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(disconnectedDataSource, true)
			).toEqual({
				displayType: 'info',
				message: PREVIOUSLY_SYNCED_MESSAGE
			});
		});

		it('without data: still shows "Previously synced data remains available" message', () => {
			expect(
				getConnectorAvailableDataAlert(disconnectedDataSource, false)
			).toEqual({
				displayType: 'info',
				message: PREVIOUSLY_SYNCED_MESSAGE
			});
		});
	});
});

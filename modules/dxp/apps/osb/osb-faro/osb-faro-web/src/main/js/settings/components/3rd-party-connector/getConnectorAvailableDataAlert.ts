import {ConnectorStatus} from './types';
import {DataSource} from 'shared/util/records';
import {getConnectorStatus} from './getConnectorStatus';

export type ConnectorAvailableDataAlertKind = 'previously-synced' | 'syncing';

export interface ConnectorAvailableDataAlert {
	displayType: 'info';
	kind: ConnectorAvailableDataAlertKind;
	message: string;
}

export function getConnectorAvailableDataAlert(
	dataSource: DataSource | undefined,
	hasData: boolean
): ConnectorAvailableDataAlert | null {
	const status = getConnectorStatus(dataSource);

	if (status === ConnectorStatus.Disconnected) {
		return {
			displayType: 'info',
			kind: 'previously-synced',
			message: Liferay.Language.get(
				'previously-synced-data-remains-available-reconnect-or-check-your-data-source-connection-to-resume-data-syncing'
			)
		};
	}

	if (status === ConnectorStatus.Inactive && hasData) {
		return {
			displayType: 'info',
			kind: 'previously-synced',
			message: Liferay.Language.get(
				'previously-synced-data-remains-available-reconnect-or-check-your-data-source-connection-to-resume-data-syncing'
			)
		};
	}

	if (status === ConnectorStatus.Active && hasData) {
		return {
			displayType: 'info',
			kind: 'syncing',
			message: Liferay.Language.get(
				'your-data-may-take-some-time-to-appear-as-syncing-completes'
			)
		};
	}

	return null;
}

import {ConnectorStatus} from './types';
import {DataSource} from 'shared/util/records';
import {getConnectorStatus} from './getConnectorStatus';

export interface ConnectorAvailableDataAlert {
	dismissible: boolean;
	displayType: 'info';
	message: string;
}

export function getConnectorAvailableDataAlert(
	dataSource: DataSource | undefined,
	hasData: boolean
): ConnectorAvailableDataAlert | null {
	const status = getConnectorStatus(dataSource);

	if (status === ConnectorStatus.Disconnected) {
		return {
			dismissible: false,
			displayType: 'info',
			message: Liferay.Language.get(
				'previously-synced-data-remains-available-reconnect-or-check-your-data-source-connection-to-resume-data-syncing'
			)
		};
	}

	if (status === ConnectorStatus.Inactive && hasData) {
		return {
			dismissible: false,
			displayType: 'info',
			message: Liferay.Language.get(
				'previously-synced-data-remains-available-reconnect-or-check-your-data-source-connection-to-resume-data-syncing'
			)
		};
	}

	if (status === ConnectorStatus.Active && hasData) {
		return {
			dismissible: true,
			displayType: 'info',
			message: Liferay.Language.get(
				'your-data-may-take-some-time-to-appear-as-syncing-completes'
			)
		};
	}

	return null;
}

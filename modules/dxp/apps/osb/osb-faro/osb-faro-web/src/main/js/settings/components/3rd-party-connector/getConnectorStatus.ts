import {ConnectorStatus} from './types';
import {DataSource} from 'shared/util/records';
import {DataSourceStates, DataSourceStatuses} from 'shared/util/constants';

export function getConnectorStatus(dataSource?: DataSource): ConnectorStatus {
	if (dataSource?.state === DataSourceStates.Disconnected) {
		return ConnectorStatus.Disconnected;
	}

	if (dataSource?.status === DataSourceStatuses.Active) {
		return ConnectorStatus.Active;
	}

	return ConnectorStatus.Inactive;
}

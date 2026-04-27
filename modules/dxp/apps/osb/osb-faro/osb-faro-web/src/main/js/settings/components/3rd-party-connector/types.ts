import {ComponentType} from 'react';

export interface ConnectorEntityCellProps {
	data: {
		channelId: string;
		count: number;
	};
}

export interface ConnectorEntityFetchParams {
	groupId: string;
	id: string;
}

export interface ConnectorEntityDescriptor {
	accessor: string;
	description: string;
	fetchCount?: (params: ConnectorEntityFetchParams) => Promise<number>;
	icon: string;
	label: string;
}

export interface ConnectorColumnDescriptor {
	accessor: string;
	cellRenderer?: ComponentType<ConnectorEntityCellProps>;
	label: string;
}

export interface Languages {
	connectDescription: string;
	connectTitle: string;
	disconnectedAlert: string;
	endpointHelper: string;
	endpointLabel: string;
	reconnectHelper: string;
	successAlert: string;
	syncHelper: string;
	tokenLabel: string;
}

export interface ConnectorConfig {
	columns?: ConnectorColumnDescriptor[];
	displayName: string;
	endpointPath: string;
	entities: ConnectorEntityDescriptor[];
	helpUrl?: string;
	languages: Languages;
	singleton?: boolean;
	slug: string;
	type: string;
}

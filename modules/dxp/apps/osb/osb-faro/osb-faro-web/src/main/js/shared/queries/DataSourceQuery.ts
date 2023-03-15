import {gql} from 'apollo-boost';

export interface DataSourceData {
	data: {
		id: string;
		name: string;
		url: string;
	};
}

export interface DataSource {
	contactsSyncDetails: {selected: boolean};
	sitesSyncDetails: {selected: boolean};
}

export interface DataSourceSyncData {
	dataSources: DataSource[];
}

export default gql`
	query DataSource(
		$credentialsType: String
		$size: Int
		$sort: Sort
		$type: String
	) {
		dataSources(
			credentialsType: $credentialsType
			size: $size
			sort: $sort
			type: $type
		) {
			contactsSyncDetails {
				selected
			}
			id
			name
			sitesSyncDetails {
				selected
			}
			url
		}
	}
`;

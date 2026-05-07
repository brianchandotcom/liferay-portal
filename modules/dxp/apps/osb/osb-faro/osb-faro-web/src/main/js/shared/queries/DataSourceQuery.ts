import {gql} from '@apollo/client';

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
	id: string;
}

export interface DataSourceSyncData {
	dataSources: DataSource[];
}

export default gql`
	query DataSource($size: Int, $sort: Sort, $type: String) {
		dataSources(size: $size, sort: $sort, type: $type) {
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

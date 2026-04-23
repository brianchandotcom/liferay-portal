import {gql} from '@apollo/client';

export default gql`
	query DataSource($type: String) {
		dataSources(type: $type) {
			id
			name
			url
		}
	}
`;

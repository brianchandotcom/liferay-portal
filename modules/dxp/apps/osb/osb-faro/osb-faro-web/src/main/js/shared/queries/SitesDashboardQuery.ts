import {gql} from 'apollo-boost';

export default gql`
	query DataSource($type: String) {
		dataSources(type: $type) {
			id
			name
			url
		}
	}
`;

import {gql} from 'apollo-boost';

export default gql`
	query Preference($key: String!) {
		preference(key: $key) {
			key
			value
		}
	}
`;

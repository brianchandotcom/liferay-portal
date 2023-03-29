import {gql} from 'apollo-boost';

export default gql`
	mutation Preference($key: String!, $value: String!) {
		preference(key: $key, value: $value) {
			key
			value
		}
	}
`;

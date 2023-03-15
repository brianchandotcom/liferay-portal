import {gql} from 'apollo-boost';

export default gql`
	query TimeRange {
		timeRange {
			default
			endDate: endLocalDateTime
			rangeKey
			startDate: startLocalDateTime
		}
	}
`;

import {gql} from 'apollo-boost';

export default gql`
	query Recommendation($jobId: String!) {
		jobById(id: $jobId) {
			id
			name
			nextRunDate
			parameters {
				name
				value
			}
			runDataPeriod
			runDate
			runFrequency
			status
			type
		}
	}
`;

export const RECOMMENDATION_BY_NAME_QUERY = gql`
	query Recommendation($name: String!) {
		jobByName(name: $name) {
			id
			name
		}
	}
`;

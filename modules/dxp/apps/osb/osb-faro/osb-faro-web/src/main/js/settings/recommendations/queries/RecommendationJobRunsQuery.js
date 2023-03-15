import {gql} from 'apollo-boost';

export default gql`
	query RecommendationJobRuns(
		$jobId: String!
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		jobRuns(jobId: $jobId, size: $size, sort: $sort, start: $start) {
			jobRuns {
				completedDate
				context {
					key
					value
				}
				id
				status
			}
			total
		}
	}
`;

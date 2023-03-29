import {gql} from 'apollo-boost';

export default gql`
	query RequestList(
		$keywords: String
		$rangeKey: Int
		$size: Int!
		$sort: Sort!
		$start: Int!
		$statuses: [DataControlTaskStatus]
		$types: [DataControlTaskType]
	) {
		dataControlTasks(
			keywords: $keywords
			rangeKey: $rangeKey
			size: $size
			sort: $sort
			start: $start
			statuses: $statuses
			types: $types
		) {
			dataControlTasks {
				batchId
				completeDate
				createDate
				emailAddress
				id
				status
				type
			}
			total
		}
	}
`;

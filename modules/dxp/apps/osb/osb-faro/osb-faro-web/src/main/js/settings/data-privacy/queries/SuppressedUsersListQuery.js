import {gql} from 'apollo-boost';

export default gql`
	query SuppressedUsersList(
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		suppressions(
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			suppressions {
				createDate
				dataControlTaskBatchId
				dataControlTaskCreateDate
				dataControlTaskStatus
				emailAddress
				id
			}
			total
		}
	}
`;

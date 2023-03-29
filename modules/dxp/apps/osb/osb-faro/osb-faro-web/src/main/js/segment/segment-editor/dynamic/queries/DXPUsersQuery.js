import {gql} from 'apollo-boost';

export default gql`
	query UsersList(
		$channelId: String
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		users(
			channelId: $channelId
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			dxpEntities {
				id
				name
				... on User {
					dataSourceName
					screenName
				}
			}
			total
		}
	}
`;

import {gql} from 'apollo-boost';

export default gql`
	query CustomAssetsList(
		$channelId: String
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		dashboards(
			channelId: $channelId
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			dashboards {
				id
				assetId
				assetTitle
				createDate
				modifiedByUserName
				modifiedDate
			}
			total
		}
	}
`;

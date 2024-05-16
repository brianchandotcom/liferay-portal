import {gql} from 'apollo-boost';

export default gql`
	query AssetAppearsOnQuery(
		$assetId: String!
		$assetType: AssetType!
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$selectedMetrics: [String]
		$size: Int!
		$start: Int!
		$title: String
	) {
		assetPages(
			assetId: $assetId
			assetType: $assetType
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			selectedMetrics: $selectedMetrics
			size: $size
			start: $start
			title: $title
		) {
			assetMetrics {
				assetTitle
				assetId
				selectedMetrics {
					name
					value
				}
			}
			total
		}
	}
`;

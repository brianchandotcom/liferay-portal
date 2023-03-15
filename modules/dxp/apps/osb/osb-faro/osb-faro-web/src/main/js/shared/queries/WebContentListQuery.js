import {gql} from 'apollo-boost';

export default gql`
	query WebContentList(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		journals(
			channelId: $channelId
			keywords: $keywords
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			sort: $sort
			start: $start
		) {
			assetMetrics {
				... on JournalMetric {
					assetId
					assetTitle
					urls
					viewsMetric {
						value
					}
				}
			}
			total
		}
	}
`;

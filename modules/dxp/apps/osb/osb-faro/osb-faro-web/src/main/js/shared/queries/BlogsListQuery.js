import {gql} from 'apollo-boost';

export default gql`
	query BlogsList(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		blogs(
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
				... on BlogMetric {
					assetId
					assetTitle
					commentsMetric {
						value
					}
					ratingsMetric {
						value
					}
					readingTimeMetric {
						value
					}
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

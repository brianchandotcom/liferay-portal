import {gql} from 'apollo-boost';

export default gql`
	query DocumentsAndMediaList(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		documents(
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
				... on DocumentMetric {
					assetId
					assetTitle
					commentsMetric {
						value
					}
					downloadsMetric {
						value
					}
					previewsMetric {
						value
					}
					ratingsMetric {
						value
					}
					urls
				}
			}
			total
		}
	}
`;

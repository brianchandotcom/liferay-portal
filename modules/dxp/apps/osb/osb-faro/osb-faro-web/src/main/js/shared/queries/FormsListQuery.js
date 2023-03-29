import {gql} from 'apollo-boost';

export default gql`
	query FormsList(
		$channelId: String
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		forms(
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
				... on FormMetric {
					abandonmentsMetric {
						value
					}
					assetId
					assetTitle
					completionTimeMetric {
						value
					}
					submissionsMetric {
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

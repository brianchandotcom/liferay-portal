import {gql} from 'apollo-boost';
import {OrderByDirections} from 'shared/util/constants';
import {SafeRangeSelectors} from 'shared/types';

export interface SitesTopPagesQueryData {
	pages: {
		assetMetrics: {
			assetId: string;
			assetTitle: string;
			entrancesMetric: {
				value: number;
			};
			exitRateMetric: {
				value: number;
			};
			visitorsMetric: {
				value: number;
			};
		}[];
		total: number;
	};
}

export interface SitesTopPagesQueryVariables extends SafeRangeSelectors {
	channelId?: string;
	size: number;
	sort: {
		column: string;
		type: OrderByDirections;
	};
	start: number;
}

export default gql`
	query Touchpoint(
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		pages(
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			sort: $sort
			start: $start
		) {
			assetMetrics {
				... on PageMetric {
					assetTitle
					assetId
					entrancesMetric {
						value
					}
					exitRateMetric {
						value
					}
					visitorsMetric {
						value
					}
				}
			}
			total
		}
	}
`;

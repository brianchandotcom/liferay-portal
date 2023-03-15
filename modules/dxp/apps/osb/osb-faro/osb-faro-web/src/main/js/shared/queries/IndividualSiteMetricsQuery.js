import {gql} from 'apollo-boost';
import {TREND_FRAGMENT} from 'shared/queries/fragments';

export default gql`
	query SiteMetrics(
		$channelId: String
		$interval: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		site(
			channelId: $channelId
			interval: $interval
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			anonymousVisitorsMetric {
				...trendFragment
			}
			knownVisitorsMetric {
				...trendFragment
			}
			visitorsMetric {
				...trendFragment
			}
		}
	}

	${TREND_FRAGMENT}
`;

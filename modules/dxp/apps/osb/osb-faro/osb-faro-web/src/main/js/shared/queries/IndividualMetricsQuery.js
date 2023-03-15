import {gql} from 'apollo-boost';

export default gql`
	query IndividualMetrics(
		$channelId: String!
		$interval: String!
		$rangeKey: Int!
	) {
		individualMetric(
			channelId: $channelId
			interval: $interval
			rangeKey: $rangeKey
		) {
			anonymousIndividualsMetric {
				...trendFragment
			}
			knownIndividualsMetric {
				...trendFragment
			}
			totalIndividualsMetric {
				...trendFragment
			}
		}
	}

	fragment trendFragment on Metric {
		value
		trend {
			percentage
		}
	}
`;

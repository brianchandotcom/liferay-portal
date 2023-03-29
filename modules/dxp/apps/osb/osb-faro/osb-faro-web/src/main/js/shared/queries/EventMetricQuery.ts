import {gql} from 'apollo-boost';
import {SessionEntityTypes} from 'shared/util/constants';
import {TREND_FRAGMENT_EVENT_METRIC} from 'shared/queries/fragments';

export interface EventMetric {
	key: string;
	value: number;
	valueKey: string;
}

export interface EventMetricsData {
	eventMetric: {
		totalEventsMetric: Metric;
		totalSessionsMetric: Metric;
	};
}

export interface EventMetricsVariables {
	channelId: string;
	entityId: string;
	entityType: SessionEntityTypes;
	interval: string;
	keywords?: string;
	rangeEnd?: string;
	rangeKey?: number;
	rangeStart?: string;
}

interface Metric {
	histogram: {metrics: EventMetric[]; total: number};
	value: number;
}

export default gql`
	query EventMetrics(
		$channelId: String!
		$entityId: String!
		$entityType: EntityType!
		$interval: String!
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		eventMetric(
			channelId: $channelId
			entityId: $entityId
			entityType: $entityType
			interval: $interval
			keywords: $keywords
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			totalEventsMetric {
				...trendFragmentEventMetric
			}
			totalSessionsMetric {
				...trendFragmentEventMetric
			}
		}
	}

	${TREND_FRAGMENT_EVENT_METRIC}
`;

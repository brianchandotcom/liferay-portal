import {gql} from '@apollo/client';
import {SessionEntityTypes} from 'shared/util/constants';
import {TrendClassification} from 'segment/types';

export interface AccountEventsTrendData {
	eventsByUserSessions: {
		totalEventsMetric: {
			previousValue: number;
			trend: {
				percentage: number;
				trendClassification: TrendClassification;
			};
			value: number;
		} | null;
	};
}

export interface AccountEventsTrendVariables {
	accountId: string;
	channelId: string;
	entityId: string;
	entityType: SessionEntityTypes;
	keywords?: string;
	rangeEnd?: string | null;
	rangeKey?: number | null;
	rangeStart?: string | null;
}

export default gql`
	query AccountEventsTrend(
		$accountId: String!
		$channelId: String!
		$entityId: String!
		$entityType: EntityType!
		$keywords: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		eventsByUserSessions(
			accountId: $accountId
			channelId: $channelId
			entityId: $entityId
			entityType: $entityType
			keywords: $keywords
			page: 0
			size: 1
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			totalEventsMetric {
				previousValue
				trend {
					percentage
					trendClassification
				}
				value
			}
		}
	}
`;

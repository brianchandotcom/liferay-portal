import {gql} from 'apollo-boost';
import {Trend} from 'commerce/utils/types';

export interface CommerceAverageOrderValueData {
	orderAverageCurrencyValues: {
		currencyCode: string;
		trend: Trend;
		value: string;
	}[];
}

export default gql`
	query CommerceAverageOrderValue(
		$channelId: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		orderAverageCurrencyValues(
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			currencyCode
			trend {
				trendClassification
				percentage
			}
			value
		}
	}
`;

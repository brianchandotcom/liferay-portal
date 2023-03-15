import {gql} from 'apollo-boost';
import {Trend} from 'commerce/utils/types';

export interface CommerceAverageRevenuePerAccountData {
	orderAccountAverageCurrencyValues: {
		currencyCode: string;
		trend: Trend;
		value: string;
	}[];
}

export default gql`
	query CommerceAverageRevenuePerAccount(
		$channelId: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		orderAccountAverageCurrencyValues(
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

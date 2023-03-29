import {gql} from 'apollo-boost';
import {Trend} from 'commerce/utils/types';

export interface CommerceTotalOrderValueData {
	orderTotalCurrencyValues: {
		currencyCode: string;
		trend: Trend;
		value: string;
	}[];
}

export default gql`
	query CommerceTotalOrderValue(
		$channelId: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		orderTotalCurrencyValues(
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

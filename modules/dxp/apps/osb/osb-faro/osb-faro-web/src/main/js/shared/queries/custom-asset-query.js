import {gql} from 'apollo-boost';

export default metric =>
	gql(`
		query Custom(
			$assetId: String!
			$channelId: String
			$country: String
			$deviceType: String
			$rangeEnd: String
			$rangeKey: Int
			$rangeStart: String
			$title: String
			$touchpoint: String
		) {
			custom(
				assetId: $assetId
				canonicalUrl: $touchpoint
				channelId: $channelId
				country: $country
				deviceType: $deviceType
				rangeEnd: $rangeEnd
				rangeKey: $rangeKey
				rangeStart: $rangeStart
				title: $title
			) {
				${metric} {
					histogram {
						metrics {
							key
							previousValue
							previousValueKey
							value
							valueKey
							trend {
								percentage
								trendClassification
							}
						}
					}
					previousValue
					trend {
						percentage
						trendClassification
					}
					value
				}
			}
		}`);

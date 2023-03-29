import {gql} from 'apollo-boost';

export default gql`
	query TouchpointPath(
		$channelId: String
		$devices: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$title: String
		$touchpoint: String
	) {
		page(
			channelId: $channelId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			title: $title
		) {
			assetTitle
			directAccessMetric {
				value
			}
			indirectAccessMetric {
				value
			}
			pageReferrerMetrics {
				assetTitle
				external
				referrer
				accessMetric {
					value
				}
			}
			viewsMetric {
				value
			}
		}
	}
`;

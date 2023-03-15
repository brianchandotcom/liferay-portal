import {gql} from 'apollo-boost';

export default gql`
	query AssetsQuery(
		$channelId: String
		$devices: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$touchpoint: String
	) {
		assets(
			channelId: $channelId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			assetId
			assetTitle
			assetType
			defaultMetric {
				value
			}
		}
	}
`;

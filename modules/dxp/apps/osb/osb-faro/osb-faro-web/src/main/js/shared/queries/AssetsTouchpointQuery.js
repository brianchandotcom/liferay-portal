import {gql} from 'apollo-boost';

export default gql`
	query AssetsTouchpointQuery(
		$assetType: AssetType!
		$assetId: String!
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$location: String
		$devices: String
		$title: String
	) {
		assetPages(
			assetType: $assetType
			assetId: $assetId
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			country: $location
			deviceType: $devices
			title: $title
		) {
			assetTitle
			assetId
		}
	}
`;

import gql from 'graphql-tag';
import {AUDIENCE_REPORT_FRAGMENT} from 'shared/queries/fragments';

export const PageAudienceReportQuery = ({metricName, name}) => gql`
	query ${name}AudienceReportQuery(
		$channelId: String
		$devices: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$title: String
		$touchpoint: String
	) {
		${name}(
			channelId: $channelId
			canonicalUrl: $touchpoint
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			title: $title
		) {
			${metricName} {
				...audienceReportFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
`;

export const AssetAudienceReportQuery = ({metricName, name}) => gql`
	query ${name}AudienceReportQuery(
		$assetId: String!
		$channelId: String
		$devices: String
		$location: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$title: String
		$touchpoint: String
	) {
		${name}(
			assetId: $assetId
			canonicalUrl: $touchpoint
			channelId: $channelId
			country: $location
			deviceType: $devices
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			title: $title
		) {
			assetId
			assetTitle
			urls
			${metricName} {
				...audienceReportFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
`;

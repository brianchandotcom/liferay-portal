import {
	AUDIENCE_REPORT_FRAGMENT,
	BROWSER_FRAGMENT,
	DEVICE_FRAGMENT,
	GEOLOCATION_FRAGMENT,
	METRIC_FRAGMENT
} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';

export default gql`
	query DocumentsAndMediaMetrics(
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
		document(
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
			commentsMetric {
				...metricFragment
			}
			downloadsMetric {
				...audienceReportFragment
				...browserFragment
				...deviceFragment
				...geolocationFragment
				...metricFragment
			}
			previewsMetric {
				...metricFragment
			}
			ratingsMetric {
				...metricFragment
			}
			urls
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
	${BROWSER_FRAGMENT}
	${DEVICE_FRAGMENT}
	${GEOLOCATION_FRAGMENT}
	${METRIC_FRAGMENT}
`;

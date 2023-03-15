import {
	AUDIENCE_REPORT_FRAGMENT,
	BROWSER_FRAGMENT,
	DEVICE_FRAGMENT,
	GEOLOCATION_FRAGMENT,
	METRIC_FRAGMENT
} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';

export default gql`
	query FormsMetrics(
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
		form(
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
			abandonmentsMetric {
				...metricFragment
			}
			assetId
			assetTitle
			completionTimeMetric {
				...metricFragment
			}
			submissionsMetric {
				...audienceReportFragment
				...browserFragment
				...deviceFragment
				...geolocationFragment
				...metricFragment
			}
			urls
			viewsMetric {
				...metricFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
	${BROWSER_FRAGMENT}
	${DEVICE_FRAGMENT}
	${GEOLOCATION_FRAGMENT}
	${METRIC_FRAGMENT}
`;

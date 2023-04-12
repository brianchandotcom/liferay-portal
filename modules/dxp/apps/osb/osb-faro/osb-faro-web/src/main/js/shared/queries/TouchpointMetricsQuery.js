import {
	BROWSER_FRAGMENT,
	DEVICE_FRAGMENT,
	GEOLOCATION_FRAGMENT,
	METRIC_FRAGMENT
} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';

export default gql`
	query TouchpointMetrics(
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
			avgTimeOnPageMetric {
				...metricFragment
			}
			bounceRateMetric {
				...metricFragment
			}
			entrancesMetric {
				...metricFragment
			}
			exitRateMetric {
				...metricFragment
			}
			visitorsMetric {
				...metricFragment
			}
			viewsMetric {
				...browserFragment
				...deviceFragment
				...metricFragment
				...geolocationFragment

				previousValue
				value
			}
		}
	}

	${BROWSER_FRAGMENT}
	${DEVICE_FRAGMENT}
	${GEOLOCATION_FRAGMENT}
	${METRIC_FRAGMENT}
`;

import gql from 'graphql-tag';
import {Metric} from './metrics';
import {
	METRIC_HISTOGRAM_FRAGMENT,
	METRIC_TABS_FRAGMENT
} from 'shared/queries/fragments';

const assetMetricFragment = (metrics: Metric[]) => {
	let fragment = '';

	for (let i = 0; i < metrics.length; i++) {
		fragment += `${metrics[i].name} {
			...TabsFragment
		}`;
	}

	return fragment;
};

const capitalizeFirstLetter = (str: string): string =>
	str.charAt(0).toUpperCase() + str.slice(1);

const queryTabsName = (str: string): string =>
	`${capitalizeFirstLetter(str)}MetricTabsQuery`;

const queryMetricName = (str: string): string =>
	`${capitalizeFirstLetter(str)}MetricQuery`;

export const AssetTabsQuery = (metrics: Metric[], name: string) => gql`
	query ${queryTabsName(name)}(
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
			${assetMetricFragment(metrics)}
		}
	}

	${METRIC_TABS_FRAGMENT}
`;

export const AssetMetricQuery = (queryName: string) => (metricName: string) =>
	gql`
	query ${queryMetricName(queryName)}(
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
		${queryName}(
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
			${metricName} {
				...HistogramFragment
			}
		}
	}

	${METRIC_HISTOGRAM_FRAGMENT}
`;

export const SitesTabsQuery = gql`
	query SitesMetricTabsQuery(
		$channelId: String
		$interval: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		site(
			channelId: $channelId
			interval: $interval
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			bounceRateMetric {
				...TabsFragment
			}
			sessionDurationMetric {
				...TabsFragment
			}
			sessionsPerVisitorMetric {
				...TabsFragment
			}
			visitorsMetric {
				...TabsFragment
			}
		}
	}

	${METRIC_TABS_FRAGMENT}
`;

const GenericMetricQuery = (metricName: string) => gql`
	query SitesMetricQuery(
		$channelId: String
		$interval: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		site(
			channelId: $channelId
			interval: $interval
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			${metricName} {
				...HistogramFragment
			}
		}
	}

	${METRIC_HISTOGRAM_FRAGMENT}
`;

const CompositeMetricQuery = gql`
	query SitesMetricQuery(
		$channelId: String
		$interval: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		site(
			channelId: $channelId
			interval: $interval
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			visitorsMetric {
				...HistogramFragment
			}
			anonymousVisitorsMetric {
				...HistogramFragment
			}
			knownVisitorsMetric {
				...HistogramFragment
			}
		}
	}

	${METRIC_HISTOGRAM_FRAGMENT}
`;

export const SitesMetricQuery = (metricName: string) => {
	if (metricName === 'visitorsMetric') {
		return CompositeMetricQuery;
	}

	return GenericMetricQuery(metricName);
};

export const PageMetricQuery = (metricName: string) => gql`
	query PageMetricQuery(
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
			${metricName} {
				...HistogramFragment
			}
		}
	}

	${METRIC_HISTOGRAM_FRAGMENT}
`;

export const PageMetricTabsQuery = gql`
	query PageMetricQuery(
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
			visitorsMetric {
				...TabsFragment
			}
			avgTimeOnPageMetric {
				...TabsFragment
			}
			bounceRateMetric {
				...TabsFragment
			}
			entrancesMetric {
				...TabsFragment
			}
			exitRateMetric {
				...TabsFragment
			}
			viewsMetric {
				...TabsFragment
			}
		}
	}

	${METRIC_TABS_FRAGMENT}
`;

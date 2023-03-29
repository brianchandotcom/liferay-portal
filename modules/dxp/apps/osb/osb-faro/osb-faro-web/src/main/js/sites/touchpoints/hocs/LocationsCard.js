import getLocationsMapper, {
	getLocationsMapperCountries
} from 'cerebro-shared/hocs/mappers/locations';
import URLConstants from 'shared/util/url-constants';
import {GEOLOCATION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {withLocationsCard} from 'cerebro-shared/hocs/LocationsCard';

const TouchpointLocationsQuery = gql`
	query TouchpointLocationsQuery(
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
			viewsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Touchpoint Locations
 */
const withTouchpointLocations = () =>
	graphql(
		TouchpointLocationsQuery,
		getLocationsMapper(result => result.page.viewsMetric)
	);

/**
 * HOC
 * @description Touchpoint Countries
 */
const withTouchpointsLocationsCountries = () =>
	graphql(
		TouchpointLocationsQuery,
		getLocationsMapperCountries(result => result.page.viewsMetric)
	);

export default withLocationsCard(
	withTouchpointLocations,
	withTouchpointsLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-views-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardPagesViewsByLocation,
		title: Liferay.Language.get('there-are-no-views-on-the-selected-period')
	}
);

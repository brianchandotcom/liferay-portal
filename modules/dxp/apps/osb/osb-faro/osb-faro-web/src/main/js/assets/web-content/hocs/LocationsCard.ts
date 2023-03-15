import getLocationsMapper, {
	getLocationsMapperCountries
} from 'cerebro-shared/hocs/mappers/locations';
import URLConstants from 'shared/util/url-constants';
import {GEOLOCATION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {withLocationsCard} from 'cerebro-shared/hocs/LocationsCard';

const GEOLOCATION_QUERY = gql`
	query WebContentMetrics(
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
		journal(
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
			viewsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Web Content Locations
 */
const withWebContentLocations = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapper(result => result.journal.viewsMetric)
	);

/**
 * HOC
 * @description Web Content Countries
 */
const withWebContentLocationsCountries = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapperCountries(result => result.journal.viewsMetric)
	);

export default withLocationsCard(
	withWebContentLocations,
	withWebContentLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-views-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardWebContentViewsByLocation,
		title: Liferay.Language.get('there-are-no-views-on-the-selected-period')
	}
);

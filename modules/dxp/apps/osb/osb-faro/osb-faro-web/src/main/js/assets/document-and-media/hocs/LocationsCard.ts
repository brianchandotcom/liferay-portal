import getLocationsMapper, {
	getLocationsMapperCountries
} from 'cerebro-shared/hocs/mappers/locations';
import URLConstants from 'shared/util/url-constants';
import {GEOLOCATION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {withLocationsCard} from 'cerebro-shared/hocs/LocationsCard';

const GEOLOCATION_QUERY = gql`
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
			downloadsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Documents And Media Locations
 */
const withBlogsLocations = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapper(result => result.document.downloadsMetric)
	);

/**
 * HOC
 * @description Documents And Media Countries
 */
const withBlogsLocationsCountries = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapperCountries(result => result.document.downloadsMetric)
	);

export default withLocationsCard(
	withBlogsLocations,
	withBlogsLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-downloads-by-location'
		),
		documentationUrl:
			URLConstants.SitesDashboardDocumentsAndMediaDownloadByLocation,
		title: Liferay.Language.get(
			'there-are-no-downloads-on-the-selected-period'
		)
	}
);

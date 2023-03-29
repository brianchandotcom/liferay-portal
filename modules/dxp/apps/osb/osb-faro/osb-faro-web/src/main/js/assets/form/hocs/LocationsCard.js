import getLocationsMapper, {
	getLocationsMapperCountries
} from 'cerebro-shared/hocs/mappers/locations';
import URLConstants from 'shared/util/url-constants';
import {GEOLOCATION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {withLocationsCard} from 'cerebro-shared/hocs/LocationsCard';

const GEOLOCATION_QUERY = gql`
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
			submissionsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

/**
 * HOC
 * @description Forms Locations
 */
const withFormsLocations = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapper(result => result.form.submissionsMetric)
	);

/**
 * HOC
 * @description Forms Countries
 */
const withFormsLocationsCountries = () =>
	graphql(
		GEOLOCATION_QUERY,
		getLocationsMapperCountries(result => result.form.submissionsMetric)
	);

export default withLocationsCard(
	withFormsLocations,
	withFormsLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-submissions-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardFormsSubmissionsByLocation,
		title: Liferay.Language.get(
			'there-are-no-submissions-on-the-selected-period'
		)
	}
);

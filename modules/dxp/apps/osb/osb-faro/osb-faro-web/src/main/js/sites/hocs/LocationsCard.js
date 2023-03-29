import getLocationsMapper, {
	getLocationsMapperCountries
} from 'cerebro-shared/hocs/mappers/locations';
import SessionLocationsQuery from 'shared/queries/SessionLocationsQuery';
import URLConstants from 'shared/util/url-constants';
import {graphql} from '@apollo/react-hoc';
import {withLocationsCard} from 'cerebro-shared/hocs/LocationsCard';

/**
 * HOC
 * @description Site Locations
 */
const withSiteLocations = () =>
	graphql(
		SessionLocationsQuery,
		getLocationsMapper(result => result.site.sessionsMetric)
	);

/**
 * HOC
 * @description Site Countries
 */
const withSiteLocationsCountries = () =>
	graphql(
		SessionLocationsQuery,
		getLocationsMapperCountries(result => result.site.sessionsMetric)
	);

export default withLocationsCard(
	withSiteLocations,
	withSiteLocationsCountries,
	{
		documentationTitle: Liferay.Language.get(
			'learn-more-about-sessions-by-location'
		),
		documentationUrl: URLConstants.SitesDashboardPagesSessionsByLocation,
		title: Liferay.Language.get(
			'there-are-no-sessions-on-the-selected-period'
		)
	}
);

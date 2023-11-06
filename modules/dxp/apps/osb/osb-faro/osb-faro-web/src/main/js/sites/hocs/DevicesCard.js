import getDevicesMapper from 'cerebro-shared/hocs/mappers/devices';
import SiteDevicesQuery from 'shared/queries/SiteDevicesQuery';
import URLConstants from 'shared/util/url-constants';
import {Containers} from 'shared/components/download-report/DownloadPDFReport';
import {graphql} from '@apollo/react-hoc';
import {withDevicesCard} from 'shared/hoc/DevicesCard';

/**
 * HOC
 * @description Site Devices
 */
const withSiteDevices = () =>
	graphql(
		SiteDevicesQuery,
		getDevicesMapper(result => result.site.sessionsMetric)
	);

export default withDevicesCard(withSiteDevices, {
	documentationTitle: Liferay.Language.get(
		'learn-more-about-sessions-by-technology'
	),
	documentationUrl: URLConstants.SitesDashboardPagesSessionsByTechnology,
	id: Containers.SessionTechnologyCard,
	title: Liferay.Language.get('there-are-no-sessions-on-the-selected-period')
});

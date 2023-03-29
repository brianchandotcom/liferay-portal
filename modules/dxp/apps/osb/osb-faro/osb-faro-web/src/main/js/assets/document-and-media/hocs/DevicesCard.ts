import getDevicesMapper from 'cerebro-shared/hocs/mappers/devices';
import URLConstants from 'shared/util/url-constants';
import {BROWSER_FRAGMENT, DEVICE_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {withDevicesCard} from 'shared/hoc/DevicesCard';

const BROWSER_DEVICE_QUERY = gql`
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
				...browserFragment
				...deviceFragment

				previousValue
				value
			}
		}
	}

	${BROWSER_FRAGMENT}
	${DEVICE_FRAGMENT}
`;

/**
 * HOC
 * @description Documents And Media Devices
 */
const withDocumentsAndMediaDevices = () =>
	graphql(
		BROWSER_DEVICE_QUERY,
		getDevicesMapper(result => result.document.downloadsMetric)
	);

export default withDevicesCard(withDocumentsAndMediaDevices, {
	documentationTitle: Liferay.Language.get(
		'learn-more-about-downloads-by-technology'
	),
	documentationUrl:
		URLConstants.SitesDashboardDocumentsAndMediaViewsByTechnology,
	title: Liferay.Language.get('there-are-no-downloads-on-the-selected-period')
});

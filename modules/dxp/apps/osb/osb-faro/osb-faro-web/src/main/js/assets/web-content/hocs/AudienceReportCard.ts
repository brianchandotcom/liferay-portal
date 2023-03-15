import getAudienceReportMapper from 'cerebro-shared/hocs/mappers/audience-report';
import {AUDIENCE_REPORT_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {Routes} from 'shared/util/router';
import {withAudienceReportCard} from 'shared/hoc/AudienceReportCard';

const AUDIENCE_QUERY = gql`
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
				...audienceReportFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
`;

const withWebContentAudienceReport = () =>
	graphql(AUDIENCE_QUERY, {
		...getAudienceReportMapper(
			result => result.journal.viewsMetric,
			Routes.ASSETS_WEB_CONTENT_KNOWN_INDIVIDUALS
		)
	});

export default withAudienceReportCard(withWebContentAudienceReport);

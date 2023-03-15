import getAudienceReportMapper from 'cerebro-shared/hocs/mappers/audience-report';
import {AUDIENCE_REPORT_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {Routes} from 'shared/util/router';
import {withAudienceReportCard} from 'shared/hoc/AudienceReportCard';

const AUDIENCE_QUERY = gql`
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
				...audienceReportFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
`;

const withDocumentsAndMediaAudienceReport = () =>
	graphql(AUDIENCE_QUERY, {
		...getAudienceReportMapper(
			result => result.document.downloadsMetric,
			Routes.ASSETS_DOCUMENTS_AND_MEDIA_KNOWN_INDIVIDUALS
		)
	});

export default withAudienceReportCard(withDocumentsAndMediaAudienceReport);

import getAudienceReportMapper from 'cerebro-shared/hocs/mappers/audience-report';
import {AUDIENCE_REPORT_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {graphql} from '@apollo/react-hoc';
import {Routes} from 'shared/util/router';
import {withAudienceReportCard} from 'shared/hoc/AudienceReportCard';

const AUDIENCE_QUERY = gql`
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
				...audienceReportFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
`;

const withFormsAudienceReport = () =>
	graphql(AUDIENCE_QUERY, {
		...getAudienceReportMapper(
			result => result.form.submissionsMetric,
			Routes.ASSETS_FORMS_KNOWN_INDIVIDUALS
		)
	});

export default withAudienceReportCard(withFormsAudienceReport);

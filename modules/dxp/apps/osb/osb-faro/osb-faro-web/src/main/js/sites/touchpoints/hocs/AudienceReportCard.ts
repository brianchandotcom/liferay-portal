import getAudienceReportMapper from 'cerebro-shared/hocs/mappers/audience-report';
import gql from 'graphql-tag';
import {AUDIENCE_REPORT_FRAGMENT} from 'shared/queries/fragments';
import {graphql} from '@apollo/react-hoc';
import {Routes} from 'shared/util/router';
import {withAudienceReportCard} from 'shared/hoc/AudienceReportCard';

const AudienceReportQuery = gql`
	query TouchpointAudienceReportQuery(
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
				...audienceReportFragment
			}
		}
	}

	${AUDIENCE_REPORT_FRAGMENT}
`;

const withTouchpointAudienceReport = () =>
	graphql(AudienceReportQuery, {
		...getAudienceReportMapper(
			result => result.page.viewsMetric,
			Routes.SITES_TOUCHPOINTS_KNOWN_INDIVIDUALS
		)
	});

export default withAudienceReportCard(withTouchpointAudienceReport);

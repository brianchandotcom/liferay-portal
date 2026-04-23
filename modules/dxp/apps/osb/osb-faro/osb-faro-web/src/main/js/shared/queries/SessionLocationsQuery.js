import {GEOLOCATION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from '@apollo/client';

export default gql`
	query SiteMetrics(
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		site(
			channelId: $channelId
			includePrevious: false
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			sessionsMetric {
				...geolocationFragment
			}
		}
	}

	${GEOLOCATION_FRAGMENT}
`;

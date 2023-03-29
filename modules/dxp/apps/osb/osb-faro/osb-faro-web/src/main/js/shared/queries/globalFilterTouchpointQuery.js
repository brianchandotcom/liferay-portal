import {DEVICE_FRAGMENT, GEOLOCATION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';

/**
 * Global Filter Touchpoint Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default (queryName, metricName) => gql`
		query GlobalFilterTouchpointQuery(
			$channelId: String
			$touchpoint: String
			$rangeKey: Int!
			$title: String
		) {
			${queryName}(
				channelId: $channelId
				canonicalUrl: $touchpoint
				rangeKey: $rangeKey
				title: $title
			) {
				${metricName} {
					...deviceFragment
					...geolocationFragment
				}
			}
		}

		${DEVICE_FRAGMENT}
		${GEOLOCATION_FRAGMENT}
	`;

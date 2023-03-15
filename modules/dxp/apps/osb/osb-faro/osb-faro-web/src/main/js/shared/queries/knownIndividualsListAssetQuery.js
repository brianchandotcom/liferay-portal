import {gql} from 'apollo-boost';
import {INDIVIDUALS_FRAGMENT} from 'shared/queries/fragments';

/**
 * Known Individuals List Asset Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default (queryName, metricName) => gql`
		query KnownIndividualsListAssetQuery(
			$assetId: String!
			$channelId: String
			$devices: String
			$keywords: String
			$location: String
			$rangeEnd: String
			$rangeKey: Int
			$rangeStart: String
			$size: Int!
			$start: Int!
			$title: String
			$touchpoint: String
		) {
			${queryName}(
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
				${metricName} {
					...individualsFragment
				}
			}
		}

		${INDIVIDUALS_FRAGMENT}
	`;

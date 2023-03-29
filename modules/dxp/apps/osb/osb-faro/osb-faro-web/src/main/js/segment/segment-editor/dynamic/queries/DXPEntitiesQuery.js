import {EntityType} from '../context/referencedObjects';
import {gql} from 'apollo-boost';

/**
 * DXP Entity List Query
 * @description Create a GraphQL query
 * @param {string} queryName
 * @param {string} metricName
 * @returns GraphQL query
 */
export default entityName => {
	if ([EntityType.Teams, EntityType.Groups].includes(entityName)) {
		return gql`
			query DXPEntitiesList(
				$channelId: String
				$keywords: String
				$size: Int!
				$sort: Sort!
				$start: Int!
			) {
				${entityName}(
					channelId: $channelId
					keywords: $keywords
					size: $size
					sort: $sort
					start: $start
				) {
					dxpEntities {
						dataSourceName
						id
						name
					}
					total
				}
			}
		`;
	}

	return gql`
		query DXPEntitiesList(
			$channelId: String
			$keywords: String
			$size: Int!
			$sort: Sort!
			$start: Int!
		) {
			${entityName}(
				channelId: $channelId
				keywords: $keywords
				size: $size
				sort: $sort
				start: $start
			) {
				dxpEntities {
					dataSourceName
					id
					name
				}
				total
			}
		}
	`;
};

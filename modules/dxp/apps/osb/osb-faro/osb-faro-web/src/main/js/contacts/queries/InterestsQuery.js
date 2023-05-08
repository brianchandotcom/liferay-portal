import {COMPOSITION_FRAGMENT} from 'shared/queries/fragments';
import {CompositionTypes} from 'shared/util/constants';
import {gql} from 'apollo-boost';

const INTERESTS_ID_MAP = {
	[CompositionTypes.AccountInterests]: 'accountId',
	[CompositionTypes.SegmentInterests]: 'individualSegmentId'
};

export default queryName => gql`
	query Interests(
		$active: Boolean!
		$channelId: String
		$id: String!
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		${queryName}(
			active: $active
			channelId: $channelId
			${INTERESTS_ID_MAP[queryName]}: $id
			keywords: $keywords
			size: $size
			sort: $sort
			start: $start
		) {
			...compositionFragment
		}
	}

	${COMPOSITION_FRAGMENT}
`;

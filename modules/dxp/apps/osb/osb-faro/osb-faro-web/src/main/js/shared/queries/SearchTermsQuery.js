import {COMPOSITION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';

export default gql`
	query SearchTerms(
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$start: Int!
	) {
		searchTerms(
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
			size: $size
			start: $start
		) {
			...compositionFragment
		}
	}

	${COMPOSITION_FRAGMENT}
`;

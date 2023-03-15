import {COMPOSITION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';

export interface IIndividualInterestsData {
	individualInterests: {
		compositions: [
			{
				name: string;
				count: number;
			}
		];
		maxCount: number;
		total: number;
		totalCount: number;
	};
}

export interface IIndividualInterestsVariables {
	active: boolean;
	channelId: string;
	id: string;
	size: number;
	sort: {
		column: string;
		type: string;
	};
	start: number;
}

export default gql`
	query Interests(
		$channelId: String
		$keywords: String
		$size: Int!
		$sort: Sort!
		$start: Int!
	) {
		individualInterests(
			channelId: $channelId
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

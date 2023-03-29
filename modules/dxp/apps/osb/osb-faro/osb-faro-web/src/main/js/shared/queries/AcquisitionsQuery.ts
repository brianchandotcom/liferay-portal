import {COMPOSITION_FRAGMENT} from 'shared/queries/fragments';
import {gql} from 'apollo-boost';
import {SafeRangeSelectors} from 'shared/types';

export interface AcquisitionsQueryData {
	acquisitions: {
		acquisitions: {
			compositions: {
				count: number;
				name: string;
			};
			maxCount: number;
			total: number;
			totalCount: number;
		};
	};
}

export interface AcquisitionsQueryVariables extends SafeRangeSelectors {
	activeTabId: string;
	channelId?: string;
	size: number;
	start: number;
}

export default gql`
	query Acquisitions(
		$activeTabId: AcquisitionType!
		$channelId: String
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
		$size: Int!
		$start: Int!
	) {
		acquisitions(
			acquisitionType: $activeTabId
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

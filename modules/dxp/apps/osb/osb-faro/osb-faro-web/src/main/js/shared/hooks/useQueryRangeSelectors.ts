import useQueryParams from './useQueryParams';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {RangeSelectors} from 'shared/types';

const useQueryRangeSelectors = (
	initialRangeSelectors: RangeSelectors = {
		rangeEnd: null,
		rangeKey: RangeKeyTimeRanges.Last30Days,
		rangeStart: null
	}
): RangeSelectors => {
	const {
		rangeEnd = initialRangeSelectors.rangeEnd,
		rangeKey = initialRangeSelectors.rangeKey,
		rangeStart = initialRangeSelectors.rangeStart
	} = useQueryParams();

	return {
		rangeEnd: rangeEnd === 'null' ? null : (rangeEnd as string),
		rangeKey: rangeKey as RangeKeyTimeRanges,
		rangeStart: rangeStart === 'null' ? null : (rangeStart as string)
	};
};

export default useQueryRangeSelectors;

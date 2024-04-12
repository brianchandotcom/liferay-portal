import {RangeKeyTimeRanges} from 'shared/util/constants';
import {RangeSelectors} from 'shared/types';
import {useQueryParams} from 'shared/hooks/useQueryParams';

export const DEFAULT_RANGE_SELECTORS = {
	rangeEnd: null,
	rangeKey: RangeKeyTimeRanges.Last30Days,
	rangeStart: null
};

export const useQueryRangeSelectors = (): RangeSelectors => {
	const rangeSelectors = useUnsafeQueryRangeSelectors();

	return rangeSelectors || DEFAULT_RANGE_SELECTORS;
};

/**
 * Used to get undefined if there is no rangeKey on query
 * @returns {RangeSelectors | undefined}
 */

export const useUnsafeQueryRangeSelectors = (): RangeSelectors | undefined => {
	const {rangeEnd, rangeKey, rangeStart} = useQueryParams();

	if (!rangeKey) return;

	return {rangeEnd, rangeKey, rangeStart};
};

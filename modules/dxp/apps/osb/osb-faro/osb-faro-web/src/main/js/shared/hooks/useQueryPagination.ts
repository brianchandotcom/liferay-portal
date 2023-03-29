import useQueryParams from './useQueryParams';
import {createOrderIOMap, paginationDefaults} from 'shared/util/pagination';
import {FilterByType, Pagination} from 'shared/types';
import {Map, OrderedMap, Set} from 'immutable';
import {OrderParams} from 'shared/util/records';
import {pick} from 'lodash';

const {
	delta: defaultDelta,
	filterBy: defaultFilterBy,
	page: defaultPage,
	query: defaultQuery
} = paginationDefaults;

type QueryPaginationParams = {
	filterFields?: string[];
	initialDelta?: number;
	initialFilterBy?: FilterByType;
	initialOrderIOMap?: OrderedMap<string, OrderParams>;
	initialPage?: number;
	initialQuery?: string;
};

const useQueryPagination = ({
	filterFields,
	initialDelta = defaultDelta,
	initialFilterBy = defaultFilterBy,
	initialOrderIOMap,
	initialPage = defaultPage,
	initialQuery = defaultQuery
}: QueryPaginationParams): Pagination => {
	const {
		delta = initialDelta,
		field,
		page = initialPage,
		query = initialQuery,
		sortOrder,
		...otherParams
	} = useQueryParams();

	const getFilterByFromFields = (): FilterByType => {
		const filterProps = pick(otherParams, filterFields);

		return Map(
			Object.keys(filterProps).reduce((acc, currentKey) => {
				const filterValues = filterProps[currentKey] as string;

				acc[currentKey] = filterValues
					? Set(filterValues.split(','))
					: Set();

				return acc;
			}, {})
		);
	};

	let orderIOMap = initialOrderIOMap;

	if (field && sortOrder) {
		orderIOMap = createOrderIOMap(field, sortOrder);
	}

	let filterBy = initialFilterBy;

	if (filterFields) {
		filterBy = getFilterByFromFields();
	}

	return {
		delta: parseInt(delta as string),
		filterBy,
		orderIOMap,
		page: parseInt(page as string),
		query: query as string
	};
};

export default useQueryPagination;

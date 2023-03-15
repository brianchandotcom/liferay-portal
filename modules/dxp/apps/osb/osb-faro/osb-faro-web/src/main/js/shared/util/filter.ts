import {orderBy} from 'lodash';
import {toThousands} from 'shared/util/numbers';

type FilterTypes = 'devices' | 'location';

export type Filters = {[key in FilterTypes]: string};

export type RawFilters = {[key in FilterTypes]: string[]};

type RawFilterEntry = {metrics: string; valueKey: string};

type FilterInput = {
	checked: boolean;
	category: FilterTypes;
	inputType: 'radio';
	label: string;
	value: string;
};

type FilterItem = {
	hasSearch: boolean;
	items: FilterInput[];
	label: string;
	name: string;
	value: string;
};

export const filterLangMap = {
	devices: Liferay.Language.get('devices'),
	location: Liferay.Language.get('location')
};

/**
 * Get Filters
 */
export const getFilters = (
	filters: RawFilters = {devices: [], location: []}
): {[key in FilterTypes]: string} =>
	['devices', 'location'].reduce(
		(acc, cur) =>
			filters[cur] && filters[cur].length
				? {...acc, [cur]: filters[cur][0]}
				: acc,
		{devices: 'Any', location: 'Any'}
	);

/**
 * Has Category Filters
 */
export const hasCategoryFilters = (
	filters: RawFilters,
	category: FilterTypes
) => {
	const categoryFilters = filters[category];

	return categoryFilters && categoryFilters.length > 0;
};

/**
 * Is Clear Filter Visible
 */
export const isClearFilterVisible = (filters: RawFilters) => {
	if (filters && Object.keys(filters).length > 1) {
		for (const category in filters) {
			if (!hasCategoryFilters(filters, category as FilterTypes)) {
				return false;
			}
		}

		return true;
	}

	return false;
};

/**
 * Map Entry to Filter Item
 */
export const mapEntryToFilterItem = ({
	metrics,
	valueKey
}: RawFilterEntry): Omit<FilterInput, 'category'> => ({
	checked: false,
	inputType: 'radio',
	label: valueKey,
	value: toThousands(metrics.length)
});

/**
 * Has Search
 * If there are many filter values, we should include search.
 */
export const hasSearch = (items: any[]) => items.length > 15;

/**
 * Get Filter Item
 */
export const getFilterItem = (
	data: RawFilterEntry[],
	category: FilterTypes
): FilterItem => {
	let items = data.map(item => ({...mapEntryToFilterItem(item), category}));

	items = orderBy(items, [({label}) => label.toLowerCase()]);

	return {
		hasSearch: hasSearch(items),
		items,
		label: filterLangMap[category],
		name: filterLangMap[category],
		value: toThousands(items.length)
	};
};

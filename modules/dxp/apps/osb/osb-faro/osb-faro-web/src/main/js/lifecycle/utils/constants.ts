export const FilterType = {
	ALL: 'ALL',
	GREATER_THAN: 'GT',
	LESS_THAN: 'LT',
	// eslint-disable-next-line sort-keys
	BETWEEN: 'BETWEEN'
};

export const REVENUE_CONFIG = {
	[FilterType.ALL]: {fields: [], op: null},
	[FilterType.GREATER_THAN]: {fields: ['min'], op: 'gt'},
	[FilterType.LESS_THAN]: {fields: ['max'], op: 'lt'},
	[FilterType.BETWEEN]: {fields: ['min', 'max'], op: ['ge', 'le']}
};

export const REVENUE_LABELS = {
	[FilterType.ALL]: Liferay.Language.get('all-revenue-ranges'),
	[FilterType.GREATER_THAN]: Liferay.Language.get('revenue-is-greater-than'),
	[FilterType.LESS_THAN]: Liferay.Language.get('revenue-is-less-than'),
	[FilterType.BETWEEN]: Liferay.Language.get('revenue-is-between')
};

export const REVENUE_FILTER_KEYS = Object.values(FilterType);

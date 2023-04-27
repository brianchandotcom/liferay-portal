import Constants, {
	OrderByDirections,
	TimeIntervals
} from 'shared/util/constants';
import sendRequest from 'shared/util/request';
import {
	buildOrderByFields,
	createOrderIOMap,
	INDIVIDUAL_COUNT,
	INTERESTS,
	NAME,
	SCORE
} from 'shared/util/pagination';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA, orderDescending}
} = Constants;

const DEFAULT_MAX = 30;
const DEFAULT_TIME_INTERVAL = TimeIntervals.Day;

export function fetch(params) {
	return search({
		...params,
		orderByFields: [
			{fieldName: NAME, orderBy: orderDescending, system: true}
		]
	}).then(({items}) => {
		if (items && items.length) {
			return items[0];
		}

		throw new Error('No entity by that name exists');
	});
}

export function search(params) {
	const {
		channelId,
		contactsEntityId,
		delta = DEFAULT_DELTA,
		groupId,
		interval = DEFAULT_TIME_INTERVAL,
		max = DEFAULT_MAX,
		name,
		orderIOMap = createOrderIOMap(SCORE, OrderByDirections.Descending),
		page = DEFAULT_PAGE,
		query = ''
	} = params;

	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, INTERESTS);

	return sendRequest({
		data: {
			channelId,
			contactsEntityId,
			cur: page,
			delta,
			interval,
			max,
			name,
			orderByFields,
			query
		},
		method: 'GET',
		path: `contacts/${groupId}/interest`
	});
}

export function searchKeywords(params) {
	const {delta = DEFAULT_DELTA, groupId, page, query} = params;

	return sendRequest({
		data: {
			cur: page,
			delta,
			query
		},
		method: 'GET',
		path: `contacts/${groupId}/interest/keywords`
	});
}
export function searchKeywordAggregations(params) {
	const {
		contactsEntityId,
		contactsEntityType,
		delta = DEFAULT_DELTA,
		groupId,
		orderIOMap = createOrderIOMap(INDIVIDUAL_COUNT),
		page = DEFAULT_PAGE,
		query = ''
	} = params;

	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams);

	return sendRequest({
		data: {
			contactsEntityId,
			contactsEntityType,
			cur: page,
			delta,
			orderByFields,
			query
		},
		method: 'GET',
		path: `contacts/${groupId}/interest/keywords/aggregations`
	});
}

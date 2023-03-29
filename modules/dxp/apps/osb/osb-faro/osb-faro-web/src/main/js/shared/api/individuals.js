import Constants from 'shared/util/constants';
import sendRequest from 'shared/util/request';
import {
	buildOrderByFields,
	createOrderIOMap,
	NAME
} from 'shared/util/pagination';
import {escapeSingleQuotes} from 'segment/segment-editor/dynamic/utils/odata';
import {INDIVIDUALS} from 'shared/util/router';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA}
} = Constants;

export function fetch({channelId, groupId, individualId}) {
	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/individual/${individualId}`
	});
}

export function fetchDetails({groupId, individualId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/individual/${individualId}/details`
	});
}

export function fetchEnrichedProfilesCount({channelId, groupId}) {
	return sendRequest({
		data: {channelId},
		method: 'GET',
		path: `contacts/${groupId}/individual/enriched_profiles_count`
	});
}

export function fetchMembership({
	delta,
	groupId,
	individualSegmentId,
	orderIOMap,
	page,
	query
}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, INDIVIDUALS);

	return sendRequest({
		data: {
			cur: page,
			delta,
			individualSegmentId,
			orderByFields,
			query
		},
		method: 'GET',
		path: `contacts/${groupId}/individual`
	});
}

export function fetchFieldValues({
	channelId,
	fieldMappingFieldName,
	groupId,
	query
}) {
	return sendRequest({
		data: {
			channelId,
			delta: 20,
			fieldMappingFieldName,
			query: escapeSingleQuotes(query)
		},
		method: 'GET',
		path: `contacts/${groupId}/individual/field_values`
	});
}

export function search(params) {
	const {
		accountId = '',
		channelId = '',
		delta = DEFAULT_DELTA,
		groupId,
		individualSegmentId = '',
		notIndividualSegmentId = '',
		orderIOMap = createOrderIOMap(NAME),
		page = DEFAULT_PAGE,
		query = '',
		...otherParams
	} = params;

	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, INDIVIDUALS);

	return sendRequest({
		data: {
			...otherParams,
			accountId,
			channelId,
			cur: page,
			delta,
			individualSegmentId,
			notIndividualSegmentId,
			orderByFields,
			query
		},
		method: 'POST',
		path: `contacts/${groupId}/individual/search`
	});
}

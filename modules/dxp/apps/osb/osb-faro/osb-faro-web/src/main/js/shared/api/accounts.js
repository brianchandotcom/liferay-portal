import FaroConstants from 'shared/util/constants';
import sendRequest from 'shared/util/request';
import {ACCOUNTS} from 'shared/util/router';
import {buildOrderByFields} from 'shared/util/pagination';
import {escapeSingleQuotes} from 'segment/segment-editor/dynamic/utils/odata';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA}
} = FaroConstants;

export function fetch({accountId, groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}`
	});
}

export function fetchDetails({accountId, groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}/details`
	});
}

export function fetchFieldValues({fieldMappingFieldName, groupId, query}) {
	return sendRequest({
		data: {
			delta: DEFAULT_DELTA,
			fieldMappingFieldName,
			query: escapeSingleQuotes(query)
		},
		method: 'GET',
		path: `contacts/${groupId}/account/field_values`
	});
}

export function search({
	channelId = '',
	delta = DEFAULT_DELTA,
	groupId,
	orderIOMap,
	page = DEFAULT_PAGE,
	query = '',
	...otherParams
}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams, ACCOUNTS);

	return sendRequest({
		data: {
			channelId,
			cur: page,
			delta,
			orderByFields,
			query,
			...otherParams
		},
		method: 'POST',
		path: `contacts/${groupId}/account/search`
	});
}

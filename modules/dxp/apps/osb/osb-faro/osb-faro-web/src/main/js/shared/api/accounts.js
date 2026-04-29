import FaroConstants from 'shared/util/constants';
import sendRequest from 'shared/util/request';
import {ACCOUNTS} from 'shared/util/router';
import {buildOrderByFields} from 'shared/util/pagination';
import {escapeSingleQuotes} from 'segment/segment-editor/dynamic/utils/odata';

const {
	pagination: {cur: DEFAULT_PAGE, delta: DEFAULT_DELTA}
} = FaroConstants;

// TODO: replace the mock with the once
// contacts/{groupId}/account/{accountId} is ready
// eslint-disable-next-line @typescript-eslint/no-unused-vars
export function fetch({accountId, groupId}) {
	return Promise.resolve({accountName: 'Dickenson plc'});
}

export function fetchDetails({accountId, groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/${accountId}/details`
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
			delta: DEFAULT_DELTA,
			fieldMappingFieldName,
			query: escapeSingleQuotes(query)
		},
		method: 'GET',
		path: `contacts/${groupId}/account/field_values`
	});
}

export function fetchMetrics({groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account/metrics`
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

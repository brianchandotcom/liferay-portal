import sendRequest from 'shared/util/request';
import {buildOrderByFields} from 'shared/util/pagination';

function delete$({groupId, ids}) {
	return sendRequest({
		data: {ids},
		method: 'DELETE',
		path: `main/${groupId}/blocked_keywords`
	});
}
export {delete$ as delete};

export function search({delta, groupId, orderIOMap, page, query}) {
	const orderParams = orderIOMap.first();

	const orderByFields = buildOrderByFields(orderParams);

	return sendRequest({
		data: {
			cur: page,
			delta,
			orderByFields,
			query
		},
		method: 'GET',
		path: `main/${groupId}/blocked_keywords`
	});
}

export function insertMany({groupId, keywords}) {
	return sendRequest({
		data: {keywords},
		method: 'POST',
		path: `main/${groupId}/blocked_keywords`
	});
}

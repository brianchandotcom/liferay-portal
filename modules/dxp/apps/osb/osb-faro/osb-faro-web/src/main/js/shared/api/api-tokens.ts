import sendRequest from 'shared/util/request';
import {RESTParams} from 'shared/types';

export function generate({groupId}: RESTParams) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/oauth2/tokens/new`
	});
}

export function search({groupId}: RESTParams) {
	return sendRequest({
		method: 'GET',
		path: `main/${groupId}/oauth2/tokens`
	});
}

export function revoke({groupId, token}: RESTParams & {token: string}) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/oauth2/tokens/${token}/revoke`
	});
}

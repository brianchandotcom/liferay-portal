import sendRequest from 'shared/util/request';
import {RESTParams} from 'shared/types';

export function generate({
	expiresIn,
	groupId
}: RESTParams & {expiresIn: string}) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/oauth2/tokens/new?expiresIn=${expiresIn}`
	});
}

export function generateDemandbaseToken({groupId}: RESTParams) {
	return sendRequest({
		method: 'POST',
		path: `main/${groupId}/oauth2/tokens/new?type=demandbase&expiresIn=`
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

import * as API from 'shared/api';
import {account} from '../middleware/schema';
import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';

export const actionTypes = {
	...createActionTypes('fetch', 'account')
};

export function fetchAccount(data) {
	return {
		meta: {
			[CALL_API]: {
				data,
				requestFn: API.accounts.fetch,
				schema: account,
				types: [
					actionTypes.FETCH_ACCOUNT_REQUEST,
					actionTypes.FETCH_ACCOUNT_SUCCESS,
					actionTypes.FETCH_ACCOUNT_FAILURE
				]
			}
		},
		payload: {
			id: data.accountId
		},
		type: 'NO_OP'
	};
}

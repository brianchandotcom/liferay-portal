import * as API from 'shared/api';
import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {user} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'current_user')
};

export function fetchCurrentUser(groupId = 0) {
	return {
		meta: {
			[CALL_API]: {
				data: {groupId},
				requestFn: API.user.fetchCurrentUser,
				schema: user,
				types: [
					actionTypes.FETCH_CURRENT_USER_REQUEST,
					actionTypes.FETCH_CURRENT_USER_SUCCESS,
					actionTypes.FETCH_CURRENT_USER_FAILURE
				]
			}
		},
		type: 'NO_OP'
	};
}

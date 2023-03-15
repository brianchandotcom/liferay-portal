import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {getLayoutSchema} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'layout')
};

export function fetchLayout({
	contactsEntityId,
	contactsLayoutTemplateId = 0,
	groupId,
	type
}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					contactsEntityId,
					contactsLayoutTemplateId,
					type
				},
				method: 'GET',
				path: `contacts/${groupId}/contacts_layout`,
				schema: getLayoutSchema(type),
				types: [
					actionTypes.FETCH_LAYOUT_REQUEST,
					actionTypes.FETCH_LAYOUT_SUCCESS,
					actionTypes.FETCH_LAYOUT_FAILURE
				]
			},
			contactsEntityId,
			type
		},
		type: 'NO_OP'
	};
}

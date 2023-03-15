import {CALL_API} from '../middleware/api';
import {createActionTypes} from 'redux-toolbox';
import {getCardSchema} from '../middleware/schema';

export const actionTypes = {
	...createActionTypes('fetch', 'card')
};

export function fetchCard({
	contactsCardTemplateId,
	contactsCardTemplateSettings = {},
	contactsEntityId,
	contactsEntityType,
	groupId
}) {
	return {
		meta: {
			[CALL_API]: {
				data: {
					contactsCardTemplateId,
					contactsCardTemplateSettings,
					contactsEntityId,
					contactsEntityType
				},
				method: 'GET',
				path: `contacts/${groupId}/contacts_card`,
				schema: getCardSchema(contactsEntityType),
				types: [
					actionTypes.FETCH_CARD_REQUEST,
					actionTypes.FETCH_CARD_SUCCESS,
					actionTypes.FETCH_CARD_FAILURE
				]
			},
			contactsEntityId,
			type: contactsEntityType
		},
		type: 'NO_OP'
	};
}

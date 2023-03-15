import sendRequest from 'shared/util/request';

export function fetch({contactsCriterionId, groupId}) {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/contacts_criterion/${contactsCriterionId}`
	});
}

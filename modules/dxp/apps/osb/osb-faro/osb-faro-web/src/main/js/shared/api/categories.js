import sendRequest from 'shared/util/request';

export function search({
	channelId,
	groupId,
	keywords = '',
	page = 1,
	pageSize = 20,
	vocabularyId = ''
}) {
	return sendRequest({
		data: {channelId, keywords, page, pageSize, vocabularyId},
		method: 'GET',
		path: `contacts/${groupId}/asset-summary-categories`
	});
}

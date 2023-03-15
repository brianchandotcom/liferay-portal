import sendRequest from 'shared/util/request';

export const create = ({
	currentUrl,
	description,
	groupId,
	title
}: {
	currentUrl: string;
	description: string;
	groupId: string;
	title: string;
}): Promise<any> =>
	sendRequest({
		data: {currentUrl, description, title},
		method: 'POST',
		path: `main/${groupId}/issue`
	});

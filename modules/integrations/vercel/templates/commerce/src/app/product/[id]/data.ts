import {WithLiferay} from '@/liferay/server';
import {getChannelProductByFriendlyUrlPath} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';

export const getProductDetails = async ({
	nestedFields,
	friendlyUrlPath,
	liferay,
}: WithLiferay<{
	friendlyUrlPath: string;
	nestedFields: string;
}>) => {
	const response = getChannelProductByFriendlyUrlPath({
		client: liferay.client,
		query: {nestedFields} as unknown as Record<string, string>,
		path: {
			channelId: liferay.getChannel().id,
			friendlyUrlPath,
		},
	});

	return response;
};

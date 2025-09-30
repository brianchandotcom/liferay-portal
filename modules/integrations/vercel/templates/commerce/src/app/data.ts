import {WithLiferay} from '@/liferay/server';
import {getChannelProductsPage} from 'liferay-headless-rest-client/headless-commerce-delivery-catalog-v1.0';
import {SearchBuilder} from 'odata-search-builder';

export const getProductsPage = async ({
	liferay,
	keywords,
	page,
	pageSize,
	specificationValues,
}: WithLiferay<{
	page: string;
	pageSize: string;
	keywords: string | undefined;
	specificationValues: string[];
}>) => {
	const searchBuider = new SearchBuilder();

	if (specificationValues.length) {
		specificationValues.forEach((specificationValue, index) => {
			const lastIndex = index + 1 === specificationValues.length;

			searchBuider.any('specificationValues', {
				operator: 'contains',
				value: specificationValue,
			});

			if (!lastIndex) {
				searchBuider.or();
			}
		});
	}

	const filter = searchBuider.build();

	const response = await getChannelProductsPage({
		client: liferay.client,
		query: {
			...(specificationValues && {filter}),
			search: keywords,
			nestedFields: 'skus',
			page,
			pageSize,
		},
		path: {
			channelId: liferay.getChannel().id,
		},
	});

	return response;
};

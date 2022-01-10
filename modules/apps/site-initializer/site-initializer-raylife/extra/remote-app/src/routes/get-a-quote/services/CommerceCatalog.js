import {LiferayAdapt} from '../../../common/services/liferay/adapter';
import {axios} from '../../../common/services/liferay/api';

const headlessAPI = 'o/headless-commerce-admin-catalog/v1.0';

/**
 * @returns {Promise<ProductQuote[]>)} Array of Product Quote
 */
export async function getProductQuotes() {
	const {data} = await axios.get(
		`${headlessAPI}/products?nestedFields=skus,catalog&page=1&pageSize=50`
	);

	return LiferayAdapt.adaptToProductQuote(data.items);
}

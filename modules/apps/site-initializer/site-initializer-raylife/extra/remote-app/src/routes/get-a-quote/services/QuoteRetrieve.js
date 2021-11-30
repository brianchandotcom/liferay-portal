import {axios} from '../../../common/services/liferay/api';
import {getScopeGroupId} from '../../../common/services/liferay/themeDisplay';

const QuoteRetrieveAPI = 'o/c/quoteretrieves';

export function createQuoteRetrieve(payload) {
	return axios.post(
		`${QuoteRetrieveAPI}/scopes/${getScopeGroupId()}`,
		payload
	);
}

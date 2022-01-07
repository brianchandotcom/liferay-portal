import {axios} from '../../../common/services/liferay/api';
import {Liferay} from '../../../common/utils/liferay';

const QuoteRetrieveAPI = 'o/c/quoteretrieves';

export function createQuoteRetrieve(payload) {
	return axios.post(
		`${QuoteRetrieveAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`,
		payload
	);
}

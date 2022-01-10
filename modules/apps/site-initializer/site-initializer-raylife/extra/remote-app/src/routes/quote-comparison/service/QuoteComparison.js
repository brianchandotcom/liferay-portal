import {axios} from '../../../common/services/liferay/api';

const quoteComparisonAPI = 'o/c/quotecomparisons';

export async function getQuoteComparisons() {
	const response = await axios.get(
		`${quoteComparisonAPI}/scopes/${Liferay.ThemeDisplay.getScopeGroupId()}`
	);

	return response.data;
}

export async function getQuoteComparisonById(id) {
	const response = await axios.get(`${quoteComparisonAPI}/${id}`);

	return response.data;
}

import {useLocation} from 'react-router-dom';

function queryStringToObject(initialQueryString: string): any {
	if (!initialQueryString) return {};

	const queryString = initialQueryString.replace('?', '');

	const params = queryString.split('&');
	const query = {};

	params.forEach(param => {
		const [key, value] = param.split('=');
		query[key] = value;
	});

	return query;
}

// TODO: Remove this once we upgrade to react-router-dom v6
export function useQueryParams() {
	const {search} = useLocation();

	return queryStringToObject(search);
}

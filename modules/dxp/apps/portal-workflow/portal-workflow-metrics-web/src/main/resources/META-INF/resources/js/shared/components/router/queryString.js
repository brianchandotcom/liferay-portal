import qs from 'qs';

export function parse(queryString) {
	if (queryString && queryString.length) {
		return qs.parse(queryString.substr(1));
	}

	return {};
}

export function stringify(query) {
	if (query) {
		return `?${qs.stringify(query)}`;
	}
}
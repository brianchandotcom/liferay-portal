import qs from 'qs';

const options = {allowDots: true, arrayFormat: 'bracket'};

export function parse(queryString) {
	if (queryString && queryString.length) {
		return qs.parse(queryString.substr(1), options);
	}

	return {};
}

export function stringify(query) {
	if (query) {
		return `?${qs.stringify(query, options)}`;
	}
}
/**
 * @module url
 * @description Convert a json to query string.
 * @param json
 * @param json.query
 * @param json.title
 * @param json.to
 * @example
 * import {jsonToUrl} from '@/shared/util/url';
 * jsonToUrl({processId: 10});
 */
export const jsonToUrl = ({query = {}, title, to}) => {
	if (title) {
		query['_title'] = title;
	}

	query['_path'] = to;

	const url = Object.keys(query)
		.map(key => `${key}=${query[key]}`)
		.join('&');

	return `#${url}`;
};
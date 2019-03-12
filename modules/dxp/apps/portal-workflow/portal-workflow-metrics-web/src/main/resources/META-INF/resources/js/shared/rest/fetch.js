/**
 * @module fetch
 * @description  Basic alias of fetch client to perform consults in portal rest API.
 * @example
 * import fetch from '@/shared/rest/fetch';
 * fetch('/process').then(res => console.log(res));
 * */
const restClient = (url, config = {}) => {
	if (url) {
		url = `/o/portal-workflow-metrics/v1.0${url}`;
	}

	return fetch(url, config).then(res => {
		if (!res.ok) {
			throw res;
		}

		return res.json();
	});
};

export default restClient;
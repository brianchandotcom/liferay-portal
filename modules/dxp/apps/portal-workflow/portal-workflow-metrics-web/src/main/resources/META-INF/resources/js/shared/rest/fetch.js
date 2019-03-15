const getResponseBody = response => {
	const contentType = response.headers.get('content-type');

	if (contentType && contentType.includes('application/json')) {
		return response.json();
	}

	return response.text().then(error => ({
		message: error
	}));
};

/**
 * @module fetch
 * @description Basic alias of fetch client to perform consults in portal rest API.
 * @example
 * import fetch from '@/shared/rest/fetch';
 * fetch('/process').then(res => console.log(res));
 */
const restClient = (url, config = {}) => {
	if (url) {
		url = `/o/portal-workflow-metrics/v1.0${url}`;
	}

	return new Promise((resolve, reject) => {
		fetch(url, config).then(response => {
			if (response && response.ok) {
				getResponseBody(response)
					.then(resolve)
					.catch(reject);
			}
			else {
				getResponseBody(response)
					.then(reject)
					.catch(reject);
			}
		});
	});
};

export default restClient;
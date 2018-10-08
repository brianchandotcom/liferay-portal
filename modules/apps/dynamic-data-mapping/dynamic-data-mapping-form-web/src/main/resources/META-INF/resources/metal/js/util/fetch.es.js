import 'url-search-params-polyfill';

const defaultHeaders = {
	'Accept': 'application/json',
	'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
};

export const makeFetch = ({url, body, headers = defaultHeaders, method = 'POST'}) => {
	const fetchData = {
		credentials: 'include',
		headers,
		method
	};

	if (method === 'POST') {
		fetchData.body = body;
	}
	return fetch(
		url,
		fetchData
	)
		.then(response => response.json())
		.catch(
			error => {
				const sessionStatus = Liferay.Session.get('sessionState');

				if (sessionStatus === 'expired' || error.status === 401) {
					window.location.reload();
				}
			}
		);
};

export const convertToSearchParams = body => {
	let searchParams = new URLSearchParams();

	if (body instanceof HTMLFormElement) {
		const formData = new FormData(body);

		formData.forEach((value, key) => searchParams.set(key, value));
	}
	else if (body instanceof FormData) {
		body.forEach((value, key) => searchParams.set(key, value));
	}
	else if (typeof body === 'object') {
		Object.entries(body).forEach(([key, value]) => searchParams.append(key, value));
	}
	else {
		searchParams = body;
	}

	return searchParams;
};
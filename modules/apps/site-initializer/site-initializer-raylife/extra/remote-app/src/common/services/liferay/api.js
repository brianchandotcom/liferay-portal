import baseAxios from 'axios';
import {Liferay} from '../../utils/liferay';

const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const baseFetch = async (url, {body, method = 'GET'} = {}) => {
	const liferayAPIUrl = new URL(`${REACT_APP_LIFERAY_API}/${url}`);

	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(liferayAPIUrl, {
		...(body && {body: JSON.stringify(body)}),
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		method,
	});

	const data = await response.json();

	return {data};
};

const LiferayFetchAPI = {
	delete: (url) => baseFetch(url, {method: 'DELETE'}),
	get: (url) => baseFetch(url),
	patch: (url, options) => baseFetch(url, {...options, method: 'PATCH'}),
	post: (url, options) => baseFetch(url, {...options, method: 'POST'}),
	put: (url, options) => baseFetch(url, {...options, method: 'PUT'}),
};

const axios = baseAxios.create({
	baseURL: REACT_APP_LIFERAY_API,
	headers: {
		'x-csrf-token': Liferay.authToken,
	},
});

export {REACT_APP_LIFERAY_API, axios};

export default LiferayFetchAPI;

import _axios from 'axios';
import {Liferay} from '../../utils/liferay';

const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const axios = _axios.create({
	baseURL: REACT_APP_LIFERAY_API,
	headers: {
		'x-csrf-token': Liferay.authToken,
	},
});

export {axios};

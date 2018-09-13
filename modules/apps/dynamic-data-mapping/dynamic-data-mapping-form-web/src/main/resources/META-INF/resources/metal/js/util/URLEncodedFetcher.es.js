/* eslint no-spaced-func: 0 */

import 'url-search-params-polyfill';
import {Config} from 'metal-state';
import objectToFormData from 'frontend-js-web/liferay/util/object_to_form_data.es';
import Component from 'metal-jsx';

class URLEncodedFetcher extends Component {
	static PROPS = {
		headers: Config.object().value(
			{
				'Accept': 'application/json',
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
			}
		),
		url: Config.string()
	};

	fetch(body) {
		const {headers, url} = this.props;

		return fetch(
			url,
			{
				body: this._getSearchParams(body),
				credentials: 'include',
				headers,
				method: 'POST'
			}
		)
			.then(response => response.json())
			.catch (
				error => {
					const sessionStatus = Liferay.Session.get('sessionState');

					if (sessionStatus === 'expired' || error.status === 401) {
						window.location.reload();
					}
				}
			);
	}

	_getSearchParams(body) {
		let formData;

		if (body instanceof HTMLFormElement) {
			formData = new FormData(body);
		}
		else if (typeof body === 'object') {
			formData = objectToFormData(body);
		}
		else {
			formData = body;
		}

		const searchParams = new URLSearchParams();

		formData.forEach((value, key) => searchParams.set(key, value));

		return searchParams;
	}
}

export default URLEncodedFetcher;
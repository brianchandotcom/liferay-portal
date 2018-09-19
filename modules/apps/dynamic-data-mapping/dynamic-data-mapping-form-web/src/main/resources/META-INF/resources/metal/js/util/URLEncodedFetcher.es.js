/* eslint no-spaced-func: 0 */

import 'url-search-params-polyfill';
import {Config} from 'metal-state';
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
				reason => {
					const sessionStatus = Liferay.Session.get('sessionState');

					if (sessionStatus === 'expired' || reason.status === 401) {
						window.location.reload();
					}

					throw reason;
				}
			);
	}

	_getSearchParams(body) {
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
	}
}

export default URLEncodedFetcher;
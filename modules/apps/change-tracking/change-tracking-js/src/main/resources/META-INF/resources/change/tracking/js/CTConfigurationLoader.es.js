import Component from 'metal-component';

import {Config} from 'metal-state';
import CTConfigurationModel from './CTConfigurationModel.es';

class CTConfigurationLoader extends Component {

	fetchCollection(filters, fetchErrorCallback, parseErrorCallback) {
		let ctConfigurationModels = [];

		return fetch(
			this._prepareActualUrl(filters),
			{
				credentials: 'include',
				headers: this.headers,
				method: this.method
			}
		)
			.then(
				response => {
					if (response.status !== 200) {
						fetchErrorCallback(response.status, response.statusText);
					}
					else {
						return response.json();
					}
				}
			)
			.then(
				json => {
					ctConfigurationModels.push(
						new CTConfigurationModel(
							{
								companyId: json.companyId,
								isChangeTrackingEnabled: json.isChangeTrackingEnabled,
								supportedContentTypeLanguageKeys: json.supportedContentTypeLanguageKeys,
								supportedContentTypes: json.supportedContentTypes
							}
						)
					);
				}
			)
			.catch(
				error => parseErrorCallback(error)
			);
	}

	fetchSingleton(filters, fetchErrorCallback, parseErrorCallback) {
		return fetch(
			this._prepareActualUrl(filters),
			{
				credentials: 'include',
				headers: this.headers,
				method: this.method
			}
		)
			.then(
				response => {
					if (response.status !== 200) {
						fetchErrorCallback(response.status, response.statusText);
					}
					else {
						return response.json();
					}
				}
			)
			.then(
				json => {
					return new CTConfigurationModel(
						{
							companyId: json.companyId,
							isChangeTrackingEnabled: json.isChangeTrackingEnabled,
							supportedContentTypeLanguageKeys: json.supportedContentTypeLanguageKeys,
							supportedContentTypes: json.supportedContentTypes
						}
					);
				}
			)
			.catch(
				error => parseErrorCallback(error)
			);
	}

	_prepareActualUrl(filters) {
		let actualUrl = this.urlEndpoint;

		filters.forEach(
			function(key, value) {
				if (actualUrl.indexOf('?') === -1) {
					actualUrl = actualUrl + '?' + key + '=' + value;
				}
				else {
					actualUrl = actualUrl + '&' + key + '=' + value;
				}
			}
		);

		return actualUrl;
	}

}

CTConfigurationLoader.STATE = {

	urlEndpoint: Config.string(),

	method: Config.string(),

	headers: Config.object()

};

export default CTConfigurationLoader;
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

import templates from './ChangeListsConfiguration.soy';

/**
 * Component for the Change Tracking Change Lists configuration screen
 * @review
 */
class ChangeListsConfiguration extends PortletBase {

	created() {
		this._getDataRequest(
			this.urlChangeTrackingConfiguration,
			response => {
				if (response) {
					this.changeTrackingEnabled = response.changeTrackingEnabled;
					this.initialFetch = true;
					this.tooltipBody = '';

					response.supportedContentTypes.forEach(
						(supportedContentType) => {
							this.tooltipBody = this.tooltipBody.concat(supportedContentType);
						}
					);
				}
			}
		);
	}

	/**
	 * Handles the change of the toggle
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleCheck(event) {
		this.changeTrackingEnabled = event.target.checked;
	}

	/**
	 * Saves the configuration
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSave(event) {
		event.preventDefault();

		let data = {
			changeTrackingEnabled: this.changeTrackingEnabled
		};

		this._putDataRequest(
			this.urlChangeTrackingConfiguration,
			data,
			response => {
				const message = Liferay.Language.get('the-configuration-has-been-saved');

				openToast(
					{
						message,
						title: Liferay.Language.get('success'),
						type: 'success'
					}
				);
			}
		);
	}

	/**
	 * Saves the configuration and redirects the user to the overview screen
	 * @param {!Event} event
	 * @private
	 * @review
	 */
	_handleSaveAndGoToOverview(event) {
		let data = {
			changeTrackingEnabled: this.changeTrackingEnabled
		};

		this._putDataRequest(
			this.urlChangeTrackingConfiguration,
			data,
			response => {
				if (response) {
					const message = Liferay.Language.get('the-configuration-has-been-saved');

					openToast(
						{
							message,
							title: Liferay.Language.get('success'),
							type: 'success'
						}
					);

					Liferay.Util.navigate(this.urlOverview);
				}
			}
		);
	}

	_getDataRequest(url, callback) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			credentials: 'include',
			headers,
			method: 'GET'
		};

		fetch(url, request)
			.then(response => response.json())
			.then(response => callback(response))
			.catch(
				(error) => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('an-error-occured-while-saving-configuration');

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);
				}
			);
	}

	_putDataRequest(url, bodyData, callback) {
		let body = JSON.stringify(bodyData);

		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			body,
			credentials: 'include',
			headers,
			method: 'PUT'
		};

		fetch(url, request)
			.then(response => response.json())
			.then(response => callback(response))
			.catch(
				(error) => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('an-error-occured-while-saving-configuration');

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);
				}
			);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
ChangeListsConfiguration.STATE = {

	/**
	 * If true, change tracking is enabled
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @review
	 * @type {boolean}
	 */

	changeTrackingEnabled: Config.bool(),

	/**
	 * If true, an initial fetch has already happened
	 * @default false
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @review
	 * @type {boolean}
	 */

	initialFetch: Config.bool().value(false),

	/**
	 * Property that contains the url for the REST service to the change
	 * tracking configuration endpoint
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @review
	 * @type {!string}
	 */

	urlChangeTrackingConfiguration: Config.string().required(),

	/**
	 * Property that contains the url for the 'Overview' screen
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @review
	 * @type {!string}
	 */

	urlOverview: Config.string().required(),

	/**
	 * Path of the available icons.
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @review
	 * @type {!string}
	 */

	spritemap: Config.string().required(),

	/**
	 * An array of content types that support change tracking
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @review
	 * @type {string}
	 */

	tooltipBody: Config.string()
};

Soy.register(ChangeListsConfiguration, templates);

export default ChangeListsConfiguration;
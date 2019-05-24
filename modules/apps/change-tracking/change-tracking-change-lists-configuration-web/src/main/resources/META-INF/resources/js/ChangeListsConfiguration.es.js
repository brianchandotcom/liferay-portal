import ClayNavigationBar from 'clay-navigation-bar';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import Soy from 'metal-soy';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

import templates from './ChangeListsConfiguration.soy';

/**
 * Provides the component for the Change Lists configuration screen.
 */
class ChangeListsConfiguration extends PortletBase {

	created() {
		this._getDataRequest(
			this.urlChangeTrackingConfiguration,
			response => {
				if (response) {
					this.changeTrackingAllowed = response.changeTrackingAllowed;
					this.changeTrackingEnabled = response.changeTrackingEnabled;
					this.currentPage = 'Global Settings';
					this.initialFetch = true;
					this.tooltipBody = '';

					response.supportedContentTypes.forEach(
						(supportedContentType) => {
							if (this.tooltipBody.length > 0) {
								this.tooltipBody = this.tooltipBody.concat(' ');
							}
							this.tooltipBody = this.tooltipBody.concat(supportedContentType);
						}
					);
				}
			}
		);
		this._getDataRequest(
			this.urlChangeTrackingUserConfiguration,
			response => {
				if (response) {
					this.checkoutCTCollectionConfirmationEnabled = response.checkoutCTCollectionConfirmationEnabled;
				}
			}
		);
	}

	/**
	 * Handles the toggle change.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleCheck(event) {
		this.changeTrackingEnabled = event.target.checked;
	}

	/**
	 * Handles navigation click.
	 *
	 * @param {!Event} event
	 * @private
	 */
	_handleNavItemClicked(event) {
		this.currentPage = event.data.item.label;
		this.navigationItems = this.navigationItems.map(
			item => {
				if (item.label === this.currentPage) {
					return Object.assign(
						{},
						item,
						{
							active: true
						}
					);
				}

				return Object.assign(
					{},
					item,
					{
						active: false
					}
				);

			}
		);
	}

	_handleUserConfigCheck(event) {
		this.checkoutCTCollectionConfirmationEnabled = event.target.checked;
	}

	_handleUserConfigSave(event) {
		event.preventDefault();

		let data = {
			checkoutCTCollectionConfirmationEnabled: this.checkoutCTCollectionConfirmationEnabled
		};

		this._putDataRequest(
			this.urlChangeTrackingUserConfiguration,
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
				}
			}
		);
	}

	/**
	 * Saves the configuration.
	 *
	 * @param {!Event} event
	 * @private
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
				Liferay.Util.navigate(this.urlConfiguration);
			}
		);
	}

	/**
	 * Saves the configuration and redirects the user to the overview screen.
	 *
	 * @param {!Event} event
	 * @private
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
		headers.append('X-CSRF-Token', Liferay.authToken);

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
		headers.append('X-CSRF-Token', Liferay.authToken);

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
 *
 * @static
 * @type {!Object}
 */
ChangeListsConfiguration.STATE = {

	/**
	 * If <code>true</code>, change tracking is allowed.
	 *
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {boolean}
	 */
	changeTrackingAllowed: Config.bool(),

	/**
	 * If <code>true</code>, change tracking is enabled.
	 *
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {boolean}
	 */
	changeTrackingEnabled: Config.bool(),

	/**
	 * If <code>true</code>, checkout change tracking confirmation is enabled.
	 *
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {boolean}
	 */
	checkoutCTCollectionConfirmationEnabled: Config.bool(),

	/**
	 * sets which page to display based on navigationItems
	 *
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {string}
	 */
	currentPage: Config.string().value('Global Settings'),

	/**
	 * If <code>true</code>, an initial fetch has already occurred.
	 *
	 * @default false
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {boolean}
	 */
	initialFetch: Config.bool().value(false),

	/**
	 * Itemlist for navigationBar
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!array}
	 */
	navigationItems: Config.arrayOf(
		Config.shapeOf(
			{
				active: Config.bool().value(false),
				href: Config.string(),
				label: Config.string().required()
			}
		)
	).required(),

	/**
	 * URL for the REST service to the change tracking configuration endpoint.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!string}
	 */
	urlChangeTrackingConfiguration: Config.string().required(),

	/**
	 * URL for the REST service to the change tracking user configuration endpoint.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!string}
	 */
	urlChangeTrackingUserConfiguration: Config.string().required(),

	/**
	 * URL for the Overview screen.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!string}
	 */
	urlOverview: Config.string().required(),

	/**
	 * URL for the Configuration screen.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!string}
	 */
	urlConfiguration: Config.string().required(),

	/**
	 * Path of the available icons.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * Content types that support change tracking.
	 *
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {string}
	 */
	tooltipBody: Config.string()
};

Soy.register(ChangeListsConfiguration, templates);

export default ChangeListsConfiguration;
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import 'clay-navigation-bar';
import {PortletBase, fetch, openToast} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './ChangeListsConfiguration.soy';

/**
 * Provides the component for the Change Lists configuration screen.
 */
class ChangeListsConfiguration extends PortletBase {
	created() {
		this._getDataRequest(this.urlChangeTrackingConfiguration, response => {
			if (response) {
				this.changeTrackingAllowed = response.changeTrackingAllowed;
				this.changeTrackingEnabled = response.changeTrackingEnabled;
				this.currentPage = 'Global Settings';
				this.initialFetch = true;
				this.tooltipBody = '';

				if (this.changeTrackingEnabled) {
					this.userSettingsEnabled = true;
				}

				response.supportedContentTypes.forEach(supportedContentType => {
					if (this.tooltipBody.length > 0) {
						this.tooltipBody = this.tooltipBody.concat(' ');
					}
					this.tooltipBody = this.tooltipBody.concat(
						supportedContentType
					);
				});
			}
		});
		this._getDataRequest(
			this.urlChangeTrackingUserConfiguration,
			response => {
				if (response) {
					this.checkoutCTCollectionConfirmationEnabled =
						response.checkoutCTCollectionConfirmationEnabled;
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

		this.navigationItems = this.navigationItems.map(item => {
			if (item.label === this.currentPage) {
				return Object.assign({}, item, {
					active: true
				});
			}

			return Object.assign({}, item, {
				active: false
			});
		});
	}

	_handleUserConfigCheck(event) {
		this.checkoutCTCollectionConfirmationEnabled = event.target.checked;
	}

	_handleUserConfigSave(event) {
		event.preventDefault();

		const data = {
			checkoutCTCollectionConfirmationEnabled: this
				.checkoutCTCollectionConfirmationEnabled
		};

		this._putDataRequest(
			this.urlChangeTrackingUserConfiguration,
			data,
			response => {
				if (response) {
					const message = Liferay.Language.get(
						'the-configuration-has-been-saved'
					);

					openToast({
						message,
						title: Liferay.Language.get('success'),
						type: 'success'
					});
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

		const data = {
			changeTrackingEnabled: this.changeTrackingEnabled
		};

		this._putDataRequest(this.urlChangeTrackingConfiguration, data, () => {
			Liferay.Util.navigate(this.urlConfiguration);
		});
	}

	/**
	 * Saves the configuration and redirects the user to the overview screen.
	 *
	 * @private
	 */
	_handleSaveAndGoToOverview() {
		const data = {
			changeTrackingEnabled: this.changeTrackingEnabled
		};

		this._putDataRequest(
			this.urlChangeTrackingConfiguration,
			data,
			response => {
				if (response) {
					const message = Liferay.Language.get(
						'the-configuration-has-been-saved'
					);

					openToast({
						message,
						title: Liferay.Language.get('success'),
						type: 'success'
					});

					Liferay.Util.navigate(this.urlOverview);
				}
			}
		);
	}

	_getDataRequest(url, callback) {
		fetch(url)
			.then(response => response.json())
			.then(response => callback(response))
			.catch(error => {
				const message =
					typeof error === 'string'
						? error
						: Liferay.Language.get(
								'an-error-occured-while-saving-configuration'
						  );

				openToast({
					message,
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
	}

	_putDataRequest(url, bodyData, callback) {
		const init = {
			body: JSON.stringify(bodyData),
			headers: {
				'content-type': 'application/json'
			},
			method: 'PUT'
		};

		fetch(url, init)
			.then(response => response.json())
			.then(response => callback(response))
			.catch(error => {
				const message =
					typeof error === 'string'
						? error
						: Liferay.Language.get(
								'an-error-occured-while-saving-configuration'
						  );

				openToast({
					message,
					title: Liferay.Language.get('error'),
					type: 'danger'
				});
			});
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
	navigationItem: Config.arrayOf(
		Config.shapeOf({
			active: Config.bool().value(false),
			href: Config.string(),
			label: Config.string().required()
		})
	).required(),

	/**
	 * Itemlist for navigationBar
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!array}
	 */
	navigationItems: Config.arrayOf(
		Config.shapeOf({
			active: Config.bool().value(false),
			href: Config.string(),
			label: Config.string().required()
		})
	).required(),

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
	tooltipBody: Config.string(),

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
	 * URL for the Configuration screen.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {!string}
	 */
	urlConfiguration: Config.string().required(),

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
	 * If <code>true</code>, User Settings is available in navigation.
	 *
	 * @default false
	 * @instance
	 * @memberOf ChangeListsConfiguration
	 * @type {boolean}
	 */
	userSettingsEnabled: Config.bool().value(false)
};

Soy.register(ChangeListsConfiguration, templates);

export default ChangeListsConfiguration;

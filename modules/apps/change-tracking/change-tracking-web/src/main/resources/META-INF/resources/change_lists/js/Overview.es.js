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

import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import templates from './Overview.soy';

/**
 * Provides the component for the Overview configuration screen.
 */
class Overview extends PortletBase {
	created() {
		this._render();
	}

	_render() {
		this.initialFetch = true;

		this.headerButtonDisabled = false;

		if (this.changeEntries.length === 0) {
			this.headerButtonDisabled = true;
		}
	}
}

/**
 * State definition.
 *
 * @static
 * @type {!Object}
 */
Overview.STATE = {
	/**
	 * The active change tracking collection.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	activeCTCollection: Config.shapeOf({
		additionCount: Config.number().value(0),
		deleteURL: Config.string(),
		deletionCount: Config.number().value(0),
		description: Config.string(),
		modifiedCount: Config.number().value(0),
		name: Config.string(),
		publishURL: Config.string()
	}).required(),

	/**
	 * Change entries for the currently selected change tracking collection.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	changeEntries: Config.arrayOf(
		Config.shapeOf({
			changeType: Config.string(),
			contentType: Config.string(),
			diffURL: Config.string(),
			editURL: Config.string(),
			lastEdited: Config.string(),
			userName: Config.string()
		})
	).required(),

	/**
	 * List of drop down menu items.
	 *
	 * @default []
	 * @instance
	 * @memberOf Overview
	 * @type {array}
	 */
	changeListsDropdownMenu: Config.arrayOf(
		Config.shapeOf({
			checkoutURL: Config.string(),
			label: Config.string()
		})
	).required(),

	/**
	 * If <code>true</code>, head button is disabled.
	 *
	 * @default false
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	headerButtonDisabled: Config.bool().value(false),

	/**
	 * If <code>true</code>, an initial fetch has already occurred.
	 *
	 * @default false
	 * @instance
	 * @memberOf Overview
	 * @type {boolean}
	 */
	initialFetch: Config.bool().value(false),

	/**
	 * The latest published change tracking process.
	 *
	 * @default
	 * @instance
	 * @memberOf Overview
	 * @type {object}
	 */
	latestCTProcess: Config.shapeOf({
		dateTime: Config.string(),
		description: Config.string(),
		name: Config.string(),
		userInitials: Config.string(),
		userName: Config.string(),
		userPortraitURL: Config.string()
	}).required(),

	/**
	 * Path of the available icons.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {!string}
	 */
	spritemap: Config.string().required(),

	/**
	 * URL to check out production.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {string}
	 */
	urlCheckoutProduction: Config.string().required(),

	/**
	 * URL for the production view.
	 *
	 * @default undefined
	 * @instance
	 * @memberOf Overview
	 * @type {!string}
	 */
	urlProductionView: Config.string().required()
};

Soy.register(Overview, templates);

export {Overview};
export default Overview;

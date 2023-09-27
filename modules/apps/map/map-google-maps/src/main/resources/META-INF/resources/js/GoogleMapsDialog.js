/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * GoogleMapsDialog
 * @review
 */
export default class GoogleMapsDialog {
	get map() {
		return this._STATE_.map;
	}

	set map(map) {
		this._STATE_.map = map;
	}

	/**
	 * Creates a new map dialog using Google Map's API
	 * @param  {Array} args List of arguments to be passed to State
	 * @review
	 */
	constructor(args = {}) {
		const {map} = args;

		this._dialog = new google.maps.InfoWindow();

		this._STATE_ = {
			map,
		};
	}

	/**
	 * Opens the dialog with the given map attribute and passes
	 * the given configuration to the dialog object.
	 * @param {Object} cfg
	 * @review
	 */
	open(cfg) {
		this._dialog.setOptions(cfg);

		this._dialog.open(this.map, cfg.marker);
	}
}

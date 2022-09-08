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

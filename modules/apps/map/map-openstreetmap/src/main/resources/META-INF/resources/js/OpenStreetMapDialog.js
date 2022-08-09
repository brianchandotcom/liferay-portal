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
 * OpenStreetMapDialog
 * @review
 */
export default class OpenStreetMapDialog {

	/**
	 * Creates a new map dialog using OpenStreetMap's API
	 * @review
	 */
	constructor(args) {
		this._dialog = L.popup({
			className: 'leaflet-popup',
			minWidth: 400,
		});

		this.map = args.map;
	}

	/**
	 * Opens the dialog with the given map attribute and passes
	 * the given configuration to the dialog object.
	 * @param {Object} cfg
	 * @review
	 */
	open(cfg) {
		this._dialog.setContent(cfg.content);
		this._dialog.setLatLng(cfg.position);

		this._dialog.options.offset = cfg.marker.options.icon.options
			.popupAnchor || [0, 0];

		this._dialog.openOn(this.map);
	}
}

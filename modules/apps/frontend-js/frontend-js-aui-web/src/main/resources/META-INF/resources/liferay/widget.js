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

window.Liferay = window.Liferay || {};

/**
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
Liferay.Widget = function (options) {
	options = options || {};

	var height = options.height || '100%';
	var width = options.width || '100%';

	var id =
		options.id ||
		'_Liferay_widget' + Math.ceil(Math.random() * new Date().getTime());
	var url =
		options.url ||
		'http://www.liferay.com/widget/web/guest/community/forums/-/message_boards';

	var html =
		'<iframe frameborder="0" height="' +
		height +
		'" id="' +
		id +
		'" src="' +
		url +
		'" width="' +
		width +
		'"></iframe>';

	document.write(html);
};

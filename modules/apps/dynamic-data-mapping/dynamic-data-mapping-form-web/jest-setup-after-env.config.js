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

Liferay.DDM = {
	FormSettings: {
		restrictedFormURL: 'http://localhost:8080/group/forms/shared/-/form/',
		sharedFormURL: 'http://localhost:8080/web/forms/shared/-/form/',
		spritemap: '/clay/icons.svg',
	},
};

window.themeDisplay = {
	getLanguageId: () => 'en_US',
};

window.Liferay = {
	...(window.Liferay || {}),
	ThemeDisplay: window.themeDisplay,
};

const REGEX_SUB = /\x$/g;

window.Liferay.Util.sub = function (string, data) {
	if (
		arguments.length > 2 ||
		(typeof data !== 'object' && typeof data !== 'function')
	) {
		data = Array.prototype.slice.call(arguments, 1);
	}

	return string.replace(REGEX_SUB, data);
};

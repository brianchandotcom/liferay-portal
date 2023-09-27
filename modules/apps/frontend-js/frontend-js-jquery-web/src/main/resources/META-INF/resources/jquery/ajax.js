/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function ($) {
	$.ajaxSetup({
		data: {},
		type: 'POST',
	});

	$.ajaxPrefilter((options) => {
		if (options.crossDomain) {
			options.contents.script = false;
		}

		if (options.url) {
			options.url = Liferay.Util.getURLWithSessionId(options.url);
		}
	});
})(window.$);

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

(function() {
	var pluginName = 'lfrpopup';

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			AUI().use('querystring-parse', A => {
				editor.popup = function(url, width, height, options) {
					var params = A.QueryString.parse(url.split('?')[1]);

					if (params.p_p_id) {
						url = url.replace(
							'CKEditorFuncNum=',
							'_' + params.p_p_id + '_CKEditorFuncNum='
						);
					}

					options = A.QueryString.parse(options);

					Liferay.Util.openWindow({
						dialog: {
							zIndex: CKEDITOR.getNextZIndex()
						},
						height,
						stack: false,
						title: options.title || '',
						uri: url,
						width
					});
				};
			});
		}
	});
})();

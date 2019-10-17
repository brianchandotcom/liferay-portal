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
	var pluginName = 'a11yhelpbtn';

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			if (editor.ui.addButton) {
				editor.ui.addButton('A11YBtn', {
					command: 'a11yHelp',
					label: Liferay.Language.get('action.HELP')
				});
			}

			editor.on('uiSpace', event => {
				var toolbarHTML = event.data.html;

				var a11ybtnIndex = toolbarHTML.indexOf('cke_button__a11ybtn');

				if (a11ybtnIndex !== -1) {
					var a11ToolbarIndex = toolbarHTML.lastIndexOf(
						'class="cke_toolbar"',
						a11ybtnIndex
					);

					var toolbarText = toolbarHTML
						.substr(a11ToolbarIndex)
						.replace(
							'class="cke_toolbar cke_toolbar_last"',
							'class="cke_toolbar cke_toolbar_last cke_toolbar__a11yhelpbtn"'
						);

					event.data.html =
						toolbarHTML.substr(0, a11ToolbarIndex) + toolbarText;
				}
			});
		}
	});
})();

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

(function () {
	const pluginName = 'a11yhelpbtn';

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			const helpText = CKEDITOR.env.mac ? ' Option+0' : ' Alt+0';

			if (editor.ui.addButton) {
				editor.ui.addButton('A11YBtn', {
					command: 'a11yHelp',
					label: Liferay.Language.get('action.HELP') + helpText,
				});
			}

			editor.on('uiSpace', (event) => {
				const toolbarHTML = event.data.html;

				const a11ybtnIndex = toolbarHTML.indexOf('cke_button__a11ybtn');

				if (a11ybtnIndex !== -1) {
					const a11ToolbarIndex = toolbarHTML.lastIndexOf(
						'class="cke_toolbar"',
						a11ybtnIndex
					);

					let toolbarText = toolbarHTML
						.substr(a11ToolbarIndex)
						.replace(
							'class="cke_toolbar cke_toolbar_last"',
							'class="cke_toolbar cke_toolbar_last cke_toolbar__a11yhelpbtn"'
						);

					if (CKEDITOR.env.mac) {
						toolbarText = toolbarText
							.replace(/\bAlt\+0\b/g, 'Option+0')
							.replace(
								'class="cke_button_label cke_button__a11ybtn_label"',
								'class="cke_button_label cke_button__a11ybtn_label mac"'
							);
					}

					event.data.html =
						toolbarHTML.substr(0, a11ToolbarIndex) + toolbarText;
				}
			});
		},
	});
})();

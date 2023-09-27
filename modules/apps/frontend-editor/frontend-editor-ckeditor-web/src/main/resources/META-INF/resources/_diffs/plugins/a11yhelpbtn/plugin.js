/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

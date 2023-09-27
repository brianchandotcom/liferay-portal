/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const pluginName = 'ajaxsave';

	CKEDITOR.plugins.add(pluginName, {
		init(editor) {
			editor.addCommand(pluginName, {
				canUndo: false,
				exec(editor) {
					editor.fire('saveContent');
				},
			});

			if (editor.ui.addButton) {
				editor.ui.addButton('AjaxSave', {
					command: pluginName,
					icon:
						Liferay.AUI.getPathCKEditor() +
						'/ckeditor/plugins/ajaxsave/assets/save.png',
					label: editor.lang.save.toolbar,
				});
			}
		},
	});
})();

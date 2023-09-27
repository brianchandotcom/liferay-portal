/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	CKEDITOR.plugins.add('creole', {
		init(editor) {
			const instance = this;

			const path = instance.path;

			const dependencies = [
				CKEDITOR.getUrl(path + 'creole_data_processor.js'),
				CKEDITOR.getUrl(path + 'creole_parser.js'),
			];

			CKEDITOR.scriptLoader.load(dependencies, () => {
				const creoleDataProcessor = CKEDITOR.plugins.get(
					'creole_data_processor'
				);

				creoleDataProcessor.init(editor);
			});
		},
	});
})();

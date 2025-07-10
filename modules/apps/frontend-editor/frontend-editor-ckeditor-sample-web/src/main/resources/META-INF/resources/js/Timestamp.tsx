/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ButtonView, Plugin, icons} from 'ckeditor5';

class Timestamp extends Plugin {
	init() {
		const editor = this.editor;

		editor.ui.componentFactory.add('timestamp', () => {
			const button = new ButtonView();

			button.set({
				icon: icons.history,
				label: 'Timestamp',
			});

			button.on('execute', () => {
				const now = new Date();

				editor.model.change((writer) => {
					editor.model.insertContent(
						writer.createText(`Current time: ${now.toString()} `)
					);
				});
			});

			return button;
		});
	}
}

export default Timestamp;

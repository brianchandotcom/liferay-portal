/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

CKEDITOR.dialog.add('videoembedDialog', (editor) => {
	return {
		contents: [
			{
				elements: [
					{
						id: 'url_video',
						label: Liferay.Language.get('video'),
						type: 'text',
						validate: CKEDITOR.dialog.validate.notEmpty(
							Liferay.Language.get('video-field-cannot-be-empty')
						),
					},
				],
				id: 'info',
			},
		],

		minHeight: 200,
		minWidth: 400,

		onOk() {
			const data = {
				type: 'video',
				url: this.getValueOf('info', 'url_video'),
			};

			editor.plugins.videoembed.onOkVideo(editor, data);
		},

		title: Liferay.Language.get('video-properties'),
	};
});

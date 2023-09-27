/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* eslint-disable @liferay/no-get-data-attribute */

import {addParams} from 'frontend-js-web';

CKEDITOR.dialog.add('video', (editor) => {
	const TPL_SCRIPT =
		'boundingBox: "#" + mediaId,' +
		'height: {height},' +
		'ogvUrl: "{ogvUrl}",' +
		'poster: "{poster}",' +
		'url: "{url}",' +
		'width: {width}';

	function commitValue(videoNode, extraStyles) {
		const instance = this;

		const id = instance.id;
		let value = instance.getValue();

		let scriptTPL = null;
		let textScript = null;

		const videoHeight = videoNode.getAttribute('data-height');
		let videoOgvUrl = videoNode.getAttribute('data-video-ogv-url');
		const videoPoster = videoNode.getAttribute('data-poster');
		let videoUrl = videoNode.getAttribute('data-video-url');
		const videoWidth = videoNode.getAttribute('data-width');

		if (id === 'poster') {
			videoNode.setAttribute('data-document-url', value);

			videoUrl = addParams('videoPreview=1&type=mp4', value);

			videoNode.setAttribute('data-video-url', videoUrl);

			videoOgvUrl = addParams('videoPreview=1&type=ogv', value);

			videoNode.setAttribute('data-video-ogv-url', videoOgvUrl);

			value = addParams('videoThumbnail=1', value);

			videoNode.setAttribute('data-poster', value);

			scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

			textScript = scriptTPL.output({
				height: videoHeight,
				ogvUrl: videoOgvUrl,
				poster: value,
				url: videoUrl,
				width: videoWidth,
			});

			editor.plugins.media.applyMediaScript(
				videoNode,
				'video',
				textScript
			);
		}

		if (value) {
			if (id === 'poster') {
				extraStyles.backgroundImage = 'url(' + value + ')';
			}
			else if (id === 'height' || id === 'width') {
				let height = videoHeight;
				let width = videoWidth;

				if (id === 'height') {
					height = value;
				}
				else {
					width = value;
				}

				extraStyles[id] = value + 'px';

				videoNode.setAttribute('data-' + id, value);

				scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

				textScript = scriptTPL.output({
					height,
					ogvUrl: videoOgvUrl,
					poster: videoPoster,
					url: videoUrl,
					width,
				});

				editor.plugins.media.applyMediaScript(
					videoNode,
					'video',
					textScript
				);
			}
		}
	}

	function loadValue(videoNode) {
		const instance = this;

		const id = instance.id;

		if (videoNode) {
			let value = null;

			if (id === 'poster') {
				value = videoNode.getAttribute('data-document-url');
			}
			else if (id === 'height') {
				value = videoNode.getAttribute('data-height');
			}
			else if (id === 'width') {
				value = videoNode.getAttribute('data-width');
			}

			if (value !== null) {
				instance.setValue(value);
			}
		}
	}

	return {
		contents: [
			{
				elements: [
					{
						children: [
							{
								commit: commitValue,
								id: 'poster',
								label: Liferay.Language.get('video'),
								setup: loadValue,
								type: 'text',
							},
							{
								filebrowser: {
									action: 'Browse',
									target: 'info:poster',
									url:
										editor.config.filebrowserVideoBrowseUrl,
								},
								hidden: 'true',
								id: 'browse',
								label: editor.lang.common.browseServer,
								style: 'display:inline-block;margin-top:10px;',
								type: 'button',
							},
						],
						type: 'hbox',
						widths: ['', '100px'],
					},
					{
						children: [
							{
								commit: commitValue,
								default: 400,
								id: 'width',
								label: editor.lang.common.width,
								setup: loadValue,
								type: 'text',
								validate: CKEDITOR.dialog.validate.notEmpty(
									Liferay.Language.get(
										'width-field-cannot-be-empty'
									)
								),
							},
							{
								commit: commitValue,
								default: 300,
								id: 'height',
								label: editor.lang.common.height,
								setup: loadValue,
								type: 'text',
								validate: CKEDITOR.dialog.validate.notEmpty(
									Liferay.Language.get(
										'height-field-cannot-be-empty'
									)
								),
							},
						],
						type: 'hbox',
						widths: ['50%', '50%'],
					},
				],
				id: 'info',
			},
		],

		minHeight: 200,
		minWidth: 400,

		onOk() {
			const instance = this;

			editor.plugins.media.onOkCallback(instance, editor, 'video');
		},

		onShow() {
			const instance = this;

			editor.plugins.media.onShowCallback(instance, editor, 'video');
		},

		title: Liferay.Language.get('video-properties'),
	};
});

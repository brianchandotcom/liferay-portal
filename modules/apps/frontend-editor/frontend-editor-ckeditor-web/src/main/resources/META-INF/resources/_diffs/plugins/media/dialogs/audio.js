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

CKEDITOR.dialog.add('audio', editor => {
	var TPL_SCRIPT =
		'boundingBox: "#" + mediaId,' + 'oggUrl: "{oggUrl}",' + 'url: "{url}"';

	function commitValue(audioNode) {
		var instance = this;

		var id = instance.id;
		var value = instance.getValue();

		var scriptTPL = null;
		var textScript = null;

		var audioOggUrl = audioNode.getAttribute('data-audio-ogg-url');
		var audioUrl = audioNode.getAttribute('data-audio-url');

		if (id === 'url') {
			audioNode.setAttribute('data-document-url', value);

			audioUrl = Liferay.Util.addParams('audioPreview=1&type=mp3', value);

			audioNode.setAttribute('data-audio-url', audioUrl);

			audioOggUrl = Liferay.Util.addParams(
				'audioPreview=1&type=ogg',
				value
			);

			audioNode.setAttribute('data-audio-ogg-url', audioOggUrl);

			scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

			textScript = scriptTPL.output({
				oggUrl: audioOggUrl,
				url: audioUrl
			});

			editor.plugins.media.applyMediaScript(
				audioNode,
				'audio',
				textScript
			);
		}
	}

	function loadValue(audioNode) {
		var instance = this;

		var id = instance.id;

		if (audioNode) {
			var value = null;

			if (id === 'url') {
				value = audioNode.getAttribute('data-document-url');
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
								id: 'url',
								label: Liferay.Language.get('audio'),
								setup: loadValue,
								type: 'text'
							},
							{
								filebrowser: {
									action: 'Browse',
									target: 'info:url',
									url: editor.config.filebrowserAudioBrowseUrl
								},
								hidden: 'true',
								id: 'browse',
								label: editor.lang.common.browseServer,
								style: 'display:inline-block;margin-top:10px;',
								type: 'button'
							}
						],
						type: 'hbox',
						widths: ['', '100px']
					}
				],
				id: 'info'
			}
		],

		minHeight: 200,
		minWidth: 400,

		onOk() {
			var instance = this;

			editor.plugins.media.onOkCallback(instance, editor, 'audio');
		},

		onShow() {
			var instance = this;

			editor.plugins.media.onShowCallback(instance, editor, 'audio');
		},

		title: Liferay.Language.get('audio-properties')
	};
});

CKEDITOR.dialog.add(
	'video',
	function(editor) {
		var TPL_SCRIPT = 'boundingBox: "#" + mediaId,' +
						'height: {height},' +
						'ogvUrl: "{ogvUrl}",' +
						'poster: "{poster}",' +
						'url: "{url}",' +
						'width: {width}';

		function commitValue(videoNode, extraStyles) {
			var instance = this;

			var id = instance.id;
			var value = instance.getValue();

			var scriptTPL = null;
			var textScript = null;

			var videoHeight = videoNode.getAttribute('data-height');
			var videoOgvUrl = videoNode.getAttribute('data-video-ogv-url');
			var videoPoster = videoNode.getAttribute('data-poster');
			var videoUrl = videoNode.getAttribute('data-video-url');
			var videoWidth = videoNode.getAttribute('data-width');

			if (id === 'poster') {
				videoNode.setAttribute('data-document-url', value);

				videoUrl = Liferay.Util.addParams('videoPreview=1&type=mp4', value);

				videoNode.setAttribute('data-video-url', videoUrl);

				videoOgvUrl = Liferay.Util.addParams('videoPreview=1&type=ogv', value);

				videoNode.setAttribute('data-video-ogv-url', videoOgvUrl);

				value = Liferay.Util.addParams('videoThumbnail=1', value);

				videoNode.setAttribute('data-poster', value);

				scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

				textScript = scriptTPL.output(
					{
						height: videoHeight,
						ogvUrl: videoOgvUrl,
						poster: value,
						url: videoUrl,
						width: videoWidth
					}
				);

				editor.plugins.media.applyMediaScript(videoNode, 'video', textScript);
			}

			if (value) {
				if (id === 'poster') {
					extraStyles.backgroundImage = 'url(' + value + ')';
				}
				else if (id === 'height' || id === 'width') {
					var height = videoHeight;
					var width = videoWidth;

					if (id === 'height') {
						height = value;
					}
					else {
						width = value;
					}

					extraStyles[id] = value + 'px';

					videoNode.setAttribute('data-' + id, value);

					scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

					textScript = scriptTPL.output(
						{
							height: height,
							ogvUrl: videoOgvUrl,
							poster: videoPoster,
							url: videoUrl,
							width: width
						}
					);

					editor.plugins.media.applyMediaScript(videoNode, 'video', textScript);
				}
			}
		}

		function loadValue(videoNode) {
			var instance = this;

			var id = instance.id;

			if (videoNode) {
				var value = null;

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
			minHeight: 200,
			minWidth: 400,

			contents: [
				{
					elements:
					[
						{
							children: [
								{
									commit: commitValue,
									id: 'poster',
									label: Liferay.Language.get('video'),
									setup: loadValue,
									type: 'text'
								},
								{
									filebrowser:
									{
										action: 'Browse',
										target: 'info:poster',
										url: editor.config.filebrowserBrowseUrl + '&Type=Video'
									},
									hidden: 'true',
									id: 'browse',
									label: editor.lang.common.browseServer,
									style: 'display:inline-block;margin-top:10px;',
									type: 'button'
								}
							],
							type: 'hbox',
							widths: [ '', '100px']
						},
						{
							children: [
								{
									commit: commitValue,
									'default': 400,
									id: 'width',
									label: editor.lang.common.width,
									setup: loadValue,
									type: 'text',
									validate: CKEDITOR.dialog.validate.notEmpty(Liferay.Language.get('width-field-cannot-be-empty'))
								},
								{
									commit: commitValue,
									'default': 300,
									id: 'height',
									label: editor.lang.common.height,
									setup: loadValue,
									type: 'text',
									validate: CKEDITOR.dialog.validate.notEmpty(Liferay.Language.get('height-field-cannot-be-empty'))
								}
							],
							type: 'hbox',
							widths: [ '50%', '50%']
						}
					],
					id: 'info'
				}
			],

			title: Liferay.Language.get('video-properties'),

			onShow: function() {
				var instance = this;

				editor.plugins.media.onShowCallback(instance, editor, 'video');
			},

			onOk: function() {
				var instance = this;

				editor.plugins.media.onOkCallback(instance, editor, 'video');
			}
		};
	}
);
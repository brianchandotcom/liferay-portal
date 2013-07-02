CKEDITOR.dialog.add(
	'video',
	function(editor) {
		var TPL_SCRIPT_PREFIX = 'AUI().use(' +
								'	\'aui-base\',\'aui-video\',' +
								'	function(A) {' +
								'		new A.Video(' +
								'			{';

		var TPL_SCRIPT = 		'				ogvUrl: \'{ogvUrl}\',' +
								'				url: \'{url}\',' +
								'				poster: \'{poster}\',' +
								'				boundingBox: \'#{videoBoxId}\',' +
								'				height: {height},' +
								'				width: {width}';

		var TPL_SCRIPT_SUFFIX = '			}' +
								'		).render();' +
								'	}' +
								');';

		var lang = editor.lang.video;

		function commitValue(videoNode, extraStyles) {
			var instance = this;

			var value = instance.getValue();
			var id = instance.id;

			var videoDivNode = videoNode.getChild(0);
			var scriptNode = videoNode.getChild(1);

			var scriptTPL = null;
			var textScript = null;

			var videoHeight = videoNode.getAttribute('data-height');
			var videoId = videoDivNode.getAttribute('id');
			var videoOgvUrl = videoNode.getAttribute('data-video-ogv-url');
			var videoPoster = videoNode.getAttribute('data-poster');
			var videoUrl = videoNode.getAttribute('data-video-url');
			var videoWidth = videoNode.getAttribute('data-width');

			if (!value && id === 'id') {
				value = generateId();
			}

			if (id === 'poster') {
				videoNode.setAttribute('data-document-url', value);

				var urlChar = '?';

				if (value.indexOf(urlChar) >= 0) {
					urlChar = '&';
				}

				videoUrl = value + urlChar + 'videoPreview=1&type=mp4';
				videoNode.setAttribute('data-video-url', videoUrl);

				videoOgvUrl = value + urlChar + 'videoPreview=1&type=ogv';
				videoNode.setAttribute('data-video-ogv-url', videoOgvUrl);

				value = value + urlChar + 'videoThumbnail=1';

				videoNode.setAttribute('data-poster', value);

				scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

				textScript = scriptTPL.output(
					{
						height: videoHeight,
						ogvUrl: videoOgvUrl,
						poster: value,
						url: videoUrl,
						videoBoxId: videoId,
						width: videoWidth
					}
				);

				scriptNode.setText(TPL_SCRIPT_PREFIX + textScript + TPL_SCRIPT_SUFFIX);
			}

			if (value) {
				if (id === 'poster') {
					extraStyles.backgroundImage = 'url(' + value + ')';
				}
				else if (id === 'height') {
					extraStyles.width = value + 'px';

					videoNode.setAttribute('data-height', value);

					if (scriptNode && scriptNode.getText()) {
						scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

						textScript = scriptTPL.output(
							{
								height: value,
								ogvUrl: videoOgvUrl,
								poster: videoPoster,
								url: videoUrl,
								videoBoxId: videoId,
								width: videoWidth
							}
						);

						scriptNode.setText(TPL_SCRIPT_PREFIX + textScript + TPL_SCRIPT_SUFFIX);
					}
				}
				else if (id === 'width') {
					extraStyles.height = value + 'px';

					videoNode.setAttribute('data-width', value);

					if (scriptNode && scriptNode.getText()) {
						scriptTPL = new CKEDITOR.template(TPL_SCRIPT);

						textScript = scriptTPL.output(
							{
								height: videoHeight,
								ogvUrl: videoOgvUrl,
								poster: videoPoster,
								url: videoUrl,
								videoBoxId: videoId,
								width: value
							}
						);

						scriptNode.setText(TPL_SCRIPT_PREFIX + textScript + TPL_SCRIPT_SUFFIX);
					}
				}
			}
		}

		function generateId() {
			return 'video' + new Date().getTime();
		}

		function loadValue(videoNode) {
			var instance = this;
			var id = instance.id;

			if (videoNode) {
				if (id === 'id') {
					instance.setValue(videoNode.getChild(0).getAttribute('id'));
				}
				else if (id === 'poster') {
					instance.setValue(videoNode.getAttribute('data-document-url'));
				}
				else if (id === 'height') {
					instance.setValue(videoNode.getAttribute('data-height'));
				}
				else if (id === 'width') {
					instance.setValue(videoNode.getAttribute('data-width'));
				}
			}
			else {
				if (id === 'id') {
					instance.setValue(generateId());
				}
			}
		}

		return {
				contents: [
					{
						elements:
						[
							{
								children: [
									{
										commit: commitValue,
										id: 'poster',
										label: lang.poster,
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
										validate: CKEDITOR.dialog.validate.notEmpty(lang.widthRequired)
									},
									{
										commit: commitValue,
										'default': 300,
										id: 'height',
										label: editor.lang.common.height,
										setup: loadValue,
										type: 'text',
										validate: CKEDITOR.dialog.validate.notEmpty(lang.heightRequired)
									},
									{
										commit: commitValue,
										id: 'id',
										label: 'Id',
										setup: loadValue,
										type: 'text'
									}
								],
								type: 'hbox',
								widths: [ '33%', '33%', '33%']
							}
						],
						id: 'info'
					}
				],
				onShow: function() {
					var instance = this;

					instance.fakeImage = null;
					instance.videoNode = null;

					var fakeImage = instance.getSelectedElement();

					if (fakeImage && fakeImage.data('cke-real-element-type') && fakeImage.data('cke-real-element-type') === 'video') {
							instance.fakeImage = fakeImage;
							var videoNode = editor.restoreRealElement(fakeImage);
							instance.videoNode = videoNode;
							instance.setupContent( videoNode);
					}
					else {
						instance.setupContent(null);
					}
				},
				onOk: function() {
					var instance = this;

					var STR_DIV = 'div';

					var tmpid = generateId();
					var extraStyles = {};

					var divNode = editor.document.createElement(STR_DIV);
					divNode.setAttribute('class', 'liferayckevideo video-container');

					var boundingBoxTmp = editor.document.createElement(STR_DIV);
					boundingBoxTmp.setAttribute('id', tmpid);

					var scriptTmp = editor.document.createElement('script');
					scriptTmp.setAttribute('type', 'text/javascript');

					divNode.append(boundingBoxTmp);
					divNode.append(scriptTmp);

					instance.commitContent(divNode, extraStyles);

					var newFakeImage = editor.createFakeElement(divNode, 'liferay_cke_video', 'video', false);
					newFakeImage.setStyles(extraStyles);

					if (instance.fakeImage) {
						newFakeImage.replace(instance.fakeImage);
						editor.getSelection().selectElement(newFakeImage);
					}
					else {
						editor.insertElement(newFakeImage);
					}
				},
				minHeight: 200,
				minWidth: 400,
				title: lang.dialogTitle
		};
	}
);

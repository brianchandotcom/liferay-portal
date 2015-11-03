(function() {
	var STR_FILE_ENTRY_RETURN_TYPE = 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType';

	var STR_UPLOADABLE_FILE_RETURN_TYPE = 'com.liferay.item.selector.criteria.UploadableFileReturnType';

	var TPL_AUDIO_SCRIPT = 'boundingBox: "#" + mediaId,' +
		'oggUrl: "{oggUrl}",' +
		'url: "{url}"';

	var TPL_VIDEO_SCRIPT = 'boundingBox: "#" + mediaId,' +
		'height: {height},' +
		'ogvUrl: "{ogvUrl}",' +
		'poster: "{poster}",' +
		'url: "{url}",' +
		'width: {width}';

	var defaultVideoHeight = 300;
	var defaultVideoWidth = 400;

	CKEDITOR.plugins.add(
		'itemselector',
		{
			init: function(editor) {
				var instance = this;

				editor.addCommand(
					'audioselector',
					{
						canUndo: false,
						exec: function(editor, callback) {
							var onSelectedAudioChangeFn = AUI().bind(
								'_onSelectedAudioChange',
								instance,
								editor,
								callback
							);

							instance._getItemSelectorDialog(
								editor,
								editor.config.filebrowserAudioBrowseUrl,
								function(itemSelectorDialog) {
									itemSelectorDialog.once('selectedItemChange', onSelectedAudioChangeFn);
									itemSelectorDialog.open();
								}
							);
						}
					}
				);

				editor.addCommand(
					'imageselector',
					{
						canUndo: false,
						exec: function(editor, callback) {
							var onSelectedImageChangeFn = AUI().bind(
								'_onSelectedImageChange',
								instance,
								editor,
								callback
							);

							instance._getItemSelectorDialog(
								editor,
								editor.config.filebrowserImageBrowseUrl,
								function(itemSelectorDialog) {
									itemSelectorDialog.once('selectedItemChange', onSelectedImageChangeFn);
									itemSelectorDialog.open();
								}
							);
						}
					}
				);

				editor.addCommand(
					'videoselector',
					{
						canUndo: false,
						exec: function(editor, callback) {
							var onSelectedVideoChangeFn = AUI().bind(
								'_onSelectedVideoChange',
								instance,
								editor,
								callback
							);

							instance._getItemSelectorDialog(
								editor,
								editor.config.filebrowserVideoBrowseUrl,
								function(itemSelectorDialog) {
									itemSelectorDialog.once('selectedItemChange', onSelectedVideoChangeFn);
									itemSelectorDialog.open();
								}
							);
						}					}
				);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'ImageSelector',
						{
							command: 'imageselector',
							icon: instance.path + 'assets/image.png',
							label: editor.lang.common.image
						}
					);

					editor.ui.addButton(
						'AudioSelector',
						{
							command: 'audioselector',
							icon: instance.path + 'assets/audio.png',
							label: Liferay.Language.get('audio')
						}
					);

					editor.ui.addButton(
						'VideoSelector',
						{
							command: 'videoselector',
							icon: instance.path + 'assets/video.png',
							label: Liferay.Language.get('video')
						}
					);
				}

				CKEDITOR.on(
					'dialogDefinition',
					function(event) {
						var dialogName = event.data.name;

						if (dialogName === 'image') {
							var dialogDefinition = event.data.definition;

							instance._bindBrowseButton(editor, dialogDefinition, 'info');
							instance._bindBrowseButton(editor, dialogDefinition, 'Link');
						}
					}
				);

				editor.once(
					'destroy',
					function() {
						if (instance._itemSelectorDialog) {
							instance._itemSelectorDialog.destroy();
						}
					}
				);
			},

			_bindBrowseButton: function(editor, dialogDefinition, tabName) {
				var tab = dialogDefinition.getContents(tabName);

				if (tab) {
					var browseButton = tab.get('browse');

					if (browseButton) {
						browseButton.onClick = function() {
							editor.execCommand(
								'imageselector',
								function(newVal) {
									dialogDefinition.dialog.setValueOf(tabName, 'txtUrl', newVal);
								}
							);
						};
					}
				}
			},

			_getItemSelectorDialog: function(editor, url, callback) {
				var instance = this;

				var itemSelectorDialog = instance._itemSelectorDialog;

				if (itemSelectorDialog) {
					itemSelectorDialog.set('url', url);
					itemSelectorDialog.set('zIndex', CKEDITOR.getNextZIndex());

					callback(itemSelectorDialog);
				}
				else {
					AUI().use(
						'liferay-item-selector-dialog',
						function(A) {
							var eventName = editor.name + 'selectItem';

							itemSelectorDialog = new A.LiferayItemSelectorDialog(
								{
									eventName: eventName,
									url: url,
									zIndex: CKEDITOR.getNextZIndex()
								}
							);

							instance._itemSelectorDialog = itemSelectorDialog;

							callback(itemSelectorDialog);
						}
					);
				}
			},

			_getItemSrc: function(editor, selectedItem) {
				var itemSrc = selectedItem.value;

				if (selectedItem.returnType === STR_FILE_ENTRY_RETURN_TYPE ||
					selectedItem.returnType === STR_UPLOADABLE_FILE_RETURN_TYPE) {
					try {
						var itemValue = JSON.parse(selectedItem.value);

						itemSrc = editor.config.attachmentURLPrefix ? editor.config.attachmentURLPrefix + itemValue.title : itemValue.url;
					}
					catch (e) {
					}
				}

				return itemSrc;
			},

			_onOkCallback: function(editor, type, commitValueFn) {
				editor.plugins.media.onOkCallback(
					{
						commitContent: commitValueFn
					},
					editor,
					type
				);
			},

			_onSelectedAudioChange: function(editor, callback, event) {
				var instance = this;

				var selectedItem = event.newVal;

				if (selectedItem) {
					var audioSrc = instance._getItemSrc(editor, selectedItem);

					if (audioSrc) {
						if (callback) {
							callback(audioSrc);
						}
						else {
							var commitValueFn = function(audioNode) {
								var value = audioSrc;

								var scriptNode = audioNode.getChild(1);

								var scriptTPL = null;
								var textScript = null;

								var audioOggUrl = audioNode.getAttribute('data-audio-ogg-url');
								var audioUrl = audioNode.getAttribute('data-audio-url');

								audioNode.setAttribute('data-document-url', value);

								audioUrl = Liferay.Util.addParams('audioPreview=1&type=mp3', value);

								audioNode.setAttribute('data-audio-url', audioUrl);

								audioOggUrl = Liferay.Util.addParams('audioPreview=1&type=ogg', value);

								audioNode.setAttribute('data-audio-ogg-url', audioOggUrl);

								scriptTPL = new CKEDITOR.template(TPL_AUDIO_SCRIPT);

								textScript = scriptTPL.output(
									{
										oggUrl: audioOggUrl,
										url: audioUrl
									}
								);

								editor.plugins.media.applyMediaScript(audioNode, 'audio', textScript);
							};

							instance._onOkCallback(editor, 'audio', commitValueFn);
						}
					}
				}
			},

			_onSelectedImageChange: function(editor, callback, event) {
				var instance = this;

				var selectedItem = event.newVal;

				if (selectedItem) {
					var imageSrc = instance._getItemSrc(editor, selectedItem);

					if (imageSrc) {
						if (callback) {
							callback(imageSrc);
						}
						else {
							var el = CKEDITOR.dom.element.createFromHtml('<img src="' + imageSrc + '">');

							editor.insertElement(el);

							editor.setData(editor.getData());
						}
					}
				}
			},

			_onSelectedVideoChange: function(editor, callback, event) {
				var instance = this;

				var selectedItem = event.newVal;

				if (selectedItem) {
					var videoSrc = instance._getItemSrc(editor, selectedItem);

					if (videoSrc) {
						if (callback) {
							callback(videoSrc);
						}
						else {
							var commitValueFn = function(videoNode, extraStyles) {
								var value = videoSrc;

								var scriptTPL = null;
								var textScript = null;

								var videoHeight = defaultVideoHeight;
								var videoWidth = defaultVideoWidth;

								var videoOgvUrl = Liferay.Util.addParams('videoPreview=1&type=ogv', value);
								var videoUrl = videoNode.getAttribute('data-video-url');

								videoNode.setAttribute('data-document-url', value);

								videoUrl = Liferay.Util.addParams('videoPreview=1&type=mp4', value);

								videoNode.setAttribute('data-video-url', videoUrl);

								videoOgvUrl = Liferay.Util.addParams('videoPreview=1&type=ogv', value);

								videoNode.setAttribute('data-video-ogv-url', videoOgvUrl);

								videoNode.setAttribute('data-height', videoHeight);
								videoNode.setAttribute('data-width', videoWidth);

								extraStyles.backgroundImage = 'url(' + value + ')';
								extraStyles.height = videoHeight + 'px';
								extraStyles.width = videoWidth + 'px';

								value = Liferay.Util.addParams('videoThumbnail=1', value);

								scriptTPL = new CKEDITOR.template(TPL_VIDEO_SCRIPT);

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
							};

							instance._onOkCallback(editor, 'video', commitValueFn);
						}
					}
				}
			}
		}
	);
})();
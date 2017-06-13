(function() {
	var STR_FILE_ENTRY_RETURN_TYPE = 'com.liferay.item.selector.criteria.FileEntryItemSelectorReturnType';

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

				instance._audioTPL = new CKEDITOR.template(TPL_AUDIO_SCRIPT);
				instance._videoTPL = new CKEDITOR.template(TPL_VIDEO_SCRIPT);

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
					'linkselector',
					{
						canUndo: false,
						exec: function(editor, callback) {
							var onSelectedLinkChangeFn = AUI().bind(
								'_onSelectedLinkChange',
								instance,
								editor,
								callback
							);

							instance._getItemSelectorDialog(
								editor,
								editor.config.filebrowserBrowseUrl,
								function(itemSelectorDialog) {
									itemSelectorDialog.once('selectedItemChange', onSelectedLinkChangeFn);
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
						}
					}
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

						var dialogDefinition = event.data.definition;

						if (dialogName === 'audio') {
							instance._bindBrowseButton(editor, dialogDefinition, 'info', 'audioselector', 'url');
						}
						else if (dialogName === 'image') {
							instance._bindBrowseButton(editor, dialogDefinition, 'info', 'imageselector', 'txtUrl');
							instance._bindBrowseButton(editor, dialogDefinition, 'Link', 'imageselector', 'txtUrl');
						}
						else if (dialogName === 'video') {
							instance._bindBrowseButton(editor, dialogDefinition, 'info', 'videoselector', 'poster');
						}
						else if (dialogName === 'link') {
							instance._bindBrowseButton(editor, dialogDefinition, 'info', 'linkselector', 'url');
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

			_bindBrowseButton: function(editor, dialogDefinition, tabName, commandName, targetField) {
				var tab = dialogDefinition.getContents(tabName);

				if (tab) {
					var browseButton = tab.get('browse');

					if (browseButton) {
						browseButton.onClick = function() {
							editor.execCommand(
								commandName,
								function(newVal) {
									dialogDefinition.dialog.setValueOf(tabName, targetField, newVal);
								}
							);
						};
					}
				}
			},

			_commitAudioValue: function(value, node, extraStyles) {
				var instance = this;

				node.setAttribute('data-document-url', value);

				var audioUrl = Liferay.Util.addParams('audioPreview=1&type=mp3', value);

				node.setAttribute('data-audio-url', audioUrl);

				var audioOggUrl = Liferay.Util.addParams('audioPreview=1&type=ogg', value);

				node.setAttribute('data-audio-ogg-url', audioOggUrl);

				return instance._audioTPL.output(
					{
						oggUrl: audioOggUrl,
						url: audioUrl
					}
				);
			},

			_commitMediaValue: function(value, editor, type) {
				var instance = this;

				var mediaPlugin = editor.plugins.media;

				if (mediaPlugin) {
					var eventName = editor.name + 'selectItem';

					Liferay.Util.getWindow(eventName).onceAfter(
						'destroy',
						function() {
							mediaPlugin.onOkCallback(
								{
									commitContent: instance._getCommitMediaValueFn(value, editor, type)
								},
								editor,
								type
							);
						}
					);
				}
			},

			_commitVideoValue: function(value, node, extraStyles) {
				var instance = this;

				node.setAttribute('data-document-url', value);

				var videoUrl = Liferay.Util.addParams('videoPreview=1&type=mp4', value);

				node.setAttribute('data-video-url', videoUrl);

				var videoOgvUrl = Liferay.Util.addParams('videoPreview=1&type=ogv', value);

				node.setAttribute('data-video-ogv-url', videoOgvUrl);

				var videoHeight = defaultVideoHeight;

				node.setAttribute('data-height', videoHeight);

				var videoWidth = defaultVideoWidth;

				node.setAttribute('data-width', videoWidth);

				var poster = Liferay.Util.addParams('videoThumbnail=1', value);

				node.setAttribute('data-poster', poster);

				extraStyles.backgroundImage = 'url(' + poster + ')';
				extraStyles.height = videoHeight + 'px';
				extraStyles.width = videoWidth + 'px';

				return instance._videoTPL.output(
					{
						height: videoHeight,
						ogvUrl: videoOgvUrl,
						poster: poster,
						url: videoUrl,
						width: videoWidth
					}
				);
			},

			_getCommitMediaValueFn: function(value, editor, type) {
				var instance = this;

				var commitValueFn = function(node, extraStyles) {
					var mediaScript;

					if (type === 'audio') {
						mediaScript = instance._commitAudioValue(value, node, extraStyles);
					}
					else if (type === 'video') {
						mediaScript = instance._commitVideoValue(value, node, extraStyles);
					}

					var mediaPlugin = editor.plugins.media;

					if (mediaPlugin) {
						mediaPlugin.applyMediaScript(node, type, mediaScript);
					}
				};

				return commitValueFn;
			},

			_getItemSelectorDialog: function(editor, url, callback) {
				var instance = this;

				var eventName = editor.name + 'selectItem';

				var itemSelectorDialog = instance._itemSelectorDialog;

				if (itemSelectorDialog) {
					itemSelectorDialog.set('eventName', eventName);
					itemSelectorDialog.set('url', url);
					itemSelectorDialog.set('zIndex', CKEDITOR.getNextZIndex());

					callback(itemSelectorDialog);
				}
				else {
					AUI().use(
						'liferay-item-selector-dialog',
						function(A) {

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

				if (selectedItem.returnType === STR_FILE_ENTRY_RETURN_TYPE) {
					try {
						var itemValue = JSON.parse(selectedItem.value);

						itemSrc = editor.config.attachmentURLPrefix ? editor.config.attachmentURLPrefix + itemValue.title : itemValue.url;
					}
					catch (e) {
					}
				}

				return itemSrc;
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
							instance._commitMediaValue(audioSrc, editor, 'audio');
						}
					}
				}
			},

			_onSelectedImageChange: function(editor, callback, event) {
				var instance = this;

				var selectedItem = event.newVal;

				if (selectedItem) {
					var eventName = editor.name + 'selectItem';

					var imageSrc = instance._getItemSrc(editor, selectedItem);

					Liferay.Util.getWindow(eventName).onceAfter(
						'destroy',
						function() {
							if (imageSrc) {
								if (callback) {
									callback(imageSrc, selectedItem);
								}
								else {
									var el = CKEDITOR.dom.element.createFromHtml('<img src="' + imageSrc + '">');

									editor.insertElement(el);

									editor.focus();
								}
							}
						}
					);
				}
			},

			_onSelectedLinkChange: function(editor, callback, event) {
				var instance = this;

				var selectedItem = event.newVal;

				if (selectedItem) {
					var eventName = editor.name + 'selectItem';

					var linkUrl = selectedItem.value;

					Liferay.Util.getWindow(eventName).onceAfter(
						'destroy',
						function() {
							if (callback) {
								callback(linkUrl);
							}
						}
					);
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
							instance._commitMediaValue(videoSrc, editor, 'video');
						}
					}
				}
			}
		}
	);
})();
(function() {
	var Lang = AUI().Lang;

	var IE9AndLater = AUI.Env.UA.ie >= 9;

	var STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE =
		'com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType';

	var STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE =
		'com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType';

	var TPL_PICTURE_TAG =
		'<picture {fileEntryAttributeName}="{fileEntryId}">{sources}<img src="{defaultSrc}"></picture>';

	var TPL_SOURCE_TAG = '<source srcset="{srcset}" media="{media}">';

	CKEDITOR.plugins.add('adaptivemedia', {
		init: function(editor) {
			var instance = this;

			instance._bindEvent(editor);
		},

		_bindEvent: function(editor) {
			var instance = this;

			editor.on('beforeCommandExec', function(event) {
				if (event.data.name === 'imageselector') {
					event.removeListener();

					event.cancel();

					var onSelectedImageChangeFn = instance._onSelectedImageChange.bind(
						instance,
						editor
					);

					editor.execCommand(
						'imageselector',
						onSelectedImageChangeFn
					);

					instance._bindEvent(editor);
				}
			});
		},

		_getImgElement: function(
			imageSrc,
			selectedItem,
			fileEntryAttributeName
		) {
			var imgEl = CKEDITOR.dom.element.createFromHtml('<img>');

			if (
				selectedItem.returnType ===
				STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE
			) {
				var itemValue = JSON.parse(selectedItem.value);

				imgEl.setAttribute('src', itemValue.url);
				imgEl.setAttribute(
					fileEntryAttributeName,
					itemValue.fileEntryId
				);
			} else {
				imgEl.setAttribute('src', imageSrc);
			}

			return imgEl;
		},

		_getPictureElement: function(selectedItem, fileEntryAttributeName) {
			var pictureEl;

			try {
				var itemValue = JSON.parse(selectedItem.value);

				var sources = '';

				itemValue.sources.forEach(function(source) {
					var propertyNames = Object.getOwnPropertyNames(
						source.attributes
					);

					var mediaText = propertyNames.reduce(function(
						previous,
						current
					) {
						var value =
							'(' +
							current +
							':' +
							source.attributes[current] +
							')';

						return previous ? previous + ' and ' + value : value;
					},
					'');

					sources += Lang.sub(TPL_SOURCE_TAG, {
						media: mediaText,
						srcset: source.src
					});
				});

				var pictureHtml = Lang.sub(TPL_PICTURE_TAG, {
					defaultSrc: itemValue.defaultSource,
					fileEntryAttributeName: fileEntryAttributeName,
					fileEntryId: itemValue.fileEntryId,
					sources: sources
				});

				pictureEl = CKEDITOR.dom.element.createFromHtml(pictureHtml);
			} catch (e) {}

			return pictureEl;
		},

		_isEmptySelection: function(editor) {
			var selection = editor.getSelection();

			var ranges = selection.getRanges();

			return (
				selection.getType() === CKEDITOR.SELECTION_NONE ||
				(ranges.length === 1 && (ranges[0].collapsed || IE9AndLater))
			);
		},

		_onSelectedImageChange: function(editor, imageSrc, selectedItem) {
			var instance = this;

			var el;

			var fileEntryAttributeName =
				editor.config.adaptiveMediaFileEntryAttributeName;

			if (
				selectedItem.returnType === STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE
			) {
				el = instance._getPictureElement(
					selectedItem,
					fileEntryAttributeName
				);
			} else {
				el = instance._getImgElement(
					imageSrc,
					selectedItem,
					fileEntryAttributeName
				);
			}

			var elementOuterHtml = el.getOuterHtml();

			editor.insertHtml(elementOuterHtml);

			if (instance._isEmptySelection(editor)) {
				if (IE9AndLater) {
					var emptySelectionMarkup = 'nbsp;';

					var usingAlloyEditor = typeof AlloyEditor == 'undefined';

					if (!usingAlloyEditor) {
						emptySelectionMarkup =
							elementOuterHtml + emptySelectionMarkup;
					}

					editor.insertHtml(emptySelectionMarkup);

					var selection = editor.getSelection();

					var element = new CKEDITOR.dom.element('br');

					editor.insertElement(element);

					var region = element.getClientRect();

					var eventData = {
						nativeEvent: {},
						selectionData: {
							element: element,
							region: region
						}
					};

					editor.fire('editorInteraction', eventData);
				} else {
					editor.execCommand('enter');
				}
			}
		}
	});
})();

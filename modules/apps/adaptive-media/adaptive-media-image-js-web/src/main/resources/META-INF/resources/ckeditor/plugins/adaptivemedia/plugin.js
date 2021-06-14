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

(function () {
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
		_bindEvent(editor) {
			var instance = this;

			editor.on('beforeCommandExec', (event) => {
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

		_getImgElement(imageSrc, selectedItem, fileEntryAttributeName) {
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
			}
			else {
				imgEl.setAttribute('src', imageSrc);
			}

			return imgEl;
		},

		_getPictureElement(selectedItem, fileEntryAttributeName) {
			var pictureEl;

			try {
				var itemValue = JSON.parse(selectedItem.value);

				var sources = '';

				itemValue.sources.forEach((source) => {
					var propertyNames = Object.getOwnPropertyNames(
						source.attributes
					);

					var mediaText = propertyNames.reduce(
						(previous, current) => {
							var value =
								'(' +
								current +
								':' +
								source.attributes[current] +
								')';

							return previous
								? previous + ' and ' + value
								: value;
						},
						''
					);

					sources += Lang.sub(TPL_SOURCE_TAG, {
						media: mediaText,
						srcset: source.src,
					});
				});

				var pictureHtml = Lang.sub(TPL_PICTURE_TAG, {
					defaultSrc: itemValue.defaultSource,
					fileEntryAttributeName,
					fileEntryId: itemValue.fileEntryId,
					sources,
				});

				pictureEl = CKEDITOR.dom.element.createFromHtml(pictureHtml);
			}
			catch (error) {}

			return pictureEl;
		},

		_onSelectedImageChange(editor, imageSrc, selectedItem) {
			var instance = this;

			var element;

			var fileEntryAttributeName =
				editor.config.adaptiveMediaFileEntryAttributeName;

			if (
				selectedItem.returnType === STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE
			) {
				element = instance._getPictureElement(
					selectedItem,
					fileEntryAttributeName
				);
			}
			else {
				element = instance._getImgElement(
					imageSrc,
					selectedItem,
					fileEntryAttributeName
				);
			}

			if (IE9AndLater) {
				if (!editor.window.$.AlloyEditor) {
					var elementOuterHtml = element.getOuterHtml();
					var emptySelectionMarkup = '&nbsp;';

					editor.insertHtml(elementOuterHtml + emptySelectionMarkup);
				}
				else {
					editor.insertElement(element);
				}
			}
			else {
				editor.insertElement(element);
			}

			element = new CKEDITOR.dom.element('br');
			editor.insertElement(element);
			editor.getSelection();

			editor.fire('editorInteraction', {
				nativeEvent: {},
				selectionData: {
					element,
					region: element.getClientRect(),
				},
			});
		},

		init(editor) {
			var instance = this;

			instance._bindEvent(editor);
		},
	});
})();

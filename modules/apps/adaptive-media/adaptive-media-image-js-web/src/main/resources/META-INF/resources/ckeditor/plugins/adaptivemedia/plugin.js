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

function pictureTagTemplate({
	defaultSrc,
	fileEntryAttributeName,
	fileEntryId,
	sources,
}) {
	return `<picture ${fileEntryAttributeName}="${fileEntryId}">${sources}<img src="${defaultSrc}"></picture>`;
}

function sourceTagTemplate({media, srcset}) {
	return `<source srcset="${srcset}" media="${media}">`;
}

(function () {
	const STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE =
		'com.liferay.adaptive.media.image.item.selector.AMImageFileEntryItemSelectorReturnType';

	const STR_ADAPTIVE_MEDIA_URL_RETURN_TYPE =
		'com.liferay.adaptive.media.image.item.selector.AMImageURLItemSelectorReturnType';

	CKEDITOR.plugins.add('adaptivemedia', {
		_bindEvent(editor) {
			const instance = this;

			editor.on('beforeCommandExec', (event) => {
				if (event.data.name === 'imageselector') {
					event.removeListener();

					event.cancel();

					const onSelectedImageChangeFn = instance._onSelectedImageChange.bind(
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
			const imgEl = CKEDITOR.dom.element.createFromHtml('<img>');

			if (
				selectedItem.returnType ===
				STR_ADAPTIVE_MEDIA_FILE_ENTRY_RETURN_TYPE
			) {
				const itemValue = JSON.parse(selectedItem.value);

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
			let pictureEl;

			try {
				const itemValue = JSON.parse(selectedItem.value);

				let sources = '';

				itemValue.sources.forEach((source) => {
					const propertyNames = Object.getOwnPropertyNames(
						source.attributes
					);

					const mediaText = propertyNames.reduce(
						(previous, current) => {
							const value =
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

					sources += sourceTagTemplate({
						media: mediaText,
						srcset: source.src,
					});
				});

				const pictureHtml = pictureTagTemplate({
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
			const instance = this;

			let element;

			const fileEntryAttributeName =
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

			if (!editor.window.$.AlloyEditor) {
				const elementOuterHtml = element.getOuterHtml();
				const emptySelectionMarkup = '&nbsp;';

				editor.insertHtml(elementOuterHtml + emptySelectionMarkup);
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
			const instance = this;

			instance._bindEvent(editor);
		},
	});
})();

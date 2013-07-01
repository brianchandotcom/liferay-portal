/*
 * Liferay Video plugin for CKEditor.
 * Based on Video plugin for CKEditor Copyright (C) 2011 Alfonso Martínez de
 * Lizarrondo
 *  == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your choice:
 *  - GNU General Public License Version 2 or later (the "GPL")
 * http://www.gnu.org/licenses/gpl.html
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 * http://www.gnu.org/licenses/lgpl.html
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 * http://www.mozilla.org/MPL/MPL-1.1.html
 *  == END LICENSE ==
 *
 */

(function() {

CKEDITOR.plugins.add(
	'liferayvideo',
	{
		afterInit: function(editor) {
			var dataProcessor = editor.dataProcessor;
			var htmlFilter = dataProcessor && dataProcessor.htmlFilter;
			var	dataFilter = dataProcessor && dataProcessor.dataFilter;

			dataFilter.addRules(
				{
					elements: {
						'div': function(realElement) {
							var attributes = realElement.attributes;

							if (attributes['class'] && attributes['class'].indexOf('liferayckevideo') >= 0) {
								var fakeElement = editor.createFakeParserElement(realElement, 'liferay_cke_video', 'liferayvideo', false);

								var fakeStyle = fakeElement.attributes.style || '';

								var height = attributes['data-height'];
								var poster = attributes['data-poster'];
								var width = attributes['data-width'];

								if (typeof width != 'undefined') {
									fakeElement.attributes.style = fakeStyle + 'width:' + CKEDITOR.tools.cssLength(width) + ';';

									fakeStyle = fakeElement.attributes.style;
								}

								if (typeof height != 'undefined') {
									fakeElement.attributes.style = fakeStyle + 'height:' + CKEDITOR.tools.cssLength(height) + ';';

									fakeStyle = fakeElement.attributes.style;
								}

								if (poster) {
									fakeElement.attributes.style = fakeStyle + 'background-image:url(' + poster + ');';

									fakeStyle = fakeElement.attributes.style;
								}

								return fakeElement;
							}
						}
					}
				}
			);
		},

		getPlaceholderCss: function() {
			var instance = this;

			return 'img.cke_video' +
				'{' +
					'background-image: url(' + CKEDITOR.getUrl(instance.path + 'icons/placeholder.png' ) + ');' +
					'background-position: center center;' +
					'background-repeat: no-repeat;' +
					'background-color:gray;'+
					'border: 1px solid #a9a9a9;' +
					'width: 80px;' +
					'height: 80px;' +
				'}';
		},

		init: function(editor) {
			var instance = this;

			var lang = editor.lang.liferayvideo;

			CKEDITOR.dialog.add('liferayvideo', instance.path + 'dialogs/video.js');

			editor.addCommand('LiferayVideo', new CKEDITOR.dialogCommand('liferayvideo'));

			editor.ui.addButton('LiferayVideo',
				{
					command: 'LiferayVideo',
					icon: instance.path + 'icons/icon.png',
					label: lang.toolbar
				}
			);

			if (editor.addMenuItems) {
				editor.addMenuItems(
					{
						liferayvideo: {
							command: 'LiferayVideo',
							group: 'flash',
							label: lang.properties
						}
					}
				);
			}

			editor.on(
					'doubleclick',
					function(event) {
						var element = event.data.element;

						if (element.is('img') && element.data('cke-real-element-type') === 'liferayvideo') {
							event.data.dialog = 'liferayvideo';
						}
					}
			);

			if (editor.contextMenu) {
				editor.contextMenu.addListener(
						function(element, selection) {
							if (element && element.is('img') && !element.isReadOnly() && (element.data('cke-real-element-type') === 'liferayvideo')) {

								return {
									video: CKEDITOR.TRISTATE_OFF
								};
							}
						}
				);
			}

			editor.lang.fakeobjects.liferayvideo = lang.fakeObject;
		},

		lang: ['en', 'es'],

		onLoad: function() {
			var instance = this;

			if (CKEDITOR.addCss) {
				CKEDITOR.addCss(instance.getPlaceholderCss());
			}
		}
	}
);

})();

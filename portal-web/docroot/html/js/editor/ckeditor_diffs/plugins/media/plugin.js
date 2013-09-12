(function() {

CKEDITOR.plugins.add(
	'media',
	{
		afterInit: function(editor) {
			var dataProcessor = editor.dataProcessor;

			var	dataFilter = dataProcessor && dataProcessor.dataFilter;
			var	htmlFilter = dataProcessor && dataProcessor.htmlFilter;

			if (dataFilter) {
				dataFilter.addRules(
					{
						elements: {
							'div': function(realElement) {
								var attributeClass = realElement.attributes['class'];

								var fakeElement;

								if (editor.plugins.media.hasClass(attributeClass, 'liferayckevideo')) {
									var realChild = realElement.children && realElement.children[0];

									if (realChild &&
										editor.plugins.media.hasClass(realChild.attributes['class'], 'ckvideo-no-id') &&
										realChild.children && realChild.children.length) {

										realChild.children[0].value = '';
									}

									fakeElement = editor.createFakeParserElement(realElement, 'liferay_cke_video', 'video', false);

									var fakeStyle = fakeElement.attributes.style || '';
									var attributes = realElement.attributes;

									var height = attributes['data-height'];
									var poster = attributes['data-poster'];
									var width = attributes['data-width'];

									if (poster) {
										fakeStyle += 'background-image:url(' + poster + ');';

										fakeElement.attributes.style = fakeStyle;
									}

									if (typeof height != 'undefined') {
										fakeStyle += 'height:' + CKEDITOR.tools.cssLength(height) + ';';

										fakeElement.attributes.style = fakeStyle;
									}

									if (typeof width != 'undefined') {
										fakeStyle += 'width:' + CKEDITOR.tools.cssLength(width) + ';';

										fakeElement.attributes.style = fakeStyle;
									}
								}

								return fakeElement;
							}
						}
					}
				);
			}
			if (htmlFilter) {
				htmlFilter.addRules(
					{
						elements: {
							'div': function(realElement) {
								var attributeClass = realElement.attributes['class'];

								if (editor.plugins.media.hasClass(attributeClass, 'ckvideo-no-id') &&
									realElement.children && realElement.children.length) {

									realElement.children[0].value = '';
								}

								return realElement;
							}
						}
					}
				);
			}
		},

		getPlaceholderCss: function() {
			var instance = this;

			return 'img.liferay_cke_video {' +
				'background: #CCC url(' + CKEDITOR.getUrl(instance.path + 'icons/placeholder.png') + ') no-repeat 50% 50%;' +
				'border: 1px solid #A9A9A9;' +
				'display: block;' +
				'height: 80px;' +
				'width: 80px;' +
			'}';
		},

		init: function(editor) {
			var instance = this;

			CKEDITOR.dialog.add('video', instance.path + 'dialogs/video.js');

			editor.addCommand('Video', new CKEDITOR.dialogCommand('video'));

			editor.ui.addButton(
				'Video',
				{
					command: 'Video',
					icon: instance.path + 'icons/icon.png',
					label: Liferay.Language.get('video')
				}
			);

			if (editor.addMenuItems) {
				editor.addMenuItems(
					{
						video: {
							command: 'Video',
							group: 'flash',
							label: Liferay.Language.get('edit-video')
						}
					}
				);
			}

			editor.on(
				'doubleclick',
				function(event) {
					var element = event.data.element;

					if (instance.isVideoElement(element)) {
						event.data.dialog = 'video';
					}
				}
			);

			if (editor.contextMenu) {
				editor.contextMenu.addListener(
					function(element, selection) {
						var value = {};

						if (instance.isVideoElement(element) && !element.isReadOnly()) {
							value.video = CKEDITOR.TRISTATE_OFF;
						}

						return value;
					}
				);
			}

			editor.lang.fakeobjects.video = Liferay.Language.get('video');
		},

		isVideoElement: function(el) {
			var instance = this;

			return (el && el.is('img') && el.data('cke-real-element-type') === 'video');
		},

		createDivStructure: function(editor, containerClass, boundingBoxClass) {
			var STR_DIV = 'div';
			
			var divNode = editor.document.createElement(STR_DIV);

			divNode.setAttribute('class', containerClass);

			var boundingBoxTmp = editor.document.createElement(STR_DIV);

			boundingBoxTmp.setAttribute('class', boundingBoxClass);

			var scriptTmp = editor.document.createElement('script');

			scriptTmp.setAttribute('type', 'text/javascript');

			divNode.append(boundingBoxTmp);
			divNode.append(scriptTmp);
			
			return divNode;
		},
		
		hasClass: function(attributeClass, target) {
			return (attributeClass && attributeClass.indexOf(target) != -1);
		},
		
		restoreElement: function(editor, instance, fakeImage, type) {			
			if (fakeImage && fakeImage.data('cke-real-element-type') && fakeImage.data('cke-real-element-type') === type) {
				
				instance.fakeImage = fakeImage;

				var node = editor.restoreRealElement(fakeImage);				

				instance.setupContent(node);
			}
			else {
				instance.setupContent(null);
			}			
		},
		
		onLoad: function() {
			var instance = this;

			if (CKEDITOR.addCss) {
				CKEDITOR.addCss(instance.getPlaceholderCss());
			}
		}
	}
);

})();
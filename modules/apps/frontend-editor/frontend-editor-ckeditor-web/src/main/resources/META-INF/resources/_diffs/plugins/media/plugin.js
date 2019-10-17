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

(function() {
	var STR_DIV = 'div';

	CKEDITOR.plugins.add('media', {
		TPL_SCRIPT_PREFIX_CONFIG: 'var mediaConfig = {',

		TPL_SCRIPT_PREFIX_FUNCTION: 'function(A) {',

		TPL_SCRIPT_PREFIX_LOGIC:
			'var mediaId = A.guid();' +
			'var mediaDivNode = A.one(".ck{dialog}-no-id");' +
			'mediaDivNode.attr("id", mediaId);' +
			'mediaDivNode.removeClass("ck{dialog}-no-id");',

		TPL_SCRIPT_PREFIX_USE: 'AUI().use(' + '"aui-base", "aui-{dialog}",',

		TPL_SCRIPT_SUFFIX_CONFIG: '};',

		TPL_SCRIPT_SUFFIX_END: '}' + ');',

		TPL_SCRIPT_SUFFIX_RENDER: 'new A.{mediaAUI}(mediaConfig).render();',

		afterInit(editor) {
			var dataProcessor = editor.dataProcessor;

			var dataFilter = dataProcessor && dataProcessor.dataFilter;
			var htmlFilter = dataProcessor && dataProcessor.htmlFilter;

			if (dataFilter) {
				dataFilter.addRules({
					elements: {
						div(realElement) {
							var attributeClass =
								realElement.attributes['class'];

							var fakeElement;

							var mediaPlugin = editor.plugins.media;

							var audio = mediaPlugin.hasClass(
								attributeClass,
								'liferayckeaudio'
							);
							var video = mediaPlugin.hasClass(
								attributeClass,
								'liferayckevideo'
							);

							if (video || audio) {
								var realChild =
									realElement.children &&
									realElement.children[0];

								if (
									realChild &&
									(mediaPlugin.hasClass(
										realChild.attributes['class'],
										'ckvideo-no-id'
									) ||
										mediaPlugin.hasClass(
											realChild.attributes['class'],
											'ckaudio-no-id'
										)) &&
									realChild.children &&
									realChild.children.length
								) {
									realChild.children[0].value = '';
								}

								var cssClass = 'liferay_cke_audio';
								var element = 'audio';

								if (video) {
									cssClass = 'liferay_cke_video';
									element = 'video';
								}

								fakeElement = editor.createFakeParserElement(
									realElement,
									cssClass,
									element,
									false
								);

								if (video) {
									var attributes = realElement.attributes;
									var fakeStyle =
										fakeElement.attributes.style || '';

									var height = attributes['data-height'];
									var poster = attributes['data-poster'];
									var width = attributes['data-width'];

									if (poster) {
										fakeStyle +=
											'background-image:url(' +
											poster +
											');';

										fakeElement.attributes.style = fakeStyle;
									}

									if (typeof height != 'undefined') {
										fakeStyle +=
											'height:' +
											CKEDITOR.tools.cssLength(height) +
											';';

										fakeElement.attributes.style = fakeStyle;
									}

									if (typeof width != 'undefined') {
										fakeStyle +=
											'width:' +
											CKEDITOR.tools.cssLength(width) +
											';';

										fakeElement.attributes.style = fakeStyle;
									}
								}
							}

							return fakeElement;
						}
					}
				});
			}
			if (htmlFilter) {
				htmlFilter.addRules({
					elements: {
						div(realElement) {
							var attributeClass =
								realElement.attributes['class'];

							var mediaPlugin = editor.plugins.media;

							if (
								(mediaPlugin.hasClass(
									attributeClass,
									'ckvideo-no-id'
								) ||
									mediaPlugin.hasClass(
										attributeClass,
										'ckaudio-no-id'
									)) &&
								realElement.children &&
								realElement.children.length
							) {
								realElement.children[0].value = '';
							}

							return realElement;
						}
					}
				});
			}
		},

		applyMediaScript(mediaNode, dialog, configText) {
			var instance = this;

			var dialogReplace = {
				dialog
			};

			var mediaAUI = 'Audio';

			if (dialog === 'video') {
				mediaAUI = 'Video';
			}

			var scriptUse = new CKEDITOR.template(
				instance.TPL_SCRIPT_PREFIX_USE
			);

			var textScriptUse = scriptUse.output(dialogReplace);

			var scriptLogic = new CKEDITOR.template(
				instance.TPL_SCRIPT_PREFIX_LOGIC
			);

			var textScriptLogic = scriptLogic.output(dialogReplace);

			var scriptRender = new CKEDITOR.template(
				instance.TPL_SCRIPT_SUFFIX_RENDER
			);

			var textScriptRender = scriptRender.output({
				mediaAUI
			});

			instance.replaceScriptContent(
				mediaNode,
				textScriptUse +
					instance.TPL_SCRIPT_PREFIX_FUNCTION +
					textScriptLogic +
					instance.TPL_SCRIPT_PREFIX_CONFIG +
					configText +
					instance.TPL_SCRIPT_SUFFIX_CONFIG +
					textScriptRender +
					instance.TPL_SCRIPT_SUFFIX_END
			);
		},

		createDivStructure(editor, containerClass, boundingBoxClass) {
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

		getPlaceholderCss() {
			var instance = this;

			return (
				'img.liferay_cke_audio {' +
				'background: #CCC url(' +
				CKEDITOR.getUrl(instance.path + 'icons/placeholder_audio.png') +
				') no-repeat 50% 50%;' +
				'border: 1px solid #A9A9A9;' +
				'display: block;' +
				'height: 30px;' +
				'width: 100%;' +
				'}' +
				'img.liferay_cke_video {' +
				'background: #CCC url(' +
				CKEDITOR.getUrl(instance.path + 'icons/placeholder_video.png') +
				') no-repeat 50% 50%;' +
				'border: 1px solid #A9A9A9;' +
				'display: block;' +
				'height: 80px;' +
				'width: 80px;' +
				'}'
			);
		},

		hasClass(attributeClass, target) {
			return attributeClass && attributeClass.indexOf(target) != -1;
		},

		init(editor) {
			var instance = this;

			CKEDITOR.dialog.add('audio', instance.path + 'dialogs/audio.js');
			CKEDITOR.dialog.add('video', instance.path + 'dialogs/video.js');

			editor.addCommand('Audio', new CKEDITOR.dialogCommand('audio'));
			editor.addCommand('Video', new CKEDITOR.dialogCommand('video'));

			editor.ui.addButton('Audio', {
				command: 'Audio',
				icon: instance.path + 'icons/icon_audio.png',
				label: Liferay.Language.get('audio')
			});

			editor.ui.addButton('Video', {
				command: 'Video',
				icon: instance.path + 'icons/icon_video.png',
				label: Liferay.Language.get('video')
			});

			if (editor.addMenuItems) {
				editor.addMenuItems({
					audio: {
						command: 'Audio',
						group: 'flash',
						label: Liferay.Language.get('edit-audio')
					},
					video: {
						command: 'Video',
						group: 'flash',
						label: Liferay.Language.get('edit-video')
					}
				});
			}

			editor.on('doubleclick', event => {
				var element = event.data.element;

				var type;

				if (instance.isElementType(element, 'audio')) {
					type = 'audio';
				} else if (instance.isElementType(element, 'video')) {
					type = 'video';
				}

				if (type) {
					event.data.dialog = type;
				}
			});

			if (editor.contextMenu) {
				editor.contextMenu.addListener(element => {
					var value = {};

					if (!element.isReadOnly()) {
						var type;

						if (instance.isElementType(element, 'audio')) {
							type = 'audio';
						} else if (instance.isElementType(element, 'video')) {
							type = 'video';
						}

						if (type) {
							value[type] = CKEDITOR.TRISTATE_OFF;
						}
					}

					return value;
				});
			}

			editor.lang.fakeobjects.audio = Liferay.Language.get('audio');
			editor.lang.fakeobjects.video = Liferay.Language.get('video');
		},

		isElementType(el, type) {
			return (
				el && el.is('img') && el.data('cke-real-element-type') === type
			);
		},

		onLoad() {
			var instance = this;

			if (CKEDITOR.addCss) {
				CKEDITOR.addCss(instance.getPlaceholderCss());
			}
		},

		onOkCallback(callerInstance, editor, dialog) {
			var instance = this;

			var extraStyles = {};

			var video = dialog === 'video';

			var containerCss = 'liferayckeaudio audio-container';
			var nonProcessedClass = 'ckaudio-no-id';

			if (video) {
				containerCss = 'liferayckevideo video-container';
				nonProcessedClass = 'ckvideo-no-id';
			}

			var divNode = instance.createDivStructure(
				editor,
				containerCss,
				nonProcessedClass
			);

			if (video) {
				callerInstance.commitContent(divNode, extraStyles);
			} else {
				callerInstance.commitContent(divNode);
			}

			var fakeClass = 'liferay_cke_audio';

			if (video) {
				fakeClass = 'liferay_cke_video';
			}

			var newFakeImage = editor.createFakeElement(
				divNode,
				fakeClass,
				dialog,
				false
			);

			if (video) {
				newFakeImage.setStyles(extraStyles);
			}

			if (callerInstance.fakeImage) {
				newFakeImage.replace(callerInstance.fakeImage);

				editor.getSelection().selectElement(newFakeImage);
			} else {
				editor.insertHtml(newFakeImage.getOuterHtml());
			}
		},

		onShowCallback(instance, editor, dialog) {
			instance.fakeImage = null;

			var fakeImage = instance.getSelectedElement();

			this.restoreElement(editor, instance, fakeImage, dialog);
		},

		replaceScriptContent(divNode, scriptContent) {
			if (divNode.getChildCount() == 2) {
				var scriptTmp = null;

				divNode.getChild(1).remove();

				AUI().use('aui-node', A => {
					var scriptNode = A.Node.create(
						'<script type="text/javascript">' +
							scriptContent +
							'</script>'
					);

					scriptTmp = new CKEDITOR.dom.element(scriptNode.getDOM());

					divNode.append(scriptTmp);
				});
			}
		},

		restoreElement(editor, instance, fakeImage, type) {
			var content = null;

			if (
				fakeImage &&
				fakeImage.data('cke-real-element-type') &&
				fakeImage.data('cke-real-element-type') === type
			) {
				instance.fakeImage = fakeImage;

				content = editor.restoreRealElement(fakeImage);
			}

			instance.setupContent(content);
		}
	});
})();

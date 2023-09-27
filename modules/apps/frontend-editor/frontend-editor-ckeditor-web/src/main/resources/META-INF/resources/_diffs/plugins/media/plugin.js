/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function () {
	const STR_DIV = 'div';

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
			const dataProcessor = editor.dataProcessor;

			const dataFilter = dataProcessor && dataProcessor.dataFilter;
			const htmlFilter = dataProcessor && dataProcessor.htmlFilter;

			if (dataFilter) {
				dataFilter.addRules({
					elements: {
						div(realElement) {
							const attributeClass =
								realElement.attributes['class'];

							let fakeElement;

							const mediaPlugin = editor.plugins.media;

							const audio = mediaPlugin.hasClass(
								attributeClass,
								'liferayckeaudio'
							);
							const video = mediaPlugin.hasClass(
								attributeClass,
								'liferayckevideo'
							);

							if (video || audio) {
								const realChild =
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

								let cssClass = 'liferay_cke_audio';
								let element = 'audio';

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
									const attributes = realElement.attributes;
									let fakeStyle =
										fakeElement.attributes.style || '';

									const height = attributes['data-height'];
									const poster = attributes['data-poster'];
									const width = attributes['data-width'];

									if (poster) {
										fakeStyle +=
											'background-image:url(' +
											poster +
											');';

										fakeElement.attributes.style = fakeStyle;
									}

									if (typeof height !== 'undefined') {
										fakeStyle +=
											'height:' +
											CKEDITOR.tools.cssLength(height) +
											';';

										fakeElement.attributes.style = fakeStyle;
									}

									if (typeof width !== 'undefined') {
										fakeStyle +=
											'width:' +
											CKEDITOR.tools.cssLength(width) +
											';';

										fakeElement.attributes.style = fakeStyle;
									}
								}
							}

							return fakeElement;
						},
					},
				});
			}
			if (htmlFilter) {
				htmlFilter.addRules({
					elements: {
						div(realElement) {
							const attributeClass =
								realElement.attributes['class'];

							const mediaPlugin = editor.plugins.media;

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
						},
					},
				});
			}
		},

		applyMediaScript(mediaNode, dialog, configText) {
			const instance = this;

			const dialogReplace = {
				dialog,
			};

			let mediaAUI = 'Audio';

			if (dialog === 'video') {
				mediaAUI = 'Video';
			}

			const scriptUse = new CKEDITOR.template(
				instance.TPL_SCRIPT_PREFIX_USE
			);

			const textScriptUse = scriptUse.output(dialogReplace);

			const scriptLogic = new CKEDITOR.template(
				instance.TPL_SCRIPT_PREFIX_LOGIC
			);

			const textScriptLogic = scriptLogic.output(dialogReplace);

			const scriptRender = new CKEDITOR.template(
				instance.TPL_SCRIPT_SUFFIX_RENDER
			);

			const textScriptRender = scriptRender.output({
				mediaAUI,
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
			const divNode = editor.document.createElement(STR_DIV);

			divNode.setAttribute('class', containerClass);

			const boundingBoxTmp = editor.document.createElement(STR_DIV);

			boundingBoxTmp.setAttribute('class', boundingBoxClass);

			const scriptTmp = editor.document.createElement('script');

			scriptTmp.setAttribute('type', 'text/javascript');

			divNode.append(boundingBoxTmp);
			divNode.append(scriptTmp);

			return divNode;
		},

		getPlaceholderCss() {
			const instance = this;

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
			return attributeClass && attributeClass.indexOf(target) !== -1;
		},

		init(editor) {
			const instance = this;

			CKEDITOR.dialog.add('audio', instance.path + 'dialogs/audio.js');
			CKEDITOR.dialog.add('video', instance.path + 'dialogs/video.js');

			editor.addCommand('Audio', new CKEDITOR.dialogCommand('audio'));
			editor.addCommand('Video', new CKEDITOR.dialogCommand('video'));

			editor.ui.addButton('Audio', {
				command: 'Audio',
				icon: instance.path + 'icons/icon_audio.png',
				label: Liferay.Language.get('audio'),
			});

			editor.ui.addButton('Video', {
				command: 'Video',
				icon: instance.path + 'icons/icon_video.png',
				label: Liferay.Language.get('video'),
			});

			if (editor.addMenuItems) {
				editor.addMenuItems({
					audio: {
						command: 'Audio',
						group: 'flash',
						label: Liferay.Language.get('edit-audio'),
					},
					video: {
						command: 'Video',
						group: 'flash',
						label: Liferay.Language.get('edit-video'),
					},
				});
			}

			editor.on('doubleclick', (event) => {
				const element = event.data.element;

				let type;

				if (instance.isElementType(element, 'audio')) {
					type = 'audio';
				}
				else if (instance.isElementType(element, 'video')) {
					type = 'video';
				}

				if (type) {
					event.data.dialog = type;
				}
			});

			if (editor.contextMenu) {
				editor.contextMenu.addListener((element) => {
					const value = {};

					if (!element.isReadOnly()) {
						let type;

						if (instance.isElementType(element, 'audio')) {
							type = 'audio';
						}
						else if (instance.isElementType(element, 'video')) {
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

		isElementType(element, type) {
			return (
				element &&
				element.is('img') &&
				element.data('cke-real-element-type') === type
			);
		},

		onLoad() {
			const instance = this;

			if (CKEDITOR.addCss) {
				CKEDITOR.addCss(instance.getPlaceholderCss());
			}
		},

		onOkCallback(callerInstance, editor, dialog) {
			const instance = this;

			const extraStyles = {};

			const video = dialog === 'video';

			let containerCss = 'liferayckeaudio audio-container';
			let nonProcessedClass = 'ckaudio-no-id';

			if (video) {
				containerCss = 'liferayckevideo video-container';
				nonProcessedClass = 'ckvideo-no-id';
			}

			const divNode = instance.createDivStructure(
				editor,
				containerCss,
				nonProcessedClass
			);

			if (video) {
				callerInstance.commitContent(divNode, extraStyles);
			}
			else {
				callerInstance.commitContent(divNode);
			}

			let fakeClass = 'liferay_cke_audio';

			if (video) {
				fakeClass = 'liferay_cke_video';
			}

			const newFakeImage = editor.createFakeElement(
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
			}
			else {
				editor.insertHtml(newFakeImage.getOuterHtml());
			}
		},

		onShowCallback(instance, editor, dialog) {
			instance.fakeImage = null;

			const fakeImage = instance.getSelectedElement();

			this.restoreElement(editor, instance, fakeImage, dialog);
		},

		replaceScriptContent(divNode, scriptContent) {
			if (divNode.getChildCount() === 2) {
				let scriptTmp = null;

				divNode.getChild(1).remove();

				AUI().use('aui-node', (A) => {
					const scriptNode = A.Node.create(
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
			let content = null;

			if (
				fakeImage &&
				fakeImage.data('cke-real-element-type') &&
				fakeImage.data('cke-real-element-type') === type
			) {
				instance.fakeImage = fakeImage;

				content = editor.restoreRealElement(fakeImage);
			}

			instance.setupContent(content);
		},
	});
})();

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

if (!CKEDITOR.plugins.get('videoembed')) {
	const REGEX_HTTP = /^https?/;

	CKEDITOR.DEFAULT_LFR_EMBED_WIDGET_TPL =
		'<div data-embed-url="{url}" class="embed-responsive embed-responsive-16by9">{content}<div class="embed-help-message">{helpMessageIcon}<span> {helpMessage}</span></div></div><br>';

	/**
	 * Enum for supported embed alignments
	 * @type {Object}
	 */

	const EMBED_ALIGNMENT = {
		CENTER: 'center',
		LEFT: 'left',
		RIGHT: 'right',
	};

	/**
	 * Enum values for supported embed alignments
	 * @type {Array}
	 */

	const ALIGN_VALUES = [
		EMBED_ALIGNMENT.CENTER,
		EMBED_ALIGNMENT.LEFT,
		EMBED_ALIGNMENT.RIGHT,
	];

	/**
	 * Necessary styles for the center alignment
	 * @type {Array.<Object>}
	 */

	const CENTERED_EMBED_STYLE = [
		{
			name: 'display',
			value: 'block',
		},
		{
			name: 'margin-left',
			value: 'auto',
		},
		{
			name: 'margin-right',
			value: 'auto',
		},
	];

	/**
	 * Retrieves the alignment value of an embed element.
	 *
	 * @param {CKEDITOR.dom.element} embed The embed element
	 * @return {String} The alignment value
	 */

	const getEmbedAlignment = function (embed) {
		let embedAlignment = embed.getStyle('float');

		if (
			!embedAlignment ||
			embedAlignment === 'inherit' ||
			embedAlignment === 'none'
		) {
			embedAlignment = embed.getAttribute('align');
		}

		if (!embedAlignment) {
			const centeredEmbed = CENTERED_EMBED_STYLE.every((style) => {
				let styleCheck = embed.getStyle(style.name) === style.value;

				if (!styleCheck && style.vendorPrefixes) {
					styleCheck = style.vendorPrefixes.some(
						(vendorPrefix) =>
							embed.getStyle(vendorPrefix + style.name) ===
							style.value
					);
				}

				return styleCheck;
			});

			embedAlignment = centeredEmbed ? EMBED_ALIGNMENT.CENTER : null;
		}

		return embedAlignment;
	};

	/**
	 * Removes the alignment value of an embed
	 *
	 * @param {CKEDITOR.dom.element} embed The embed element
	 * @param {String} embedAlignment The embed alignment value to be removed
	 */

	const removeEmbedAlignment = function (embed, embedAlignment) {
		if (
			embedAlignment === EMBED_ALIGNMENT.LEFT ||
			embedAlignment === EMBED_ALIGNMENT.RIGHT
		) {
			embed.removeStyle('float');

			if (embedAlignment === getEmbedAlignment(embed)) {
				embed.removeAttribute('align');
			}
		}
		else if (embedAlignment === EMBED_ALIGNMENT.CENTER) {
			CENTERED_EMBED_STYLE.forEach((style) => {
				embed.removeStyle(style.name);

				if (style.vendorPrefixes) {
					style.vendorPrefixes.forEach((vendorPrefix) =>
						embed.removeStyle(vendorPrefix + style.name)
					);
				}
			});
		}
	};

	/**
	 * Sets the alignment value of an embed
	 *
	 * @param {CKEDITOR.dom.element} embed The embed element
	 * @param {String} embedAlignment The embed alignment value to be set
	 */

	const setEmbedAlignment = function (embed, embedAlignment) {
		removeEmbedAlignment(embed, getEmbedAlignment(embed));

		if (
			embedAlignment === EMBED_ALIGNMENT.LEFT ||
			embedAlignment === EMBED_ALIGNMENT.RIGHT
		) {
			embed.setStyle('float', embedAlignment);
		}
		else if (embedAlignment === EMBED_ALIGNMENT.CENTER) {
			CENTERED_EMBED_STYLE.forEach((style) => {
				embed.setStyle(style.name, style.value);

				if (style.vendorPrefixes) {
					style.vendorPrefixes.forEach((vendorPrefix) =>
						embed.setStyle(vendorPrefix + style.name, style.value)
					);
				}
			});
		}
	};

	const getSelectedElement = function (editor) {
		const result = {
			alignment: null,
			element: null,
		};

		const selection = editor.getSelection();

		if (selection) {
			const selectedElement = selection.getSelectedElement();

			if (
				selectedElement &&
				selectedElement.getAttribute('data-cke-widget-wrapper')
			) {
				result.alignment = getEmbedAlignment(selectedElement);
				result.element = selectedElement;
			}
		}

		return result;
	};

	const resizeElement = function (el, width, height) {
		const wrapperElement = el.parentElement;

		if (wrapperElement && width > 0 && height > 0) {
			wrapperElement.setAttribute('style', `width:${width}px;`);

			const widgetElement = wrapperElement.querySelector(
				'[data-widget="videoembed"]'
			);

			if (widgetElement) {
				const styles =
					JSON.parse(widgetElement.getAttribute('data-styles')) || {};

				styles.width = `${width}px`;
				styles.height = `${height}px`;

				widgetElement.setAttribute(
					'data-styles',
					JSON.stringify(styles)
				);

				const iframeElement = widgetElement.querySelector('iframe');

				if (iframeElement) {
					iframeElement.setAttribute('width', width);
					iframeElement.setAttribute('height', height);
				}
			}
		}
	};

	const selectWidget = function (editor) {
		requestAnimationFrame(() => {
			const selection = editor.getSelection();

			if (selection) {
				const wrapperElement = selection.root.find(
					'[data-cke-widget-wrapper]'
				);

				if (wrapperElement) {
					const elementList = wrapperElement.$;
					if (elementList.length > 0) {
						const lastElement = new CKEDITOR.dom.element(
							elementList[elementList.length - 1]
						);

						const imageElement = lastElement.findOne('img');
						const widgetElement = lastElement.findOne(
							'[data-widget="videoembed"]'
						);

						if (imageElement && widgetElement) {
							const range = editor.createRange();

							if (!range) {
								return;
							}

							range.setStart(widgetElement, 0);
							range.setEnd(imageElement, 1);

							selection.selectRanges([range]);
							selection.selectElement(lastElement);
						}
					}
				}
			}
		});
	};

	let currentAlignment = null;
	let currentElement = null;
	let resizer = null;

	// CSS is added in a compressed form

	CKEDITOR.addCss(
		'img::selection{color:rgba(0,0,0,0)}img.ckimgrsz{outline:1px dashed #000}#ckimgrsz{position:absolute;width:0;height:0;cursor:default;z-index:10001}#ckimgrsz span{display:none;position:absolute;top:0;left:0;width:0;height:0;background-size:100% 100%;opacity:.65;outline:1px dashed #000}#ckimgrsz i{position:absolute;display:block;width:5px;height:5px;background:#fff;border:1px solid #000}#ckimgrsz i.active,#ckimgrsz i:hover{background:#000}#ckimgrsz i.br,#ckimgrsz i.tl{cursor:nwse-resize}#ckimgrsz i.bm,#ckimgrsz i.tm{cursor:ns-resize}#ckimgrsz i.bl,#ckimgrsz i.tr{cursor:nesw-resize}#ckimgrsz i.lm,#ckimgrsz i.rm{cursor:ew-resize}body.dragging-br,body.dragging-br *,body.dragging-tl,body.dragging-tl *{cursor:nwse-resize!important}body.dragging-bm,body.dragging-bm *,body.dragging-tm,body.dragging-tm *{cursor:ns-resize!important}body.dragging-bl,body.dragging-bl *,body.dragging-tr,body.dragging-tr *{cursor:nesw-resize!important}body.dragging-lm,body.dragging-lm *,body.dragging-rm,body.dragging-rm *{cursor:ew-resize!important}'
	);

	/**
	 * CKEditor plugin which adds the infrastructure to embed urls as media objects
	 *
	 * This plugin adds an `videoembed` button that can be used to easily embed a URL and transform it
	 * to an embedded content.
	 *
	 * @class CKEDITOR.plugins.videoembed
	 */

	CKEDITOR.plugins.add('videoembed', {
		_defaultEmbedWidgetUpcastFn(editor, element, data) {
			let upcastWidget = false;

			if (
				element.name === 'div' &&
				element.attributes['data-embed-url']
			) {
				data.url = element.attributes['data-embed-url'];

				upcastWidget = true;
			}
			else if (
				element.name === 'div' &&
				element.attributes['data-embed-id']
			) {
				const iframe = element.children[0];

				data.url = iframe.attributes.src;

				delete element.attributes.style;

				const embedContent = this._generateEmbedContent(
					editor,
					data.url,
					element.getOuterHtml()
				);

				const widgetFragment = new CKEDITOR.htmlParser.fragment.fromHtml(
					embedContent
				);

				upcastWidget = widgetFragment.children[0];

				upcastWidget.attributes['data-styles'] =
					element.attributes['data-styles'];
				upcastWidget.removeClass('embed-responsive');
				upcastWidget.removeClass('embed-responsive-16by9');

				element.replaceWith(upcastWidget);
			}

			return upcastWidget;
		},

		_generateEmbedContent(editor, url, content) {
			return this._getWidgetTemplate(editor).output({
				content,
				helpMessage: Liferay.Language.get(
					'video-playback-is-disabled-during-edit-mode'
				),
				helpMessageIcon: Liferay.Util.getLexiconIconTpl('info-circle'),
				url,
			});
		},

		_getProviders(editor) {
			const providers = editor.config.embedProviders || [];

			return providers.map((provider) => {
				return {
					id: provider.id,
					tpl: new CKEDITOR.template(
						`<div data-embed-id="{embedId}">${provider.tpl}</div>`
					),
					type: provider.type,
					urlSchemes: provider.urlSchemes.map(
						(scheme) => new RegExp(scheme)
					),
				};
			});
		},

		_getWidgetTemplate(editor) {
			return new CKEDITOR.template(
				editor.config.embedWidgetTpl ||
					CKEDITOR.DEFAULT_LFR_EMBED_WIDGET_TPL
			);
		},

		_showError(editor, errorMsg) {
			Liferay.Util.openToast({
				message: errorMsg,
				type: 'danger',
			});

			setTimeout(() => {
				editor.getSelection().removeAllRanges();

				editor.focus();

				resizer.hide();
			}, 0);
		},

		afterInit(editor) {
			editor.on('resize', () => {
				resizer.hide();
				selectWidget(editor);
			});

			ALIGN_VALUES.forEach((alignValue) => {
				const command = editor.getCommand('justify' + alignValue);

				if (command) {
					command.on('exec', (event) => {
						const selectedElement = editor
							.getSelection()
							.getSelectedElement();

						if (
							selectedElement &&
							selectedElement.getAttribute(
								'data-cke-widget-wrapper'
							)
						) {
							const selectedEmbed = selectedElement.findOne(
								'[data-widget="videoembed"] [data-embed-id]'
							);

							if (selectedEmbed) {
								const embedAlignment = getEmbedAlignment(
									selectedElement
								);

								if (embedAlignment === alignValue) {
									removeEmbedAlignment(
										selectedElement,
										alignValue
									);
								}
								else {
									setEmbedAlignment(
										selectedElement,
										alignValue
									);
								}

								currentElement = selectedElement;
								currentAlignment = getEmbedAlignment(
									selectedElement
								);

								const imageElement = selectedElement.findOne(
									'img'
								);

								if (imageElement) {
									resizer.show(imageElement.$);
								}

								event.cancel();

								const elementPath = new CKEDITOR.dom.elementPath(
									selectedElement
								);

								ALIGN_VALUES.forEach((alignValue) => {
									const command = editor.getCommand(
										'justify' + alignValue
									);

									if (command) {
										command.refresh(editor, elementPath);
									}
								});
							}
						}
					});

					command.on('refresh', (event) => {
						const lastElement = event.data.path.lastElement;

						if (
							lastElement &&
							lastElement.getAttribute(
								'data-cke-widget-wrapper'
							) &&
							lastElement.findOne('[data-widget] [data-embed-id]')
						) {
							const embedAlignment = getEmbedAlignment(
								lastElement
							);

							event.sender.setState(
								embedAlignment === alignValue
									? CKEDITOR.TRISTATE_ON
									: CKEDITOR.TRISTATE_OFF
							);

							event.cancel();
						}
					});
				}
			});
		},

		getValidProvider(editor, url, type) {
			const validProvider = this._getProviders(editor)
				.filter((provider) => {
					return type ? provider.type === type : true;
				})
				.find((provider) => {
					const scheme = provider.urlSchemes.find((scheme) =>
						scheme.test(url)
					);
					if (scheme) {
						return provider;
					}
				});

			return validProvider;
		},

		init(editor) {
			const instance = this;

			editor.widgets.add('videoembed', {
				data(event) {
					const instance = this;

					const stylesJSON = instance.element.getAttribute(
						'data-styles'
					);

					let styles = stylesJSON ? JSON.parse(stylesJSON) : null;

					if (!styles) {
						const iframe = instance.wrapper.findOne('iframe');

						const bounds = instance.wrapper.$.getBoundingClientRect();
						const width = iframe.getAttribute('width');

						const pwidth =
							width >= bounds.width
								? 100
								: Math.round((width / bounds.width) * 100);

						styles = {
							width: `${pwidth}%`,
						};
					}

					instance.wrapper.setAttribute(
						'style',
						CKEDITOR.tools.writeCssText(styles)
					);

					const doc = instance.wrapper.getDocument();
					doc.appendStyleSheet('/o/frontend-css-web/main.css');

					function mouseDownListener(event) {
						const result = getSelectedElement(editor);

						currentAlignment = result.alignment;
						currentElement = result.element;

						if (resizer.isHandle(event.target)) {
							resizer.initDrag(event);
						}
					}

					doc.$.addEventListener('mousedown', mouseDownListener);

					const body = doc.getBody();
					if (body) {
						body.addClass('cke_editor_content');
					}

					if (editor._selectEmbedWidget === event.data.url) {
						selectWidget(editor);
					}
				},

				downcast(widget) {
					const embedContent = widget.children[0];

					embedContent.attributes.class =
						'embed-responsive embed-responsive-16by9';

					embedContent.attributes['data-styles'] = JSON.stringify(
						CKEDITOR.tools.parseCssText(
							widget.parent.attributes.style
						)
					);

					embedContent.attributes.style =
						widget.parent.attributes.style;

					return embedContent;
				},

				draggable: false,

				mask: true,

				requiredContent: 'div[data-embed-url]',

				upcast(element, data) {
					const embedWidgetUpcastFn =
						editor.config.embedWidgetUpcastFn ||
						instance._defaultEmbedWidgetUpcastFn.bind(instance);

					return embedWidgetUpcastFn(editor, element, data);
				},
			});

			editor.addCommand(
				'videoembed',
				new CKEDITOR.dialogCommand('videoembedDialog')
			);

			if (editor.ui.addButton) {
				editor.ui.addButton('VideoEmbed', {
					command: 'videoembed',
					icon: instance.path + 'icons/video.png',
					label: Liferay.Language.get('video'),
				});
			}

			CKEDITOR.dialog.add(
				'videoembedDialog',
				instance.path + 'dialogs/videoembedDialog.js'
			);

			window.addEventListener(
				'resize',
				() => {
					resizer.hide();
					selectWidget(editor);
				},
				false
			);

			editor.on('selectionChange', (_event) => {
				const selection = editor.getSelection();

				if (selection) {
					const element = selection.getSelectedElement();

					if (element) {
						const widgetElement = element.findOne(
							'[data-widget="videoembed"]'
						);

						if (widgetElement) {
							const scrollPosition = new CKEDITOR.dom.window(
								window
							).getScrollPosition();

							const region = element.getClientRect();

							region.direction = CKEDITOR.SELECTION_BOTTOM_TO_TOP;
							region.left -= scrollPosition.x;
							region.top += scrollPosition.y;

							editor.fire('editorInteraction', {
								nativeEvent: {},
								selectionData: {
									element: widgetElement,
									region,
								},
							});
						}

						const imageElement = element.findOne(
							'img.cke_widget_mask'
						);

						if (imageElement) {
							resizer.show(imageElement.$);
						}
					}
					else {
						resizer.hide();
					}
				}
			});

			editor.on('destroy', () => {
				const resizeElement = document.getElementById('ckimgrsz');

				if (resizeElement) {
					resizeElement.remove();
				}
			});

			editor.on('blur', () => {
				resizer.hide();
			});

			editor.filter.addElementCallback((element) => {
				if ('data-embed-url' in element.attributes) {
					return CKEDITOR.FILTER_SKIP_TREE;
				}
			});

			var path = instance.path;

			var dependencies = [
				CKEDITOR.getUrl(path + 'DragEvent.es.js'),
				CKEDITOR.getUrl(path + 'Resizer.es.js'),
			];

			CKEDITOR.scriptLoader.load(dependencies, () => {
				resizer = new Liferay.ResizerCKEditor(editor, {
					onComplete(element, width, height) {
						resizeElement(element, width, height);

						if (currentAlignment && currentElement) {
							setEmbedAlignment(currentElement, currentAlignment);
						}
						selectWidget(editor);
					},
				});
			});
		},

		onOkVideo(editor, data) {
			const type = data.type;
			const url = data.url;
			let content;

			if (REGEX_HTTP.test(url)) {
				const provider = this.getValidProvider(editor, url, type);

				if (provider) {
					const schemeProvider = provider.urlSchemes.find((scheme) =>
						scheme.test(url)
					);

					if (schemeProvider) {
						const embedId = schemeProvider.exec(url)[1];

						content = provider.tpl.output({
							embedId,
						});
					}

					editor._selectEmbedWidget = url;

					const embedContent = this._generateEmbedContent(
						editor,
						url,
						content
					);

					editor.insertHtml(embedContent);
				}
				else {
					this._showError(
						editor,
						Liferay.Language.get(
							'sorry,-this-platform-is-not-supported'
						)
					);
				}
			}
			else {
				this._showError(
					editor,
					Liferay.Language.get('enter-a-valid-url')
				);
			}
		},

		onOkVideoHtml(editor, html, url) {
			const embedContent = this._generateEmbedContent(
				editor,
				url,
				`<div data-embed-id="${url}">${html}</div>`
			);

			editor.insertHtml(embedContent);
		},

		requires: 'widget',
	});
}

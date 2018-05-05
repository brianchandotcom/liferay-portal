if (!CKEDITOR.plugins.get('embedurl')) {
	const REGEX_HTTP = /^https?/;

	CKEDITOR.DEFAULT_LFR_EMBED_WIDGET_TPL = '<div data-embed-url="{url}">{content}<div class="embed-help-message">{helpMessageIcon}<span> {helpMessage}</span></div></div><br>';

	/**
	 * Enum for supported embed alignments
	 * @type {Object}
	 */

	const EMBED_ALIGNMENT = {
		CENTER: 'center',
		LEFT: 'left',
		RIGHT: 'right'
	};

	/**
	 * Enum values for supported embed alignments
	 * @type {Array}
	 */

	const ALIGN_VALUES = [
		EMBED_ALIGNMENT.CENTER,
		EMBED_ALIGNMENT.LEFT,
		EMBED_ALIGNMENT.RIGHT
	];

	/**
	 * Necessary styles for the center alignment
	 * @type {Array.<Object>}
	 */

	const CENTERED_EMBED_STYLE = [
		{
			name: 'display',
			value: 'block'
		},
		{
			name: 'margin-left',
			value: 'auto'
		},
		{
			name: 'margin-right',
			value: 'auto'
		}
	];

	/**
	 * Retrieves the alignment value of an embed element.
	 *
	 * @param {CKEDITOR.dom.element} embed The embed element
	 * @return {String} The alignment value
	 */

	const getEmbedAlignment = function(embed) {
		let embedAlignment = embed.getStyle('float');

		if (!embedAlignment || embedAlignment === 'inherit' || embedAlignment === 'none') {
			embedAlignment = embed.getAttribute('align');
		}

		if (!embedAlignment) {
			const centeredEmbed = CENTERED_EMBED_STYLE.every(
				style => {
					let styleCheck = embed.getStyle(style.name) === style.value;

					if (!styleCheck && style.vendorPrefixes) {
						styleCheck = style.vendorPrefixes.some(
							vendorPrefix => embed.getStyle(vendorPrefix + style.name) === style.value
						);
					}

					return styleCheck;
				}
			);

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

	const removeEmbedAlignment = function(embed, embedAlignment) {
		if (embedAlignment === EMBED_ALIGNMENT.LEFT || embedAlignment === EMBED_ALIGNMENT.RIGHT) {
			embed.removeStyle('float');

			if (embedAlignment === getEmbedAlignment(embed)) {
				embed.removeAttribute('align');
			}
		}
		else if (embedAlignment === EMBED_ALIGNMENT.CENTER) {
			CENTERED_EMBED_STYLE.forEach(
				style => {
					embed.removeStyle(style.name);

					if (style.vendorPrefixes) {
						style.vendorPrefixes.forEach(
							vendorPrefix => embed.removeStyle(vendorPrefix + style.name)
						);
					}
				}
			);
		}
	};

	/**
	 * Sets the alignment value of an embed
	 *
	 * @param {CKEDITOR.dom.element} embed The embed element
	 * @param {String} embedAlignment The embed alignment value to be set
	 */

	const setEmbedAlignment = function(embed, embedAlignment) {
		removeEmbedAlignment(
			embed,
			getEmbedAlignment(embed)
		);

		if (embedAlignment === EMBED_ALIGNMENT.LEFT || embedAlignment === EMBED_ALIGNMENT.RIGHT) {
			embed.setStyle('float', embedAlignment);
		}
		else if (embedAlignment === EMBED_ALIGNMENT.CENTER) {
			CENTERED_EMBED_STYLE.forEach(
				style => {
					embed.setStyle(style.name, style.value);

					if (style.vendorPrefixes) {
						style.vendorPrefixes.forEach(
							vendorPrefix => embed.setStyle(vendorPrefix + style.name, style.value)
						);
					}
				}
			);
		}
	};

	/**
	 * CKEditor plugin which adds the infrastructure to embed urls as media objects
	 *
	 * This plugin adds an `embedUrl` command that can be used to easily embed a URL and transform it
	 * to an embedded content.
	 *
	 * @class CKEDITOR.plugins.embedurl
	 */

	CKEDITOR.plugins.add(
		'embedurl',
		{
			requires: 'widget',

			init: function(editor) {
				var LFR_EMBED_WIDGET_TPL = new CKEDITOR.template(editor.config.embedWidgetTpl || CKEDITOR.DEFAULT_LFR_EMBED_WIDGET_TPL);

				var providers = editor.config.embedProviders || [];

				providers = providers.map(
					provider => {
						return {
							id: provider.id,
							tpl: new CKEDITOR.template(`<div data-embed-id="{embedId}">${provider.tpl}</div>`),
							type: provider.type,
							urlSchemes: provider.urlSchemes.map(scheme => new RegExp(scheme))
						};
					}
				);

				var generateEmbedContent = function(url, content) {
					return LFR_EMBED_WIDGET_TPL.output(
						{
							content: content,
							helpMessage: Liferay.Language.get('video-playback-is-disabled-during-edition-mode'),
							helpMessageIcon: Liferay.Util.getLexiconIconTpl('info-circle'),
							url: url
						}
					);
				};

				var defaultEmbedWidgetUpcastFn = function(element, data) {
					var upcastWidget = false;

					if (element.name === 'div' && element.attributes['data-embed-url']) {
						data.url = element.attributes['data-embed-url'];

						upcastWidget = true;
					}
					else if (element.name === 'div' && element.attributes['data-embed-id']) {
						var iframe = element.children[0];

						data.url = iframe.attributes.src;

						var embedContent = generateEmbedContent(data.url, element.getOuterHtml());

						var widgetFragment = new CKEDITOR.htmlParser.fragment.fromHtml(embedContent);

						upcastWidget = widgetFragment.children[0];

						upcastWidget.attributes['data-styles'] = JSON.stringify(element.styles);

						element.replaceWith(upcastWidget);
					}

					return upcastWidget;
				};

				var showError = function(errorMsg) {
					editor.fire('error', errorMsg);

					setTimeout(
						function() {
							editor.getSelection().removeAllRanges();
							editor.focus();
						},
						0
					);
				};

				editor.addCommand(
					'embedUrl',
					{
						exec: function(editor, data) {
							var type = data.type;
							var url = data.url;
							var content;

							if (REGEX_HTTP.test(url)) {
								var validProvider = providers.filter(
									provider => {
										return type ? provider.type === type : true;
									}
								).some(
									provider => {
										var scheme = provider.urlSchemes.find(
											function(scheme) {
												return scheme.test(url);
											}
										);

										if (scheme) {
											var embedId = scheme.exec(url)[1];

											content = provider.tpl.output(
												{
													embedId: embedId
												}
											);
										}

										return scheme;
									}
								);

								if (validProvider) {
									editor._selectEmbedWidget = url;

									var embedContent = generateEmbedContent(url, content);

									editor.insertHtml(embedContent);
								}
								else {
									showError(Liferay.Language.get('sorry,-this-platform-is-not-supported'));
								}
							}
							else {
								showError(Liferay.Language.get('please-enter-a-valid-url'));
							}
						}
					}
				);

				editor.widgets.add(
					'embedurl',
					{
						draggable: false,
						mask: true,
						requiredContent: 'div[data-embed-url]',

						data: function(event) {
							const instance = this;

							// Sync dimensions and alignment with editor wrapper

							let styles = JSON.parse(instance.element.getAttribute('data-styles'));

							if (!styles) {
								const iframe = instance.wrapper.findOne('iframe');

								styles = {
									height: `${iframe.getAttribute('height')}px`,
									width: `${iframe.getAttribute('width')}px`
								};
							}

							instance.wrapper.setAttribute('style', CKEDITOR.tools.writeCssText(styles));

							if (editor._selectEmbedWidget === event.data.url) {
								setTimeout(
									function() {
										editor.getSelection().selectElement(instance.wrapper);

										editor._selectEmbedWidget = null;
									},
									0
								);
							}
						},

						downcast: function(widget) {
							const embedContent = widget.children[0];

							embedContent.attributes.style = CKEDITOR.tools.writeCssText(widget.parent.styles);

							return embedContent;
						},

						upcast: function(element, data) {
							var embedWidgetUpcastFn = editor.config.embedWidgetUpcastFn || defaultEmbedWidgetUpcastFn;

							return embedWidgetUpcastFn(element, data);
						}
					}
				);

				editor.on(
					'selectionChange',
					function(event) {
						var selection = editor.getSelection();

						if (selection) {
							var element = selection.getSelectedElement();

							if (element) {
								var widgetElement = element.findOne('[data-widget="embedurl"]');

								if (widgetElement) {
									var scrollPosition = new CKEDITOR.dom.window(window).getScrollPosition();

									var region = element.getClientRect();

									region.direction = CKEDITOR.SELECTION_BOTTOM_TO_TOP;
									region.left -= scrollPosition.x;
									region.top += scrollPosition.y;

									editor.fire(
										'editorInteraction',
										{
											nativeEvent: {},
											selectionData: {
												element: widgetElement,
												region: region
											}
										}
									);
								}
							}
						}
					}
				);

				editor.filter.addElementCallback(
					function(element) {
						if ('data-embed-url' in element.attributes) {
							return CKEDITOR.FILTER_SKIP_TREE;
						}
					}
				);
			},

			afterInit: function(editor) {
				ALIGN_VALUES.forEach(
					alignValue => {
						const command = editor.getCommand('justify' + alignValue);

						if (command) {
							command.on(
								'exec',
								event => {
									const selectedElement = editor.getSelection().getSelectedElement();

									if (selectedElement && selectedElement.getAttribute('data-cke-widget-wrapper')) {
										const selectedEmbed = selectedElement.findOne('[data-widget="embedurl"] [data-embed-id]');

										if (selectedEmbed) {
											const embedAlignment = getEmbedAlignment(selectedElement);

											if (embedAlignment === alignValue) {
												removeEmbedAlignment(selectedElement, alignValue);
											}
											else {
												setEmbedAlignment(selectedElement, alignValue);
											}

											event.cancel();

											const elementPath = new CKEDITOR.dom.elementPath(selectedElement);

											ALIGN_VALUES.forEach(
												alignValue => {
													const command = editor.getCommand('justify' + alignValue);

													if (command) {
														command.refresh(editor, elementPath);
													}
												}
											);
										}
									}
								}
							);

							command.on(
								'refresh',
								event => {
									const lastElement = event.data.path.lastElement;

									if (lastElement &&
										lastElement.getAttribute('data-cke-widget-wrapper') &&
										lastElement.findOne('[data-widget] [data-embed-id]')) {

										const embedAlignment = getEmbedAlignment(lastElement);

										event.sender.setState(embedAlignment === alignValue ? CKEDITOR.TRISTATE_ON : CKEDITOR.TRISTATE_OFF);

										event.cancel();
									}
								}
							);
						}
					}
				);
			}
		}
	);
}

export default CKEDITOR.plugins.get('embedurl');
if (!CKEDITOR.plugins.get('embedurl')) {
	var REGEX_HTTP = /^https?/;

	CKEDITOR.DEFAULT_LFR_EMBED_WIDGET_TPL = '<div data-embed-url="{url}">{content}<div class="embed-help-message">{helpMessageIcon}<span> {helpMessage}</span></div></div><br>';

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
							tpl: new CKEDITOR.template(`<div class="embed-responsive embed-responsive-16by9" data-embed-id="{embedId}">${provider.tpl}</div>`),
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
							var instance = this;

							if (editor._selectEmbedWidget === event.data.url) {
								setTimeout(
									function() {
										var iframe = instance.wrapper.findOne('iframe');

										if (iframe) {
											iframe.addClass('embed-responsive-item');
										}

										editor.getSelection().selectElement(instance.wrapper);

										editor._selectEmbedWidget = null;
									},
									0
								);
							}
						},

						downcast: function(widget) {
							return widget.children[0];
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
			}
		}
	);
}

export default CKEDITOR.plugins.get('embedurl');
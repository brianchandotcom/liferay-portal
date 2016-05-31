AUI.add(
	'liferay-fullscreen-source-editor',
	function(A) {
		var Lang = A.Lang;

		var CONTENT_TEMPLATE = '<div class="lfr-fullscreen-source-editor-header row">' +
				'<div class="col-xs-6">' +
					'<button class="btn btn-default btn-xs pull-right" id="switchTheme" type="button">' +
						'<span class="icon-monospaced">' +
							'<svg class="lexicon-icon lexicon-icon-moon" role="img">' +
								'<use xlink:href="{pathThemeImages}/lexicon/icons.svg#moon" />' +
							'</svg>' +
						'</span>' +
					'</button>' +
				'</div>' +
				'<div class="col-xs-6 layout-selector">' +
					'<span class="icon-pause" data-layout="vertical"></span>' +
					'<span class="icon-pause icon-rotate-90" data-layout="horizontal"></span>' +
					'<span class="icon-stop" data-layout="simple"></span>' +
				'</div>' +
			'</div>' +
			'<div class="lfr-fullscreen-source-editor-content">' +
				'<div class="source-panel">' +
					'<div class="source-html"></div>' +
				'</div>' +
				'<div class="preview-panel"></div>' +
				'<div class="panel-splitter"></div>' +
			'</div>';

		var CSS_PREVIEW_PANEL = '.preview-panel';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CLICK = 'click';

		var STR_DOT = '.';

		var STR_LAYOUT = 'layout';

		var STR_VALUE = 'value';

		var LiferayFullScreenSourceEditor = A.Component.create(
			{
				ATTRS: {
					aceOptions: {
						validator: Lang.isObject,
						value: {
							fontSize: 13,
							showInvisibles: false,
							showPrintMargin: false
						}
					},

					dataProcessor: {
						validator: Lang.isObject
					},

					layout: {
						validator: Lang.isString,
						value: 'vertical'
					},

					previewCssClass: {
						validator: Lang.isString,
						value: ''
					},

					previewDelay: {
						validator: Lang.isNumber,
						value: 100
					},

					value: {
						getter: '_getValue',
						validator: Lang.isString,
						value: ''
					}
				},

				CSS_PREFIX: 'lfr-fullscreen-source-editor',

				EXTENDS: A.Widget,

				NAME: 'liferayfullscreensourceeditor',

				NS: 'liferayfullscreensourceeditor',

				prototype: {
					CONTENT_TEMPLATE: Lang.sub(
						CONTENT_TEMPLATE,
						{
							pathThemeImages: themeDisplay.getPathThemeImages()
						}
					),

					renderUI: function() {
						var instance = this;

						var boundingBox = instance.get(STR_BOUNDING_BOX);

						boundingBox.one(STR_DOT + instance.getClassName('content')).addClass(instance.get(STR_LAYOUT));

						instance._editorSwitchTheme = boundingBox.one('#switchTheme');

						instance._editor = new A.LiferaySourceEditor(
							{
								aceOptions: instance.get('aceOptions'),
								boundingBox: boundingBox.one('.source-html'),
								height: '100%',
								mode: 'html',
								on: {
									themeSwitched: function(event) {
										instance._editorSwitchTheme.one('.lexicon-icon').replace(event.themes[event.nextThemeIndex].icon);
									}
								},
								value: instance.get(STR_VALUE)
							}
						).render();

						instance._previewPanel = boundingBox.one(CSS_PREVIEW_PANEL);

						instance._previewPanel.html(instance._getHtml(instance.get(STR_VALUE)));
						instance._previewPanel.addClass(instance.get('previewCssClass'));
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get(STR_BOUNDING_BOX);

						var onChangeTask = A.debounce('_onEditorChange', instance.get('previewDelay'), instance);

						instance._eventHandles = [
							instance._editor.on('change', onChangeTask),
							instance.on('layoutChange', instance._onLayoutChange),
							instance.on('valueChange', instance._onValueChange),
							instance._editorSwitchTheme.on('click', instance._switchTheme, instance),
							boundingBox.one(STR_DOT + instance.getClassName('header')).delegate(STR_CLICK, instance._onLayoutClick, '[data-layout]', instance),
							boundingBox.one(CSS_PREVIEW_PANEL).delegate(STR_CLICK, instance._onPreviewLink, 'a', instance)
						];
					},

					destructor: function() {
						var instance = this;

						var sourceEditor = instance._editor;

						if (sourceEditor) {
							sourceEditor.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					resizeEditor: function() {
						var instance = this;

						instance._editor.getEditor().resize();
					},

					_getHtml: function(val) {
						var instance = this;

						var dataProcessor = instance.get('dataProcessor');

						if (dataProcessor && dataProcessor.toHtml) {
							val = dataProcessor.toHtml(val);
						}

						return val;
					},

					_getValue: function(val) {
						var instance = this;

						return instance._editor ? instance._editor.get(STR_VALUE) : val;
					},

					_onEditorChange: function(event) {
						var instance = this;

						instance._previewPanel.html(instance._getHtml(event.newVal));
					},

					_onLayoutChange: function(event) {
						var instance = this;

						instance.get(STR_BOUNDING_BOX).one(STR_DOT + instance.getClassName('content')).replaceClass(event.prevVal, event.newVal);

						instance.resizeEditor();
					},

					_onLayoutClick: function(event) {
						var instance = this;

						instance.set(STR_LAYOUT, event.currentTarget.attr('data-layout'));
					},

					_onPreviewLink: function(event) {
						event.currentTarget.attr('target', '_blank');
					},

					_onValueChange: function(event) {
						var instance = this;

						instance._editor.set(STR_VALUE, event.newVal);
					},

					_switchTheme: function(event) {
						var instance = this;

						instance._editor.switchTheme();
					}
				}
			}
		);

		A.LiferayFullScreenSourceEditor = LiferayFullScreenSourceEditor;
	},
	'',
	{
		requires: ['liferay-source-editor']
	}
);
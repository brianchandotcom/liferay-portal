/* global ace */

AUI.add(
	'liferay-source-editor',
	function(A) {
		var Lang = A.Lang;

		var CSS_ACTIVE_CELL = 'ace_gutter-active-cell';

		var CSS_CONTENT_HTML = 'content-html';

		var CSS_CONTENT_PREVIEW = 'content-preview';

		var CSS_DIALOG = 'fullscreen-dialog';

		var CSS_PREFIX = 'lfr-source-editor';

		var CSS_SOURCE_EDITOR_FULLSCREEN = 'lfr-source-editor-fullscreen';

		var EVENT_FULLSCREEN_CANCEL = 'fullscreen-cancel';

		var EVENT_FULLSCREEN_DONE = 'fullscreen-done';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CHANGE_CURSOR = 'changeCursor';

		var STR_CHANGE_FOLD = 'changeFold';

		var STR_CODE = 'code';

		var STR_DATA_CURRENT_THEME = 'data-currenttheme';

		var STR_DOT = '.';

		var STR_THEMES = 'themes';

		var STR_TOOLBAR = 'toolbar';

		var STR_VALUE = 'value';

		var TPL_CODE_CONTAINER = '<div class="{cssClass}"></div>';

		var TPL_FULL_SCREEN = '<div class="lfr-header-fullscreen">' +
			'<div class="header-left">HTML</div>' +
			'<div class="header-right">' +
				'<span id="vertical" class="icon-pause"></span>' +
				'<span id="horizontal" class="icon-pause icon-rotate-90"></span>' +
				'<span id="simple" class="icon-stop"></span>'+
			'</div>'+
		'</div>' +
		'<div class="' + CSS_SOURCE_EDITOR_FULLSCREEN + ' vertical">' +
			'<div class="' + CSS_CONTENT_HTML +' {cssPrefix}"> <div id="{sourceCodeId}" class="{cssCode}"></div> </div>' +
			'<div class="splitter"></div>' +
			'<div class="alloy-editor alloy-editor-placeholder ' + CSS_CONTENT_PREVIEW + '"> {preview} </div>'+
		'</div>';

		var TPL_THEME_BUTTON = '<li data-action="{action}"><button type="button" class="btn btn-default btn-lg"><i class="{iconCssClass}"></i></button></li>';

		var TPL_TOOLBAR = '<ul class="{cssClass}">{buttons}</ul>';

		var LiferaySourceEditor = A.Component.create(
			{
				ATTRS: {
					aceFullScreenOptions: {
						validator: Lang.isObject,
						valueFn: function() {
							var instance = this;

							var aceEditor = instance.getEditor();

							return {
								fontSize: 13,
								showInvisibles: false,
								showPrintMargin: false
							};
						}
					},

					aceOptions: {
						validator: Lang.isObject,
						valueFn: function() {
							var instance = this;

							var aceEditor = instance.getEditor();

							return {
								fontSize: 13,
								maxLines: Math.floor(A.DOM.winHeight() / aceEditor.renderer.lineHeight) - 15,
								minLines: 10,
								showInvisibles: false,
								showPrintMargin: false
							};
						}
					},

					fullScreenTitle: {
						validator: Lang.isString,
						value: Liferay.Language.get('edit-content')
					},

					height: {
						validator: function(value) {
							return Lang.isString(value) || Lang.isNumber(value);
						},
						value: 'auto'
					},

					previewDelay: {
						validator: Lang.isNumber,
						value: 100
					},

					themes: {
						validator: Lang.isArray,
						value: [
							{
								cssClass: '',
								iconCssClass: 'icon-sun'
							},
							{
								cssClass: 'ace_dark',
								iconCssClass: 'icon-moon'
							}
						]
					},

					width: {
						validator: function(value) {
							return Lang.isString(value) || Lang.isNumber(value);
						},
						value: '100%'
					}
				},

				CSS_PREFIX: CSS_PREFIX,

				EXTENDS: A.AceEditor,

				NAME: 'liferaysourceeditor',

				NS: 'liferaysourceeditor',

				prototype: {
					initializer: function() {
						var instance = this;

						var aceEditor = instance.getEditor();

						aceEditor.setOptions(instance.get('aceOptions'));

						instance._currentEditor = instance.getEditor();

						instance._initializeThemes();
						instance._highlightActiveGutterLine(0);
					},

					bindUI: function() {
						var instance = this;

						var aceEditor = instance.getEditor();

						instance._bindAceEditorEvents(aceEditor);

						var toolbar = instance.get(STR_BOUNDING_BOX).one(STR_DOT + instance.getClassName(STR_TOOLBAR));

						instance._bindToolbarEvents(toolbar);
					},

					destructor: function() {
						var instance = this;

						var aceEditor = instance.getEditor();

						aceEditor.selection.removeAllListeners(STR_CHANGE_CURSOR);
						aceEditor.session.removeAllListeners(STR_CHANGE_FOLD);

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					getEditor: function() {
						var instance = this;

						if (!instance.editor) {
							var boundingBox = instance.get(STR_BOUNDING_BOX);

							var codeContainer = boundingBox.one(STR_DOT + instance.getClassName(STR_CODE));

							if (!codeContainer) {
								codeContainer = A.Node.create(
									Lang.sub(
										TPL_CODE_CONTAINER,
										{
											cssClass: instance.getClassName(STR_CODE)
										}
									)
								);

								boundingBox.append(codeContainer);
							}

							var toolbarContainer = boundingBox.one(STR_DOT + instance.getClassName(STR_TOOLBAR));

							if (!toolbarContainer) {
								boundingBox.append(
									Lang.sub(
										TPL_TOOLBAR,
										{
											buttons: instance._getButtonsMarkup(),
											cssClass: instance.getClassName(STR_TOOLBAR)
										}
									)
								);
							}

							instance.editor = ace.edit(codeContainer.getDOM());
						}

						return instance.editor;
					},

					openFullScreen: function() {
						var instance = this;

						var sourceCodeId = A.guid();

						var templateContent = Lang.sub(
							TPL_FULL_SCREEN,
							{
								cssCode: instance.getClassName(STR_CODE),
								cssPrefix: CSS_PREFIX,
								preview: instance.get(STR_VALUE),
								sourceCodeId: sourceCodeId
							}
						);

						var fullScreenDialog;

						Liferay.Util.openWindow(
							{
								dialog: {
									after: {
										destroy: function() {
											instance._currentEditor.destroy();

											instance._currentEditor = instance.getEditor();
										}
									},
									bodyContent: templateContent,
									constrain: true,
									cssClass: CSS_DIALOG,
									destroyOnHide: true,
									modal: true,
									toolbars: {
										footer: [
											{
												cssClass: 'btn-primary',
												label: Liferay.Language.get('done'),
												on: {
													click: function() {
														var currentValue = instance._currentEditor.getValue();

														fullScreenDialog.hide();

														instance.fire(
															EVENT_FULLSCREEN_DONE,
															{
																content: currentValue
															}
														);
													}
												}
											},
											{
												label: Liferay.Language.get('cancel'),
												on: {
													click: function() {
														fullScreenDialog.hide();

														instance.fire(EVENT_FULLSCREEN_CANCEL);
													}
												}
											}
										],
										header: [
											{
												cssClass: 'close',
												label: '\u00D7',
												on: {
													click: function(event) {
														fullScreenDialog.hide();

														event.domEvent.stopPropagation();

														instance.fire(EVENT_FULLSCREEN_CANCEL);
													}
												},
												render: true
											}
										]
									}
								},
								title: instance.get('fullScreenTitle')
							},
							function(dialog) {
								fullScreenDialog = dialog;

								instance._renderFullScreenEditor(sourceCodeId);
							}
						);
					},

					_attachFullScreenEvents: function() {
						var instance = this;

						var dialogContainer = AUI.$(STR_DOT + CSS_DIALOG);

						dialogContainer.find('.header-right span').bind(
							'click',
							function(event) {
								var alignment = event.currentTarget.id;

								dialogContainer
									.find(STR_DOT + CSS_SOURCE_EDITOR_FULLSCREEN)
									.attr('class', CSS_SOURCE_EDITOR_FULLSCREEN + ' ' + alignment);

								instance._currentEditor.resize();
							}
						);

						dialogContainer.find(STR_DOT + CSS_CONTENT_PREVIEW + ' a').on(
							'click',
							function(event) {
								AUI.$(event.currentTarget).attr('target', '_blank');
							}
						);
					},

					_bindAceEditorEvents: function(aceEditor) {
						var instance = this;

						var updateActiveLineFn = A.bind('_updateActiveLine', instance);

						aceEditor.selection.on(STR_CHANGE_CURSOR, updateActiveLineFn);
						aceEditor.session.on(STR_CHANGE_FOLD, updateActiveLineFn);
					},

					_bindToolbarEvents: function(toolbar) {
						var instance = this;

						instance._eventHandles = instance._eventHandles || [];

						instance._eventHandles.push(
							toolbar.delegate('click', A.bind('_onToolbarClick', instance), 'li[data-action]')
						);
					},

					_getButtonsMarkup: function() {
						var instance = this;

						var toolbarButtons = '';

						var themes = instance.get(STR_THEMES);

						if (themes.length > 1) {
							toolbarButtons += Lang.sub(
								TPL_THEME_BUTTON,
								{
									action: STR_THEMES,
									iconCssClass: themes[1].iconCssClass || 'icon-adjust'
								}
							);
						}

						return toolbarButtons;
					},

					_getThemeIcon: function(themeIndex) {
						var instance = this;

						var themes = instance.get(STR_THEMES);

						return themes[(themeIndex + 1) % themes.length].iconCssClass;
					},

					_highlightActiveGutterLine: function(line) {
						var instance = this;

						var editor = instance._currentEditor;

						var session = editor.getSession();

						if (editor._currentLine !== null) {
							session.removeGutterDecoration(editor._currentLine, CSS_ACTIVE_CELL);
						}

						session.addGutterDecoration(line, CSS_ACTIVE_CELL);

						editor._currentLine = line;
					},

					_initializeFullScreenTheme: function() {
						var instance = this;

						var themes = instance.get(STR_THEMES);

						var currentThemeIndex = instance._currentThemeIndex || 0;

						var currentTheme = themes[currentThemeIndex];

						if (currentTheme) {
							var boundingBox = AUI.$(instance._currentEditor.container).parent();

							boundingBox.addClass(currentTheme.cssClass);

							var themeIcon = instance._getThemeIcon(currentThemeIndex);

							boundingBox.find(STR_DOT + instance.getClassName(STR_TOOLBAR) + ' i').attr('class', themeIcon);
						}
					},

					_initializeThemes: function() {
						var instance = this;

						var themes = instance.get(STR_THEMES);

						if (themes.length) {
							instance.get(STR_BOUNDING_BOX).addClass(themes[0].cssClass);
						}
					},

					_onToolbarClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var action = currentTarget.attr('data-action');

						if (action === STR_THEMES) {
							instance._switchTheme(currentTarget);
						}
					},

					_refreshPreviewEntry: function() {
						var instance = this;

						AUI.$(STR_DOT + CSS_CONTENT_PREVIEW).html(instance._currentEditor.getValue());
					},

					_renderFullScreenEditor: function(sourceCodeId) {
						var instance = this;

						var options = A.merge(
							instance.get('aceFullScreenOptions'),
							{
								mode: instance.get('mode').$id
							}
						);

						var fullScreenEditor = ace.edit(A.one('#' + sourceCodeId).getDOM());

						fullScreenEditor.setOptions(options);

						fullScreenEditor.getSession().setValue(instance.get(STR_VALUE));

						var onChangeFn = A.bind(instance._refreshPreviewEntry, instance);

						fullScreenEditor.getSession().on('change', A.debounce(onChangeFn, instance.get('previewDelay')));

						instance._currentEditor = fullScreenEditor; window.fseditor = fullScreenEditor;

						AUI.$(STR_DOT + CSS_CONTENT_HTML).append(
							Lang.sub(
								TPL_TOOLBAR,
								{
									buttons: instance._getButtonsMarkup(),
									cssClass: instance.getClassName(STR_TOOLBAR)
								}
							)
						);

						instance._bindAceEditorEvents(fullScreenEditor);

						var dialogContainer = A.one(STR_DOT + CSS_DIALOG);
						var toolbar = dialogContainer.one(STR_DOT + instance.getClassName(STR_TOOLBAR));

						instance._bindToolbarEvents(toolbar);

						instance._attachFullScreenEvents();

						instance._initializeFullScreenTheme();

						instance._highlightActiveGutterLine(0);
					},

					_switchTheme: function(themeSelector) {
						var instance = this;

						var themes = instance.get(STR_THEMES);

						var currentThemeIndex = Lang.toInt(themeSelector.attr(STR_DATA_CURRENT_THEME));

						var nextThemeIndex = (currentThemeIndex + 1) % themes.length;

						var currentTheme = themes[currentThemeIndex];
						var nextTheme = themes[nextThemeIndex];

						var nextThemeIcon = instance._getThemeIcon(nextThemeIndex);

						themeSelector.attr(STR_DATA_CURRENT_THEME, nextThemeIndex);

						themeSelector.one('i').replaceClass(nextTheme.iconCssClass, nextThemeIcon);

						var boundingBox = A.one(instance._currentEditor.container).get('parentNode');

						boundingBox.replaceClass(currentTheme.cssClass, nextTheme.cssClass);

						instance._currentThemeIndex = nextThemeIndex;
					},

					_updateActiveLine: function() {
						var instance = this;

						var line = instance._currentEditor.getCursorPosition().row;

						var session = instance._currentEditor.getSession();

						if (session.isRowFolded(line)) {
							line = session.getRowFoldStart(line);
						}

						instance._highlightActiveGutterLine(line);
					}
				}
			}
		);

		A.LiferaySourceEditor = LiferaySourceEditor;
	},
	'',
	{
		requires: ['aui-ace-editor']
	}
);
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

/* global ace */

AUI.add(
	'liferay-source-editor',
	A => {
		var Lang = A.Lang;
		var UA = A.UA;

		var CSS_ACTIVE_CELL = 'ace_gutter-active-cell';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CHANGE = 'change';

		var STR_CHANGE_CURSOR = 'changeCursor';

		var STR_CHANGE_FOLD = 'changeFold';

		var STR_CODE = 'code';

		var STR_DOT = '.';

		var STR_THEMES = 'themes';

		var TPL_CODE_CONTAINER = '<div class="{cssClass}"></div>';

		var LiferaySourceEditor = A.Component.create({
			ATTRS: {
				aceOptions: {
					validator: Lang.isObject,
					valueFn() {
						var instance = this;

						var aceEditor = instance.getEditor();

						return {
							fontSize: 13,
							maxLines:
								Math.floor(
									A.DOM.winHeight() /
										aceEditor.renderer.lineHeight
								) - 15,
							minLines: 10,
							showInvisibles: false,
							showPrintMargin: false
						};
					}
				},

				height: {
					validator(value) {
						return Lang.isString(value) || Lang.isNumber(value);
					},
					value: 'auto'
				},

				themes: {
					validator: Array.isArray,
					valueFn() {
						return [
							{
								cssClass: '',
								icon: A.one(Liferay.Util.getLexiconIcon('sun')),
								tooltip: Liferay.Language.get('light-theme')
							},
							{
								cssClass: 'ace_dark',
								icon: A.one(
									Liferay.Util.getLexiconIcon('moon')
								),
								tooltip: Liferay.Language.get('dark-theme')
							}
						];
					}
				},

				width: {
					validator(value) {
						return Lang.isString(value) || Lang.isNumber(value);
					},
					value: '100%'
				}
			},

			CSS_PREFIX: 'lfr-source-editor',

			EXTENDS: A.AceEditor,

			NAME: 'liferaysourceeditor',

			NS: 'liferaysourceeditor',

			prototype: {
				_highlightActiveGutterLine(line) {
					var instance = this;

					var session = instance.getSession();

					if (instance._currentLine !== null) {
						session.removeGutterDecoration(
							instance._currentLine,
							CSS_ACTIVE_CELL
						);
					}

					session.addGutterDecoration(line, CSS_ACTIVE_CELL);

					instance._currentLine = line;
				},

				_initializeThemes() {
					var instance = this;

					var themes = instance.get(STR_THEMES);

					if (themes.length) {
						instance
							.get(STR_BOUNDING_BOX)
							.addClass(themes[0].cssClass);
					}
				},

				_notifyEditorChange(data) {
					var instance = this;

					instance.fire('change', {
						change: data,
						newVal: instance.get('value')
					});
				},

				_updateActiveLine() {
					var instance = this;

					var line = instance.getEditor().getCursorPosition().row;

					var session = instance.getSession();

					if (session.isRowFolded(line)) {
						line = session.getRowFoldStart(line);
					}

					instance._highlightActiveGutterLine(line);
				},

				CONTENT_TEMPLATE: null,

				bindUI() {
					var instance = this;

					var updateActiveLineFn = A.bind(
						'_updateActiveLine',
						instance
					);

					var aceEditor = instance.getEditor();

					aceEditor.selection.on(
						STR_CHANGE_CURSOR,
						updateActiveLineFn
					);
					aceEditor.session.on(STR_CHANGE_FOLD, updateActiveLineFn);
					aceEditor.session.on(
						STR_CHANGE,
						A.bind('_notifyEditorChange', instance)
					);
				},

				destructor() {
					var instance = this;

					var aceEditor = instance.getEditor();

					aceEditor.selection.removeAllListeners(STR_CHANGE_CURSOR);
					aceEditor.session.removeAllListeners(STR_CHANGE_FOLD);
					aceEditor.session.removeAllListeners(STR_CHANGE);

					new A.EventHandle(instance._eventHandles).detach();
				},

				getEditor() {
					var instance = this;

					if (!instance.editor) {
						var boundingBox = instance.get(STR_BOUNDING_BOX);

						var codeContainer = boundingBox.one(
							STR_DOT + instance.getClassName(STR_CODE)
						);

						if (!codeContainer) {
							codeContainer = A.Node.create(
								Lang.sub(TPL_CODE_CONTAINER, {
									cssClass: instance.getClassName(STR_CODE)
								})
							);

							boundingBox.append(codeContainer);
						}

						instance.editor = ace.edit(codeContainer.getDOM());
					}

					return instance.editor;
				},

				initializer() {
					var instance = this;

					var aceEditor = instance.getEditor();

					aceEditor.setOptions(instance.get('aceOptions'));

					instance._initializeThemes();
					instance._highlightActiveGutterLine(0);

					// LPS-67768

					if (UA.linux && UA.chrome) {
						aceEditor.renderer.$computeLayerConfig();
					}
				},

				switchTheme(themeToSwitch) {
					var instance = this;

					var themes = instance.get(STR_THEMES);

					var currentThemeIndex = instance._currentThemeIndex || 0;

					var nextThemeIndex =
						themeToSwitch ||
						(currentThemeIndex + 1) % themes.length;

					var currentTheme = themes[currentThemeIndex];
					var nextTheme = themes[nextThemeIndex];

					var boundingBox = instance.get(STR_BOUNDING_BOX);

					boundingBox.replaceClass(
						currentTheme.cssClass,
						nextTheme.cssClass
					);

					var prevThemeIndex = currentThemeIndex;

					currentThemeIndex = nextThemeIndex;
					nextThemeIndex = (currentThemeIndex + 1) % themes.length;

					instance._currentThemeIndex = currentThemeIndex;

					instance.fire('themeSwitched', {
						currentThemeIndex,
						nextThemeIndex,
						prevThemeIndex,
						themes
					});
				}
			}
		});

		A.LiferaySourceEditor = LiferaySourceEditor;
	},
	'',
	{
		requires: ['aui-ace-editor']
	}
);

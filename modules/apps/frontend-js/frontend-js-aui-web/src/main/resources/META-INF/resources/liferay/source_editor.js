/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* global ace */

AUI.add(
	'liferay-source-editor',
	(A) => {
		const Lang = A.Lang;
		const UA = A.UA;

		const CSS_ACTIVE_CELL = 'ace_gutter-active-cell';

		const STR_BOUNDING_BOX = 'boundingBox';

		const STR_CHANGE = 'change';

		const STR_CHANGE_CURSOR = 'changeCursor';

		const STR_CHANGE_FOLD = 'changeFold';

		const STR_CODE = 'code';

		const STR_DOT = '.';

		const STR_THEMES = 'themes';

		const TPL_CODE_CONTAINER = '<div class="{cssClass}"></div>';

		const LiferaySourceEditor = A.Component.create({
			ATTRS: {
				aceOptions: {
					validator: Lang.isObject,
					valueFn() {
						const instance = this;

						const aceEditor = instance.getEditor();

						return {
							fontSize: 13,
							maxLines:
								Math.floor(
									A.DOM.winHeight() /
										aceEditor.renderer.lineHeight
								) - 15,
							minLines: 10,
							showInvisibles: false,
							showPrintMargin: false,
						};
					},
				},

				height: {
					validator(value) {
						return Lang.isString(value) || Lang.isNumber(value);
					},
					value: 'auto',
				},

				themes: {
					validator: Array.isArray,
					valueFn() {
						return [
							{
								cssClass: '',
								icon: A.one(Liferay.Util.getLexiconIcon('sun')),
								tooltip: Liferay.Language.get('light-theme'),
							},
							{
								cssClass: 'ace_dark',
								icon: A.one(
									Liferay.Util.getLexiconIcon('moon')
								),
								tooltip: Liferay.Language.get('dark-theme'),
							},
						];
					},
				},

				width: {
					validator(value) {
						return Lang.isString(value) || Lang.isNumber(value);
					},
					value: '100%',
				},
			},

			CSS_PREFIX: 'lfr-source-editor',

			EXTENDS: A.AceEditor,

			NAME: 'liferaysourceeditor',

			NS: 'liferaysourceeditor',

			prototype: {
				_highlightActiveGutterLine(line) {
					const instance = this;

					const session = instance.getSession();

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
					const instance = this;

					const themes = instance.get(STR_THEMES);

					if (themes.length) {
						instance
							.get(STR_BOUNDING_BOX)
							.addClass(themes[0].cssClass);
					}
				},

				_notifyEditorChange(data) {
					const instance = this;

					instance.fire('change', {
						change: data,
						newVal: instance.get('value'),
					});
				},

				_updateActiveLine() {
					const instance = this;

					let line = instance.getEditor().getCursorPosition().row;

					const session = instance.getSession();

					if (session.isRowFolded(line)) {
						line = session.getRowFoldStart(line);
					}

					instance._highlightActiveGutterLine(line);
				},

				CONTENT_TEMPLATE: null,

				bindUI() {
					const instance = this;

					const updateActiveLineFn = A.bind(
						'_updateActiveLine',
						instance
					);

					const aceEditor = instance.getEditor();

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
					const instance = this;

					const aceEditor = instance.getEditor();

					aceEditor.selection.removeAllListeners(STR_CHANGE_CURSOR);
					aceEditor.session.removeAllListeners(STR_CHANGE_FOLD);
					aceEditor.session.removeAllListeners(STR_CHANGE);

					new A.EventHandle(instance._eventHandles).detach();
				},

				getEditor() {
					const instance = this;

					if (!instance.editor) {
						const boundingBox = instance.get(STR_BOUNDING_BOX);

						let codeContainer = boundingBox.one(
							STR_DOT + instance.getClassName(STR_CODE)
						);

						if (!codeContainer) {
							codeContainer = A.Node.create(
								Lang.sub(TPL_CODE_CONTAINER, {
									cssClass: instance.getClassName(STR_CODE),
								})
							);

							boundingBox.append(codeContainer);
						}

						instance.editor = ace.edit(codeContainer.getDOM());
					}

					return instance.editor;
				},

				initializer() {
					const instance = this;

					const aceEditor = instance.getEditor();

					aceEditor.setOptions(instance.get('aceOptions'));

					instance._initializeThemes();
					instance._highlightActiveGutterLine(0);

					// LPS-67768

					if (UA.linux && UA.chrome) {
						aceEditor.renderer.$computeLayerConfig();
					}
				},

				switchTheme(themeToSwitch) {
					const instance = this;

					const themes = instance.get(STR_THEMES);

					let currentThemeIndex = instance._currentThemeIndex || 0;

					let nextThemeIndex =
						themeToSwitch ||
						(currentThemeIndex + 1) % themes.length;

					const currentTheme = themes[currentThemeIndex];
					const nextTheme = themes[nextThemeIndex];

					const boundingBox = instance.get(STR_BOUNDING_BOX);

					boundingBox.replaceClass(
						currentTheme.cssClass,
						nextTheme.cssClass
					);

					const prevThemeIndex = currentThemeIndex;

					currentThemeIndex = nextThemeIndex;
					nextThemeIndex = (currentThemeIndex + 1) % themes.length;

					instance._currentThemeIndex = currentThemeIndex;

					instance.fire('themeSwitched', {
						currentThemeIndex,
						nextThemeIndex,
						prevThemeIndex,
						themes,
					});
				},
			},
		});

		A.LiferaySourceEditor = LiferaySourceEditor;
	},
	'',
	{
		requires: ['aui-ace-editor'],
	}
);

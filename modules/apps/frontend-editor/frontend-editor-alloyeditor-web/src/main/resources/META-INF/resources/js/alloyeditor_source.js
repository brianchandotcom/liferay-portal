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

AUI.add(
	'liferay-alloy-editor-source',
	(A) => {
		var CSS_SHOW_SOURCE = 'show-source';

		var MAP_TOGGLE_STATE = {
			false: {
				iconCssClass: 'code',
			},
			true: {
				iconCssClass: 'text-editor',
			},
		};

		var STR_HOST = 'host';

		var STRINGS = 'strings';

		var STR_VALUE = 'value';

		var LiferayAlloyEditorSource = A.Component.create({
			ATTRS: {
				strings: {
					value: {
						cancel: Liferay.Language.get('cancel'),
						done: Liferay.Language.get('done'),
						editContent: Liferay.Language.get('edit-content'),
					},
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'liferayalloyeditorsource',

			NS: 'liferayalloyeditorsource',

			prototype: {
				_createSourceEditor() {
					var instance = this;

					var host = instance.get(STR_HOST);

					var sourceEditor = new A.LiferaySourceEditor({
						boundingBox: instance._editorSource,
						mode: 'html',
						on: {
							themeSwitched(event) {
								var editorSwitchTheme =
									instance._editorSwitchTheme;

								var nextTheme =
									event.themes[event.nextThemeIndex];

								editorSwitchTheme
									.one('.lexicon-icon')
									.replace(nextTheme.icon);

								editorSwitchTheme.setAttribute(
									'data-title',
									nextTheme.tooltip
								);
							},
						},
						value: host.getHTML(),
					}).render();

					instance._toggleEditorModeUI();

					instance._sourceEditor = sourceEditor;
				},

				_getEditorStateLexiconIcon() {
					var instance = this;

					var icon;

					var currentState = MAP_TOGGLE_STATE[instance._isVisible];

					if (currentState.icon) {
						icon = currentState.icon.cloneNode(true);
					}
					else {
						icon = Liferay.Util.getLexiconIcon(
							currentState.iconCssClass
						);

						currentState.icon = icon;
					}

					return icon;
				},

				_getHTML() {
					var instance = this;

					var sourceEditor = instance._sourceEditor;

					if (sourceEditor && instance._isVisible) {
						var text = sourceEditor.get('value');

						return new A.Do.AlterReturn(
							'Modified source editor text',
							text
						);
					}
				},

				_onEditorUpdate(event) {
					var instance = this;

					instance._toggleSourceSwitchFn(event.data.state);
				},

				_onFullScreenBtnClick() {
					var instance = this;

					var host = instance.get(STR_HOST);
					var strings = instance.get(STRINGS);

					var fullScreenDialog = instance._fullScreenDialog;
					var fullScreenEditor = instance._fullScreenEditor;

					if (fullScreenDialog) {
						fullScreenEditor.set('value', host.getHTML());

						fullScreenDialog.show();
					}
					else {
						Liferay.Util.openWindow(
							{
								dialog: {
									constrain: true,
									cssClass:
										'lfr-fulscreen-source-editor-dialog modal-full-screen',
									modal: true,
									'toolbars.footer': [
										{
											label: strings.cancel,
											on: {
												click() {
													fullScreenDialog.hide();
												},
											},
										},
										{
											cssClass: 'btn-primary',
											label: strings.done,
											on: {
												click() {
													fullScreenDialog.hide();
													instance._switchMode({
														content: fullScreenEditor.get(
															'value'
														),
													});
												},
											},
										},
									],
								},
								title: strings.editContent,
							},
							(dialog) => {
								fullScreenDialog = dialog;

								Liferay.Util.getTop()
									.AUI()
									.use(
										'liferay-fullscreen-source-editor',
										(A) => {
											fullScreenEditor = new A.LiferayFullScreenSourceEditor(
												{
													boundingBox: dialog
														.getStdModNode(
															A.WidgetStdMod.BODY
														)
														.appendChild(
															'<div></div>'
														),
													dataProcessor: host.getNativeEditor()
														.dataProcessor,
													previewCssClass:
														'alloy-editor alloy-editor-placeholder',
													value: host.getHTML(),
												}
											).render();

											instance._fullScreenDialog = fullScreenDialog;

											instance._fullScreenEditor = fullScreenEditor;
										}
									);
							}
						);
					}
				},

				_onSwitchBlur() {
					var instance = this;

					instance._isFocused = false;

					instance._toggleSourceSwitchFn({
						hidden: true,
					});
				},

				_onSwitchFocus() {
					var instance = this;

					instance._isFocused = true;

					instance._toggleSourceSwitchFn({
						hidden: false,
					});
				},

				_onSwitchMouseDown() {
					var instance = this;

					instance._isClicked = true;
				},

				_onSwitchMouseOut() {
					var instance = this;

					instance._isClicked = false;
				},

				_setHTML(value) {
					var instance = this;

					var sourceEditor = instance._sourceEditor;

					if (sourceEditor && instance._isVisible) {
						sourceEditor.set(STR_VALUE, value);
					}
				},

				_switchMode(event) {
					var instance = this;

					instance._isClicked = false;

					var host = instance.get(STR_HOST);

					var editor = instance._sourceEditor;

					if (instance._isVisible) {
						var content =
							event.content ||
							(editor ? editor.get(STR_VALUE) : '');

						host.setHTML(content);

						instance._toggleEditorModeUI();
					}
					else if (editor) {
						var currentContent = event.content || host.getHTML();

						if (currentContent !== editor.get(STR_VALUE)) {
							editor.set(STR_VALUE, currentContent);
						}

						instance._toggleEditorModeUI();
					}
					else {
						instance._createSourceEditor();
					}
				},

				_switchTheme() {
					var instance = this;

					instance._sourceEditor.switchTheme();
				},

				_toggleEditorModeUI() {
					var instance = this;

					var editorFullscreen = instance._editorFullscreen;
					var editorSwitch = instance._editorSwitch;
					var editorSwitchContainer = editorSwitch.ancestor();
					var editorSwitchTheme = instance._editorSwitchTheme;
					var editorWrapper = instance._editorWrapper;

					editorWrapper.toggleClass(CSS_SHOW_SOURCE);
					editorSwitchContainer.toggleClass(CSS_SHOW_SOURCE);
					editorFullscreen.toggleClass('hide');
					editorSwitchTheme.toggleClass('hide');

					instance._isVisible = editorWrapper.hasClass(
						CSS_SHOW_SOURCE
					);

					editorSwitch
						.one('.lexicon-icon')
						.replace(instance._getEditorStateLexiconIcon());
					editorSwitch.setAttribute(
						'data-title',
						instance._isVisible
							? Liferay.Language.get('text-view')
							: Liferay.Language.get('code-view')
					);

					instance._toggleSourceSwitchFn({
						hidden: true,
					});
				},

				_toggleSourceSwitch(editorState) {
					var instance = this;

					var showSourceSwitch = true;

					if (!instance._isClicked) {
						showSourceSwitch =
							instance._isVisible ||
							instance._isFocused ||
							!editorState.hidden;
					}

					instance._editorSwitch
						.ancestor()
						.toggleClass('hide', !showSourceSwitch);
				},

				destructor() {
					var instance = this;

					var sourceEditor = instance._sourceEditor;

					if (sourceEditor) {
						sourceEditor.destroy();
					}

					var fullScreenEditor = instance._fullScreenEditor;

					if (fullScreenEditor) {
						fullScreenEditor.destroy();
					}

					var fullScreenDialog = instance._fullScreenDialog;

					if (fullScreenDialog) {
						fullScreenDialog.destroy();
					}

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					var host = instance.get(STR_HOST);

					instance._editorFullscreen = host.one('#Fullscreen');
					instance._editorSource = host.one('#Source');
					instance._editorSwitch = host.one('#Switch');
					instance._editorSwitchTheme = host.one('#SwitchTheme');
					instance._editorWrapper = host.one('#Wrapper');

					instance._toggleSourceSwitchFn = A.debounce(
						instance._toggleSourceSwitch,
						100,
						instance
					);

					host.getNativeEditor().on(
						'editorUpdate',
						A.bind('_onEditorUpdate', instance)
					);

					instance._eventHandles = [
						instance._editorFullscreen.on(
							'click',
							instance._onFullScreenBtnClick,
							instance
						),
						instance._editorSwitch.on(
							'blur',
							instance._onSwitchBlur,
							instance
						),
						instance._editorSwitch.on(
							'click',
							instance._switchMode,
							instance
						),
						instance._editorSwitch.on(
							'focus',
							instance._onSwitchFocus,
							instance
						),
						instance._editorSwitch.on(
							'mousedown',
							instance._onSwitchMouseDown,
							instance
						),
						instance._editorSwitch.on(
							'mouseout',
							instance._onSwitchMouseOut,
							instance
						),
						instance._editorSwitchTheme.on(
							'click',
							instance._switchTheme,
							instance
						),
						instance.doAfter(
							'getHTML',
							instance._getHTML,
							instance
						),
						instance.doAfter(
							'setHTML',
							instance._setHTML,
							instance
						),
					];
				},
			},
		});

		A.Plugin.LiferayAlloyEditorSource = LiferayAlloyEditorSource;
	},
	'',
	{
		requires: [
			'aui-debounce',
			'liferay-fullscreen-source-editor',
			'liferay-source-editor',
			'plugin',
		],
	}
);

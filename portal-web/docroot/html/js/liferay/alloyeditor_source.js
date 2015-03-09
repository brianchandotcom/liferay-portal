AUI.add(
	'liferay-alloy-editor-source',
	function(A) {
		var Lang = A.Lang;

		var CSS_SHOW_SOURCE = 'show-source';

		var STRINGS = 'strings';

		var STR_HOST = 'host';

		var STR_VALUE = 'value';

		var LiferayAlloyEditorSource = A.Component.create(
			{
				ATTRS: {
					strings: {
						value: {
							cancel: Liferay.Language.get('cancel'),
							done: Liferay.Language.get('done'),
							editContent: Liferay.Language.get('edit-content')
						}
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'liferayalloyeditorsource',

				NS: 'liferayalloyeditorsource',

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._editorFullscreen = host.one('#Fullscreen');
						instance._editorSource = host.one('#Source');
						instance._editorSwitch = host.one('#Switch');
						instance._editorWrapper = host.one('#Wrapper');

						instance._eventHandles = [
							instance._editorSwitch.on('click', instance._switchMode, instance),
							instance._editorFullscreen.on('click', instance._onFullScreenBtnClick, instance),
							instance.doAfter('getHTML', instance._getHTML, instance)
						];
					},

					destructor: function() {
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

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_createSourceEditor: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var sourceEditor = new A.LiferaySourceEditor(
							{
								boundingBox: instance._editorSource,
								mode: 'html',
								value: host.getHTML()
							}
						).render();

						instance._toggleEditorModeUI();

						instance._sourceEditor = sourceEditor;
					},

					_getHTML: function() {
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

					_onFullScreenBtnClick: function() {
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
										cssClass: 'lfr-fulscreen-source-editor-dialog',
										modal: true,
										'toolbars.footer': [
											{
												cssClass: 'btn-primary',
												label: strings.done,
												on: {
													click: function() {
														fullScreenDialog.hide();
														instance._switchMode(
															{
																content: fullScreenEditor.get('value')
															}
														);
													}
												}
											},
											{
												label: strings.cancel,
												on: {
													click: function() {
														fullScreenDialog.hide();
													}
												}
											}
										]
									},
									title: strings.editContent
								},
								function(dialog) {
									fullScreenDialog = dialog;

									fullScreenEditor = new A.LiferayFullScreenSourceEditor(
										{
											boundingBox: dialog.getStdModNode(A.WidgetStdMod.BODY).appendChild('<div></div>'),
											previewCssClass: 'alloy-editor alloy-editor-placeholder',
											value: host.getHTML()
										}
									).render();

									instance._fullScreenDialog = fullScreenDialog;

									instance._fullScreenEditor = fullScreenEditor;
								}
							);
						}
					},

					_switchMode: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var editor = instance._sourceEditor;

						if (instance._isVisible) {
							var content = event.content || (editor ? editor.get(STR_VALUE) : '');

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

					_toggleEditorModeUI: function() {
						var instance = this;

						var editorWrapper = instance._editorWrapper;
						var editorFullscreen = instance._editorFullscreen;
						var editorSwitch = instance._editorSwitch;
						var editorSwitchContainer = editorSwitch.ancestor();

						editorWrapper.toggleClass(CSS_SHOW_SOURCE);
						editorSwitchContainer.toggleClass(CSS_SHOW_SOURCE);
						editorFullscreen.toggleClass('hide');

						instance._isVisible = editorWrapper.hasClass(CSS_SHOW_SOURCE);

						editorSwitch.setHTML(instance._isVisible ? 'abc' : '&lt;/&gt;');
					}
				}
			}
		);

		A.Plugin.LiferayAlloyEditorSource = LiferayAlloyEditorSource;
	},
	'',
	{
		requires: ['liferay-fullscreen-source-editor', 'liferay-source-editor']
	}
);
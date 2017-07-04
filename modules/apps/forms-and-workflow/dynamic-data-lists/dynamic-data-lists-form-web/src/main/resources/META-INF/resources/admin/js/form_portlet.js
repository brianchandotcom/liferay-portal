AUI.add(
	'liferay-ddl-portlet',
	function(A) {
		var LayoutSerializer = Liferay.DDL.LayoutSerializer;

		var Settings = Liferay.DDL.Settings;

		var EMPTY_FN = A.Lang.emptyFn;

		var MINUTE = 60000;

		var STR_TRANSLATION_MANAGER = 'translationManager';

		var STR_UNTITLED_FORM = Liferay.Language.get('untitled-form');

		var TPL_BUTTON_SPINNER = '<span aria-hidden="true"><span class="icon-spinner icon-spin"></span></span>';

		var DDLPortlet = A.Component.create(
			{
				ATTRS: {
					alert: {
					},

					context: {
					},

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					editForm: {
					},

					editingLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					formBuilder: {
						valueFn: '_valueFormBuilder'
					},

					localizedDescription: {
						value: {}
					},

					localizedName: {
						value: {}
					},

					published: {
						lazyAdd: false,
						setter: '_setPublished',
						value: false
					},

					recordSetId: {
						getter: '_getRecordSetId',
						value: 0
					},

					ruleBuilder: {
						valueFn: '_valueRuleBuilder'
					},

					rules: {
						value: []
					},

					translationManager: {
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-portlet',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.layoutVisitor = new LayoutSerializer(
							{
								builder: instance.get('formBuilder'),
								defaultLanguageId: instance.get('defaultLanguageId')
							}
						);

						instance.renderUI();
						instance.bindUI();

						instance.savedState = instance.getState();
					},

					renderUI: function() {
						var instance = this;

						instance.one('#loader').remove();

						instance.one('.portlet-forms').removeClass('hide');

						instance.get('formBuilder').render(instance.one('#formBuilder'));
						instance.get('ruleBuilder').render(instance.one('#ruleBuilder'));

						instance.createEditor(instance.ns('descriptionEditor'));
						instance.createEditor(instance.ns('nameEditor'));

						instance.createCopyPublishFormURLPopover();
						instance.createPublishTooltip();
					},

					bindUI: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						var translationManager = instance.get(STR_TRANSLATION_MANAGER);

						translationManager.on('editingLocaleChange', instance._afterEditingLocaleChange.bind(instance));

						var descriptionEditor = CKEDITOR.instances[instance.ns('descriptionEditor')];

						descriptionEditor.on('change', A.bind('_onDescriptionEditorChange', instance));

						var nameEditor = CKEDITOR.instances[instance.ns('nameEditor')];

						nameEditor.on('change', A.bind('_onNameEditorChange', instance));

						instance._eventHandlers = [
							formBuilder._layoutBuilder.after('layout-builder:moveEnd', A.bind(instance._afterFormBuilderLayoutBuilderMoveEnd, instance)),
							formBuilder._layoutBuilder.after('layout-builder:moveStart', A.bind(instance._afterFormBuilderLayoutBuilderMoveStart, instance)),
							instance.after('autosave', instance._afterAutosave),
							instance.one('.back-url-link').on('click', A.bind('_onBack', instance)),
							instance.one('#preview').on('click', A.bind('_onPreviewButtonClick', instance)),
							instance.one('#publish').on('click', A.bind('_onPublishButtonClick', instance)),
							instance.one('#publishIcon').on('click', A.bind('_onPublishIconClick', instance)),
							instance.one('#save').on('click', A.bind('_onSaveButtonClick', instance)),
							instance.one('#showForm').on('click', A.bind('_onFormButtonClick', instance)),
							instance.one('#showRules').on('click', A.bind('_onRulesButtonClick', instance)),
							Liferay.on('destroyPortlet', A.bind('_onDestroyPortlet', instance))
						];

						var autosaveInterval = Settings.autosaveInterval;

						if (autosaveInterval > 0) {
							instance._intervalId = setInterval(A.bind('_autosave', instance), autosaveInterval * MINUTE);
						}
					},

					destructor: function() {
						var instance = this;

						clearInterval(instance._intervalId);

						instance.get('formBuilder').destroy();
						instance.get('ruleBuilder').destroy();

						(new A.EventHandle(instance._eventHandlers)).detach();

						instance._copyPublishFormURLPopover.destroy();
						instance._publishTooltip.destroy();
					},

					createCopyPublishFormURLPopover: function() {
						var instance = this;

						instance._copyPublishFormURLPopover = new Liferay.DDL.FormBuilderCopyPublishFormURLPopover(
							{
								portletNamespace: instance.get('namespace')
							}
						);

						instance._copyPublishFormURLPopover.setAlign(
							{
								node: A.one('.publish-icon'),
								points: [A.WidgetPositionAlign.RC, A.WidgetPositionAlign.LC]
							}
						);
					},

					createEditor: function(editorName) {
						var instance = this;

						var editor = window[editorName];

						if (editor) {
							editor.create();
						}
						else {
							Liferay.once(
								'editorAPIReady',
								function(event) {
									if (event.editorName === editorName) {
										event.editor.create();
									}
								}
							);
						}
					},

					createPublishTooltip: function() {
						var instance = this;

						instance._publishTooltip = new A.TooltipDelegate(
							{
								position: 'left',
								trigger: '.publish-icon',
								triggerHideEvent: ['blur', 'mouseleave'],
								triggerShowEvent: ['focus', 'mouseover'],
								visible: false,
								zIndex: 900
							}
						);
					},

					disableDescriptionEditor: function() {
						var instance = this;

						var descriptionEditor = CKEDITOR.instances[instance.ns('descriptionEditor')];

						descriptionEditor.setReadOnly(true);
					},

					disableNameEditor: function() {
						var instance = this;

						var nameEditor = CKEDITOR.instances[instance.ns('nameEditor')];

						nameEditor.setReadOnly(true);
					},

					enableDescriptionEditor: function() {
						var instance = this;

						var descriptionEditor = CKEDITOR.instances[instance.ns('descriptionEditor')];

						descriptionEditor.setReadOnly(false);
					},

					enableNameEditor: function() {
						var instance = this;

						var nameEditor = CKEDITOR.instances[instance.ns('nameEditor')];

						nameEditor.setReadOnly(false);
					},

					getState: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');
						var ruleBuilder = instance.get('ruleBuilder');

						var pageManager = formBuilder.get('pageManager');

						instance.layoutVisitor.set('pages', formBuilder.get('layouts'));

						var translationManager = instance.get('translationManager');

						return {
							availableLanguageIds: translationManager.get('availableLocales'),
							defaultLanguageId: translationManager.get('defaultLocale'),
							description: instance.get('localizedDescription'),
							name: instance._getLocalizedName(),
							pages: instance.layoutVisitor.getPages(),
							paginationMode: pageManager.get('mode'),
							rules: ruleBuilder.get('rules'),
							successPageSettings: pageManager.get('successPageSettings')
						};
					},

					isEmpty: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						var count = 0;

						formBuilder.eachFields(
							function(field) {
								count++;
							}
						);

						return count === 0;
					},

					openConfirmationModal: function(confirm, cancel) {
						var instance = this;

						var dialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									bodyContent: Liferay.Language.get('any-unsaved-changes-will-be-lost-are-you-sure-you-want-to-leave'),
									destroyOnHide: true,
									height: 200,
									resizable: false,
									toolbars: {
										footer: [
											{
												cssClass: 'btn-lg btn-primary',
												label: Liferay.Language.get('leave'),
												on: {
													click: function() {
														confirm.call(instance, dialog);
													}
												}
											},
											{
												cssClass: 'btn-lg btn-link',
												label: Liferay.Language.get('stay'),
												on: {
													click: function() {
														cancel.call(instance, dialog);
													}
												}
											}
										]
									},
									width: 600
								},
								title: Liferay.Language.get('leave-form')
							}
						);

						return dialog;
					},

					submitForm: function() {
						var instance = this;

						instance.syncInputValues();

						var editForm = instance.get('editForm');

						submitForm(editForm.form);
					},

					syncInputValues: function() {
						var instance = this;

						var state = instance.getState();

						instance.one('#description').val(JSON.stringify(state.description));
						instance.one('#name').val(JSON.stringify(state.name));

						instance.one('#serializedFormBuilderContext').val(JSON.stringify(state));

						var settingsDDMForm = Liferay.component('settingsDDMForm');

						var publishedField = settingsDDMForm.getField('published');

						publishedField.set('value', instance.get('published'));

						var settings = settingsDDMForm.get('context');

						var settingsInput = instance.one('#serializedSettingsContext');

						settingsInput.val(JSON.stringify(settings));
					},

					_afterAutosave: function(event) {
						var instance = this;

						var autosaveMessage = A.Lang.sub(
							Liferay.Language.get('draft-saved-on-x'),
							[
								event.modifiedDate
							]
						);

						instance.one('#autosaveMessage').set('innerHTML', autosaveMessage);
						A.one('.publish-icon').removeClass('hide');
					},

					_afterEditingLocaleChange: function(event) {
						var instance = this;

						var editingLanguageId = event.newVal;

						var formBuilder = instance.get('formBuilder');

						instance.set('editingLanguageId', editingLanguageId);
						formBuilder.set('editingLanguageId', editingLanguageId);

						instance._syncName();
						instance._syncDescription();
					},

					_afterFormBuilderLayoutBuilderMoveEnd: function() {
						var instance = this;

						instance.enableDescriptionEditor();
						instance.enableNameEditor();
					},

					_afterFormBuilderLayoutBuilderMoveStart: function() {
						var instance = this;

						instance.disableDescriptionEditor();
						instance.disableNameEditor();
					},

					_autosave: function(callback) {
						var instance = this;

						callback = callback || EMPTY_FN;

						instance.syncInputValues();

						var state = instance.getState();

						if (!instance.isEmpty()) {
							if (!instance._isSameState(instance.savedState, state)) {
								var editForm = instance.get('editForm');

								var formData = instance._getFormData(A.IO.stringify(editForm.form));

								A.io.request(
									Settings.autosaveURL,
									{
										after: {
											success: function(event, id, xhr) {
												var requestURL = this.get('uri');
												var responseURL = xhr.responseURL;

												if (requestURL !== responseURL) {
													window.location.reload();
												}
												else {
													var responseData = this.get('responseData');

													instance._defineIds(responseData);

													instance.savedState = state;

													instance.fire(
															'autosave',
															{
																modifiedDate: responseData.modifiedDate
															}
													);

													callback.call();
												}
											}
										},
										data: formData,
										dataType: 'JSON',
										method: 'POST'
									}
								);
							}
							else {
								callback.call();
							}
						}
					},

					_createFormURL: function() {
						var instance = this;

						var formURL;

						var settingsDDMForm = Liferay.component('settingsDDMForm');

						var requireAuthenticationField = settingsDDMForm.getField('requireAuthentication');

						if (requireAuthenticationField.getValue()) {
							formURL = Settings.restrictedFormURL;
						}
						else {
							formURL = Settings.sharedFormURL;
						}

						var recordSetId = instance.byId('recordSetId').val();

						return formURL + recordSetId;
					},

					_createPreviewURL: function() {
						var instance = this;

						var formURL = instance._createFormURL();

						return formURL + '/preview';
					},

					_defineIds: function(response) {
						var instance = this;

						var recordSetIdNode = instance.byId('recordSetId');

						var ddmStructureIdNode = instance.byId('ddmStructureId');

						if (recordSetIdNode.val() === '0') {
							recordSetIdNode.val(response.recordSetId);
						}

						if (ddmStructureIdNode.val() === '0') {
							ddmStructureIdNode.val(response.ddmStructureId);
						}
					},

					_getDescription: function() {
						var instance = this;

						var editor = instance._getDescriptionEditor();

						return editor.getHTML();
					},

					_getDescriptionEditor: function() {
						var instance = this;

						return window[instance.ns('descriptionEditor')];
					},

					_getFormData: function(formString) {
						var instance = this;

						var formObject = A.QueryString.parse(formString);

						var state = instance.getState();

						formObject[instance.ns('name')] = JSON.stringify(state.name);
						formObject[instance.ns('published')] = JSON.stringify(instance.get('published'));

						formString = A.QueryString.stringify(formObject);

						return formString;
					},

					_getLocalizedName: function() {
						var instance = this;

						var defaultLanguageId = instance.get('defaultLanguageId');
						var localizedName = instance.get('localizedName');

						if (!localizedName[defaultLanguageId]) {
							localizedName[defaultLanguageId] = STR_UNTITLED_FORM;
						}

						return localizedName;
					},

					_getName: function() {
						var instance = this;

						var editor = instance._getNameEditor();

						return editor.getHTML();
					},

					_getNameEditor: function() {
						var instance = this;

						return window[instance.ns('nameEditor')];
					},

					_getRecordSetId: function() {
						var instance = this;

						return instance.byId('recordSetId').val();
					},

					_handlePublishAction: function() {
						var instance = this;

						var publishMessage = Liferay.Language.get('the-form-was-published-successfully-access-it-with-this-url-x');

						var formUrl = instance._createFormURL();

						var span = '<span style="font-weight: 500"><a href=' + formUrl + ' target="_blank">' + formUrl + '</a></span>';

						publishMessage = publishMessage.replace(/\{0\}/gim, span);

						instance._showAlert(publishMessage, 'success');

						instance.one('#publish').html(Liferay.Language.get('unpublish-form'));
					},

					_handleUnpublishAction: function() {
						var instance = this;

						instance._showAlert(Liferay.Language.get('the-form-was-unpublished-successfully'), 'success');

						instance.one('#publish').html(Liferay.Language.get('publish-form'));
					},

					_isSameState: function(state1, state2) {
						var instance = this;

						return AUI._.isEqual(
							state1,
							state2,
							function(value1, value2, key) {
								return (key === 'instanceId') || undefined;
							}
						);
					},

					_onBack: function(event) {
						var instance = this;

						if (!instance._isSameState(instance.getState(), instance.savedState)) {
							event.preventDefault();
							event.stopPropagation();

							instance.openConfirmationModal(
								function(dialog) {
									window.location.href = event.currentTarget.get('href');

									dialog.hide();
								},
								function(dialog) {
									dialog.hide();
								}
							);
						}
					},

					_onDescriptionEditorChange: function(event) {
						var instance = this;

						var editingLanguageId = instance.get('editingLanguageId');
						var localizedDescription = instance.get('localizedDescription');

						var descriptionEditor = instance._getDescriptionEditor();

						localizedDescription[editingLanguageId] = descriptionEditor.getHTML();
					},

					_onDestroyPortlet: function(event) {
						var instance = this;

						instance.destroy();
					},

					_onFormButtonClick: function() {
						var instance = this;

						instance.one('#formBuilder').show();

						instance.get('ruleBuilder').hide();

						A.one('.ddl-form-builder-buttons').removeClass('hide');
						A.one('.portlet-forms').removeClass('liferay-ddl-form-rule-builder');

						instance.one('#showRules').removeClass('active');
						instance.one('#showForm').addClass('active');
					},

					_onNameEditorChange: function(event) {
						var instance = this;

						var editingLanguageId = instance.get('editingLanguageId');
						var localizedName = instance.get('localizedName');

						var nameEditor = instance._getNameEditor();

						localizedName[editingLanguageId] = nameEditor.getHTML();
					},

					_onPreviewButtonClick: function() {
						var instance = this;

						instance._autosave(
							function() {
								var previewURL = instance._createPreviewURL();

								window.open(previewURL, '_blank');
							}
						);
					},

					_onPublishButtonClick: function() {
						var instance = this;

						instance._autosave(
							function() {
								var publishedValue = instance.get('published');
								var newPublishedValue = !publishedValue;

								var payload = instance.ns(
									{
										published: newPublishedValue,
										recordSetId: instance.byId('recordSetId').val()
									}
								);

								A.io.request(
									Settings.publishRecordSetURL,
									{
										after: {
											success: function(event, id, xhr) {
												var requestURL = this.get('uri');
												var responseURL = xhr.responseURL;

												if (requestURL !== responseURL) {
													window.location.reload();
												}
												else {
													var responseData = this.get('responseData');

													instance.set('published', newPublishedValue);

													instance.syncInputValues();

													if (newPublishedValue) {
														instance._handlePublishAction();
													}
													else {
														instance._handleUnpublishAction();
													}
												}
											}
										},
										data: payload,
										dataType: 'JSON',
										method: 'POST'
									}
								);
							}
						);
					},

					_onPublishIconClick: function() {
						var instance = this;

						if (!instance.get('published')) {
							return;
						}

						instance._copyPublishFormURLPopover.set('publishURL', instance._createFormURL());

						instance._copyPublishFormURLPopover.show();
					},

					_onRulesButtonClick: function() {
						var instance = this;

						instance.one('#formBuilder').hide();

						instance.get('ruleBuilder').show();

						A.one('.ddl-form-builder-buttons').addClass('hide');
						A.one('.portlet-forms').addClass('liferay-ddl-form-rule-builder');

						instance.one('#showRules').addClass('active');
						instance.one('#showForm').removeClass('active');
					},

					_onSaveButtonClick: function(event) {
						var instance = this;

						event.preventDefault();

						var saveButton = instance.one('#save');

						saveButton.html(Liferay.Language.get('saving'));

						saveButton.append(TPL_BUTTON_SPINNER);

						instance.submitForm();
					},

					_setDescription: function(value) {
						var instance = this;

						var editor = instance._getDescriptionEditor();

						editor.setHTML(value);
					},

					_setName: function(value) {
						var instance = this;

						var editor = instance._getNameEditor();

						editor.setHTML(value);
					},

					_setPublished: function(value) {
						var instance = this;

						var title;

						if (value) {
							title = Liferay.Language.get('copy-url');
						}
						else {
							title = Liferay.Language.get('publish-the-form-to-get-its-shareable-link');
						}

						var publishIcon = A.one('.publish-icon');

						publishIcon.toggleClass('disabled', !value);
						publishIcon.attr('title', title);
					},

					_showAlert: function(message, type) {
						var instance = this;

						var alert = instance.get('alert');

						if (alert) {
							alert.destroy();
						}

						var icon = 'exclamation-full';

						if (type === 'success') {
							icon = 'check';
						}

						alert = new Liferay.Alert(
							{
								closeable: true,
								delay: {
									hide: 3000,
									show: 0
								},
								icon: icon,
								message: message,
								type: type
							}
						);

						if (!alert.get('rendered')) {
							alert.render('.management-bar-default .container-fluid-1280');
						}

						alert.show();

						instance.set('alert', alert);
					},

					_syncDescription: function() {
						var instance = this;

						var editingLanguageId = instance.get('editingLanguageId');
						var defaultLanguageId = instance.get('defaultLanguageId');

						var localizedDescription = instance.get('localizedDescription');

						var description = localizedDescription[editingLanguageId] || localizedDescription[defaultLanguageId];

						localizedDescription[editingLanguageId] = description;

						instance._setDescription(description);
					},

					_syncName: function() {
						var instance = this;

						var editingLanguageId = instance.get('editingLanguageId');
						var defaultLanguageId = instance.get('defaultLanguageId');

						var localizedName = instance.get('localizedName');

						var name = localizedName[editingLanguageId] || localizedName[defaultLanguageId];

						localizedName[editingLanguageId] = name;

						instance._setName(name);
					},

					_valueFormBuilder: function() {
						var instance = this;

						return new Liferay.DDL.FormBuilder(
							{
								context: instance.get('context'),
								defaultLanguageId: instance.get('defaultLanguageId'),
								editingLanguageId: instance.get('editingLanguageId')
							}
						);
					},

					_valueRuleBuilder: function() {
						var instance = this;

						return new Liferay.DDL.FormBuilderRuleBuilder(
							{
								formBuilder: instance.get('formBuilder'),
								rules: instance.get('rules'),
								visible: false
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').Portlet = DDLPortlet;
	},
	'',
	{
		requires: ['aui-tooltip', 'io-base', 'liferay-alert', 'liferay-ddl-form-builder', 'liferay-ddl-form-builder-copy-publish-form-url-popover', 'liferay-ddl-form-builder-definition-serializer', 'liferay-ddl-form-builder-layout-serializer', 'liferay-ddl-form-builder-rule-builder', 'liferay-portlet-base', 'liferay-util-window', 'querystring-parse']
	}
);
AUI.add(
	'liferay-ddl-portlet',
	function(A) {
		var DefinitionSerializer = Liferay.DDL.DefinitionSerializer;

		var LayoutSerializer = Liferay.DDL.LayoutSerializer;

		var EMPTY_FN = A.Lang.emptyFn;

		var MINUTE = 60000;

		var TPL_BUTTON_SPINNER = '<span aria-hidden="true"><span class="icon-spinner icon-spin"></span></span>';

		var DDLPortlet = A.Component.create(
			{
				ATTRS: {
					alert: {
						valueFn: '_valueAlert'
					},

					autosaveInterval: {
					},

					autosaveURL: {
					},

					availableLanguageIds: {
						value: [
							themeDisplay.getDefaultLanguageId()
						]
					},

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					definition: {
					},

					description: {
						getter: '_getDescription',
						value: ''
					},

					editForm: {
					},

					evaluatorURL: {
					},

					fieldTypesDefinitions: {
						value: {}
					},

					formBuilder: {
						valueFn: '_valueFormBuilder'
					},

					functionsMetadata: {
						value: []
					},

					getDataProviderInstancesURL: {
						value: ''
					},

					getDataProviderParametersSettingsURL: {
						value: ''
					},

					getFieldTypeSettingFormContextURL: {
						value: ''
					},

					getFunctionsURL: {
						value: ''
					},

					getRolesURL: {
						value: ''
					},

					layout: {
					},

					name: {
						getter: '_getName',
						setter: '_setName',
						value: ''
					},

					published: {
						lazyAdd: false,
						setter: '_setPublished',
						value: false
					},

					publishRecordSetURL: {
					},

					recordSetId: {
						value: 0
					},

					restrictedFormURL: {
					},

					ruleBuilder: {
						valueFn: '_valueRuleBuilder'
					},

					rules: {
						value: []
					},

					sharedFormURL: {
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-portlet',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.definitionSerializer = new DefinitionSerializer(
							{
								availableLanguageIds: instance.get('availableLanguageIds'),
								defaultLanguageId: instance.get('defaultLanguageId'),
								fieldTypesDefinitions: instance.get('fieldTypesDefinitions')
							}
						);

						instance.layoutSerializer = new LayoutSerializer(
							{
								builder: instance.get('formBuilder'),
								defaultLanguageId: instance.get('defaultLanguageId')
							}
						);

						instance.renderUI();

						instance.bindUI();

						instance.savedState = instance.initialState = instance.getState();
					},

					renderUI: function() {
						var instance = this;

						instance.one('#loader').remove();

						instance.one('.portlet-forms').removeClass('hide');

						instance.get('formBuilder').render(instance.one('#formBuilder'));

						instance.get('ruleBuilder').render(instance.one('#ruleBuilder'));

						instance.createEditor(instance.ns('descriptionEditor'));
						instance.createEditor(instance.ns('nameEditor'));

						instance.createPublishPopover();
						instance.createPublishTooltip();
					},

					bindUI: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						instance._eventHandlers = [
							instance.after('autosave', instance._afterAutosave),
							formBuilder._layoutBuilder.after('layout-builder:moveEnd', A.bind(instance._afterFormBuilderLayoutBuilderMoveEnd, instance)),
							formBuilder._layoutBuilder.after('layout-builder:moveStart', A.bind(instance._afterFormBuilderLayoutBuilderMoveStart, instance)),
							instance.one('.back-url-link').on('click', A.bind('_onBack', instance)),
							instance.one('#preview').on('click', A.bind('_onPreviewButtonClick', instance)),
							instance.one('#publish').on('click', A.bind('_onPublishButtonClick', instance)),
							instance.one('#publishIcon').on('click', A.bind('_onPublishIconClick', instance)),
							instance.one('#save').on('click', A.bind('_onSaveButtonClick', instance)),
							instance.one('#showRules').on('click', A.bind('_onRulesButtonClick', instance)),
							instance.one('#showForm').on('click', A.bind('_onFormButtonClick', instance)),
							instance.one('#requireAuthenticationCheckbox').on('change', A.bind('_onRequireAuthenticationCheckboxChanged', instance)),
							Liferay.on('destroyPortlet', A.bind('_onDestroyPortlet', instance))
						];

						var autosaveInterval = instance.get('autosaveInterval');

						if (autosaveInterval > 0) {
							instance._intervalId = setInterval(A.bind('_autosave', instance), autosaveInterval * MINUTE);
						}

					},

					destructor: function() {
						var instance = this;

						clearInterval(instance._intervalId);

						instance.get('alert').destroy();
						instance.get('formBuilder').destroy();
						instance.get('ruleBuilder').destroy();

						(new A.EventHandle(instance._eventHandlers)).detach();

						instance._publishPopover.destroy();
						instance._publishTooltip.destroy();
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

					createPublishPopover: function() {
						var instance = this;

						instance._publishPopover = new A.Popover(
							{
								align: {
									node: A.one('.publish-icon'),
									points: [A.WidgetPositionAlign.RC, A.WidgetPositionAlign.LC]
								},
								bodyContent: A.one('.publish-popover-content.edit-popover'),
								constrain: false,
								cssClass: 'form-builder-publish-popover',
								position: 'left',
								visible: false,
								width: 500,
								zIndex: 999
							}
						).render();

						instance._publishPopover.set(
							'hideOn',[
								{
									eventName: 'key',
									keyCode: 'esc',
									node: A.getDoc()
								},
								{
									eventName: 'clickoutside',
									node: A.one('.form-builder-publish-popover')
								}
							]
						);

						instance._publishPopover.after("visibleChange", function(event) {

							if (event.prevVal) {
								var popoverContent = A.one('.publish-popover-content.edit-popover');

								var formGroup = popoverContent.one('.form-group');

								formGroup.removeClass('has-error');
								formGroup.removeClass('has-success');

								var copyButton = popoverContent.one('.btn');

								copyButton.removeClass('btn-danger');
								copyButton.removeClass('btn-success');

								popoverContent.one('.publish-button-text').html(Liferay.Language.get('copy'));
							}
						});
					},

					createPublishTooltip: function() {
						var instance = this;

						instance._publishTooltip = new A.TooltipDelegate(
							{
								position: 'left',
								trigger: '.publish-icon',
								triggerHideEvent: ['blur', 'mouseleave'],
								triggerShowEvent: ['focus', 'mouseover'],
								visible: false
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

						var pages = formBuilder.get('layouts');

						instance.definitionSerializer.set('pages', pages);

						instance.definitionSerializer.set('successPage', formBuilder.getSuccessPageDefinition());

						var definition = JSON.parse(instance.definitionSerializer.serialize());

						var rules = JSON.stringify(ruleBuilder.get('rules'));

						instance.layoutSerializer.set('pages', pages);

						var layout = JSON.parse(instance.layoutSerializer.serialize());

						return {
							definition: definition,
							description: instance.get('description').trim(),
							layout: layout,
							name: instance.get('name').trim(),
							rules: rules
						};
					},

					isEmpty: function() {
						var instance = this;

						var state = instance.getState();

						var definition = state.definition;

						var fields = definition.fields;

						return fields.length === 0;
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

					serializeFormBuilder: function() {
						var instance = this;

						var state = instance.getState();

						instance.one('#description').val(state.description);

						instance.one('#definition').val(JSON.stringify(state.definition));

						instance.one('#layout').val(JSON.stringify(state.layout));

						instance.one('#name').val(state.name);

						instance.one('#rules').val(state.rules);

						var publishCheckbox = instance.one('#publishCheckbox');

						var settingsDDMForm = Liferay.component('settingsDDMForm');

						var publishedField = settingsDDMForm.getField('published');

						var requireAuthenticationCheckbox = instance.one('#requireAuthenticationCheckbox');

						var requireAuthenticationField = settingsDDMForm.getField('requireAuthentication');

						requireAuthenticationField.setValue(requireAuthenticationCheckbox.attr('checked'));
						publishedField.setValue(instance.get('published'));

						var settings = settingsDDMForm.toJSON();

						var settingsInput = instance.one('#serializedSettingsDDMFormValues');

						settingsInput.val(JSON.stringify(settings));
					},

					submitForm: function() {
						var instance = this;

						if (!instance.get('name').trim()) {
							instance.set('name', Liferay.Language.get('untitled-form'));
						}

						instance.serializeFormBuilder();

						var editForm = instance.get('editForm');

						submitForm(editForm.form);
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

						instance.serializeFormBuilder();

						var state = instance.getState();

						if (!instance.isEmpty()) {
							if (!instance._isSameState(instance.savedState, state)) {
								var editForm = instance.get('editForm');

								var formData = instance._getFormData(A.IO.stringify(editForm.form));

								A.io.request(
									instance.get('autosaveURL'),
									{
										after: {
											success: function() {
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

						var requireAuthenticationCheckbox = instance.one('#requireAuthenticationCheckbox');

						if (requireAuthenticationCheckbox.attr('checked')) {
							formURL = instance.get('restrictedFormURL');
						}
						else {
							formURL = instance.get('sharedFormURL');
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

						return window[instance.ns('descriptionEditor')].getHTML();
					},

					_getFormData: function(formString) {
						var instance = this;

						if (!instance.get('name').trim()) {
							var formObject = A.QueryString.parse(formString);

							formObject[instance.ns('name')] = Liferay.Language.get('untitled-form');

							formString = A.QueryString.stringify(formObject);
						}

						return formString;
					},

					_getName: function() {
						var instance = this;

						return window[instance.ns('nameEditor')].getHTML();
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

						if (!instance._isSameState(instance.getState(), instance.initialState)) {
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

					_onPreviewButtonClick: function() {
						var instance = this;

						instance._autosave(
							function() {
								var previewURL = instance._createPreviewURL();

								window.open(previewURL, '_blank');
							}
						);
					},

					_onPublishIconClick: function() {
						var instance = this;

						if (!instance.get('published')) {
							return;
						}

						var clipboardInput = instance.one('#clipboardEdit');

						clipboardInput.set('value', instance._createFormURL());

						instance._publishPopover.show();
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
										recordSetId: instance.get('recordSetId')
									}
								);

								A.io.request(
									instance.get('publishRecordSetURL'),
									{
										after: {
											success: function() {
												instance.set('published', newPublishedValue);

												if (newPublishedValue) {
													instance._handlePublishAction();
												}
												else {
													instance._handleUnpublishAction();
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

					_handlePublishAction: function() {
						var instance = this;

						var publishMessage = Liferay.Language.get('the-form-was-published-successfully-access-it-with-this-url-x')

						var formUrl = '<span style="font-weight: 500">' + instance._createFormURL() + '</span>';

						publishMessage = publishMessage.replace(/\{0\}/gim, formUrl);

						instance._showAlert(publishMessage, "success");

						instance.one('#publish').html(Liferay.Language.get('unpublish-form'));
					},

					_handleUnpublishAction: function() {
						var instance = this;

						instance._showAlert(Liferay.Language.get('the-form-was-unpublished-successfully'), "success");

						instance.one('#publish').html(Liferay.Language.get('publish-form'));
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

					_setName: function(value) {
						var instance = this;

						window[instance.ns('nameEditor')].setHTML(value);
					},

					_setPublished: function(value) {
						var instance = this;

						if (value) {
							A.one('.publish-icon').removeClass("disabled");
						}
						else {
							A.one('.publish-icon').addClass("disabled");
						}
					},

					_showAlert: function(message, type) {
						var instance = this;

						var alert = instance.get('alert');

						var icon = "exclamation-full";

						if (type === 'success') {
							icon = 'check';
						}

						alert.setAttrs(
							{
								icon: icon,
								message: message,
								type: type
							}
						);

						if (!alert.get('rendered')) {
							alert.render('.management-bar-default .container-fluid-1280');
						}

						alert.show();
					},

					_valueAlert: function() {
						var instance = this;

						return new Liferay.Alert(
							{
								closeable: true
							}
						);
					},

					_valueFormBuilder: function() {
						var instance = this;

						var layout = instance.get('layout');

						return new Liferay.DDL.FormBuilder(
							{
								defaultLanguageId: instance.get('defaultLanguageId'),
								definition: instance.get('definition'),
								evaluatorURL: instance.get('evaluatorURL'),
								getFieldTypeSettingFormContextURL: instance.get('getFieldTypeSettingFormContextURL'),
								pagesJSON: layout.pages,
								portletNamespace: instance.get('namespace'),
								recordSetId: instance.get('recordSetId')
							}
						);
					},

					_valueRuleBuilder: function() {
						var instance = this;

						return new Liferay.DDL.FormBuilderRuleBuilder(
							{
								formBuilder: instance.get('formBuilder'),
								functionsMetadata: instance.get('functionsMetadata'),
								getDataProviderInstancesURL: instance.get('getDataProviderInstancesURL'),
								getDataProviderParametersSettingsURL: instance.get('getDataProviderParametersSettingsURL'),
								getFunctionsURL: instance.get('getFunctionsURL'),
								getRolesURL: instance.get('getRolesURL'),
								portletNamespace: instance.get('namespace'),
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
		requires: ['aui-popover', 'aui-tooltip', 'io-base', 'event-outside', 'liferay-ddl-form-builder', 'liferay-ddl-form-builder-definition-serializer', 'liferay-ddl-form-builder-layout-serializer', 'liferay-ddl-form-builder-rule-builder', 'liferay-portlet-base', 'liferay-util-window', 'querystring-parse']
	}
);
AUI.add(
	'liferay-ddl-portlet',
	function(A) {
		var DefinitionSerializer = Liferay.DDL.DefinitionSerializer;
		var LayoutSerializer = Liferay.DDL.LayoutSerializer;

		var TPL_BUTTON_SPINNER = '<span aria-hidden="true"><span class="icon-spinner icon-spin"></span></span>';

		var isNode = function(node) {
			return node && (node._node || node.nodeType);
		};

		var DDLPortlet = A.Component.create(
			{
				ATTRS: {
					dataProviders: {
					},

					definition: {
					},

					description: {
						getter: '_getDescription',
						value: ''
					},

					editForm: {
					},

					formBuilder: {
						valueFn: '_valueFormBuilder'
					},

					layout: {
					},

					name: {
						getter: '_getName',
						value: ''
					},

					publishRecordSetURL: {
					},

					recordSetId: {
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-portlet',

				openDDMDataProvider: function(dataProviderURL) {
					Liferay.Util.openWindow(
						{
							dialog: {
								cssClass: 'dynamic-data-mapping-data-providers-modal',
								destroyOnHide: true
							},
							id: 'ddmDataProvider',
							title: Liferay.Language.get('data-providers'),
							uri: dataProviderURL
						}
					);
				},

				prototype: {
					initializer: function() {
						var instance = this;

						instance.definitionSerializer = new DefinitionSerializer();

						instance.layoutSerializer = new LayoutSerializer(
							{
								builder: instance.get('formBuilder')
							}
						);

						instance.renderUI();

						instance.bindUI();

						instance.initialState = instance.getState();
					},

					renderUI: function() {
						var instance = this;

						instance.one('#loader').remove();

						instance.one('.portlet-forms').removeClass('hide');

						instance.get('formBuilder').render(instance.one('#formBuilder'));
					},

					bindUI: function() {
						var instance = this;

						var editForm = instance.get('editForm');

						editForm.set('onSubmit', A.bind('_onSubmitEditForm', instance));

						instance._eventHandlers = [
							instance.one('#publishCheckbox').on('change', A.bind('_onChangePublishCheckbox', instance)),
							instance.one('.btn-cancel').on('click', A.bind('_onCancel', instance)),
							Liferay.on('destroyPortlet', A.bind('_onDestroyPortlet', instance))
						];
					},

					destructor: function() {
						var instance = this;

						instance.get('formBuilder').destroy();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					getState: function() {
						var instance = this;

						var formBuilder = instance.get('formBuilder');

						var pages = formBuilder.get('layouts');

						instance.definitionSerializer.set('pages', pages);

						var definition = JSON.parse(instance.definitionSerializer.serialize());

						instance.layoutSerializer.set('pages', pages);

						var layout = JSON.parse(instance.layoutSerializer.serialize());

						return {
							definition: definition,
							description: instance.get('description'),
							layout: layout,
							name: instance.get('name')
						};
					},

					openConfirmationModal: function(confirm, cancel) {
						var instance = this;

						var dialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									bodyContent: Liferay.Language.get('are-you-sure-you-want-to-cancel'),
									destroyOnHide: true,
									height: 200,
									resizable: false,
									toolbars: {
										footer: [
											{
												cssClass: 'btn-lg btn-primary',
												label: Liferay.Language.get('yes-cancel'),
												on: {
													click: function() {
														confirm.call(instance, dialog);
													}
												}
											},
											{
												cssClass: 'btn-lg btn-link',
												label: Liferay.Language.get('no-continue'),
												on: {
													click: function() {
														cancel.call(instance, dialog);
													}
												}
											}
										]
									},
									width: 500
								},
								title: Liferay.Language.get('confirm')
							}
						);

						return dialog;
					},

					openPublishModal: function() {
						var instance = this;

						Liferay.Util.openWindow(
							{
								dialog: {
									height: 360,
									resizable: false,
									width: 720
								},
								id: instance.ns('publishModalContainer'),
								title: Liferay.Language.get('publish')
							},
							function(dialogWindow) {
								var publishNode = instance.byId(instance.ns('publishModal'));

								if (publishNode) {
									publishNode.show();

									dialogWindow.bodyNode.append(publishNode);
								}
							}
						);
					},

					serializeFormBuilder: function() {
						var instance = this;

						var state = instance.getState();

						instance.one('#description').val(state.description);

						instance.one('#definition').val(JSON.stringify(state.definition));

						instance.one('#layout').val(JSON.stringify(state.layout));

						instance.one('#name').val(state.name);

						var publishCheckbox = instance.one('#publishCheckbox');

						var settingsDDMForm = Liferay.component('settingsDDMForm');

						var publishedField = settingsDDMForm.getField('published');

						publishedField.setValue(publishCheckbox.attr('checked'));

						var settings = settingsDDMForm.toJSON();

						var settingsInput = instance.one('#serializedSettingsDDMFormValues');

						settingsInput.val(JSON.stringify(settings));
					},

					submitForm: function() {
						var instance = this;

						instance.serializeFormBuilder();

						var submitButton = instance.one('#submit');

						submitButton.html(Liferay.Language.get('saving'));

						submitButton.append(TPL_BUTTON_SPINNER);

						var editForm = instance.get('editForm');

						submitForm(editForm.form);
					},

					_getDescription: function(value) {
						var instance = this;

						var editor = window[instance.ns('descriptionEditor')];

						if (editor && !isNode(editor)) {
							value = editor.getHTML();
						}

						return value;
					},

					_getName: function(value) {
						var instance = this;

						var editor = window[instance.ns('nameEditor')];

						if (editor && !isNode(editor)) {
							value = editor.getHTML();
						}

						return value;
					},

					_isSameState: function() {
						var instance = this;

						return AUI._.isEqual(
							instance.getState(),
							instance.initialState,
							function(value1, value2, key) {
								return (key === 'instanceId') || undefined;
							}
						);
					},

					_onCancel: function(event) {
						var instance = this;

						if (!instance._isSameState()) {
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

					_onChangePublishCheckbox: function(event) {
						var instance = this;

						var publishCheckbox = event.currentTarget;

						var payload = instance.ns(
							{
								published: publishCheckbox.attr('checked'),
								recordSetId: instance.get('recordSetId')
							}
						);

						A.io.request(
							instance.get('publishRecordSetURL'),
							{
								data: payload,
								dataType: 'JSON',
								method: 'POST'
							}
						);
					},

					_onDestroyPortlet: function(event) {
						var instance = this;

						instance.destroy();
					},

					_onSubmitEditForm: function(event) {
						var instance = this;

						event.preventDefault();

						instance.serializeFormBuilder();

						instance.submitForm();
					},

					_valueFormBuilder: function() {
						var instance = this;

						var layout = instance.get('layout');

						return new Liferay.DDL.FormBuilder(
							{
								dataProviders: instance.get('dataProviders'),
								definition: instance.get('definition'),
								pagesJSON: layout.pages,
								portletNamespace: instance.get('namespace')
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
		requires: ['liferay-ddl-form-builder', 'liferay-ddl-form-builder-definition-serializer', 'liferay-ddl-form-builder-layout-serializer', 'liferay-portlet-base', 'liferay-util-window']
	}
);
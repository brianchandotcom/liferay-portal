AUI.add(
	'liferay-ddl-portlet',
	function(A) {
		var DefinitionSerializer = Liferay.DDL.DefinitionSerializer;
		var LayoutSerializer = Liferay.DDL.LayoutSerializer;

		var TPL_BUTTON_SPINNER = '<span aria-hidden="true"><span class="icon-spinner icon-spin"></span></span>';

		var DDLPortlet = A.Component.create(
			{
				ATTRS: {
					dataProviders: {
					},

					definition: {
					},

					editForm: {
					},

					formBuilder: {
						valueFn: '_valueFormBuilder'
					},

					layout: {
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

						instance.get('formBuilder').render(instance.one('#formBuilder'));
					},

					bindUI: function() {
						var instance = this;

						var editForm = instance.get('editForm');

						editForm.set('onSubmit', A.bind('_onSubmitEditForm', instance));

						instance._eventHandlers = [
							instance.one('#publishCheckbox').on('change', A.bind('_onChangePublishCheckbox', instance)),
							Liferay.on('destroyPortlet', A.bind('_onDestroyPortlet', instance)),
							instance.one('.btn-cancel').on('click', A.bind('_onCancel', instance))
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
							description: window[instance.ns('descriptionEditor')].getHTML(),
							layout: layout.pages,
							name: window[instance.ns('nameEditor')].getHTML()
						}
					},

					openConfirmDialog: function(params) {
						var message = params.message || Liferay.Language.get('are-you-sure');

						var title = params.title || Liferay.Language.get('confirm');

						var confirm = params.confirm || A.Lang.emptyFn;

						var confirmLabel = params.confirmLabel || Liferay.Language.get('confirm');

						var cancel = params.cancel || A.Lang.emptyFn;

						var cancelLabel = params.cancelLabel || Liferay.Language.get('cancel');

						var dialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									bodyContent: message,
									destroyOnHide: true,
									height: 200,
									toolbars: {
										footer: [
											{
												cssClass: 'btn-primary',
												label: confirmLabel,
												on: {
													click: function() {
														confirm();

														dialog.hide();
													}
												}
											},
											{
												label: cancelLabel,
												on: {
													click: function() {
														cancel();

														dialog.hide();
													}
												}
											}
										]
									},
									width: 300
								},
								title: title
							}
						);

						dialog.render().show();
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

						var description = window[instance.ns('descriptionEditor')].getHTML();

						instance.one('#description').val(description);

						var formBuilder = instance.get('formBuilder');

						var pages = formBuilder.get('layouts');

						var definitionInput = instance.one('#definition');

						instance.definitionSerializer.set('pages', pages);

						definitionInput.val(instance.definitionSerializer.serialize());

						var layoutInput = instance.one('#layout');

						instance.layoutSerializer.set('pages', pages);

						layoutInput.val(instance.layoutSerializer.serialize());

						var name = window[instance.ns('nameEditor')].getHTML();

						instance.one('#name').val(name);

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

					_onCancel: function(event) {
						var instance = this;

						var currentState = instance.getState();

						if (!_.isEqual(currentState, instance.initialState)) {
							var url = event.currentTarget.get('href');

							event.preventDefault();
							event.stopPropagation();

							instance.openConfirmDialog(
								{
									message: Liferay.Language.get('are-you-sure-you-want-to-cancel'),
									cancelLabel: Liferay.Language.get('no-keep'),
									confirm: function() {
										window.location.href = url;
									},
									confirmLabel: Liferay.Language.get('yes-cancel'),
									title: Liferay.Language.get('confirm')
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
		requires: ['liferay-ddl-form-builder', 'liferay-ddl-form-builder-definition-serializer', 'liferay-ddl-form-builder-layout-serializer', 'liferay-portlet-base']
	}
);
AUI.add(
	'liferay-ddl-portlet',
	function(A) {
		var DefinitionSerializer = Liferay.DDL.DefinitionSerializer;
		var Lang = A.Lang;
		var LayoutSerializer = Liferay.DDL.LayoutSerializer;

		var DDLPortlet = A.Component.create(
			{
				ATTRS: {
					editForm: {
					},

					definition: {
					},

					layout: {
					},

					formBuilder: {
						valueFn: '_valueFormBuilder'
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-ddl-portlet',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.definitionSerializer = new DefinitionSerializer();

						instance.layoutSerializer = new LayoutSerializer(
							{
								builder: instance.get('formBuilder')
							}
						);

						instance.bindUI();
						instance.renderUI();
					},

					bindUI: function() {
						var instance = this;

						var editForm = instance.get('editForm');

						editForm.set('onSubmit', A.bind('_onSubmitEditForm', instance));

						instance._eventHandlers = [
							Liferay.on('destroyPortlet', A.bind('_onDestroyPortlet', instance))
						];
					},

					renderUI: function() {
						var instance = this;

						instance.get('formBuilder').render(instance.one('#formBuilder'));
					},

					destructor: function() {
						var instance = this;

						instance.get('formBuilder').destroy();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					_onDestroyPortlet: function(event) {
						var instance = this;

						instance.destroy();
					},

					_onSubmitEditForm: function(event) {
						var instance = this;

						var description = window[instance.ns('descriptionEditor')].getHTML();

						instance.one('#description').val(description);

						var formBuilder = instance.get('formBuilder');

						var layouts = formBuilder.get('layouts');

						var definitionInput = instance.one('#definition');

						instance.definitionSerializer.set('layouts', layouts);

						definitionInput.val(instance.definitionSerializer.serialize());

						var layoutInput = instance.one('#layout');

						instance.layoutSerializer.set('layouts', layouts);

						layoutInput.val(instance.layoutSerializer.serialize());

						var name = window[instance.ns('nameEditor')].getHTML();

						instance.one('#name').val(name);
					},

					_valueFormBuilder: function() {
						var instance = this;

						return new Liferay.DDL.FormBuilder(
							{
								definition: instance.get('definition'),
								layouts: instance.get('layout')
							}
						)
					}
				}
			}
		);

		Liferay.namespace('DDL').Portlet = DDLPortlet;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder', 'liferay-ddl-form-builder-definition-serializer', 'liferay-ddl-form-builder-layout-serializer', 'liferay-form', 'liferay-portlet-base']
	}
);
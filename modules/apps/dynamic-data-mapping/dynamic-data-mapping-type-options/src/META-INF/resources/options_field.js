AUI.add(
	'liferay-ddm-form-field-options',
	function(A) {
		var OptionsField = A.Component.create(
			{
				ATTRS: {
					type: {
						value: 'options'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-options',

				prototype: {
					getContextValue: function() {
						var instance = this;

						return A.map(
							instance.get('value'),
							function(item) {
								var label = item.label && item.label[instance.get('locale')] || '';

								return {
									label: label,
									value: item.value
								};
							}
						);
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							OptionsField.superclass.getTemplateContext.apply(instance, arguments),
							{
								value: instance.getContextValue()
							}
						);
					},

					getValue: function() {
						var instance = this;

						return instance.serializeAutoFields();
					},

					render: function() {
						var instance = this;

						OptionsField.superclass.render.apply(instance, arguments);

						var container = instance.get('container');

						instance.autoFields = new Liferay.AutoFields(
							{
								contentBox: container.one('.auto-fields'),
								fieldIndexes: instance.getQualifiedName(),
								namespace: instance.get('portletNamespace'),
								sortable: true,
								sortableHandle: '.ddm-options-row'
							}
						).render();

						return instance;
					},

					serializeAutoFields: function() {
						var instance = this;

						var autoFields = instance.autoFields;

						var visibleRows = autoFields._contentBox.all('.lfr-form-row').each(autoFields._clearHiddenRows, autoFields);

						var serializedData = [];

						visibleRows.each(
							function(item) {
								var labelField = item.one('.ddm-options-field-label');

								var valueField = item.one('.ddm-options-field-value');

								var label = {};

								label[instance.get('locale')] = labelField.val();

								serializedData.push(
									{
										label: label,
										value: valueField.val()
									}
								);
							}
						);

						return serializedData;
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Options = OptionsField;
	},
	'',
	{
		requires: ['liferay-auto-fields', 'liferay-ddm-form-renderer-field']
	}
);
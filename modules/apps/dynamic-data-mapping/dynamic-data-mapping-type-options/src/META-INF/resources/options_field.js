AUI.add(
	'liferay-ddm-form-field-options',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var OptionsField = A.Component.create(
			{
				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-options',

				prototype: {
					renderUI: function() {
						var instance = this;

						OptionsField.superclass.renderUI.apply(instance, arguments);

						var container = instance.get('container');

						instance.autoFields = new Liferay.AutoFields(
							{
								contentBox: container.one('.auto-fields'),
								fieldIndexes: instance.getQualifiedName(),
								namespace: instance.get('portletNamespace'),
								sortable: true
							}
						).render();
					},

					getContextValue: function() {
						var instance = this;

						return _.map(
							instance.get('value'),
							function(item) {
								return {
									label: item.label[instance.get('locale')],
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

					serializeAutoFields: function() {
						var instance = this;

						var autoFields = instance.autoFields;

						var visibleRows = autoFields._contentBox.all('.lfr-form-row').each(autoFields._clearHiddenRows, autoFields);

						var serializedData = [];

						visibleRows.each(
							function(item) {
								var labelField = item.one('.ddm-options-field-label');

								var valueField = item.one('.ddm-options-field-value');

								serializedData.push(
									{
										label: {
											en_US: labelField.val()
										},
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
		requires: ['liferay-ddm-form-renderer-field', 'liferay-auto-fields']
	}
);
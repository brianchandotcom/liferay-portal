AUI.add(
	'liferay-ddm-form-field-radio',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var RadioField = A.Component.create(
			{
				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-radio',

				prototype: {
					getOptions: function() {
						var instance = this;

						return _.map(
							instance.get('definition').options,
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
								RadioField.superclass.getTemplateContext.apply(instance, arguments),
							{
								options: instance.getOptions()
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Radio = RadioField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);
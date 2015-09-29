AUI.add(
	'liferay-ddm-form-field-text',
	function(A) {
		var Lang = A.Lang;

		var TextField = A.Component.create(
			{
				ATTRS: {
					displayStyle: {
						value: 'singleline'
					},

					placeholder: {
						value: ''
					},

					tooltip: {
						valueFn: '_valueTooltip'
					},

					type: {
						value: 'text'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-text',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							TextField.superclass.getTemplateContext.apply(instance, arguments),
							{
								displayStyle: instance.get('displayStyle'),
								placeholder: instance._getLocalizedValue(instance.get('placeholder')),
								tip: instance._getLocalizedValue(instance.get('tip'))
							}
						);
					},

					render: function() {
						var instance = this;

						TextField.superclass.render.apply(instance, arguments);

						var container = instance.get('container');

						var helpIcon = container.one('.help-icon');

						if (helpIcon) {
							var tooltip = instance.get('tooltip');

							tooltip.set('trigger', helpIcon);
						}
					},

					_getLocalizedValue: function(localizedValue) {
						var instance = this;

						if (Lang.isObject(localizedValue)) {
							localizedValue = localizedValue[instance.get('locale')];
						}

						return localizedValue;
					},

					_renderErrorMessage: function() {
						var instance = this;

						TextField.superclass._renderErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-group-default');

						if (inputGroup) {
							inputGroup.placeAfter(container.one('.validation-message'));
						}
					},

					_showFeedback: function() {
						var instance = this;

						TextField.superclass._showFeedback.apply(instance, arguments);

						var container = instance.get('container');

						var feedBack = container.one('.form-control-feedback');

						var inputGroupAddOn = container.one('.input-group-addon');

						if (inputGroupAddOn) {
							feedBack.appendTo(inputGroupAddOn);
						}
						else {
							var inputGroupContainer = container.one('.input-group-container');

							inputGroupContainer.placeAfter(feedBack);
						}
					},

					_valueTooltip: function() {
						var instance = this;

						return new A.Tooltip(
							{
								position: 'left',
								visible: false
							}
						).render();
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Text = TextField;
	},
	'',
	{
		requires: ['aui-tooltip', 'liferay-ddm-form-renderer-field']
	}
);
AUI.add(
	'liferay-ddm-form-field-password',
	function(A) {
		new A.TooltipDelegate(
			{
				cssClass: 'clay-tooltip',
				opacity: 1,
				position: 'right',
				trigger: '.liferay-ddm-form-field-password .trigger-tooltip',
				triggerHideEvent: ['blur', 'mouseleave'],
				triggerShowEvent: ['focus', 'mouseover'],
				visible: false
			}
		);

		var PasswordField = A.Component.create(
			{
				ATTRS: {
					type: {
						value: 'password'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-password',

				prototype: {
				}
			}
		);

		Liferay.namespace('DDM.Field').Password = PasswordField;
	},
	'',
	{
		requires: ['aui-tooltip', 'liferay-ddm-form-renderer-field']
	}
);
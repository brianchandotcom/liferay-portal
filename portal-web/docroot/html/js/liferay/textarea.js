AUI.add(
	'liferay-textarea',
	function(A) {
		var Lang = A.Lang,

			NAME = 'liferay-textarea',

			CSS_FIELD_INPUT = A.getClassName(NAME, 'input');

		var Textarea = A.Component.create(
			{
				AUGMENTS: [A.TextareaBase],

				NAME: NAME,

				ATTRS: {
					node: {
						setter: function(value) {
							var instance = this;

							return A.one(value) || instance._createFieldNode();
						}
					}
				},

				prototype: {
					_createFieldNode: function() {
						var instance = this;

						var fieldTemplate = instance.FIELD_TEMPLATE;

						instance.FIELD_TEMPLATE = Lang.sub(
							fieldTemplate,
							{
								cssClass: CSS_FIELD_INPUT,
								id: instance.get('id'),
								name: instance.get('name'),
								type: instance.get('type')
							}
						);

						return A.Node.create(instance.FIELD_TEMPLATE);
					}
				}
			}
		);

		Liferay.Textarea = Textarea;
	},
	'',
	{
		requires: ['aui-form-textareabase']
	}
);
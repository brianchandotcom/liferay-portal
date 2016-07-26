AUI.add(
	'liferay-ddm-form-field-text',
	function(A) {
		new A.TooltipDelegate(
			{
				position: 'left',
				trigger: '.liferay-ddm-form-field-text .help-icon',
				triggerHideEvent: ['blur', 'mouseleave'],
				triggerShowEvent: ['focus', 'mouseover'],
				visible: false
			}
		);

		var TextField = A.Component.create(
			{
				ATTRS: {
					displayStyle: {
						value: 'singleline'
					},

					type: {
						value: 'text'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-text',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.bindInputEvent('focus', instance._onFocusInput);
					},

					destructor: function() {
						var instance = this;

						if (instance.get('displayStyle') === 'multiline') {
							var textAreaNode = instance.getInputNode();

							if (textAreaNode.autosize) {
								textAreaNode.autosize.destroy();
							}
						}
					},

					bindInputEvent: function(eventName, callback, volatile) {
						var instance = this;

						if (eventName === instance.getChangeEventName()) {
							callback = A.debounce(callback, 300, instance);
						}

						return TextField.superclass.bindInputEvent.apply(instance, [eventName, callback, volatile]);
					},

					getChangeEventName: function() {
						return 'input';
					},

					showErrorMessage: function() {
						var instance = this;

						TextField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-group-container');

						inputGroup.insert(container.one('.help-block'), 'after');
					},

					_onFocusInput: function() {
						var instance = this;

						if (instance.get('displayStyle') === 'multiline') {
							var textAreaNode = instance.getInputNode();

							if (!textAreaNode.autosize) {
								textAreaNode.plug(A.Plugin.Autosize);
								textAreaNode.height(textAreaNode.get('scrollHeight'));
							}

							textAreaNode.autosize._uiAutoSize();
						}
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Text = TextField;
	},
	'',
	{
		requires: ['aui-autosize-deprecated', 'aui-tooltip', 'liferay-ddm-form-renderer-field']
	}
);
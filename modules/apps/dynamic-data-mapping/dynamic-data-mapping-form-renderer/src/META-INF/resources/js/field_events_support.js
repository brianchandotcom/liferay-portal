AUI.add(
	'liferay-ddm-form-renderer-field-events',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var FieldEventsSupport = function() {
		};

		FieldEventsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after('containerChange', instance._bindEvents)
				);

				instance._bindEvents();
			},

			_bindEvents: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance._bindOnBlur(),
					instance._bindOnChange()
				);
			},

			_bindOnBlur: function() {
				var instance = this;

				var container = instance.get('container');

				return container.delegate('blur', A.bind('_onInputBlur', instance), instance.getInputSelector());
			},

			_bindOnChange: function() {
				var instance = this;

				var container = instance.get('container');

				return container.delegate('change', A.bind('_onInputChange', instance), instance.getInputSelector());
			},

			_onInputBlur: function(event) {
				var instance = this;

				instance.fire(
					'blur',
					{
						domEvent: event,
						field: instance
					}
				);
			},

			_onInputChange: function(event) {
				var instance = this;

				instance.fire(
					'valueChanged',
					{
						domEvent: event,
						field: instance,
						value: instance.getValue()
					}
				);
			}
		};

		Liferay.namespace('DDM.Renderer').FieldEventsSupport = FieldEventsSupport;
	},
	'',
	{
		requires: []
	}
);
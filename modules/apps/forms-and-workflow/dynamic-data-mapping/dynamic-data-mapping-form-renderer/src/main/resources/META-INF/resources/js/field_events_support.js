AUI.add(
	'liferay-ddm-form-renderer-field-events',
	function(A) {
		var FieldEventsSupport = function() {
		};

		FieldEventsSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.after(instance._afterEventsRender, instance, 'render')
				);

				instance._domEvents = [];
			},

			bindContainerEvent: function(eventName, callback, selector, volatile) {
				var instance = this;

				var container = instance.get('container');

				var query = selector;

				if (query.call) {
					query = query.call(instance);
				}

				var handler = container.delegate(eventName, A.bind(callback, instance), query);

				instance._domEvents.push(
					{
						callback: callback,
						handler: handler,
						name: eventName,
						selector: selector,
						volatile: volatile
					}
				);

				return handler;
			},

			bindInputEvent: function(eventName, callback, volatile) {
				var instance = this;

				return instance.bindContainerEvent(eventName, callback, instance.getInputSelector, volatile);
			},

			getChangeEventName: function() {
				return 'change';
			},

			_afterEventsRender: function() {
				var instance = this;

				var events = instance._domEvents;

				instance._domEvents = [];

				var length = events.length;

				while (length--) {
					var event = events[length];

					event.handler.detach();

					if (!event.volatile) {
						instance.bindContainerEvent(event.name, event.callback, event.selector);
					}
				}

				instance._bindDefaultEvents();
			},

			_bindDefaultEvents: function() {
				var instance = this;

				instance.bindInputEvent('blur', instance._onInputBlur, true);
				instance.bindInputEvent('focus', instance._onInputFocus);
				instance.bindInputEvent(instance.getChangeEventName(), instance._onValueChange, true);
			},

			_getEventPayload: function(originalEvent) {
				var instance = this;

				return {
					field: instance,
					originalEvent: originalEvent
				};
			},

			_onInputBlur: function(event) {
				var instance = this;

				instance.fire('blur', instance._getEventPayload(event));
			},

			_onInputFocus: function(event) {
				var instance = this;

				instance.fire('focus', instance._getEventPayload(event));
			},

			_onValueChange: function(event) {
				var instance = this;

				instance.set('value', instance.getValue());
			}
		};

		Liferay.namespace('DDM.Renderer').FieldEventsSupport = FieldEventsSupport;
	},
	'',
	{
		requires: []
	}
);
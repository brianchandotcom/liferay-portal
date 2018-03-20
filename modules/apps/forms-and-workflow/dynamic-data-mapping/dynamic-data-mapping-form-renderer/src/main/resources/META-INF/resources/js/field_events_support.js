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

			_fireStartedFillingEvent: function() {
				var instance = this;

				if (!instance.get('startedFilling')) {
					instance.set('startedFilling', true);

					var root = instance.getRoot();

					if (root) {
						Liferay.fire("ddmFieldStartedFilling", {
							fieldName: instance.get("fieldName"),
							formId: root.getFormId(),
							page: root.getCurrentPage() || 1
						});
					}
				}
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

				var root = instance.getRoot();

				if (root) {
					var now = new Date();

					Liferay.fire("ddmFieldBlur", {
						fieldName: instance.get("fieldName"),
						focusDuration: (now - (instance.get('fieldFocusDate') || now)),
						formId: root.getFormId(),
						page: root.getCurrentPage() || 1
					});
				}
			},

			_onInputFocus: function(event) {
				var instance = this;

				instance.fire('focus', instance._getEventPayload(event));

				var root = instance.getRoot();

				if (root) {
					instance.set('fieldFocusDate', new Date());

					Liferay.fire("ddmFieldFocus", {
						fieldName: instance.get("fieldName"),
						formId: root.getFormId(),
						page: root.getCurrentPage() || 1
					});
				}
			},

			_onValueChange: function(event) {
				var instance = this;

				var value = instance.getValue();

				instance.set('value', value);

				instance._fireStartedFillingEvent();
			}
		};

		Liferay.namespace('DDM.Renderer').FieldEventsSupport = FieldEventsSupport;
	},
	'',
	{
		requires: []
	}
);
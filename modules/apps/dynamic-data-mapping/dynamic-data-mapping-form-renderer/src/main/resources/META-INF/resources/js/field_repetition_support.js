AUI.add(
	'liferay-ddm-form-renderer-field-repetition',
	function(A) {
		var AObject = A.Object;

		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

		var FieldTypes = Renderer.FieldTypes;

		var SELECTOR_REPEAT_BUTTONS = '.lfr-ddm-form-field-repeatable-add-button, .lfr-ddm-form-field-repeatable-delete-button';

		var TPL_REPEATABLE_ADD = '<a class="icon-plus-sign lfr-ddm-form-field-repeatable-add-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_DELETE = '<a class="hide icon-minus-sign lfr-ddm-form-field-repeatable-delete-button" href="javascript:;"></a>';

		var TPL_REPEATABLE_TOOLBAR = '<div class="lfr-ddm-form-field-repeatable-toolbar">' + TPL_REPEATABLE_DELETE + TPL_REPEATABLE_ADD + '</div>';

		var FieldRepetitionSupport = function() {
		};

		FieldRepetitionSupport.ATTRS = {
			repeatable: {
				setter: A.DataType.Boolean.parse,
				value: false
			},

			repeatedIndex: {
				state: true,
				value: 0
			},

			repetitions: {
				valueFn: '_valueRepetitions'
			}
		};

		FieldRepetitionSupport.prototype = {
			initializer: function() {
				var instance = this;

				if (instance.get('repeatable')) {
					instance._eventHandlers.push(
						instance.after('render', instance._afterRepeatableFieldRender)
					);
				}
			},

			destructor: function() {
				var instance = this;

				instance._removeCurrentFieldFromRepetitionList();

				instance._syncOtherRepeatableFields();
			},

			copy: function() {
				var instance = this;

				var config = instance.copyConfiguration();

				var fieldClass = instance.getFieldClass();

				return new fieldClass(config);
			},

			copyConfiguration: function() {
				var instance = this;

				var context = instance.get('context');

				var repetitions = instance.get('repetitions');

				var config = A.merge(
					context,
					{
						context: A.clone(context),
						enableEvaluations: instance.get('enableEvaluations'),
						fieldName: instance.get('fieldName'),
						parent: instance.get('parent'),
						portletNamespace: instance.get('portletNamespace'),
						repeatable: instance.get('repeatable'),
						repeatedIndex: repetitions.length,
						repetitions: repetitions,
						type: instance.get('type'),
						visible: instance.get('visible')
					}
				);

				var newInstanceId = Util.generateInstanceId(8);
				var oldInstanceId = config.instanceId;

				instance._updateInstanceIdConfiguration(config, newInstanceId);
				instance._updateNameConfiguration(config, oldInstanceId, newInstanceId);
				instance._updateValueConfiguration(config);

				return config;
			},

			getFieldClass: function() {
				var instance = this;

				var type = instance.get('type');

				var fieldType = FieldTypes.get(type);

				var fieldClassName = fieldType.get('className');

				return AObject.getValue(window, fieldClassName.split('.'));
			},

			getRepeatedSiblings: function() {
				var instance = this;

				return instance.get('repetitions');
			},

			remove: function() {
				var instance = this;

				instance.destroy();
			},

			renderRepeatable: function() {
				var instance = this;

				instance.renderRepeatableUI();

				instance.syncRepeatablelUI();
			},

			renderRepeatableUI: function() {
				var instance = this;

				var container = instance.get('container');

				if (!instance.get('readOnly')) {
					container.append(TPL_REPEATABLE_TOOLBAR);
				}
			},

			repeat: function() {
				var instance = this;

				var copiedField = instance.copy();

				var repetitions = instance.getRepeatedSiblings();

				var index = repetitions.indexOf(instance) + 1;

				copiedField.set('repeatedIndex', index);

				repetitions.splice(index, 0, copiedField);

				var container = instance.get('container');

				container.insert(copiedField.get('container'), 'after');

				copiedField.render();

				repetitions.filter(
					function(repetition, currentIndex) {
						return currentIndex > index;
					}
				).forEach(A.bind('_syncRepeatableField', instance));

				return copiedField;
			},

			syncRepeatablelUI: function() {
				var instance = this;

				if (!instance.get('readOnly')) {
					var container = instance.get('container');

					container.one('.lfr-ddm-form-field-repeatable-delete-button').toggle(instance.get('repeatedIndex') > 0);
				}
			},

			_afterRepeatableFieldRender: function() {
				var instance = this;

				var container = instance.get('container');

				instance.renderRepeatable();

				if (!instance.get('readOnly')) {
					(new A.EventHandle(instance._DOMEventHandlers)).detach();

					instance._DOMEventHandlers = [
						container.delegate('click', instance._handleToolbarClick, SELECTOR_REPEAT_BUTTONS, instance)
					];
				}
			},

			_handleToolbarClick: function(event) {
				var instance = this;

				var currentTarget = event.currentTarget;

				if (currentTarget.hasClass('lfr-ddm-form-field-repeatable-delete-button')) {
					instance.remove();
				}
				else {
					instance.repeat();
				}

				event.stopPropagation();
			},

			_removeCurrentFieldFromRepetitionList: function() {
				var instance = this;

				var repetitions = instance.get('repetitions');

				var index = repetitions.indexOf(instance);

				if (index > -1) {
					repetitions.splice(index, 1);
				}
			},

			_syncOtherRepeatableFields: function() {
				var instance = this;

				var repetitions = instance.get('repetitions');

				repetitions.forEach(A.bind('_syncRepeatableField', instance));
			},

			_syncRepeatableField: function(field) {
				var instance = this;

				if (field.get('rendered')) {
					var repeatedSiblings = instance.getRepeatedSiblings();

					var value = field.getValue();

					field.set('repeatedIndex', repeatedSiblings.indexOf(field));
					field.set('repetitions', repeatedSiblings);

					field.render();

					field.setValue(value);
				}
			},

			_updateInstanceIdConfiguration: function(config, newInstanceId) {
				var instance = this;

				config.instanceId = config.context.instanceId = newInstanceId;
			},

			_updateNameConfiguration: function(config, oldInstanceId, newInstanceId) {
				var instance = this;

				var name = config.name;

				if (name) {
					config.name = config.context.name = name.replace(oldInstanceId, newInstanceId);
				}
			},

			_updateValueConfiguration: function(config) {
				var instance = this;

				var value = instance.getValue();

				if (Lang.isArray(value)) {
					value = [];
				}
				else if (Lang.isObject(value)) {
					value = {};
				}
				else {
					value = '';
				}

				config.value = config.context.value = value;
			},

			_valueRepetitions: function() {
				var instance = this;

				return [instance];
			}
		};

		Liferay.namespace('DDM.Renderer').FieldRepetitionSupport = FieldRepetitionSupport;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-types', 'liferay-ddm-form-renderer-util']
	}
);
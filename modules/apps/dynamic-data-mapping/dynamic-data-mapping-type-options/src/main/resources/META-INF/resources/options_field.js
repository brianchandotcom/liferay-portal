AUI.add(
	'liferay-ddm-form-field-options',
	function(A) {
		var AArray = A.Array;

		var TPL_DRAG_HANDLE = '<div class="drag-handle icon-reorder"><span aria-hidden="true"></span></div>';

		var TPL_DRAG_HELPER = '<div class="drag-helper"></div>';

		var TPL_DRAG_PLACEHOLDER = '<div class="drag-placeholder"></div>';

		var TPL_REMOVE_BUTTON = '<button class="close close-modal" type="button"><span aria-hidden="true">×</span></button>';

		var OptionsField = A.Component.create(
			{
				ATTRS: {
					sortableList: {
						valueFn: '_valueSortableList'
					},

					strings: {
						value: {
							addAnOption: Liferay.Language.get('add-an-option')
						}
					},

					type: {
						value: 'options'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-options',

				prototype: {
					initializer: function() {
						var instance = this;

						var sortableList = instance.get('sortableList');

						instance._eventHandlers.push(
							sortableList.after('drag:end', A.bind('_afterSortableListDragEnd', instance)),
							sortableList.after('drag:start', A.bind('_afterSortableListDragStart', instance))
						);

						instance._createMainField();
					},

					addField: function() {
						var instance = this;

						var lastField = instance.getLastField();

						var repeatedField = lastField.repeat();

						instance._bindFieldUI(repeatedField);
						instance._renderFieldUI(repeatedField);
						instance._syncFieldUI(repeatedField);
						instance._syncFieldUI(lastField);

						return repeatedField;
					},

					clearValidationStatus: function() {
						var instance = this;

						instance.eachRepetition(
							function(field) {
								field.clearValidationStatus();
							}
						);

						OptionsField.superclass.clearValidationStatus.apply(instance, arguments);
					},

					eachRepetition: function(fn) {
						var instance = this;

						var field = instance._mainField;

						field.get('repetitions').forEach(A.bind(fn, instance));
					},

					getLastField: function() {
						var instance = this;

						var repetitions = instance._mainField.get('repetitions');

						return repetitions[repetitions.length - 1];
					},

					getOptionsValues: function() {
						var instance = this;

						return A.map(
							instance.get('value'),
							function(item) {
								var label = instance.getLocalizedValue(item.label) || '';

								return {
									label: label,
									value: item.value
								};
							}
						);
					},

					getValue: function() {
						var instance = this;

						var values = [];

						instance.eachRepetition(
							function(item) {
								var key = item.get('key');

								var label = {};

								if (key) {
									label[instance.get('locale')] = item.getValue();

									values.push(
										{
											label: label,
											value: key
										}
									);
								}
							}
						);

						return values;
					},

					hasErrors: function() {
						var instance = this;

						var hasErrors = false;

						instance.eachRepetition(
							function(field) {
								if (field.hasErrors()) {
									hasErrors = true;
								}
							}
						);

						return hasErrors;
					},

					hideErrorMessage: function() {
						var instance = this;

						instance.eachRepetition(
							function(field) {
								field.hideErrorMessage();
							}
						);
					},

					moveField: function(field, oldIndex, newIndex) {
						var instance = this;

						var repetitions = field.get('repetitions');

						repetitions.splice(newIndex, 0, repetitions.splice(oldIndex, 1)[0]);

						repetitions.forEach(A.bind('_syncRepeatableField', field));
					},

					processValidation: function() {
						var instance = this;

						var value = instance.getValue();

						if (value.length === 0) {
							instance.showErrorMessage(Liferay.Language.get('please-add-at-least-one-option'));

							instance.showValidationStatus();

							instance._mainField.focus();
						}
						else {
							OptionsField.superclass.processValidation.apply(instance, arguments);
						}
					},

					render: function() {
						var instance = this;

						OptionsField.superclass.render.apply(instance, arguments);

						instance._clearRepetitions();

						instance._renderFields();

						return instance;
					},

					showErrorMessage: function(errorMessage) {
						var instance = this;

						var mainField = instance._mainField;

						mainField.showErrorMessage(errorMessage);
					},

					showValidationStatus: function() {
						var instance = this;

						var hasErrors = instance.hasErrors();

						var mainField = instance._mainField;

						mainField.get('container').toggleClass('has-error', hasErrors);
					},

					_afterSortableListDragEnd: function(event) {
						var instance = this;

						var dragNode = event.target.get('node');

						var dragEndIndex = instance._getNodeIndex(dragNode);

						var dragStartIndex = instance._dragStartIndex;

						if (dragEndIndex !== dragStartIndex) {
							var mainField = instance._mainField;

							var field = AArray.find(
								mainField.get('repetitions'),
								function(item) {
									return item.get('container') === dragNode;
								}
							);

							instance.moveField(field, dragStartIndex, dragEndIndex);
						}
					},

					_afterSortableListDragStart: function(event) {
						var instance = this;

						var dragNode = event.target.get('node');

						instance._dragStartIndex = instance._getNodeIndex(dragNode);

						var sortableList = instance.get('sortableList');

						var placeholderNode = sortableList.get('placeholder');

						placeholderNode.setContent(dragNode.clone().show());
					},

					_bindFieldUI: function(field) {
						var instance = this;

						field.after('render', A.bind('_renderFieldUI', instance, field));

						field.bindContainerEvent('click', A.bind('_onFieldClickClose', instance, field), '.close');
						field.bindInputEvent('valuechange', A.bind('_onFieldValueChange', instance, field));
					},

					_canSortNode: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropNode = event.drop.get('node');

						var lastField = instance.getLastField();
						var lastFieldContainer = lastField.get('container');

						return lastFieldContainer !== dropNode && lastFieldContainer !== dragNode;
					},

					_clearRepetitions: function() {
						var instance = this;

						var mainField = instance._mainField;

						mainField.get('repetitions').forEach(
							function(field) {
								if (field !== mainField) {
									field.remove();
								}
							}
						);

						mainField.set('repetitions', [mainField]);
					},

					_createMainField: function() {
						var instance = this;

						var strings = instance.get('strings');

						instance._mainField = new Liferay.DDM.Field.KeyValue(
							{
								enableEvaluations: false,
								placeholder: strings.addAnOption,
								repeatable: true,
								showLabel: false,
								visibilityExpression: 'true'
							}
						);

						instance._bindFieldUI(instance._mainField);
					},

					_getNodeIndex: function(node) {
						var instance = this;

						var container = instance.get('container');

						var options = container.one('.options');

						var siblings = options.all('> .lfr-ddm-form-field-container');

						return siblings.indexOf(node);
					},

					_onFieldClickClose: function(field) {
						var instance = this;

						if (field === instance._mainField) {
							var repetitions = field.get('repetitions');

							var index = repetitions.indexOf(field);

							instance._mainField = repetitions[index + 1];
						}

						field.remove();
					},

					_onFieldValueChange: function(field) {
						var instance = this;

						var repetitions = field.get('repetitions');

						if (field.get('repeatedIndex') === repetitions.length - 1) {
							var newField = instance.addField();

							newField.get('container').scrollIntoView();
						}
					},

					_renderFields: function() {
						var instance = this;

						var container = instance.get('container');

						var mainField = instance._mainField;

						mainField.render(container.one('.options'));

						instance._syncFieldUI(mainField);

						var optionsValues = instance.getOptionsValues();

						var hasOptionValues = !!optionsValues.length;

						if (hasOptionValues) {
							var optionValue = optionsValues.shift();

							instance._restoreField(mainField, optionValue);
						}

						optionsValues.forEach(
							function(optionValue) {
								var newField = instance.addField();

								instance._restoreField(newField, optionValue);
							}
						);

						if (hasOptionValues) {
							instance.addField();
						}
					},

					_renderFieldUI: function(field) {
						var instance = this;

						var container = field.get('container');

						container.append(TPL_DRAG_HANDLE + TPL_REMOVE_BUTTON);
					},

					_restoreField: function(field, contextValue) {
						field.set('key', contextValue.value);
						field.set('value', contextValue.label);
					},

					_syncFieldUI: function(field) {
						var instance = this;

						var addLastFieldClass = instance.getLastField() === field;

						var container = field.get('container');

						container.toggleClass('last-option', addLastFieldClass);

						var sortableList = instance.get('sortableList');

						sortableList.add(container);
					},

					_valueSortableList: function() {
						var instance = this;

						return new A.SortableList(
							{
								dd: {
									handles: ['.drag-handle']
								},
								helper: A.Node.create(TPL_DRAG_HELPER),
								placeholder: A.Node.create(TPL_DRAG_PLACEHOLDER),
								sortCondition: A.bind('_canSortNode', instance)
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Options = OptionsField;
	},
	'',
	{
		requires: ['aui-sortable-list', 'liferay-ddm-form-field-key-value', 'liferay-ddm-form-renderer-field']
	}
);
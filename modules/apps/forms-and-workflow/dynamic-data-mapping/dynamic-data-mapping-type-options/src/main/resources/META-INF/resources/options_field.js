AUI.add(
	'liferay-ddm-form-field-options',
	function(A) {
		var AArray = A.Array;

		var Renderer = Liferay.DDM.Renderer;

		var Util = Renderer.Util;

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
							addOptionMessage: Liferay.Language.get('enter-an-option')
						}
					},

					type: {
						value: 'options'
					},

					value: {
						repaint: false,
						value: []
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

						instance._createMainOption();
					},

					addOption: function() {
						var instance = this;

						var lastOption = instance.getLastOption();

						var repeatedOption = lastOption.repeat();

						instance._bindOptionUI(repeatedOption);
						instance._renderOptionUI(repeatedOption);
						instance._syncOptionUI(repeatedOption);
						instance._syncOptionUI(lastOption);

						instance.fire('addOption');

						return repeatedOption;
					},

					clearValidationStatus: function() {
						var instance = this;

						instance.eachOption(
							function(option) {
								option.clearValidationStatus();
							}
						);

						OptionsField.superclass.clearValidationStatus.apply(instance, arguments);
					},

					eachOption: function(fn) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.getRepeatedSiblings().forEach(fn, instance);
					},

					empty: function() {
						var instance = this;

						var mainOption = instance._mainOption;

						var options = mainOption.getRepeatedSiblings();

						while (options.length > 1) {
							var option = options[options.length - 1];

							if (option !== mainOption) {
								option.remove();
							}
						}

						mainOption.set('key', '');
					},

					getLastOption: function() {
						var instance = this;

						var repetitions = instance._mainOption.getRepeatedSiblings();

						return repetitions[repetitions.length - 1];
					},

					getValue: function() {
						var instance = this;

						var values = [];

						instance.eachOption(
							function(item) {
								var key = item.get('key');

								if (key) {
									values.push(
										{
											label: item.getValue(),
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

						if (instance.get('visible')) {
							instance.eachOption(
								function(option) {
									if (option.hasErrors()) {
										hasErrors = true;
									}
								}
							);
						}

						return hasErrors;
					},

					hideErrorMessage: function() {
						var instance = this;

						instance.eachOption(
							function(option) {
								option.hideErrorMessage();
							}
						);
					},

					moveOption: function(option, oldIndex, newIndex) {
						var instance = this;

						var repetitions = option.getRepeatedSiblings();

						repetitions.splice(newIndex, 0, repetitions.splice(oldIndex, 1)[0]);

						repetitions.forEach(A.bind('_syncRepeatableField', option));
					},

					processEvaluationContext: function(context) {
						var instance = this;

						var value = instance.getValue();

						if (value.length === 0 && instance.get('required')) {
							context.valid = false;
							context.errorMessage = Liferay.Language.get('please-add-at-least-one-option');
						}

						return context;
					},

					render: function() {
						var instance = this;

						OptionsField.superclass.render.apply(instance, arguments);

						instance._renderOptions(instance.get('value'));

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						if (!Util.compare(value, instance.get('value'))) {
							instance.set('value', value);
							instance._renderOptions(value);
						}
					},

					showErrorMessage: function() {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.showErrorMessage();
					},

					updateContainer: function() {
						var instance = this;

						OptionsField.superclass.updateContainer.apply(instance, arguments);

						instance.eachOption(
							function(option) {
								option.updateContainer();
							}
						);
					},

					_afterErrorMessageChange: function(event) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.set('errorMessage', event.newVal);
					},

					_afterRender: function(option) {
						var instance = this;

						instance._bindListEvents();
						instance._renderOptionUI(option);
					},

					_afterSortableListDragEnd: function(event) {
						var instance = this;

						var dragNode = event.target.get('node');

						var dragEndIndex = instance._getNodeIndex(dragNode);

						var dragStartIndex = instance._dragStartIndex;

						if (dragEndIndex !== dragStartIndex) {
							var mainOption = instance._mainOption;

							var option = AArray.find(
								mainOption.getRepeatedSiblings(),
								function(item) {
									return item.get('container') === dragNode;
								}
							);

							instance.moveOption(option, dragStartIndex, dragEndIndex);
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

					_afterValidChange: function(event) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.set('valid', event.newVal);
					},

					_bindListEvents: function() {
						var instance = this;

						var optionsNode = instance._getOptionsNode();

						instance._eventHandlers.push(
							optionsNode.delegate('focus', A.bind('_onFocusOption', instance), '.last-option .field')
						);
					},

					_bindOptionUI: function(option) {
						var instance = this;

						option.after('render', A.bind('_afterRender', instance, option));

						option.bindContainerEvent('click', A.bind('_onOptionClickClose', instance, option), '.close');
						option.on('valueChanged', A.bind('_onOptionValueChange', instance));
					},

					_canSortNode: function(event) {
						var instance = this;

						var dragNode = event.drag.get('node');
						var dropNode = event.drop.get('node');

						var lastOption = instance.getLastOption();
						var lastOptionContainer = lastOption.get('container');

						return lastOptionContainer !== dropNode && lastOptionContainer !== dragNode;
					},

					_createMainOption: function() {
						var instance = this;

						var strings = instance.get('strings');

						var config = {
							enableEvaluations: false,
							fieldName: instance.get('fieldName') + 'Option',
							placeholder: strings.addOptionMessage,
							repeatable: true,
							repeatedIndex: 0,
							showLabel: false,
							value: '',
							visible: true
						};

						config.context = A.clone(config);

						instance._mainOption = new Liferay.DDM.Field.KeyValue(config);

						instance._bindOptionUI(instance._mainOption);
					},

					_getNodeIndex: function(node) {
						var instance = this;

						var optionsNode = instance._getOptionsNode();

						var siblings = optionsNode.all('> .lfr-ddm-form-field-container');

						return siblings.indexOf(node);
					},

					_getOptionsNode: function() {
						var instance = this;

						var container = instance.get('container');

						return container.one('.options');
					},

					_onFocusOption: function(event) {
						event.target.scrollIntoView();
					},

					_onOptionClickClose: function(option) {
						var instance = this;

						if (option === instance._mainOption) {
							var repetitions = option.getRepeatedSiblings();

							var index = repetitions.indexOf(option);

							instance._mainOption = repetitions[index + 1];
						}

						option.remove();

						instance.fire('removeOption');
					},

					_onOptionValueChange: function(event) {
						var instance = this;

						var option = event.target;

						var repetitions = option.getRepeatedSiblings();

						if (option.get('repeatedIndex') === repetitions.length - 1) {
							instance.addOption();
						}

						var value = instance.getValue();

						if (value.length > 0 && instance.get('required')) {
							instance.set('errorMessage', '');
							instance.set('valid', true);
						}
					},

					_renderOptions: function(optionsValues) {
						var instance = this;

						var container = instance.get('container');

						var mainOption = instance._mainOption;

						instance.empty();

						mainOption.render(container.one('.options'));

						instance._syncOptionUI(mainOption);

						optionsValues.forEach(
							function(optionValue, index) {
								if (index === 0) {
									instance._restoreOption(mainOption, optionValue);
								}
								else {
									var newOption = instance.addOption();

									instance._restoreOption(newOption, optionValue);
								}
							}
						);

						if (optionsValues.length) {
							instance.addOption();
						}
					},

					_renderOptionUI: function(option) {
						var instance = this;

						var container = option.get('container');

						container.append(TPL_DRAG_HANDLE + TPL_REMOVE_BUTTON);
					},

					_restoreOption: function(option, contextValue) {
						option.setValue(contextValue.label);
						option.set('key', contextValue.value);
					},

					_syncOptionUI: function(option) {
						var instance = this;

						var addLastOptionClass = instance.getLastOption() === option;

						var container = option.get('container');

						container.toggleClass('last-option', addLastOptionClass);

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
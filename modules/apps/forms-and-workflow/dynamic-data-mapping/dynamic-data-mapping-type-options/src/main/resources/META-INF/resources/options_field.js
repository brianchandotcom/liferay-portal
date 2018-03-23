AUI.add(
	'liferay-ddm-form-field-options',
	function(A) {
		var AObject = A.Object;

		var TPL_DRAG_HANDLE = '<div class="drag-handle icon-reorder"><span aria-hidden="true"></span></div>';

		var TPL_DRAG_HELPER = '<div class="drag-helper"></div>';

		var TPL_DRAG_PLACEHOLDER = '<div class="drag-placeholder"></div>';

		var TPL_REMOVE_BUTTON = '<button class="close close-modal" type="button"><span aria-hidden="true">×</span></button>';

		var OptionsField = A.Component.create(
			{
				ATTRS: {
					allowEmptyOptions: {
						value: false
					},

					editable: {
						value: true
					},

					sortable: {
						value: true
					},

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
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-options',

				prototype: {
					initializer: function() {
						var instance = this;

						var sortableList = instance.get('sortableList');

						instance._eventHandlers.push(
							instance.on('liferay-ddm-form-field-key-value:destroy', instance._onDestroyOption),
							instance.after('liferay-ddm-form-field-key-value:render', instance._afterRenderOption),
							instance.after('liferay-ddm-form-field-key-value:blur', instance._afterBlur),
							instance.after('liferay-ddm-form-field-key-value:valueChange', instance._afterOptionValueChange),
							instance.after('editableChange', instance._afterEditableChange),
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

						repeatedOption.addTarget(instance);

						instance.fire(
							'addOption',
							{
								option: repeatedOption
							}
						);

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

						instance.getOptions().forEach(fn, instance);
					},

					empty: function() {
						var instance = this;

						var mainOption = instance._mainOption;

						var options = instance.getOptions();

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

						var options = instance.getOptions();

						return instance.getOption(options.length - 1);
					},

					getMainOption: function() {
						var instance = this;

						return instance._mainOption;
					},

					getOption: function(index) {
						var instance = this;

						var options = instance.getOptions();

						return options[index];
					},

					getOptions: function() {
						var instance = this;

						return instance._mainOption.getRepeatedSiblings();
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
											label: item.get('value'),
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

					moveOption: function(oldIndex, newIndex) {
						var instance = this;

						var value = instance.get('value');

						AObject.keys(value).forEach(
							function(languageId) {
								value[languageId].splice(newIndex, 0, value[languageId].splice(oldIndex, 1)[0]);
							}
						);

						instance.setValue(value);
					},

					processEvaluationContext: function(context) {
						var instance = this;

						var value = instance.getValue();

						if (value.length === 0 && instance.get('required')) {
							context.errorMessage = Liferay.Language.get('please-add-at-least-one-option');
							context.valid = false;
						}

						return context;
					},

					removeOption: function(option) {
						var instance = this;

						var options = instance.getOptions();

						var index = options.indexOf(option);

						var value = instance.get('value');

						var optionTextValue = option.get('value');

						optionTextValue = optionTextValue.trim();

						if (optionTextValue.length > 0) {
							AObject.keys(value).forEach(
								function(languageId) {
									value[languageId].splice(index, 1);
								}
							);

							instance.setValue(value);
						}

						instance.fire('removeOption');

						instance.render();

						if (index > 0 && value.length > 0) {
							options[index - 1].focus();
						}
						else {
							instance.getLastOption().focus();
						}
					},

					render: function() {
						var instance = this;

						OptionsField.superclass.render.apply(instance, arguments);

						instance._renderOptions();

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						instance.set('value', value);

						instance._renderOptions();
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

					_afterBlur: function(event) {
						var instance = this;

						var value = instance.getValue();

						if (value.length === 0 || value.length === 1 && value[0].label === '') {
							instance._setValue([]);
						}

					},

					_afterEditableChange: function(event) {
						var instance = this;

						var options = instance.getOptions();

						var editable = event.newVal;

						options.forEach(
							function(option) {
								if (option.getValue()) {
									option.set('generationLocked', !editable);
								}
							}
						);
					},

					_afterErrorMessageChange: function(event) {
						var instance = this;

						var mainOption = instance._mainOption;

						mainOption.set('errorMessage', event.newVal);
					},

					_afterOptionNormalizeKey: function(key, option) {
						var instance = this;

						var name = A.Do.originalRetVal;

						if (name) {
							var valueInItem = function(value, item) {
								return item.value === value && item.value !== option.get('key');
							};

							var optionsValues = instance.getValue();

							var hasOptionWithName = function() {
								return optionsValues.filter(A.bind(valueInItem, null, name)).length > 0;
							};

							var counter = 0;

							do {
								if (counter > 0) {
									name = A.Do.originalRetVal + counter;
								}

								counter++;
							}
							while (hasOptionWithName());
						}

						return new A.Do.AlterReturn(null, name);
					},

					_afterOptionValueChange: function(event) {
						var instance = this;

						if (instance._skipOptionValueChange) {
							return;
						}

						if (event.target === instance.getLastOption()) {
							instance.addOption();
						}

						var value = instance.getValue();

						if (value.length > 0 && instance.get('required')) {
							instance.set('errorMessage', '');
							instance.set('valid', true);
						}

						instance._setValue(value);
					},

					_afterRenderOption: function(event) {
						var instance = this;

						var option = event.target;

						instance._bindListEvents();
						instance._renderOptionUI(option);
					},

					_afterSortableListDragEnd: function(event) {
						var instance = this;

						var dragEndIndex = instance._getNodeIndex(event.target.get('node'));

						var dragStartIndex = instance._dragStartIndex;

						if (dragEndIndex !== dragStartIndex) {

							// Drag doesn't like that we are removing the node right after
							// drag:end. So we postpone it to the next clock cycle.

							A.later(0, instance, instance.moveOption, [dragStartIndex, dragEndIndex]);
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

						option.after(A.rbind('_afterOptionNormalizeKey', instance, option), option, 'normalizeKey');
						option.bindContainerEvent('click', A.bind('_onOptionClickClose', instance, option), '.close');
					},

					_canSortNode: function(event) {
						var instance = this;

						var sortable = instance.get('sortable');

						if (!sortable) {
							return false;
						}

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

						instance._mainOption.addTarget(instance);

						instance._bindOptionUI(instance._mainOption);
					},

					_getCurrentDefaultLanguageId: function() {
						var instance = this;

						var form = instance.get('parent');

						if (!form) {
							return instance.get('locale');
						}
						var builder = form.get('builder');

						return builder.get('defaultLanguageId');
					},

					_getCurrentEditingLanguageId: function() {
						var instance = this;

						var form = instance.get('parent');

						if (!form) {
							return instance.get('locale');
						}
						var builder = form.get('builder');

						return builder.get('editingLanguageId');
					},

					_getCurrentLocaleOptionsValues: function() {
						var instance = this;

						var value = instance.get('value');

						var defaultLanguageId = instance._getCurrentDefaultLanguageId();
						var editingLanguageId = instance._getCurrentEditingLanguageId();

						return value[editingLanguageId] || value[defaultLanguageId] || [];
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

					_onDestroyOption: function(event) {
						var instance = this;

						var option = event.target;

						A.DD.DDM.getDrag(option.get('container')).destroy();
					},

					_onFocusOption: function(event) {
						event.target.scrollIntoView();
					},

					_onOptionClickClose: function(option) {
						var instance = this;

						instance.removeOption(option);
					},

					_renderOptions: function() {
						var instance = this;

						var container = instance.get('container');

						var optionsValues = instance._getCurrentLocaleOptionsValues();

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

						if (optionsValues.length && optionsValues[optionsValues.length - 1].value) {
							instance.addOption();
						}
					},

					_renderOptionUI: function(option) {
						var instance = this;

						var container = option.get('container');

						container.one('input').setAttribute('data-predefined-value', Liferay.Language.get('option'));
						container.one('input').setAttribute('data-interaction', true);

						container.append(TPL_DRAG_HANDLE + TPL_REMOVE_BUTTON);
					},

					_restoreOption: function(option, contextValue) {
						var instance = this;

						instance._skipOptionValueChange = true;

						option.set('value', contextValue.label);
						option.set('key', contextValue.value);

						option.setValue(contextValue.label);

						if (contextValue.value && option.normalizeKey(contextValue.label) !== contextValue.value) {
							option.set('generationLocked', true);
						}
						else {
							option.set('generationLocked', false);
						}

						instance._skipOptionValueChange = false;
					},

					_setContext: function(val) {
						var instance = this;

						var context = OptionsField.superclass._setContext.apply(instance, arguments);

						var locale = instance._getCurrentEditingLanguageId();

						var value = context.value;

						if (value && !value[locale]) {
							value[locale] = value[context.defaultLanguageId];
						}

						return context;
					},

					_setValue: function(optionValues) {
						var instance = this;

						var value = instance.get('value');

						var editingLanguageId = instance._getCurrentEditingLanguageId();

						value[editingLanguageId] = optionValues;

						instance.set('value', value);
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
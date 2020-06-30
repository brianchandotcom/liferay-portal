AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var CSS_ACTIVE = A.getClassName('active');

		var CSS_CUSTOM_CONTROL_INPUT = A.getClassName('custom', 'control', 'input');

		var CSS_DROP_CHOSEN = A.getClassName('drop', 'chosen');

		var CSS_DROP_CHOSEN_SEARCH = A.getClassName('drop', 'chosen', 'search');

		var CSS_FORM_FIELD_CONTAINER = A.getClassName('lfr', 'ddm', 'form', 'field', 'container');

		var CSS_HELP_BLOCK = A.getClassName('help', 'block');

		var CSS_HIDE = A.getClassName('hide');

		var CSS_INPUT_SELECT_WRAPPER = A.getClassName('input', 'select', 'wrapper');

		var CSS_SEARCH_CHOSEN = A.getClassName('drop', 'chosen');

		var CSS_SELECT_LABEL_ITEM_CLOSE = A.getClassName('trigger', 'label', 'item', 'close');

		var CSS_SELECT_OPTION_ITEM = A.getClassName('select', 'option', 'item');

		var CSS_SELECT_TRIGGER_ACTION = A.getClassName('select', 'field', 'trigger');

		var MAX_DROPDOWN_ITEMS = 11;

		var Lang = A.Lang;

		var TPL_OPTION = '<option>{label}</option>';

		var SelectField = A.Component.create(
			{
				ATTRS: {
					dataSourceType: {
						value: 'manual'
					},

					fixedOptions: {
						getter: '_getFixedOptions',
						setter: '_setOptions',
						state: true,
						validator: Array.isArray,
						value: []
					},

					multiple: {
						state: true,
						value: false
					},

					options: {
						getter: '_getOptions',
						setter: '_setOptions',
						state: true,
						validator: Array.isArray,
						value: []
					},

					predefinedValue: {
						state: true,
						validator: Array.isArray,
						value: []
					},

					showPlaceholder: {
						lazyAdd: false,
						value: true
					},

					strings: {
						value: {
							chooseAnOption: Liferay.Language.get('choose-an-option'),
							chooseOptions: Liferay.Language.get('choose-options'),
							dynamicallyLoadedData: Liferay.Language.get('dynamically-loaded-data'),
							emptyList: Liferay.Language.get('empty-list'),
							search: Liferay.Language.get('search')
						}
					},

					triggers: {
						value: []
					},

					type: {
						value: 'select'
					},

					value: {
						state: true,
						value: []
					}
				},

				AUGMENTS: [
					Liferay.DDM.Field.SelectFieldSearchSupport
				],

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					_selectedLabels: {},

					initializer: function() {
						var instance = this;

						instance._open = false;

						instance._eventHandlers.push(
							A.one('doc').after('click', A.bind(instance._afterClickOutside, instance)),
							instance.bindContainerEvent('click', instance._handleContainerClick, '.' + CSS_FORM_FIELD_CONTAINER),
							instance.bindContainerEvent('keydown', instance._handleSearchFieldKeydown, '.' + CSS_DROP_CHOSEN_SEARCH),
							instance.bindContainerEvent('keydown', instance._handleSelectFieldKeydown, '.' + CSS_SELECT_TRIGGER_ACTION)
						);

						if (instance.get('multiple')) {
							instance._eventHandlers.push(
								instance.bindContainerEvent('blur', instance._handleMultipleOptionsBlur, '.' + CSS_CUSTOM_CONTROL_INPUT),
								instance.bindContainerEvent('focus', instance._handleMultipleOptionsFocus, '.' + CSS_CUSTOM_CONTROL_INPUT),
								instance.bindContainerEvent('keydown', instance._handleMultipleOptionsKeydown, '.' + CSS_CUSTOM_CONTROL_INPUT)
							);
						}
						else {
							instance._eventHandlers.push(
								instance.bindContainerEvent('keydown', instance._handleOptionsKeydown, '.' + CSS_SELECT_OPTION_ITEM)
							);
						}
					},

					destructor: function() {
						var instance = this;

						if (instance._virtualScroller) {
							instance._virtualScroller.destroy();
						}

						instance._selectedLabels = {};
					},

					alignPosition: function(selectInputNode, dropdownMenuNode, dropdownMenu) {
						var Align = DDMSelect.Align;

						var dropdownHeight = dropdownMenuNode.clientHeight;

						var scrollHeight = document.querySelector('body').scrollHeight;

						Align.align(
							dropdownMenuNode,
							selectInputNode,
							Align.Bottom
						);

						if (document.querySelector('body').scrollHeight < scrollHeight) {
							Align.align(
								dropdownMenuNode,
								selectInputNode,
								Align.Top,
								false
							);
							dropdownMenu.setStyle('height', dropdownHeight);
						}
					},

					cleanSelect: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						inputNode.setAttribute('selected', false);

						instance.set('value', []);
					},

					closeList: function() {
						var instance = this;

						if (!instance.get('readOnly') && instance._isListOpen()) {
							instance._open = false;

							instance.onceAfter('render', function() {
								if (instance._isErrorRequired()) {
									instance.showErrorMessage();
								}
	
								instance.fire('closeList');
							});

							instance.render();
						}
					},

					focus: function(target) {
						var instance = this;

						var container = instance.get('container');

						if (instance._hasMultipleOptionFocused) {
							var dropdownNodesList = container.all('.select-option-item');

							var focusedDropdownNodeList = dropdownNodesList.filter(
								function(dropdownNode) {
									return dropdownNode.attr('data-option-index') === target.getAttribute('data-option-index');
								}
							);

							var focusedDropdownNode = focusedDropdownNodeList.first();

							var focusedMultipleOption = focusedDropdownNode.one('.' + CSS_CUSTOM_CONTROL_INPUT);

							focusedMultipleOption.focus();
						}
						else {
							var selectField = container.one('.' + CSS_SELECT_TRIGGER_ACTION);

							selectField.focus();
						}
					},

					getEvaluationContext: function(context) {
						return {
							multiple: context.multiple
						};
					},

					getTemplateContext: function() {
						var instance = this;

						var soyIncDom = window.DDMSelect.render.Soy.toIncDom;

						var options = instance.getOptions();
						var paddingBottom = 0;
						var paddingTop = 0;

						if (instance.get('multiple')) {
							var scroller = instance._virtualScroller;

							options = scroller.getVisibleItems();
							paddingBottom = scroller.getPaddingBottom();
							paddingTop = scroller.getPaddingTop();
						}

						var predefinedValue = instance.get('predefinedValue');
						var value = instance.getValue();		

						return A.merge(
							SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								fixedOptions: instance.get('fixedOptions').map(A.bind(instance._isSelectedOption, instance)),
								labelCloseIcon: soyIncDom(Liferay.Util.getLexiconIconTpl('times')),
								multiple: instance.get('multiple'),
								open: instance._open,
								options: options.map(A.bind(instance._isSelectedOption, instance)),
								paddingBottom: paddingBottom,
								paddingTop: paddingTop,
								predefinedValue: instance.get('readOnly') ? predefinedValue : value,
								selectCaretDoubleIcon: soyIncDom(Liferay.Util.getLexiconIconTpl('caret-double')),
								selectedLabels: instance._selectedLabels,
								selectSearchIcon: soyIncDom(Liferay.Util.getLexiconIconTpl('search')),
								showPlaceholderOption: instance._showPlaceholderOption(),
								showSearch: instance._showSearch(),
								strings: instance.get('strings'),
								value: value
							}
						);
					},

					getVirtualScrollerElement: function() {
						var instance = this;

						return instance.get('container').one('.inline-scroller');
					},

					getValue: function() {
						var instance = this;

						var fixedOptions = instance.get('fixedOptions');

						var options = instance.get('options');

						var value = instance.get('value') || [];

						return value.filter(function(currentValue) {
							return (
								options.find(function(currentOption) {
									return currentOption.value === currentValue;
								}) ||
								fixedOptions.find(function(currentOption) {
									return currentOption.value === currentValue;
								})
							)
						});
					},

					hasFocus: function(node) {
						var instance = this;

						if (node) {
							var title = node.get('title');

							var container = instance.get('container');

							var dropChosen = container.one('.drop-chosen');

							if (dropChosen && dropChosen.one('a[title="' + title + '"]')) {
								return true;
							}

							if (node.hasClass(CSS_SELECT_LABEL_ITEM_CLOSE)) {
								return true;
							}
						}

						return SelectField.superclass.hasFocus.apply(instance, arguments);
					},

					openList: function() {
						var instance = this;

						instance._open = true;

						instance.onceAfter('render', function() {
							var trigger = instance._getSelectTriggerAction();

							trigger.addClass(CSS_ACTIVE);

							var container = instance.get('container');

							if (container.ancestor('.ddm-user-view-content')) {
								var selectInputNode = container.one('.input-select-wrapper')._node;

								var dropdownMenu = container.one('.dropdown-menu');

								var dropdownMenuNode = dropdownMenu._node;

								instance.alignPosition(selectInputNode, dropdownMenuNode, dropdownMenu);
							}
						});

						instance.render();
					},

					render: function() {
						var instance = this;

						if (instance.get('multiple')) {
							var options = instance.getOptions();

							if (!instance._virtualScroller) {
								instance._virtualScroller = new Liferay.DDM.VirtualScroller({
									items: options
								});
		
								instance._virtualScroller.on('update', function() {
									window.requestAnimationFrame(function() {
										instance.render();
									});
								});
							}

							instance._virtualScroller.set('items', options);

							instance.onceAfter('render', function() {
								var scroller = instance._virtualScroller;
								var scrollerElement = instance.getVirtualScrollerElement();

								if (scroller && instance._open) {
									scroller.set('element', scrollerElement);

									scroller.bindScrollEvent();
								}
							});
						}

						SelectField.superclass.render.apply(instance, arguments);

						var dataSourceType = instance.get('dataSourceType');

						if (dataSourceType !== 'manual' && instance.get('builder')) {
							var inputNode = instance.getInputNode();

							var strings = instance.get('strings');

							inputNode.attr('disabled', true);

							inputNode.html(
								Lang.sub(
									TPL_OPTION,
									{
										label: strings.dynamicallyLoadedData
									}
								)
							);
						}

						return instance;
					},

					setValue: function(value) {
						var instance = this;

						instance.onceAfter('render', function() {
							if (instance._isErrorRequired()) {
								instance.showErrorMessage();
							}
						});

						instance.set('value', value);

						instance.render();
					},

					showErrorMessage: function() {
						var instance = this;

						SelectField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.' + CSS_INPUT_SELECT_WRAPPER);

						inputGroup.insert(container.one('.' + CSS_HELP_BLOCK), 'after');
					},

					showPlaceholderOption: function () {
						var instance = this;

						return instance._showPlaceholderOption();
					},

					toggleList: function(event) {
						var instance = this;

						var container = instance.get('container');

						var selectTrigger = container.one('.select-field-trigger');

						if (instance._isListOpen()) {
							instance.closeList();
						}
						else if (!selectTrigger.hasAttribute('disabled')) {
							instance.openList();
						}
					},

					_afterClickOutside: function(event) {
						var instance = this;

						if (instance._isClickingOutside(event)) {
							instance.closeList();
						}

						instance._preventDocumentClick = false;
					},

					_closeListAfterDelay: function() {
						var instance = this;

						setTimeout(
							function() {
								if (!instance.hasFocus()) {
									instance.closeList();
								}
							},
							100
						);
					},

					_getFixedOptions: function(fixedOptions) {
						return fixedOptions || [];
					},

					_getOptions: function(options) {
						return options || [];
					},

					_getSelectTriggerAction: function() {
						var instance = this;

						return instance.get('container').one('.' + CSS_SELECT_TRIGGER_ACTION);
					},

					_handleContainerClick: function(event) {
						var instance = this;

						var target = event.target;

						var closeIconNode = target.ancestor('.' + CSS_SELECT_LABEL_ITEM_CLOSE, true);

						var optionNode = target.ancestor('.select-option-item');

						if (closeIconNode) {
							instance._handleLabelItemCloseClick(closeIconNode);
						}
						else if (optionNode) {
							instance._handleItemClick(optionNode);
						}
						else if (
							!target.hasClass('lfr-ddm-form-field-repeatable-add-button') &&
							!target.hasClass('lfr-ddm-form-field-repeatable-delete-button')
						) {
							instance._handleSelectTriggerClick(event);
						}

						instance._preventDocumentClick = true;
					},

					_handleItemClick: function(currentTarget) {
						var instance = this;

						var value = instance.get('value') || [];

						var dataset = currentTarget._node.dataset;

						var optionValue = dataset.optionValue;
						var optionSelected = dataset.optionSelected;

						if (instance.get('multiple')) {
							instance._open = true;

							if (optionSelected) {
								value = instance._removeValue(optionValue);
							}
							else if (value.indexOf(optionValue) == -1) {
								value.push(optionValue);
							}
						}
						else {
							if (optionValue === '' || optionValue === undefined) {
								value = [];
							}
							else {
								value = [optionValue];
							}

							instance._open = false;
						}

						instance.onceAfter('render', function() {
							instance.focus(currentTarget);

							instance._fireStartedFillingEvent();
						});

						instance.setValue(value);

						instance.render();
					},

					_handleLabelItemCloseClick: function(target) {
						var instance = this;

						var value = target.getAttribute('data-label-value');

						var values = instance._removeValue(value);

						instance.setValue(values);

						instance.focus(target);
					},

					_handleMultipleOptionsBlur: function() {
						var instance = this;

						instance._hasMultipleOptionFocused = false;

						instance._closeListAfterDelay();
					},

					_handleMultipleOptionsFocus: function() {
						var instance = this;

						instance._hasMultipleOptionFocused = true;
					},

					_handleMultipleOptionsKeydown: function(event) {
						var instance = this;

						instance._preventFormSubmissionWhenEnterIsPressed(event);
					},

					_handleOptionsKeydown: function(event) {
						var instance = this;

						var keyCodes = instance._setKeyCodesForKeyboardNavigation(event);

						if (keyCodes.pressedKeyCode === keyCodes.enterCode || keyCodes.pressedKeyCode === keyCodes.spaceCode) {
							instance._handleItemClick(event.target);
						}
						else if (keyCodes.pressedKeyCode === keyCodes.tabCode) {
							instance._closeListAfterDelay();
						}
					},

					_handleSearchFieldKeydown: function(event) {
						var instance = this;

						instance._preventFormSubmissionWhenEnterIsPressed(event);
					},

					_handleSelectFieldKeydown: function(event) {
						var instance = this;

						var keyCodes = instance._setKeyCodesForKeyboardNavigation(event);

						if (keyCodes.pressedKeyCode === keyCodes.enterCode || keyCodes.pressedKeyCode === keyCodes.spaceCode) {
							var targetNode = event.target._node;

							if (targetNode && !targetNode.classList.contains(CSS_SELECT_LABEL_ITEM_CLOSE)) {
								instance.toggleList();
							}
						}
						else if (keyCodes.pressedKeyCode === keyCodes.tabCode) {
							instance._closeListAfterDelay();
						}
					},

					_handleSelectTriggerClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
							if (instance._isClickingOutside(event)) {
								instance.closeList();

								return;
							}

							var target = event.target;

							if (target.ancestor('.' + CSS_SEARCH_CHOSEN)) {
								return;
							}

							instance.toggleList(event);
						}
					},

					_hasOption: function(value) {
						var instance = this;

						var hasOption = false;

						var inputNode = instance.getInputNode();

						inputNode.all('option').each(
							function(optionNode) {
								if (optionNode.val() === value) {
									hasOption = true;
								}
							}
						);

						return hasOption;
					},

					_isClickingOutside: function(event) {
						var instance = this;

						var container = instance.get('container');

						if (!container.inDoc()) {
							return true;
						}

						var triggers = instance.get('triggers');

						if (triggers.length) {
							for (var i = 0; i < triggers.length; i++) {
								if (triggers[i].contains(event.target)) {
									return false;
								}
							}
						}

						return !container.contains(event.target);
					},

					_isErrorRequired: function() {
						var instance = this;

						var required = instance.get('required');

						var valid = instance.get('valid');

						var value = instance.getValue();

						if (required && !valid && (value.length < 1)) {
							return true;
						}

						return false;
					},

					_isListOpen: function() {
						var instance = this;

						var container = instance.get('container');

						var dropChosen = container.one('.' + CSS_DROP_CHOSEN);

						if (dropChosen) {
							return !dropChosen.hasClass(CSS_HIDE);
						}

						return false;
					},

					_isSelectedOption: function(option) {
						var instance = this;

						var value = instance.getValue();

						option.selected = '';

						if (value.indexOf(option.value) > -1) {
							option.selected = 'true';
						}

						return option;
					},

					_preventFormSubmissionWhenEnterIsPressed: function(event) {
						var instance = this;

						var keyCodes = instance._setKeyCodesForKeyboardNavigation(event);

						if (keyCodes.pressedKeyCode === keyCodes.enterCode) {
							event.preventDefault();
						}
					},

					_removeValue: function(value) {
						var instance = this;

						var values = instance.get('value');

						var index = values.indexOf(value);

						if (index >= 0) {
							values.splice(index, 1);
						}

						return values;
					},

					_selectDOMOption: function(optionNode, value) {
						var selected = false;

						if (value) {
							if (optionNode.val()) {
								selected = value.indexOf(optionNode.val()) > -1;
							}

							if (selected) {
								optionNode.attr('selected', selected);
							}
							else {
								optionNode.removeAttribute('selected');
							}
						}
					},

					_setKeyCodesForKeyboardNavigation: function(event) {
						var keyCodes = {};

						if (event.code) {
							keyCodes.enterCode = 'Enter';
							keyCodes.spaceCode = 'Space';
							keyCodes.tabCode = 'Tab';
							keyCodes.pressedKeyCode = event.code;
						}
						else if (event.keyCode) {
							keyCodes.enterCode = 13;
							keyCodes.spaceCode = 32;
							keyCodes.tabCode = 9;
							keyCodes.pressedKeyCode = event.keyCode;
						}

						return keyCodes;
					},

					_setOptions: function(options) {
						var instance = this;

						for (var i = 0; i < options.length; i++) {
							instance._selectedLabels[options[i].value] = options[i].label;
						}

						return options;
					},

					_setSelectNodeOptions: function(optionNode, value) {
						var instance = this;

						for (var i = 0; i < value.length; i++) {
							instance._selectDOMOption(optionNode, value[i]);
						}
					},

					_showPlaceholderOption: function() {
						var instance = this;

						if (!instance.get('showPlaceholder')) {
							return false;
						}

						if ((instance.get('fixedOptions') || instance.get('options')) && !instance.get('multiple')) {
							return true;
						}

						return false;
					},

					_showSearch: function() {
						var instance = this;

						var fixedOptions = instance.get('fixedOptions');

						var options = instance.get('options');

						var fieldOptions = options.concat(fixedOptions);

						var showSearch = false;

						if (fieldOptions.length > MAX_DROPDOWN_ITEMS) {
							showSearch = true;
						}

						return showSearch;
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: [
			'aui-tooltip',
			'liferay-ddm-form-field-select',
			'liferay-ddm-form-field-select-search-support',
			'liferay-ddm-form-renderer-field',
			'liferay-ddm-form-field-select-virtual-scroller',
			'yui-throttle'
		]
	}
);
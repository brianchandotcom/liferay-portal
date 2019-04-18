AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var CSS_ACTIVE = A.getClassName('active');

		var CSS_DROP_CHOSEN = A.getClassName('drop', 'chosen');

		var CSS_FORM_FIELD_CONTAINER = A.getClassName('lfr', 'ddm', 'form', 'field', 'container');

		var CSS_HELP_BLOCK = A.getClassName('help', 'block');

		var CSS_HIDE = A.getClassName('hide');

		var CSS_INPUT_SELECT_WRAPPER = A.getClassName('input', 'select', 'wrapper');

		var CSS_SEARCH_CHOSEN = A.getClassName('drop', 'chosen');

		var CSS_SELECT_ARROW_DOWN = A.getClassName('select', 'arrow', 'down', 'container');

		var CSS_SELECT_DROPDOWN_ITEM = A.getClassName('dropdown', 'item');

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
						validator: '_validateValue',
						value: []
					}
				},

				AUGMENTS: [
					Liferay.DDM.Field.SelectFieldSearchSupport
				],

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._open = false;

						instance._createLabelTooltip();

						instance._eventHandlers.push(
							A.one('doc').after('click', A.bind(instance._afterClickOutside, instance)),
							instance.bindContainerEvent('click', instance._handleContainerClick, '.' + CSS_FORM_FIELD_CONTAINER)
						);
					},

					destructor: function() {
						var instance = this;

						if (instance._tooltip) {
							instance._tooltip.destroy();
						}
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
							var container = instance.get('container');

							container.one('.' + CSS_DROP_CHOSEN).addClass(CSS_HIDE);

							container.one('.' + CSS_SELECT_TRIGGER_ACTION).removeClass(CSS_ACTIVE);

							instance._open = false;

							if (instance._isErrorRequired()) {
								instance.showErrorMessage();
							}

							instance.fire('closeList');
						}
					},

					focus: function() {
						var instance = this;

						var container = instance.get('container');

						var arrowSelect = container.one('.' + CSS_SELECT_ARROW_DOWN);

						arrowSelect.focus();
					},

					getEvaluationContext: function(context) {
						return {
							multiple: context.multiple
						};
					},

					getTemplateContext: function() {
						var instance = this;

						var soyIncDom = window.DDMSelect.render.Soy.toIncDom;

						return A.merge(
							SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								fixedOptions: instance.get('fixedOptions'),
								labelCloseIcon: soyIncDom(Liferay.Util.getLexiconIconTpl('times')),
								multiple: instance.get('multiple'),
								open: instance._open,
								options: instance.get('options'),
								predefinedValue: instance.get('readOnly') ? instance.get('predefinedValue') : instance.getValue(),
								selectCaretDoubleIcon: soyIncDom(Liferay.Util.getLexiconIconTpl('caret-double')),
								selectSearchIcon: soyIncDom(Liferay.Util.getLexiconIconTpl('search')),
								showPlaceholderOption: instance._showPlaceholderOption(),
								showSearch: instance._showSearch(),
								strings: instance.get('strings'),
								value: instance.getValue()
							}
						);
					},

					getValue: function() {
						var instance = this;

						return instance.get('value') || [];
					},

					hasFocus: function(node) {
						var instance = this;

						var hasFocus = SelectField.superclass.hasFocus.apply(instance, arguments);

						if (node) {
							var title = node.get('title');

							var container = instance.get('container');

							var dropChosen = container.one('.drop-chosen');

							if (dropChosen && dropChosen.one('a[title="' + title + '"]')) {
								hasFocus = true;
							}

							if (node.hasClass('trigger-label-item-close')) {
								hasFocus = true;
							}
						}

						return hasFocus;
					},

					openList: function() {
						var instance = this;

						instance._getSelectTriggerAction().addClass(CSS_ACTIVE);

						instance.get('container').one('.form-group').removeClass(CSS_HIDE);
						instance.get('container').one('.' + CSS_DROP_CHOSEN).removeClass(CSS_HIDE);

						instance._open = true;
					},

					render: function() {
						var instance = this;

						var dataSourceType = instance.get('dataSourceType');

						SelectField.superclass.render.apply(instance, arguments);

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

						instance.set('value', value);

						instance.render();

						if (instance._isErrorRequired()) {
							instance.showErrorMessage();
						}
					},

					showErrorMessage: function() {
						var instance = this;

						SelectField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.' + CSS_INPUT_SELECT_WRAPPER);

						inputGroup.insert(container.one('.' + CSS_HELP_BLOCK), 'after');
					},

					toggleList: function(event) {
						var instance = this;

						event.stopPropagation();

						var container = instance.get('container');

						var selectTrigger = container.one('.select-field-trigger');

						if (instance._isListOpen()) {
							instance.closeList();
						}
						else if (!selectTrigger.hasAttribute('disabled')) {
							instance.openList();

							var userView = container.ancestor('.ddm-user-view-content');

							if (userView) {
								var selectInputNode = event.currentTarget.one('.input-select-wrapper')._node;

								var dropdownMenu = event.currentTarget.one('.dropdown-menu');

								var dropdownMenuNode = dropdownMenu._node;

								instance.alignPosition(selectInputNode, dropdownMenuNode, dropdownMenu);
							}
						}
					},

					_afterClickOutside: function(event) {
						var instance = this;

						if (!instance._preventDocumentClick && instance._isClickingOutside(event)) {
							instance.closeList();
						}

						instance._preventDocumentClick = false;
					},

					_createLabelTooltip: function() {
						var instance = this;

						instance._tooltip = new A.TooltipDelegate(
							{
								position: 'bottom',
								trigger: '.multiple-label-list .multiple-label',
								triggerHideEvent: ['blur', 'mouseleave'],
								triggerShowEvent: ['focus', 'mouseover'],
								visible: false
							}
						);
					},

					_getFixedOptions: function(fixedOptions) {
						return fixedOptions || [];
					},

					_getOptions: function(options) {
						var instance = this;

						if (instance.get('readOnly')) {

							var selectedValue = instance.get('selectedValue');

							if ((selectedValue !== undefined) && (selectedValue.length > 0)) {
								options = [];

								options.push(
									{
										label: instance.get('selectedValue'),
										value: instance.get('selectedValue')
									}
								);
							}
						}

						return options || [];
					},

					_getSelectTriggerAction: function() {
						var instance = this;

						return instance.get('container').one('.' + CSS_SELECT_TRIGGER_ACTION);
					},

					_handleContainerClick: function(event) {
						var instance = this;

						var target = event.target;

						var addRepeatebleButton = target.hasClass('lfr-ddm-form-field-repeatable-add-button');

						var closeIconNode = target.ancestor('.' + CSS_SELECT_LABEL_ITEM_CLOSE, true);

						var deleteRepeatebleButton = target.hasClass('lfr-ddm-form-field-repeatable-delete-button');

						var optionNode = target.ancestor('.' + CSS_SELECT_OPTION_ITEM, true);

						if (instance.get('multiple')) {
							optionNode = target.ancestor('.' + CSS_SELECT_DROPDOWN_ITEM, true);
						}

						if (closeIconNode) {
							instance._handleLabelItemCloseClick(closeIconNode);
						}
						else if (optionNode) {
							instance._handleItemClick(optionNode);
						}
						else if (!addRepeatebleButton && !deleteRepeatebleButton) {
							instance._handleSelectTriggerClick(event);
						}

						instance._preventDocumentClick = true;
					},

					_handleItemClick: function(target) {
						var instance = this;

						var value = instance.get('value') || [];

						var currentTarget = target;

						var itemValue = currentTarget.getAttribute('data-option-value');

						if (instance.get('multiple')) {
							instance._open = true;

							if (currentTarget.getAttribute('data-option-selected')) {
								value = instance._removeValue(itemValue);
							}
							else if (value.indexOf(itemValue) == -1) {
								value.push(itemValue);
							}
						}
						else {
							if (itemValue === '') {
								value = [];
							}
							else {
								value = [itemValue];
							}

							instance._open = false;
						}

						instance.setValue(value);

						instance.focus();

						instance._fireStartedFillingEvent();
					},

					_handleLabelItemCloseClick: function(target) {
						var instance = this;

						var value = target.getAttribute('data-label-value');

						var values = instance._removeValue(value);

						instance.setValue(values);
					},

					_handleSelectTriggerClick: function(event) {
						var instance = this;

						if (!instance.get('readOnly')) {
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
						var outside = !container.contains(event.target);

						var triggers = instance.get('triggers');

						if (triggers.length) {
							for (var i = 0; i < triggers.length; i++) {
								if (triggers[i].contains(event.target)) {
									outside = false;

									break;
								}
							}
						}

						return outside;
					},

					_isErrorRequired: function() {
						var instance = this;

						var errorRequired = false;
						var required = instance.get('required');
						var valid = instance.get('valid');
						var value = instance.getValue();

						if (required && !valid && (value.length < 1)) {
							errorRequired = true;
						}

						return errorRequired;
					},

					_isListOpen: function() {
						var instance = this;

						var container = instance.get('container');
						var dropChosen = container.one('.' + CSS_DROP_CHOSEN);
						var openList = false;

						if (dropChosen) {
							openList = !dropChosen.hasClass(CSS_HIDE);
						}

						return openList;
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

					_setSelectNodeOptions: function(optionNode, value) {
						var instance = this;

						for (var i = 0; i < value.length; i++) {
							instance._selectDOMOption(optionNode, value[i]);
						}
					},

					_showPlaceholderOption: function() {
						var instance = this;
						var showPlaceholderOption = false;

						if (
							!instance.get('multiple') &&
							!instance.get('showPlaceholder') &&
							(
								instance.get('fixedOptions') ||
								instance.get('options')
							)
						) {
							showPlaceholderOption = true;
						}

						return showPlaceholderOption;
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
					},

					_validateValue: function(value) {
						var instance = this;
						var valid = false;

						if (value.length == 0) {
							valid = true;
						}
						else {
							if (instance.get('readOnly')) {
								instance.set('selectedValue', value);
							}

							var fixedOptions = instance.get('fixedOptions');

							var options = instance.get('options');

							var fieldOptions = options.concat(fixedOptions);

							for (var indexOption in fieldOptions) {
								for (var indexValue in value) {
									if (fieldOptions[indexOption].value == value[indexValue]) {
										valid = true;
									}
								}
							}
						}

						return valid;
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['aui-tooltip', 'liferay-ddm-form-field-select', 'liferay-ddm-form-field-select-search-support', 'liferay-ddm-form-renderer-field']
	}
);
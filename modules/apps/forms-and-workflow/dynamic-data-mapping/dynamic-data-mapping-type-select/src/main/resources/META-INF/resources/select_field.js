AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var CSS_SELECT_TRIGGER_ACTION = 'form-builder-select-field';

		var Lang = A.Lang;

		var TPL_OPTION = '<option>{label}</option>';

		var SelectField = A.Component.create(
			{
				ATTRS: {
					dataSourceType: {
						getter: '_getDataSourceType',
						value: 'manual'
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

					strings: {
						value: {
							chooseAnOption: Liferay.Language.get('choose-an-option'),
							dynamicallyLoadedData: Liferay.Language.get('dynamically-loaded-data')
						}
					},

					type: {
						value: 'select'
					},

					value: {
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

						instance._eventHandlers.push(
							A.one('doc').after('click', A.bind(instance._afterClickOutside, instance)),
							instance.bindContainerEvent('mousedown', instance._afterClickSelectTrigger, '.' + CSS_SELECT_TRIGGER_ACTION),
							instance.bindContainerEvent('mousedown', instance._onClickItem, 'li')
						);
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

							container.one('.drop-chosen').addClass('hide');

							container.one('.form-builder-select-field').removeClass('active');

							instance.fire('closeList');
						}
					},

					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								options: instance.get('options'),
								selectCaretDoubleIcon: Liferay.Util.getLexiconIconTpl('caret-double-l', 'icon-monospaced'),
								selectSearchIcon: Liferay.Util.getLexiconIconTpl('search', 'icon-monospaced'),
								strings: instance.get('strings'),
								value: instance.getValueSelected()
							}
						);
					},

					getValue: function() {
						var instance = this;

						var inputNode = instance.getInputNode();

						var value;

						if (instance.get('multiple')) {
							value = [];

							inputNode.all('option').each(
								function(optionNode) {
									if (optionNode.attr('selected')) {
										value.push(optionNode.val());
									}
								}
							);

							value = value.join();
						}
						else {
							value = inputNode.val();
						}

						return value;
					},

					getValueSelected: function() {
						var instance = this;

						var value = instance.get('value');

						if (!Lang.isArray(value)) {
							value = [value];
						}

						var values = instance._getOptionsSelected(value);

						if (!instance.get('multiple')) {
							values = values[0];
						}

						return values;
					},

					openList: function() {
						var instance = this;

						instance._getSelectTriggerAction().addClass('active');

						instance.get('container').one('.drop-chosen').toggleClass('hide');
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

						var inputNode = instance.getInputNode();

						if (!Lang.isArray(value)) {
							value = [value];
						}

						inputNode.all('option').each(
							function(optionNode) {
								instance._setSelectNodeOptions(optionNode, value);
							}
						);
					},

					showErrorMessage: function() {
						var instance = this;

						SelectField.superclass.showErrorMessage.apply(instance, arguments);

						var container = instance.get('container');

						var inputGroup = container.one('.input-select-wrapper');

						inputGroup.insert(container.one('.help-block'), 'after');
					},

					_afterClickOutside: function(event) {
						var instance = this;

						if (instance._isClickingOutSide(event)) {
							instance.closeList();
						}
					},

					_afterClickSelectTrigger: function(event) {
						event.preventDefault();

						var instance = this;

						if (!instance.get('readOnly')) {
							var target = event.target;

							if (target.ancestor('.search-chosen')) {
								return;
							}

							instance.openList();
						}
					},

					_getDataSourceType: function(value) {
						if (Lang.isString(value)) {
							try {
								value = JSON.parse(value);
							}
							catch (e) {
							}
						}

						if (Lang.isArray(value)) {
							value = value[0];
						}

						return value;
					},

					_getOptions: function(options) {
						return options || [];
					},

					_getOptionsSelected: function(value) {
						var instance = this;

						var options = instance.get('options');

						var optionsSelected = [];

						if (Lang.isArray(value)) {
							value.forEach(
								function(value, index) {
									options.forEach(
										function(option, index) {
											if (option.value.indexOf(value) > -1) {
												optionsSelected.push(option);
											}
										}
									);
								}
							);
						}

						return optionsSelected;
					},

					_getSelectTriggerAction: function() {
						var instance = this;

						return instance.get('container').one('.' + CSS_SELECT_TRIGGER_ACTION);
					},

					_isClickingOutSide: function(event) {
						var instance = this;

						var ancestor = event.target.ancestor('.' + CSS_SELECT_TRIGGER_ACTION);

						return !ancestor || ancestor !== instance._getSelectTriggerAction();
					},

					_isListOpen: function() {
						var instance = this;

						var container = instance.get('container');

						var openList = container.one('.drop-chosen').hasClass('hide');

						return !openList;
					},

					_onClickItem: function(event) {
						var instance = this;

						var options = instance.get('options');

						var value = event.target.getAttribute('data-option-value');

						instance.setValue(value);

						instance.set('value', [value]);

						instance.render();
					},

					_selectDOMOption: function(optionNode, value) {
						var selected = false;

						if (Lang.isArray(value)) {
							value = value[0];
						}

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

						if (instance.get('multiple')) {
							for (var i = 0; i < value.length; i++) {
								instance._selectDOMOption(optionNode, value[i]);
							}
						}
						else {
							instance._selectDOMOption(optionNode, value);
						}
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-select', 'liferay-ddm-form-field-select-search-support', 'liferay-ddm-form-renderer-field']
	}
);
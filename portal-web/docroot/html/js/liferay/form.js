AUI().add(
	'liferay-form',
	function(A) {
		var DEFAULTS_FORM_VALIDATOR = AUI.defaults.FormValidator;
		var PLACEHOLDER_TEXT_CLASS = 'aui-text-placeholder';

		var defaultAcceptFiles = DEFAULTS_FORM_VALIDATOR.RULES.acceptFiles;

		var acceptFiles = function(val, node, ruleValue) {
			if (ruleValue == '*') {
				return true;
			}

			return defaultAcceptFiles(val, node, ruleValue);
		};

		A.mix(
			DEFAULTS_FORM_VALIDATOR.RULES,
			{
				acceptFiles: acceptFiles
			},
			true
		);

		A.mix(
			DEFAULTS_FORM_VALIDATOR.STRINGS,
			{
				DEFAULT: Liferay.Language.get('please-fix-this-field'),
				acceptFiles: Liferay.Language.get('please-enter-a-file-with-a-valid-extension-x'),
				alpha: Liferay.Language.get('please-enter-only-alpha-characters'),
				alphanum: Liferay.Language.get('please-enter-only-alphanumeric-characters'),
				date: Liferay.Language.get('please-enter-a-valid-date'),
				digits: Liferay.Language.get('please-enter-only-digits'),
				email: Liferay.Language.get('please-enter-a-valid-email-address'),
				equalTo: Liferay.Language.get('please-enter-the-same-value-again'),
				max: Liferay.Language.get('please-enter-a-value-less-than-or-equal-to-x'),
				maxLength: Liferay.Language.get('please-enter-no-more-than-x-characters'),
				min: Liferay.Language.get('please-enter-a-value-greater-than-or-equal-to-x'),
				minLength: Liferay.Language.get('please-enter-at-list-x-characters'),
				number: Liferay.Language.get('please-enter-a-valid-number'),
				range: Liferay.Language.get('please-enter-a-value-between-x-and-x'),
				rangeLength: Liferay.Language.get('please-enter-a-value-between-x-and-x-characters-long'),
				required: Liferay.Language.get('this-field-is-required'),
				url: Liferay.Language.get('please-enter-a-valid-url')
			},
			true
		);

		var Form = A.Component.create(
			{
				ATTRS: {
					id: {},
					namespace: {},
					fieldRules: {},
					onSubmit: {
						valueFn: function() {
							var instance = this;

							return instance._onSubmit;
						}
					}
				},

				EXTENDS: A.Base,

				prototype: {
					initializer: function() {
						var instance = this;

						var id = instance.get('id');

						var fieldRules = instance.get('fieldRules');

						var rules = {};
						var fieldStrings = {};

						for (var rule in fieldRules) {
							instance._processFieldRule(rules, fieldStrings, fieldRules[rule]);
						}

						var form = document[id];
						var formNode = A.one(form);

						instance.form = form;
						instance.formNode = formNode;

						if (formNode) {
							var formValidator = new A.FormValidator(
								{
									boundingBox: formNode,
									fieldStrings: fieldStrings,
									rules: rules
								}
							);

							instance.formValidator = formValidator;

							instance._bindForm();
						}

						//if placeholder is not supported
						instance._supportPlaceholders = A.Object.owns(document.createElement('input'), 'placeholder');

						if (!instance._supportPlaceholders) {
							var placeHolderInputs = formNode.all('input[placeholder],textarea[placeholder]');

							if (!placeHolderInputs.isEmpty()) {
								placeHolderInputs.each(
									function(item, index, collection) {
										if (!item.val()) {
											item.addClass(PLACEHOLDER_TEXT_CLASS);
											item.val(item.attr('placeholder'));
										}
									}
								);
							}
						}
					},

					_bindForm: function() {
						var instance = this;

						var formNode = instance.formNode;
						var formValidator = instance.formValidator;

						if (!instance._supportPlaceholders) {
							formValidator.on('submit', instance._removePlaceholdersBeforeSubmit, instance);
						}

						formValidator.on('submit', instance._onValidatorSubmit, instance);

						formNode.delegate('blur', instance._onFieldFocusChange, 'button,input,select,textarea', instance);
						formNode.delegate('focus', instance._onFieldFocusChange, 'button,input,select,textarea', instance);
					},

					_defaultSubmitFn: function(event) {
						var instance = this;

						if (!event.stopped) {
							submitForm(instance.form);
						}
					},

					_onFieldFocusChange: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var row = currentTarget.ancestor('.aui-field');

						if (row) {
							row.toggleClass('aui-field-focused', (event.type == 'focus'));
						}

						if (!instance._supportPlaceholders && currentTarget.attr('placeholder')) {
							if (event.type == 'focus') {
								if (currentTarget.val() == currentTarget.attr('placeholder')) {
									currentTarget.val('');
									currentTarget.removeClass(PLACEHOLDER_TEXT_CLASS);

								}
							}
							else if (event.type == 'blur') {
								if (!currentTarget.val()) {
									currentTarget.val(currentTarget.attr('placeholder'));
									currentTarget.addClass(PLACEHOLDER_TEXT_CLASS);
								}
							}
						}
					},

					_onSubmit: function(event) {
						var instance = this;

						event.preventDefault();

						setTimeout(
							function() {
								instance._defaultSubmitFn.call(instance, event);
							},
							0
						);
					},

					_onValidatorSubmit: function(event) {
						var instance = this;

						var onSubmit = instance.get('onSubmit');

						onSubmit.call(instance, event.validator.formEvent);
					},

					_processFieldRule: function(rules, strings, rule) {
						var instance = this;

						var value = true;

						var fieldName = rule.fieldName;
						var validatorName = rule.validatorName;

						if (rule.body && !rule.custom) {
							value = rule.body;
						}

						var fieldRules = rules[fieldName];

						if (!fieldRules) {
							fieldRules = {};

							rules[fieldName] = fieldRules;
						}

						fieldRules[validatorName] = value;

						fieldRules.custom = rule.custom;

						if (rule.custom) {
							DEFAULTS_FORM_VALIDATOR.RULES[validatorName] = rule.body;
						}

						var errorMessage = rule.errorMessage;

						if (errorMessage) {
							var fieldStrings = strings[fieldName];

							if (!fieldStrings) {
								fieldStrings = {};

								strings[fieldName] = fieldStrings;
							}

							fieldStrings[validatorName] = errorMessage;
						}
					},

					_removePlaceholdersBeforeSubmit: function() {
						var instance = this;

						var formNode = instance.formNode;
						var placeHolderInputs = formNode.all('input[placeholder],textarea[placeholder]');

						if (placeHolderInputs.size() > 0) {
							placeHolderInputs.each(
								function(item, index, collection) {
									if (item.val() == item.attr('placeholder')) {
										item.val('');
									}
								}
							);
						}
					}
				},

				register: function(config) {
					var instance = this;

					var form = new Liferay.Form(config);

					var formName = config.id || config.namespace;

					instance._INSTANCES[formName] = form;

					Liferay.fire(
						'form:registered',
						{
							form: form,
							formName: formName
						}
					);

					return form;
				},

				_INSTANCES: {}
			}
		);

		Liferay.Form = Form;
	},
	'',
	{
		requires: ['aui-base', 'aui-form-validator'],
		use: []
	}
);
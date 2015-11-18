AUI.add(
	'liferay-form-placeholders',
	function(A) {
		var ANode = A.Node;
		var AObject = A.Object;

		var CSS_PLACEHOLDER = 'text-placeholder';

		var MAP_IGNORE_ATTRS = {
			id: 1,
			name: 1,
			type: 1
		};

		var SELECTOR_PLACEHOLDER_INPUTS = 'input[placeholder], textarea[placeholder]';

		var STR_BLANK = '';

		var STR_DATA_TYPE_PASSWORD_PLACEHOLDER = 'data-type-password-placeholder';

		var STR_FOCUS = 'focus';

		var STR_PASSWORD = 'password';

		var STR_PLACEHOLDER = 'placeholder';

		var STR_SPACE = ' ';

		var STR_TYPE = 'type';

		var Placeholders = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,
				NAME: 'placeholders',
				NS: STR_PLACEHOLDER,
				prototype: {
					initializer: function(config) {
						var instance = this;

						var host = instance.get('host');

						var formNode = host.formNode;

						if (formNode) {
							var placeholderInputs = formNode.all(SELECTOR_PLACEHOLDER_INPUTS);

							placeholderInputs.each(
								function(item, index, collection) {
									if (!item.val()) {
										if (item.attr(STR_TYPE) === STR_PASSWORD) {
											instance._initializePasswordNode(item);
										}
										else {
											item.addClass(CSS_PLACEHOLDER);

											item.val(item.attr(STR_PLACEHOLDER));
										}
									}
								}
							);

							instance.host = host;

							instance.beforeHostMethod('_onValidatorSubmit', instance._removePlaceholders, instance);
							instance.beforeHostMethod('_onFieldFocusChange', instance._togglePlaceholders, instance);
						}
					},

					_initializePasswordNode: function(field) {
						var placeholder = ANode.create('<input name="' + field.attr('name') + '_pass_placeholder" type="text" />');

						Liferay.Util.getAttributes(
							field,
							function(value, name, attrs) {
								var result = false;

								if (!MAP_IGNORE_ATTRS[name]) {
									if (name === 'class') {
										value += STR_SPACE + CSS_PLACEHOLDER;
									}

									placeholder.setAttribute(name, value);
								}

								return result;
							}
						);

						placeholder.val(field.attr(STR_PLACEHOLDER));

						placeholder.attr(STR_DATA_TYPE_PASSWORD_PLACEHOLDER, true);

						field.placeBefore(placeholder);

						field.hide();
					},

					_removePlaceholders: function() {
						var instance = this;

						var formNode = instance.host.formNode;

						var placeholderInputs = formNode.all(SELECTOR_PLACEHOLDER_INPUTS);

						placeholderInputs.each(
							function(item, index, collection) {
								if (item.val() == item.attr(STR_PLACEHOLDER)) {
									item.val(STR_BLANK);
								}
							}
						);
					},

					_toggleLocalizedPlaceholders: function(event, currentTarget) {
						var placeholder = currentTarget.attr(STR_PLACEHOLDER);

						if (placeholder) {
							var value = currentTarget.val();

							if (event.type === STR_FOCUS) {
								if (value === placeholder) {
									currentTarget.removeClass(CSS_PLACEHOLDER);
								}
							}
							else if (!value) {
								currentTarget.val(placeholder);

								currentTarget.addClass(CSS_PLACEHOLDER);
							}
						}
					},

					_togglePasswordPlaceholders: function(event, currentTarget) {
						var placeholder = currentTarget.attr(STR_PLACEHOLDER);

						if (placeholder) {
							if (event.type === STR_FOCUS) {
								if (currentTarget.hasAttribute(STR_DATA_TYPE_PASSWORD_PLACEHOLDER)) {
									currentTarget.hide();

									var passwordField = currentTarget.next();

									passwordField.show();

									setTimeout(
										function() {
											Liferay.Util.focusFormField(passwordField);
										},
										0
									);
								}
							}
							else if (currentTarget.attr(STR_TYPE) === STR_PASSWORD) {
								var value = currentTarget.val();

								if (!value) {
									currentTarget.hide();

									currentTarget.previous().show();
								}
							}
						}
					},

					_togglePlaceholders: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.hasAttribute(STR_DATA_TYPE_PASSWORD_PLACEHOLDER) || currentTarget.attr(STR_TYPE) === STR_PASSWORD) {
							instance._togglePasswordPlaceholders(event, currentTarget);
						}
						else if (currentTarget.hasClass('language-value')) {
							instance._toggleLocalizedPlaceholders(event, currentTarget);
						}
						else {
							var placeholder = currentTarget.attr(STR_PLACEHOLDER);

							if (placeholder) {
								var value = currentTarget.val();

								if (event.type === STR_FOCUS) {
									if (value === placeholder) {
										currentTarget.val(STR_BLANK);

										currentTarget.removeClass(CSS_PLACEHOLDER);
									}
								}
								else if (!value) {
									currentTarget.val(placeholder);

									currentTarget.addClass(CSS_PLACEHOLDER);
								}
							}
						}
					}
				}
			}
		);

		Liferay.Form.Placeholders = Placeholders;

		A.Base.plug(Liferay.Form, Placeholders);
	},
	'',
	{
		requires: ['liferay-form', 'plugin']
	}
);
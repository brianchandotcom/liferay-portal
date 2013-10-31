AUI.add(
	'liferay-form-placeholders',
	function(A) {
		var AObject = A.Object;

		var PLACEHOLDER_TEXT_CLASS = 'text-placeholder';

		var SELECTOR_PLACEHOLDER_INPUTS = 'input[placeholder], textarea[placeholder]';

		var STR_BLANK = '';

		var STR_DATA_TYPE_PASSWORD_PLACEHOLDER = 'data-type-password-placeholder';

		var STR_FOCUS = 'focus';

		var STR_NAME = 'name';

		var STR_PASSWORD = 'password';

		var STR_PLACEHOLDER = 'placeholder';

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
											instance._initializePassword(item);
										}
										else {
											item.addClass(PLACEHOLDER_TEXT_CLASS);

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

					_initializePassword: function(passwordItem) {
						var container = passwordItem.get('parentNode');

						var passwordPlaceholderItem = A.Node.create('<input name="' + passwordItem.attr(STR_NAME) + '_pass_placeholder" type="text" />');

						var passwordItemAttrs = Liferay.Util.getAttributes(
							passwordItem, 
							function (value, name, attrs) {
								var result = false;

								if (name !== 'id' && name !== STR_NAME && name !== STR_TYPE) {
									result = value;
								}

								return result;
							}
						);

						AObject.each(
							passwordItemAttrs,
							function (item, index, collection){
								passwordPlaceholderItem.setAttribute(index, item);
							}
						);

						passwordPlaceholderItem.val(passwordItem.attr(STR_PLACEHOLDER));

						passwordPlaceholderItem.addClass(PLACEHOLDER_TEXT_CLASS);

						passwordPlaceholderItem.attr(STR_DATA_TYPE_PASSWORD_PLACEHOLDER, true);

						container.insertBefore(passwordPlaceholderItem, passwordItem.next());

						passwordItem.hide();
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

					_togglePasswordPlaceholders: function(currentTarget, event) {
						var placeholder = currentTarget.attr(STR_PLACEHOLDER);

						if (placeholder) {
							if (event.type === STR_FOCUS) {
								if (currentTarget.hasAttribute(STR_DATA_TYPE_PASSWORD_PLACEHOLDER)) {
									currentTarget.hide();

									var passwordItem = currentTarget.previous();

									passwordItem.show();

									setTimeout(
										function () {
											passwordItem.focus();
										},
										0
									);
								}
							}
							else if (currentTarget.attr(STR_TYPE) === STR_PASSWORD) {
								var value = currentTarget.val();

								if (!value) {
									currentTarget.hide();

									var passwordPlaceholderItem = currentTarget.next();

									passwordPlaceholderItem.show();
								}
							}
						}
					},

					_togglePlaceholders: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						if (currentTarget.hasAttribute(STR_DATA_TYPE_PASSWORD_PLACEHOLDER) || currentTarget.attr(STR_TYPE) === STR_PASSWORD){
							instance._togglePasswordPlaceholders(currentTarget, event);
						}
						else {
							var placeholder = currentTarget.attr(STR_PLACEHOLDER);

							if (placeholder) {
								var value = currentTarget.val();

								if (event.type === STR_FOCUS) {
									if (value === placeholder) {
										currentTarget.val(STR_BLANK);

										currentTarget.removeClass(PLACEHOLDER_TEXT_CLASS);
									}						
								}
								else if (!value) {
									currentTarget.val(placeholder);

									currentTarget.addClass(PLACEHOLDER_TEXT_CLASS);
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
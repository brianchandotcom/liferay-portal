/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/**
 * The Form Placeholders Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-form-placeholders
 */

AUI.add(
	'liferay-form-placeholders',
	(A) => {
		const ANode = A.Node;

		const CSS_PLACEHOLDER = 'text-placeholder';

		const MAP_IGNORE_ATTRS = {
			id: 1,
			name: 1,
			type: 1,
		};

		const SELECTOR_PLACEHOLDER_INPUTS =
			'input[placeholder], textarea[placeholder]';

		const STR_BLANK = '';

		const STR_DATA_TYPE_PASSWORD_PLACEHOLDER =
			'data-type-password-placeholder';

		const STR_FOCUS = 'focus';

		const STR_PASSWORD = 'password';

		const STR_PLACEHOLDER = 'placeholder';

		const STR_SPACE = ' ';

		const STR_TYPE = 'type';

		const Placeholders = A.Component.create({
			EXTENDS: A.Plugin.Base,

			NAME: 'placeholders',

			NS: STR_PLACEHOLDER,

			prototype: {
				_initializePasswordNode(field) {
					const placeholder = ANode.create(
						'<input name="' +
							field.attr('name') +
							'_pass_placeholder" type="text" />'
					);

					Liferay.Util.getAttributes(field, (value, name) => {
						const result = false;

						if (!MAP_IGNORE_ATTRS[name]) {
							if (name === 'class') {
								value += STR_SPACE + CSS_PLACEHOLDER;
							}

							placeholder.setAttribute(name, value);
						}

						return result;
					});

					placeholder.val(field.attr(STR_PLACEHOLDER));

					placeholder.attr(STR_DATA_TYPE_PASSWORD_PLACEHOLDER, true);

					field.placeBefore(placeholder);

					field.hide();
				},

				_removePlaceholders() {
					const instance = this;

					const formNode = instance.host.formNode;

					const placeholderInputs = formNode.all(
						SELECTOR_PLACEHOLDER_INPUTS
					);

					placeholderInputs.each((item) => {
						if (item.val() === item.attr(STR_PLACEHOLDER)) {
							item.val(STR_BLANK);
						}
					});
				},

				_toggleLocalizedPlaceholders(event, currentTarget) {
					const placeholder = currentTarget.attr(STR_PLACEHOLDER);

					if (placeholder) {
						const value = currentTarget.val();

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

				_togglePasswordPlaceholders(event, currentTarget) {
					const placeholder = currentTarget.attr(STR_PLACEHOLDER);

					if (placeholder) {
						if (event.type === STR_FOCUS) {
							if (
								currentTarget.hasAttribute(
									STR_DATA_TYPE_PASSWORD_PLACEHOLDER
								)
							) {
								currentTarget.hide();

								const passwordField = currentTarget.next();

								passwordField.show();

								setTimeout(() => {
									Liferay.Util.focusFormField(passwordField);
								}, 0);
							}
						}
						else if (
							currentTarget.attr(STR_TYPE) === STR_PASSWORD
						) {
							const value = currentTarget.val();

							if (!value) {
								currentTarget.hide();

								currentTarget.previous().show();
							}
						}
					}
				},

				_togglePlaceholders(event) {
					const instance = this;

					const currentTarget = event.currentTarget;

					if (
						currentTarget.hasAttribute(
							STR_DATA_TYPE_PASSWORD_PLACEHOLDER
						) ||
						currentTarget.attr(STR_TYPE) === STR_PASSWORD
					) {
						instance._togglePasswordPlaceholders(
							event,
							currentTarget
						);
					}
					else if (currentTarget.hasClass('language-value')) {
						instance._toggleLocalizedPlaceholders(
							event,
							currentTarget
						);
					}
					else {
						const placeholder = currentTarget.attr(STR_PLACEHOLDER);

						if (placeholder) {
							const value = currentTarget.val();

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
				},

				initializer() {
					const instance = this;

					const host = instance.get('host');

					const formNode = host.formNode;

					if (formNode) {
						const placeholderInputs = formNode.all(
							SELECTOR_PLACEHOLDER_INPUTS
						);

						placeholderInputs.each((item) => {
							if (!item.val()) {
								if (item.attr(STR_TYPE) === STR_PASSWORD) {
									instance._initializePasswordNode(item);
								}
								else {
									item.addClass(CSS_PLACEHOLDER);

									item.val(item.attr(STR_PLACEHOLDER));
								}
							}
						});

						instance.host = host;

						instance.beforeHostMethod(
							'_onValidatorSubmit',
							instance._removePlaceholders,
							instance
						);
						instance.beforeHostMethod(
							'_onFieldFocusChange',
							instance._togglePlaceholders,
							instance
						);
					}
				},
			},
		});

		Liferay.Form.Placeholders = Placeholders;

		A.Base.plug(Liferay.Form, Placeholders);
	},
	'',
	{
		requires: ['liferay-form', 'plugin'],
	}
);

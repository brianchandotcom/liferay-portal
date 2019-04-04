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

AUI.add(
	'liferay-ddm-form-field-checkbox-multiple',
	function(A) {
		var CheckboxMultipleField = A.Component.create(
			{
				ATTRS: {
					inline: {
						value: true
					},

					options: {
						getter: '_getOptions',
						state: true,
						validator: Array.isArray,
						value: []
					},

					showAsSwitcher: {
						value: false
					},

					type: {
						value: 'checkbox_multiple'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-checkbox-multiple',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						return A.merge(
							CheckboxMultipleField.superclass.getTemplateContext.apply(instance, arguments),
							{
								inline: instance.get('inline'),
								options: instance.get('options'),
								showAsSwitcher: instance.get('showAsSwitcher')
							}
						);
					},

					getValue: function() {
						var instance = this;

						var container = instance.get('container');

						var values = [];

						container.all(instance.getInputSelector()).each(
							function(optionNode) {
								var checked = !!optionNode.attr('checked');

								if (checked) {
									values.push(optionNode.val());
								}
							}
						);

						return values;
					},

					setValue: function(value) {
						var instance = this;

						var container = instance.get('container');
						var data = [];

						var checkboxNodeList = container.all('input[type="checkbox"]');

						for (var i = 0; i < checkboxNodeList.size(); i++) {
							var node = checkboxNodeList.item(i);

							if (value.indexOf(checkboxNodeList.item(i).val()) > -1) {
								node.setAttribute('checked', true);

								data = value;
							}
							else {
								node.removeAttribute('checked');
							}
						}

						instance.set('value', data);
						instance.set('predefinedValue', data);
					},

					showErrorMessage: function() {
						var instance = this;

						CheckboxMultipleField.superclass.showErrorMessage.apply(instance, arguments);
					},

					showPendingErrorMessage: function() {
						var instance = this;

						if (!instance.hasFocus()) {
							instance.showErrorMessage();
						}
					},

					_getOptions: function(options) {
						var instance = this;

						return options || [];
					},

					_onValueChange: function(event) {
						var instance = this;

						var value = instance.getValue();

						instance.setValue(value);

						instance._fireStartedFillingEvent();
					}

				}
			}
		);

		Liferay.namespace('DDM.Field').CheckboxMultiple = CheckboxMultipleField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);
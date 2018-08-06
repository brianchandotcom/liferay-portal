<%--
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
--%>

<%@ include file="/metal/init.jsp" %>

<div id="<portlet:namespace />-container"></div>

<aui:script require="<%= mainRequire %>">
	const spritemap = 'http://localhost:8080/o/admin-theme/images/clay/icons.svg';

	const listFields = [
		{
			name: 'Date',
			type: 'date',
			icon: 'calendar',
			description: 'Select date from a Datepicker.'
		},
		{
			name: 'Text Field',
			type: 'text',
			icon: 'text',
			description: 'Single line or multiline text area.'
		},
		{
			name: 'Single Selection',
			type: 'radio',
			icon: 'radio-button',
			description: 'Select only one item with a radio button.'
		},
		{
			name: 'Select from list',
			type: 'select',
			icon: 'list',
			description: 'Choose an or more options from a list.'
		},
		{
			name: 'Grid',
			type: 'grid',
			icon: 'grid',
			description: 'Select options from a matrix.'
		},
		{
			name: 'Multiple Selection',
			type: 'checkbox',
			icon: 'select-from-list',
			description: 'Select multiple options using a checkbox.'
		}
	];

	const fieldContext = [
		{
			rows: [
				{
					columns: [
						{
							fields: [
								{
									key: 'label',
									label: 'Label',
									required: false,
									spritemap: spritemap,
									type: 'text'
								}
							],
							size: 12
						}
					]
				},
				{
					columns: [
						{
							fields: [
								{
									key: 'helpText',
									label: 'Help text',
									required: false,
									spritemap: spritemap,
									type: 'text'
								}
							],
							size: 12
						}
					]
				},
				{
					columns: [
						{
							fields: [
								{
									key: 'required',
									type: 'checkbox',
									items: [
										{label: 'Required Field'}
									],
									required: false,
									showAsSwitcher: true,
									spritemap: spritemap
								}
							],
							size: 12
						}
					]
				},
				{
					columns: [
						{
							fields: [
								{
									key: 'items',
									type: 'options',
									placeholder: 'Enter an option',
									items: [
										{
											disabled: false,
											label: 'Option 1'
										}
									],
									label: 'Options',
									required: true,
									spritemap: spritemap
								}
							],
							size: 12
						}
					]
				}
			]
		},
		{
			rows: [
				{
					columns: [
						{
							fields: [
								{
									key: 'value',
									label: 'Predefined Value',
									required: false,
									spritemap: spritemap,
									type: 'select'
								}
							],
							size: 12
						}
					]
				}
			]
		}
	];

	const serializedFormBuilderContext = <%= serializedFormBuilderContext %>;

	main.DDMForm(
		{
			spritemap,
			listFields,
			context: serializedFormBuilderContext.pages,
			fieldContext,
			modules: Liferay.MODULES,
			dependencies: ['dynamic-data-mapping-form-field-type/metal']
		},
		'#<portlet:namespace />-container',
		function(instance) {
		}
	);
</aui:script>
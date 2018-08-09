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
	const spritemap = '<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg';

	const fieldsList = [
		{
			icon: 'calendar',
			name: '<%= LanguageUtil.get(request, "date") %>',
			type: 'date'
		},
		{
			icon: 'text',
			name: '<%= LanguageUtil.get(request, "text-field") %>',
			type: 'text'
		},
		{
			icon: 'radio-button',
			name: '<%= LanguageUtil.get(request, "radio-field-type-label") %>',
			type: 'radio'
		},
		{
			icon: 'list',
			name: '<%= LanguageUtil.get(request, "select-field-type-label") %>',
			type: 'select'
		},
		{
			icon: 'grid',
			name: '<%= LanguageUtil.get(request, "grid-field-type-label") %>',
			type: 'grid'
		},
		{
			icon: 'select-from-list',
			name: '<%= LanguageUtil.get(request, "checkbox-field-type-label") %>',
			type: 'checkbox'
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
									label: '<%= LanguageUtil.get(request, "label") %>',
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
									label: '<%= LanguageUtil.get(request, "help-text") %>',
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
									items: [
										{label: '￿0￿'}
									],
									key: 'required',
									required: false,
									showAsSwitcher: true,
									spritemap: spritemap,
									type: 'checkbox'
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
									items: [
										{
											disabled: false,
											label: '<%= LanguageUtil.get(request, "option") %>'
										}
									],
									key: 'items',
									label: '<%= LanguageUtil.get(request, "options") %>',
									placeholder: '<%= LanguageUtil.get(request, "enter-an-option") %>',
									required: true,
									spritemap: spritemap,
									type: 'options'
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
									label: '<%= LanguageUtil.get(request, "predefined-value") %>',
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
			context: serializedFormBuilderContext.pages,
			dependencies: ['dynamic-data-mapping-form-field-type/metal'],
			fieldContext,
			fieldsList,
			modules: Liferay.MODULES,
			spritemap
		},
		'#<portlet:namespace />-container',
		function() {
		}
	);
</aui:script>
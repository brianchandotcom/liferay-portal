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

<%@ include file="/data_layout_builder/init.jsp" %>

<div id="<%= namespace + "container" %>"></div>

<aui:script require='<%= fieldTypesModules + ", " + layoutBuilderModule + " as LayoutBuilder" %>'>
	new LayoutBuilder.default(
		{
			context: <%= dataLayout %>,
			dataDefinitionInputId: '<%= namespace + dataDefinitionInputId %>',
			dataLayoutInputId: '<%= namespace + dataLayoutInputId %>',
			editingLanguageId: '<%= themeDisplay.getLanguageId() %>',
			fieldTypes: <%= fieldTypes %>,
			namespace: '<%= namespace %>',
			spritemap: '<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
		},
		'#<%= namespace %>container'
	);
</aui:script>
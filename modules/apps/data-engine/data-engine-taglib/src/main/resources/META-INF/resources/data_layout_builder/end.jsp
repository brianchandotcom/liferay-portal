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

<div id="<%= componentId + "container" %>"></div>

<aui:script require='<%= fieldTypesModules + ", " + dataLayoutBuilderModule + " as DataLayoutBuilder" %>'>
	Liferay.component(
		'<%= componentId %>',
		new DataLayoutBuilder.default(
			{
				componentId: '<%= componentId %>',
				context: <%= dataLayoutJSONObject %>,
				dataDefinitionInputId: '<%= namespace + dataDefinitionInputId %>',
				dataLayoutInputId: '<%= namespace + dataLayoutInputId %>',
				editingLanguageId: '<%= themeDisplay.getLanguageId() %>',
				fieldTypes: <%= fieldTypesJSONArray %>,
				localizable: <%= localizable %>,
				portletNamespace: '<%= namespace %>',
				spritemap:
					'<%= themeDisplay.getPathThemeImages() %>/lexicon/icons.svg'
			},
			'#<%= componentId %>container'
		)
	);

	var clearPortletHandlers = function(event) {
		if (event.portletId === '<%= portletDisplay.getRootPortletId() %>') {
			Liferay.destroyComponent('<%= componentId %>');

			Liferay.detach('destroyPortlet', clearPortletHandlers);
		}
	};

	Liferay.on('destroyPortlet', clearPortletHandlers);
</aui:script>
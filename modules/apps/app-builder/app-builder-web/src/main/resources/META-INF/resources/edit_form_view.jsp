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

<%@ include file="/init.jsp" %>

<%
String editFormViewRootElementId = renderResponse.getNamespace() + "-app-builder-edit-form-view";
String componentId = renderResponse.getNamespace() + "dataLayoutBuilder";
%>

<div id="<%= editFormViewRootElementId %>"></div>

<aui:form>
	<aui:input name="dataDefinition" type="hidden" />
	<aui:input name="dataLayout" type="hidden" />

	<liferay-data-engine:data-layout-builder
		componentId="<%= componentId %>"
		dataDefinitionInputId="dataDefinition"
		dataLayoutId="<%= 0L %>"
		dataLayoutInputId="dataLayout"
		namespace="<%= renderResponse.getNamespace() %>"
	/>
</aui:form>

<aui:script require='<%= npmResolvedPackageName + "/js/pages/form-view/EditFormViewApp.es as EditFormViewApp" %>'>
	Liferay.componentReady('<%= componentId %>').then(
		function(dataLayoutBuilder) {
			EditFormViewApp.default(
				'<%= editFormViewRootElementId %>',
				{
					dataLayoutBuilder: dataLayoutBuilder
				}
			);
		}
	);
</aui:script>
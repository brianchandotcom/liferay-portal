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
String componentId = renderResponse.getNamespace() + "dataLayoutBuilder";
String dataLayoutBuilderElementId = renderResponse.getNamespace() + "-app-builder-data-layout-builder";
String editFormViewRootElementId = renderResponse.getNamespace() + "-app-builder-edit-form-view";

long dataDefinitionId = ParamUtil.getLong(request, "dataDefinitionId");
long dataLayoutId = ParamUtil.getLong(request, "dataLayoutId");
boolean newCustomObject = ParamUtil.getBoolean(request, "newCustomObject");
%>

<div class="app-builder-root">
	<aui:form>
		<aui:input name="dataDefinition" type="hidden" />
		<aui:input name="dataLayout" type="hidden" />

		<div id="<%= editFormViewRootElementId %>"></div>

		<div class="app-builder-sidebar-content" id="<%= dataLayoutBuilderElementId %>">
			<liferay-data-engine:data-layout-builder
				componentId="<%= componentId %>"
				dataDefinitionInputId="dataDefinition"
				dataLayoutId="<%= dataLayoutId %>"
				dataLayoutInputId="dataLayout"
				namespace="<%= renderResponse.getNamespace() %>"
			/>
		</div>
	</aui:form>
</div>

<portlet:renderURL var="basePortletURL" />

<aui:script require='<%= npmResolvedPackageName + "/js/pages/form-view/EditFormViewApp.es as EditFormViewApp" %>'>
	Liferay.componentReady('<%= componentId %>').then(
		function(dataLayoutBuilder) {
			EditFormViewApp.default(
				'<%= editFormViewRootElementId %>',
				{
					basePortletURL: '<%= basePortletURL %>',
					dataLayoutBuilder: dataLayoutBuilder,
					dataLayoutBuilderElementId: '<%= dataLayoutBuilderElementId %>',
					dataDefinitionId: <%= dataDefinitionId %>,
					dataLayoutId: <%= dataLayoutId %>,
					newCustomObject: <%= newCustomObject %>
				}
			);
		}
	);
</aui:script>
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
String customObjectSidebarElementId = renderResponse.getNamespace() + "-app-builder-custom-object-sidebar";
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

		<portlet:renderURL var="basePortletURL" />

		<div class="app-builder-form-view-app" id="<%= editFormViewRootElementId %>">

			<%
			Map<String, Object> data = HashMapBuilder.<String, Object>put(
				"basePortletURL", basePortletURL.toString()
			).put(
				"customObjectSidebarElementId", customObjectSidebarElementId
			).put(
				"dataDefinitionId", dataDefinitionId
			).put(
				"dataLayoutBuilderElementId", dataLayoutBuilderElementId
			).put(
				"dataLayoutBuilderId", componentId
			).put(
				"dataLayoutId", dataLayoutId
			).put(
				"newCustomObject", newCustomObject
			).build();
			%>

			<react:component
				data="<%= data %>"
				module="js/pages/form-view/EditFormViewApp.es"
			/>
		</div>

		<div class="app-builder-form-view-body">
			<div class="app-builder-custom-object-sidebar" id="<%= customObjectSidebarElementId %>"></div>

			<div class="data-layout-builder-wrapper" id="<%= dataLayoutBuilderElementId %>">
				<liferay-data-engine:data-layout-builder
					componentId="<%= componentId %>"
					dataDefinitionId="<%= dataDefinitionId %>"
					dataLayoutId="<%= dataLayoutId %>"
					namespace="<%= renderResponse.getNamespace() %>"
					scopes='<%= SetUtil.fromCollection(Arrays.asList("app-builder")) %>'
				/>
			</div>
		</div>
	</aui:form>
</div>
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
String componentId = liferayPortletResponse.getNamespace() + "dataLayoutBuilder";
String customObjectSidebarElementId = liferayPortletResponse.getNamespace() + "-app-builder-custom-object-sidebar";
String dataLayoutBuilderElementId = liferayPortletResponse.getNamespace() + "-app-builder-data-layout-builder";
String editFormViewRootElementId = liferayPortletResponse.getNamespace() + "-app-builder-edit-form-view";

long dataDefinitionId = ParamUtil.getLong(request, "dataDefinitionId");
long dataLayoutId = ParamUtil.getLong(request, "dataLayoutId");
boolean isPopUpWindow = LiferayWindowState.isPopUp(request);
boolean newCustomObject = ParamUtil.getBoolean(request, "newCustomObject");
String popUpCssClass = "";

if (isPopUpWindow) {
	popUpCssClass = "app-builder-popup";
}
%>

<div class="app-builder-root">
	<aui:form>
		<aui:input name="dataDefinition" type="hidden" />
		<aui:input name="dataLayout" type="hidden" />

		<portlet:renderURL var="basePortletURL" />

		<div class="app-builder-form-view-app <%= popUpCssClass %>" id="<%= editFormViewRootElementId %>">
			<react:component
				module="js/pages/form-view/EditFormViewApp.es"
				props='<%=
					HashMapBuilder.<String, Object>put(
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
						"isPopUpWindow", isPopUpWindow
					).put(
						"newCustomObject", newCustomObject
					).put(
						"showTranslationManager", request.getAttribute(AppBuilderWebKeys.SHOW_TRANSLATION_MANAGER)
					).build()
				%>'
			/>
		</div>

		<div class="app-builder-form-view-body <%= popUpCssClass %>">
			<div class="app-builder-custom-object-sidebar" id="<%= customObjectSidebarElementId %>"></div>

			<div class="data-layout-builder-wrapper" id="<%= dataLayoutBuilderElementId %>">
				<liferay-data-engine:data-layout-builder
					componentId="<%= componentId %>"
					contentType="app-builder"
					dataDefinitionId="<%= dataDefinitionId %>"
					dataLayoutId="<%= dataLayoutId %>"
					fieldSetContentType="app-builder-fieldset"
					namespace="<%= liferayPortletResponse.getNamespace() %>"
					scopes='<%= SetUtil.fromCollection(Arrays.asList("app-builder")) %>'
				/>
			</div>
		</div>
	</aui:form>
</div>
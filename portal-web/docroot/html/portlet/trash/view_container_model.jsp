<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/trash/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

ContainerModel containerModel = (ContainerModel)request.getAttribute(WebKeys.TRASH_CONTAINER_MODEL);

long containerModelId = 0;

if (containerModel != null) {
	containerModelId = containerModel.getContainerModelId();
}

PortletURL containerURL = renderResponse.createRenderURL();

containerURL.setParameter("struts_action", "/trash/view_container_model");
containerURL.setParameter("redirect", redirect);
containerURL.setParameter("className", className);
containerURL.setParameter("classPK", String.valueOf(classPK));
containerURL.setParameter("containerModelClassName", trashHandler.getContainerModelClassName());

TrashUtil.addContainerModelBreadcrumbEntries(request, trashHandler, containerModel, containerURL);
%>

<div class="portlet-msg-alert">
	<liferay-ui:message arguments="<%= trashRenderer.getTitle(locale) %>" key="the-original-folder-does-not-exist-anymore" />
</div>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title='<%= LanguageUtil.format(pageContext, "select-x", trashHandler.getContainerModelName(), true) %>'
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	containerURL.setParameter("containerModelId", String.valueOf(containerModelId));
	%>

	<liferay-ui:search-container
		searchContainer="<%= new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, containerURL, null, null) %>"
	>
		<liferay-ui:search-container-results>

			<%
			pageContext.setAttribute("results", trashHandler.getContainerModels(classPK, containerModelId, searchContainer.getStart(), searchContainer.getEnd()));
			pageContext.setAttribute("total", trashHandler.getContainerModelsCount(classPK, containerModelId));
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.ContainerModel"
			keyProperty="containerModelId"
			modelVar="curContainerModel"
		>

			<%
			containerURL.setParameter("containerModelId", String.valueOf(curContainerModel.getContainerModelId()));
			%>

			<liferay-ui:search-container-column-text
				name="<%= LanguageUtil.get(pageContext, trashHandler.getContainerModelName()) %>"
			>
				<c:choose>
					<c:when test="<%= curContainerModel.getContainerModelId() > 0 %>">

						<%
						TrashHandler containerTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(((BaseModel)curContainerModel).getModelClassName());

						TrashRenderer containerTrashRenderer = containerTrashHandler.getTrashRenderer(curContainerModel.getContainerModelId());
						%>

						<liferay-ui:icon label="<%= true %>" message="<%= curContainerModel.getContainerModelName() %>" src="<%= containerTrashRenderer.getIconPath(renderRequest) %>" url="<%= containerURL.toString() %>" />
					</c:when>
					<c:otherwise>
						<%= curContainerModel.getContainerModelName() %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name='<%= LanguageUtil.format(pageContext, "num-of-x", trashHandler.getContainerModelName(), true) %>'
				value="<%= String.valueOf(trashHandler.getContainerModelsCount(classPK, curContainerModel.getContainerModelId())) %>"
			/>

			<%
			StringBundler sb = new StringBundler(10);

			sb.append(renderResponse.getNamespace());
			sb.append("selectContainer('");
			sb.append(redirect);
			sb.append("', '");
			sb.append(className);
			sb.append("', ");
			sb.append(classPK);
			sb.append(", ");
			sb.append(curContainerModel.getContainerModelId());
			sb.append(");");
			%>

			<liferay-ui:search-container-column-button
				align="right"
				href="<%= sb.toString() %>"
				name="choose"
			/>
		</liferay-ui:search-container-row>

		<aui:button-row>

			<%
			String taglibSelectOnClick = renderResponse.getNamespace() + "selectContainer('" + redirect + "', '" + className + "', " + classPK + ", " + containerModelId + ");";
			%>

			<aui:button
				onClick="<%= taglibSelectOnClick %>"
				value='<%= LanguageUtil.format(pageContext, "choose-this-x", trashHandler.getContainerModelName(), true) %>'
			/>
		</aui:button-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	function <portlet:namespace />selectContainer(redirect, className, classPK, containerModelId) {
		var topWindow = Liferay.Util.getTop();

		topWindow.<portlet:namespace />submitForm(redirect, className, classPK, containerModelId);
	}
</aui:script>
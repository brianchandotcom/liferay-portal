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

<%@ include file="/html/portlet/trash/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

String className = ParamUtil.getString(request, "className");
long classPK = ParamUtil.getLong(request, "classPK");
boolean rootContainerModelMovable = ParamUtil.getBoolean(request, "rootContainerModelMovable");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectContainer");

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

ContainerModel containerModel = (ContainerModel)request.getAttribute(WebKeys.TRASH_CONTAINER_MODEL);

String containerModelClassName = trashHandler.getContainerModelClassName(classPK);

long containerModelId = 0;

if (containerModel != null) {
	containerModelClassName = containerModel.getModelClassName();

	containerModelId = containerModel.getContainerModelId();
}

String containerModelName = trashHandler.getContainerModelName(classPK);

if (rootContainerModelMovable) {
	containerModelName = trashHandler.getRootContainerModelName();
}

PortletURL containerURL = renderResponse.createRenderURL();

containerURL.setParameter("struts_action", "/trash/view_container_model");
containerURL.setParameter("redirect", redirect);
containerURL.setParameter("backURL", currentURL);
containerURL.setParameter("className", className);
containerURL.setParameter("classPK", String.valueOf(classPK));
containerURL.setParameter("containerModelClassName", containerModelClassName);

TrashUtil.addContainerModelBreadcrumbEntries(request, liferayPortletResponse, containerModelClassName, containerModelId, containerURL);
%>

<div class="alert alert-block">
	<liferay-ui:message arguments="<%= new Object[] {LanguageUtil.get(request, containerModelName), HtmlUtil.escape(trashRenderer.getTitle(locale))} %>" key="the-original-x-does-not-exist-anymore" translateArguments="<%= false %>" />
</div>

<aui:form method="post" name="selectContainerFm">
	<liferay-ui:header
		backURL="<%= backURL %>"
		showBackURL="<%= containerModel != null %>"
		title='<%= LanguageUtil.format(request, "select-x", containerModelName) %>'
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<c:if test="<%= !rootContainerModelMovable %>">
		<aui:button-row>

			<%
			Map<String, Object> data = new HashMap<String, Object>();

			data.put("classname", className);
			data.put("classpk", classPK);
			data.put("containermodelid", containerModelId);
			data.put("redirect", redirect);
			%>

			<aui:button cssClass="selector-button" data="<%= data %>" value='<%= LanguageUtil.format(request, "choose-this-x", containerModelName) %>' />
		</aui:button-row>

		<br />
	</c:if>

	<%
	containerURL.setParameter("containerModelId", String.valueOf(containerModelId));
	%>

	<liferay-ui:search-container
		searchContainer="<%= new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, containerURL, null, null) %>"
		total="<%= rootContainerModelMovable ? trashHandler.getRootContainerModelsCount(scopeGroupId) : trashHandler.getContainerModelsCount(classPK, containerModelId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= rootContainerModelMovable ? trashHandler.getRootContainerModels(scopeGroupId) : trashHandler.getContainerModels(classPK, containerModelId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.ContainerModel"
			keyProperty="containerModelId"
			modelVar="curContainerModel"
		>

			<%
			TrashHandler curContainerModelTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(((BaseModel)curContainerModel).getModelClassName());

			TrashRenderer curContainerModelTrashRenderer = curContainerModelTrashHandler.getTrashRenderer(curContainerModel.getContainerModelId());

			long curContainerModelId = curContainerModel.getContainerModelId();

			containerURL.setParameter("containerModelClassName", curContainerModelTrashHandler.getClassName());
			containerURL.setParameter("containerModelId", String.valueOf(curContainerModelId));
			%>

			<liferay-ui:search-container-column-text
				name="<%= LanguageUtil.get(request, containerModelName) %>"
			>
				<c:choose>
					<c:when test="<%= curContainerModelId > 0 %>">

						<liferay-ui:icon
							iconCssClass="<%= curContainerModelTrashRenderer.getIconCssClass() %>"
							label="<%= true %>"
							message="<%= curContainerModel.getContainerModelName() %>"
							method="get"
							url="<%= containerURL.toString() %>"
						/>
					</c:when>
					<c:otherwise>
						<%= curContainerModel.getContainerModelName() %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name='<%= LanguageUtil.format(request, "num-of-x", curContainerModelTrashHandler.getSubcontainerModelName()) %>'
				value="<%= String.valueOf(curContainerModelTrashHandler.getContainerModelsCount(classPK, curContainerModelId)) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("classname", className);
				data.put("classpk", classPK);
				data.put("containermodelid", curContainerModelId);
				data.put("redirect", redirect);
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectContainerFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>
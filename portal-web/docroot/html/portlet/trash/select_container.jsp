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
long containerModelId = 0;
long entryId = ParamUtil.getLong(request, "entryId");
String className = ParamUtil.getString(request, "entryClassName");

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

ContainerModel containerModel = (ContainerModel)request.getAttribute(WebKeys.TRASH_DESTINATION_CONTAINER_MODEL);

if (containerModel != null) {
	containerModelId = containerModel.getContainerModelId();

	List<Tuple> tuples = new ArrayList<Tuple>();

	tuples.add(new Tuple(containerModel.getContainerModelId(), containerModel.getName()));

	ContainerModel curContainerModel = containerModel;

	while (curContainerModel.getParentContainerModelId() > 0) {
		curContainerModel = trashHandler.getContainerModel(curContainerModel.getParentContainerModelId());

		tuples.add(0, new Tuple(curContainerModel.getContainerModelId(), curContainerModel.getName()));
	}

	tuples.add(0, new Tuple(0L, LanguageUtil.get(pageContext, trashHandler.getRootContainerName())));

	for (Tuple tuple : tuples) {
		long curContainerId = (Long)tuple.getObject(0);

		PortletURL containerURL = renderResponse.createRenderURL();

		containerURL.setParameter("struts_action", "/trash/select_container");
		containerURL.setParameter("containerModelId", String.valueOf(curContainerId));
		containerURL.setParameter("entryId", String.valueOf(entryId));
		containerURL.setParameter("entryClassName", className);

		if (curContainerId == containerModel.getContainerModelId()) {
			PortalUtil.addPortletBreadcrumbEntry(request, String.valueOf(tuple.getObject(1)), null);
		}
		else {
			PortalUtil.addPortletBreadcrumbEntry(request, String.valueOf(tuple.getObject(1)), containerURL.toString());
		}
	}
}
else {
	PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(pageContext, trashHandler.getRootContainerName()), null);
}
%>

<aui:form method="post" name="fm">
	<liferay-ui:header
		title="select-destination"
	/>

	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

	<%
	PortletURL portletURL = renderResponse.createRenderURL();

	portletURL.setParameter("struts_action", "/trash/select_container");
	portletURL.setParameter("containerModelId", String.valueOf(containerModelId));
	portletURL.setParameter("entryId", String.valueOf(entryId));
	portletURL.setParameter("entryClassName", className);

	List<String> headerNames = new ArrayList<String>();

	headerNames.add("container");
	headerNames.add("num-of-subcontainers");
	headerNames.add(StringPool.BLANK);
	%>

	<liferay-ui:search-container
		searchContainer="<%= new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, portletURL, headerNames, null) %>"
	>

		<liferay-ui:search-container-results>

			<%
			pageContext.setAttribute("results", trashHandler.getContainerModels(entryId, containerModelId, searchContainer.getStart(), searchContainer.getEnd()));
			pageContext.setAttribute("total", trashHandler.getContainerModelsCount(entryId, containerModelId));
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.ContainerModel"
			keyProperty="containerModelId"
			modelVar="curContainerModel"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/trash/select_container");
			rowURL.setParameter("containerModelId", String.valueOf(curContainerModel.getContainerModelId()));
			rowURL.setParameter("entryId", String.valueOf(entryId));
			rowURL.setParameter("entryClassName", className);
			%>

			<liferay-ui:search-container-column-text
				name="container"
			>
				<c:choose>
					<c:when test="<%= curContainerModel.getContainerModelId() > 0 %>">

						<%
						TrashHandler containerTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(((BaseModel)curContainerModel).getModelClassName());

						TrashRenderer containerTrashRenderer = containerTrashHandler.getTrashRenderer(curContainerModel.getContainerModelId());
						%>

						<liferay-ui:icon label="<%= true %>" message="<%= curContainerModel.getName() %>" src="<%= containerTrashRenderer.getIconPath(renderRequest) %>" url="<%= rowURL.toString() %>" />
					</c:when>
					<c:otherwise>
						<%=curContainerModel.getName() %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="num-of-subcontainers"
				value="<%= String.valueOf(trashHandler.getContainerModelsCount(entryId, curContainerModel.getContainerModelId())) %>"
			/>

			<c:choose>
				<c:when test="<%= rowURL != null %>">

					<%
					StringBuilder sb = new StringBuilder();

					sb.append("opener.");
					sb.append(renderResponse.getNamespace());
					sb.append("selectContainer(");
					sb.append(entryId);
					sb.append(", ");
					sb.append(curContainerModel.getContainerModelId());
					sb.append("); window.close();");
					%>

					<liferay-ui:search-container-column-button
						align="right"
						href="<%= sb.toString() %>"
						name="choose"
					/>
				</c:when>
				<c:otherwise>
					<liferay-ui:search-container-column-text> </liferay-ui:search-container-column-text>
				</c:otherwise>
			</c:choose>
		</liferay-ui:search-container-row>

		<aui:button-row>

			<%
			String taglibSelectOnClick = "opener." + renderResponse.getNamespace() + "selectContainer("+ entryId + ", " + containerModelId + "); window.close();";
			%>

			<aui:button onClick="<%= taglibSelectOnClick %>" value="choose-this-destination" />
		</aui:button-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>
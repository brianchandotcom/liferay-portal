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
ContainerModel container = (ContainerModel)request.getAttribute(WebKeys.TRASH_DESTINATION_CONTAINER);
long containerId = 0;

long entryId = ParamUtil.getLong(request, "entryId");
String className = ParamUtil.getString(request, "entryClassName");

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

String containerName = StringPool.BLANK;

if (container == null) {
	containerName = LanguageUtil.get(pageContext, trashHandler.getRootContainerName());
}
else {
	containerId = container.getContainerId();
	containerName = container.getName();
}

if (container != null) {
	List<Tuple> tuples = new ArrayList<Tuple>();

	tuples.add(new Tuple(container.getContainerId(), container.getName()));

	ContainerModel curContainer = container;

	while (curContainer.getParentContainerId() > 0) {
		curContainer = trashHandler.getContainer(curContainer.getParentContainerId());

		tuples.add(0, new Tuple(curContainer.getContainerId(), curContainer.getName()));
	}

	tuples.add(0, new Tuple(0L, LanguageUtil.get(pageContext, trashHandler.getRootContainerName())));

	for (Tuple tuple : tuples) {
		long curContainerId = (Long)tuple.getObject(0);

		PortletURL containerURL = renderResponse.createRenderURL();

		containerURL.setParameter("struts_action", "/trash/select_container");
		containerURL.setParameter("containerId", String.valueOf(curContainerId));
		containerURL.setParameter("entryId", String.valueOf(entryId));
		containerURL.setParameter("entryClassName", className);

		if (curContainerId == container.getContainerId()) {
			PortalUtil.addPortletBreadcrumbEntry(request, String.valueOf(tuple.getObject(1)), null);
		}
		else {
			PortalUtil.addPortletBreadcrumbEntry(request, String.valueOf(tuple.getObject(1)), containerURL.toString());
		}
	}
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
	portletURL.setParameter("containerId", String.valueOf(containerId));
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
			pageContext.setAttribute("results", trashHandler.getContainers(entryId, containerId, searchContainer.getStart(), searchContainer.getEnd()));
			pageContext.setAttribute("total", trashHandler.getContainersCount(entryId, containerId));
			%>

		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.ContainerModel"
			keyProperty="containerId"
			modelVar="curContainer"
		>

			<%
			PortletURL rowURL = renderResponse.createRenderURL();

			rowURL.setParameter("struts_action", "/trash/select_container");
			rowURL.setParameter("containerId", String.valueOf(curContainer.getContainerId()));
			rowURL.setParameter("entryId", String.valueOf(entryId));
			rowURL.setParameter("entryClassName", className);
			%>

			<liferay-ui:search-container-column-text
				name="container"
			>
				<c:choose>
					<c:when test="<%= curContainer.getContainerId() > 0 %>">

						<%
						TrashHandler containerTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(((BaseModel)curContainer).getModelClassName());

						TrashRenderer containerTrashRenderer = containerTrashHandler.getTrashRenderer(curContainer.getContainerId());
						%>

						<liferay-ui:icon label="<%= true %>" message="<%= curContainer.getName() %>" src="<%= containerTrashRenderer.getIconPath(renderRequest) %>" url="<%= rowURL.toString() %>" />
					</c:when>
					<c:otherwise>
						<%= curContainer.getName() %>
					</c:otherwise>
				</c:choose>
			</liferay-ui:search-container-column-text>

			<liferay-ui:search-container-column-text
				name="num-of-subcontainers"
				value="<%= String.valueOf(trashHandler.getContainersCount(entryId, curContainer.getContainerId())) %>"
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
					sb.append(curContainer.getContainerId());
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
			String taglibSelectOnClick = "opener." + renderResponse.getNamespace() + "selectContainer("+ entryId + ", " + containerId + "); window.close();";
			%>

			<aui:button onClick="<%= taglibSelectOnClick %>" value="choose-this-destination" />
		</aui:button-row>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>
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
String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

long classNameId = ParamUtil.getLong(request, "classNameId");

String className = PortalUtil.getClassName(classNameId);

long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectContainer");

TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(className);

TrashRenderer trashRenderer = trashHandler.getTrashRenderer(classPK);

long containerModelClassNameId = ParamUtil.getLong(request, "containerModelClassNameId", PortalUtil.getClassNameId(trashHandler.getContainerModelClassName(classPK)));

String containerModelClassName = PortalUtil.getClassName(containerModelClassNameId);

long containerModelId = ParamUtil.getLong(request, "containerModelId");

TrashHandler containerTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(containerModelClassName);

ContainerModel containerModel = null;

if (containerModelId > 0) {
	containerModel = containerTrashHandler.getContainerModel(containerModelId);
}

String containerModelName = trashHandler.getContainerModelName();

PortletURL containerURL = renderResponse.createRenderURL();

containerURL.setParameter("mvcPath", "/view_container_model.jsp");
containerURL.setParameter("redirect", redirect);
containerURL.setParameter("backURL", currentURL);
containerURL.setParameter("classNameId", String.valueOf(classNameId));
containerURL.setParameter("classPK", String.valueOf(classPK));

TrashUtil.addContainerModelBreadcrumbEntries(request, liferayPortletResponse, containerModelClassName, containerModelId, containerURL);

portletDisplay.setShowBackIcon(containerModel != null);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.format(request, "select-x", containerModelName));
%>

<div class="alert alert-block">
	<liferay-ui:message arguments="<%= new Object[] {LanguageUtil.get(request, containerModelName), HtmlUtil.escape(trashRenderer.getTitle(locale))} %>" key="the-original-x-does-not-exist-anymore" translateArguments="<%= false %>" />
</div>

<aui:form cssClass="container-fluid-1280" method="post" name="selectContainerFm">
	<liferay-ui:breadcrumb showGuestGroup="<%= false %>" showLayout="<%= false %>" showParentGroups="<%= false %>" />

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

	<%
	containerURL.setParameter("containerModelId", String.valueOf(containerModelId));
	%>

	<liferay-ui:search-container
		searchContainer="<%= new SearchContainer(renderRequest, null, null, SearchContainer.DEFAULT_CUR_PARAM, SearchContainer.DEFAULT_DELTA, containerURL, null, null) %>"
		total="<%= trashHandler.getContainerModelsCount(classPK, containerModelId) %>"
	>
		<liferay-ui:search-container-results
			results="<%= trashHandler.getContainerModels(classPK, containerModelId, searchContainer.getStart(), searchContainer.getEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portal.model.ContainerModel"
			keyProperty="containerModelId"
			modelVar="curContainerModel"
		>

			<%
			long curContainerModelId = curContainerModel.getContainerModelId();

			containerURL.setParameter("containerModelId", String.valueOf(curContainerModelId));

			TrashHandler curContainerTrashHandler = TrashHandlerRegistryUtil.getTrashHandler(curContainerModel.getModelClassName());
			%>

			<liferay-ui:search-container-column-text
				name="<%= LanguageUtil.get(request, containerModelName) %>"
			>
				<c:choose>
					<c:when test="<%= curContainerModel.getContainerModelId() > 0 %>">
						<liferay-ui:icon
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
				name='<%= LanguageUtil.format(request, "num-of-x", containerModelName) %>'
				value="<%= String.valueOf(curContainerTrashHandler.getContainerModelsCount(classPK, curContainerModelId)) %>"
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

		<liferay-ui:search-iterator markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectContainerFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>
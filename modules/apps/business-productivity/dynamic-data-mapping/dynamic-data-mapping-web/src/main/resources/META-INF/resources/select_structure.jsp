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
long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="mvcPath" value="/select_structure.jsp" />
	<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<%
SearchContainer structureSearch = new StructureSearch(renderRequest, portletURL, WorkflowConstants.STATUS_APPROVED);
%>

<c:if test="<%= showToolbar %>">

	<%
	request.setAttribute(WebKeys.SEARCH_CONTAINER, structureSearch);
	%>

	<liferay-util:include page="/structure_toolbar.jsp" servletContext="<%= application %>" />
</c:if>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectStructureFm">
	<c:if test="<%= !showToolbar %>">
		<liferay-ui:header
			localizeTitle="<%= false %>"
			title="<%= ddmDisplay.getStructureName(locale) %>"
		/>
	</c:if>

	<div class="container-fluid-1280">
		<liferay-ui:search-container
			searchContainer="<%= structureSearch %>"
		>

			<liferay-ui:search-container-results>
				<%@ include file="/structure_search_results.jspf" %>
			</liferay-ui:search-container-results>

			<liferay-ui:search-container-row
				className="com.liferay.dynamic.data.mapping.model.DDMStructure"
				keyProperty="structureId"
				modelVar="structure"
			>

				<liferay-ui:search-container-column-text
					name="id"
					value="<%= String.valueOf(structure.getStructureId()) %>"
				/>

				<liferay-ui:search-container-column-text
					name="name"
					value="<%= HtmlUtil.escape(structure.getName(locale)) %>"
				/>

				<liferay-ui:search-container-column-text
					name="description"
					value="<%= HtmlUtil.escape(structure.getDescription(locale)) %>"
				/>

				<liferay-ui:search-container-column-date
					name="modified-date"
					value="<%= structure.getModifiedDate() %>"
				/>

				<liferay-ui:search-container-column-text cssClass="entry-action">
					<c:if test="<%= (structure.getStructureId() != classPK) && ((classPK == 0) || (structure.getParentStructureId() == 0) || (structure.getParentStructureId() != classPK)) %>">

						<%
						Map<String, Object> data = new HashMap<String, Object>();

						data.put("confirm-selection", Boolean.TRUE);
						data.put("confirm-selection-message", LanguageUtil.get(request, "selecting-a-new-structure-changes-the-available-input-fields-and-available-templates"));
						data.put("ddmstructureid", structure.getStructureId());
						data.put("ddmstructurekey", structure.getStructureKey());
						data.put("name", structure.getName(locale));
						%>

						<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
					</c:if>
				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</div>

	<c:if test="<%= ddmDisplay.isShowAddStructureButton() && DDMStructurePermission.containsAddStruturePermission(permissionChecker, groupId, scopeClassNameId) %>">
		<portlet:renderURL var="viewStructureURL">
			<portlet:param name="mvcPath" value="/select_structure.jsp" />
			<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
			<portlet:param name="eventName" value="<%= eventName %>" />
		</portlet:renderURL>

		<portlet:renderURL var="addStructureURL">
			<portlet:param name="mvcPath" value="/edit_structure.jsp" />
			<portlet:param name="redirect" value="<%= viewStructureURL %>" />
			<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
		</portlet:renderURL>

		<liferay-frontend:add-menu>
			<liferay-frontend:add-menu-item title='<%= LanguageUtil.get(request, "add") %>' url="<%= addStructureURL %>" />
		</liferay-frontend:add-menu>
	</c:if>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />searchForm.<portlet:namespace />keywords);
</aui:script>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectStructureFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>
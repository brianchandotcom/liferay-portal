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

<%@ include file="/html/portlet/dynamic_data_mapping/init.jsp" %>

<%
long groupId = ParamUtil.getLong(request, "groupId", scopeGroupId);
long classPK = ParamUtil.getLong(request, "classPK");
String eventName = ParamUtil.getString(request, "eventName", "selectStructure");
%>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/dynamic_data_mapping/select_structure" />
	<portlet:param name="classPK" value="<%= String.valueOf(classPK) %>" />
	<portlet:param name="eventName" value="<%= eventName %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectStructureFm">
	<c:if test="<%= !showToolbar %>">
		<liferay-ui:header
			localizeTitle="<%= false %>"
			title="<%= ddmDisplay.getStructureName(locale) %>"
		/>
	</c:if>

	<liferay-ui:search-container
		searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
	>
		<c:if test="<%= showToolbar %>">

			<%
			request.setAttribute(WebKeys.SEARCH_CONTAINER, searchContainer);
			%>

			<liferay-util:include page="/html/portlet/dynamic_data_mapping/structure_toolbar.jsp">
				<liferay-util:param name="groupId" value="<%= String.valueOf(groupId) %>" />
			</liferay-util:include>
		</c:if>

		<div class="separator"><!-- --></div>

		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/dynamic_data_mapping/structure_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.dynamicdatamapping.model.DDMStructure"
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

					data.put("ddmstructureid", structure.getStructureId());
					data.put("ddmstructurekey", structure.getStructureKey());
					data.put("name", structure.getName(locale));
					%>

					<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
				</c:if>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script>
	Liferay.Util.focusFormField(document.<portlet:namespace />selectStructureFm.<portlet:namespace />keywords);
</aui:script>

<aui:script>
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectStructureFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>
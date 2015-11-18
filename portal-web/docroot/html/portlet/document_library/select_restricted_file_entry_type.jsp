<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String eventName = ParamUtil.getString(request, "eventName", liferayPortletResponse.getNamespace() + "selectFileEntryType");
%>

<liferay-util:include page="/html/portlet/document_library/file_entry_type_toolbar.jsp">
	<liferay-util:param name="strutsAction" value="/document_library/select_restricted_file_entry_type" />
</liferay-util:include>

<liferay-portlet:renderURL varImpl="portletURL">
	<portlet:param name="struts_action" value="/document_library/select_restricted_file_entry_type" />
	<portlet:param name="includeBasicFileEntryType" value="1" />
</liferay-portlet:renderURL>

<aui:form action="<%= portletURL.toString() %>" method="post" name="selectFileEntryTypeFm">
	<liferay-ui:search-container
		searchContainer="<%= new StructureSearch(renderRequest, portletURL) %>"
	>
		<liferay-ui:search-container-results>
			<%@ include file="/html/portlet/document_library/file_entry_type_search_results.jspf" %>
		</liferay-ui:search-container-results>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
			keyProperty="fileEntryTypeId"
			modelVar="fileEntryType"
		>
			<liferay-ui:search-container-column-text
				name="name"
				value="<%= HtmlUtil.escape(fileEntryType.getName(locale)) %>"
			/>

			<liferay-ui:search-container-column-text>

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("fileentrytypeid", fileEntryType.getFileEntryTypeId());
				data.put("fileentrytypename", fileEntryType.getName(locale));
				%>

				<aui:button cssClass="selector-button" data="<%= data %>" value="choose" />
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	Liferay.Util.selectEntityHandler('#<portlet:namespace />selectFileEntryTypeFm', '<%= HtmlUtil.escapeJS(eventName) %>');
</aui:script>
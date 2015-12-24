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

<%@ include file="/document_library/init.jsp" %>

<%
long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId");
String eventName = ParamUtil.getString(request, "eventName", renderResponse.getNamespace() + "selectFileEntryType");

long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(scopeGroupId);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcPath", "/document_library/select_file_entry_type.jsp");
portletURL.setParameter("eventName", eventName);
%>

<aui:nav-bar markupView="lexicon">
	<aui:nav cssClass="navbar-nav">
		<aui:nav-item label="document-types" selected="<%= true %>" />
	</aui:nav>
</aui:nav-bar>

<aui:form action="<%= portletURL.toString() %>" cssClass="container-fluid-1280" method="post" name="selectFileEntryTypeFm">
	<liferay-ui:search-container
		iteratorURL="<%= portletURL %>"
	>
		<liferay-ui:search-container-results
			results="<%= DLFileEntryTypeServiceUtil.getFileEntryTypes(groupIds) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.portlet.documentlibrary.model.DLFileEntryType"
			cssClass="select-action"
			modelVar="fileEntryType"
		>

			<%
			if (fileEntryTypeId == fileEntryType.getFileEntryTypeId()) {
				row.setCssClass("select-action active");
			}

			Map<String, Object> rowData = new HashMap<String, Object>();

			rowData.put("fileEntryTypeId", fileEntryType.getFileEntryTypeId());

			row.setData(rowData);
			%>

			<liferay-ui:search-container-column-icon
				icon="edit-layout"
			/>

			<liferay-ui:search-container-column-text
				colspan="<%= 2 %>"
			>
				<h5><%= HtmlUtil.escape(fileEntryType.getName(locale)) %></h5>

				<h6 class="text-default">
					<span><%= fileEntryType.getDescription(locale) %></span>
				</h6>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator displayStyle="descriptive" markupView="lexicon" />
	</liferay-ui:search-container>
</aui:form>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />selectFileEntryTypeFm');

	form.delegate(
		'click',
		function(event) {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			A.all('.select-action').removeClass('active');

			currentTarget.addClass('active');

			Liferay.Util.getOpener().Liferay.fire(
				'<%= HtmlUtil.escapeJS(eventName) %>',
				{
					data: currentTarget.attr('data-fileEntryTypeId')
				}
			);
		},
		'.select-action'
	);
</aui:script>
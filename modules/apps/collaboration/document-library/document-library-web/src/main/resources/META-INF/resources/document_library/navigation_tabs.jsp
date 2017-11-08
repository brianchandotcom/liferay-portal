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
String mvcRenderCommandName = ParamUtil.getString(request, "mvcRenderCommandName", "/document_library/view");
%>

<aui:nav cssClass="navbar-nav">
	<portlet:renderURL var="viewEntriesURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<aui:nav-item
		href="<%= viewEntriesURL %>"
		label="documents-and-media"
		selected='<%= !mvcRenderCommandName.equals("/document_library/view_file_entry_types") %>'
	/>

	<portlet:renderURL var="viewFileEntryTypesURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view_file_entry_types" />
		<portlet:param name="redirect" value="<%= currentURL %>" />
	</portlet:renderURL>

	<c:if test="<%= DLPortletKeys.DOCUMENT_LIBRARY_ADMIN.equals(dlRequestHelper.getPortletName()) %>">
		<aui:nav-item
			href="<%= viewFileEntryTypesURL %>"
			label="document-types"
			selected='<%= mvcRenderCommandName.equals("/document_library/view_file_entry_types") %>'
		/>
	</c:if>

	<%
	Portlet portlet = PortletLocalServiceUtil.getPortletById(portletDisplay.getId());

	PortletURL viewMetadataSetsURL = PortletURLFactoryUtil.create(liferayPortletRequest, PortletProviderUtil.getPortletId(com.liferay.dynamic.data.mapping.model.DDMStructure.class.getName(), PortletProvider.Action.VIEW), PortletRequest.RENDER_PHASE);

	viewMetadataSetsURL.setParameter("mvcPath", "/view.jsp");
	viewMetadataSetsURL.setParameter("backURL", themeDisplay.getURLCurrent());
	viewMetadataSetsURL.setParameter("groupId", String.valueOf(themeDisplay.getScopeGroupId()));
	viewMetadataSetsURL.setParameter("refererPortletName", DLPortletKeys.DOCUMENT_LIBRARY);
	viewMetadataSetsURL.setParameter("refererWebDAVToken", WebDAVUtil.getStorageToken(portlet));
	viewMetadataSetsURL.setParameter("showAncestorScopes", Boolean.TRUE.toString());
	viewMetadataSetsURL.setParameter("showManageTemplates", Boolean.FALSE.toString());
	%>

	<c:if test="<%= DLPortletKeys.DOCUMENT_LIBRARY_ADMIN.equals(dlRequestHelper.getPortletName()) %>">
		<aui:nav-item
			href="<%= viewMetadataSetsURL.toString() %>"
			label="metadata-sets"
			selected="<%= false %>"
		/>
	</c:if>
</aui:nav>
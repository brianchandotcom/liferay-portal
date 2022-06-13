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

<%@ include file="/admin/init.jsp" %>

<%
EditClientExtensionEntryPartDisplayContext<CETIFrame> editClientExtensionEntryPartDisplayContext = (EditClientExtensionEntryPartDisplayContext)renderRequest.getAttribute(ClientExtensionAdminWebKeys.EDIT_CLIENT_EXTENSION_ENTRY_PART_DISPLAY_CONTEXT);

CETIFrame cetIFrame = editClientExtensionEntryPartDisplayContext.getCET();
%>

<aui:input label="url" name="url" type="text" value="<%= cetIFrame.getURL() %>" />

<c:choose>
	<c:when test="<%= editClientExtensionEntryPartDisplayContext.isNew() %>">
		<aui:input label="instanceable" name="instanceable" type="checkbox" value="<%= cetIFrame.isInstanceable() %>" />
	</c:when>
	<c:otherwise>
		<aui:input disabled="<%= true %>" label="instanceable" name="instanceable-disabled" type="checkbox" value="<%= cetIFrame.isInstanceable() %>" />
		<aui:input name="instanceable" type="hidden" value="<%= cetIFrame.isInstanceable() %>" />
	</c:otherwise>
</c:choose>

<clay:select
	label="portlet-category-name"
	name="portletCategoryName"
	options="<%= editClientExtensionEntryPartDisplayContext.getPortletCategoryNameSelectOptions(cetIFrame.getPortletCategoryName()) %>"
/>

<aui:input label="friendly-url-mapping" name="friendlyURLMapping" type="text" value="<%= cetIFrame.getFriendlyURLMapping() %>" />
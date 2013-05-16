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

<%@ include file="/html/portlet/layouts_admin/init.jsp" %>

<%
String cmd = ParamUtil.getString(request, Constants.CMD, Constants.EXPORT);

long groupId = ParamUtil.getLong(request, "groupId");

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");

String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(groupId, ImportLayoutsAction.class.getName());
%>

<c:choose>
	<c:when test="<%= cmd.equals(Constants.EXPORT) %>">
		<%@ include file="/html/portlet/layouts_admin/export_options.jspf" %>
	</c:when>
	<c:when test="<%= cmd.equals(Constants.IMPORT) && (tempFileEntryNames.length > 0) %>">
		<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_resources.jsp" />
	</c:when>
	<c:otherwise>
		<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_validation.jsp" />
	</c:otherwise>
</c:choose>
<c:if test='<%= SessionMessages.contains(liferayPortletRequest, "requestProcessed") %>'>
	<aui:script>
		var opener = Liferay.Util.getOpener();

		if (opener.<portlet:namespace />saveLayoutset) {
			Liferay.fire(
				'closeWindow',
				{
					id: '<portlet:namespace />importDialog'
				}
			);

			opener.<portlet:namespace />saveLayoutset('view');
		}
	</aui:script>
</c:if>
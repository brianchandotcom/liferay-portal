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

<%@ include file="/html/portlet/portlet_configuration/init.jsp" %>

<%
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(scopeGroupId, ExportImportHelper.TEMP_FOLDER_NAME + portletDisplay.getId());
%>

<div id="<portlet:namespace />exportImportOptions">
	<c:choose>
		<c:when test="<%= (tempFileEntryNames.length > 0) && !validate %>">
			<liferay-util:include page="/html/portlet/portlet_configuration/import_portlet_resources.jsp" />
		</c:when>
		<c:otherwise>
			<liferay-util:include page="/html/portlet/portlet_configuration/import_portlet_validation.jsp" />
		</c:otherwise>
	</c:choose>
</div>
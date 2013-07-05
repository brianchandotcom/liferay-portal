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
long groupId = ParamUtil.getLong(request, "groupId");
boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
boolean validate = ParamUtil.getBoolean(request, "validate", true);

String[] tempFileEntryNames = LayoutServiceUtil.getTempFileEntryNames(groupId, ExportImportHelper.TEMP_FOLDER_NAME);
%>

<liferay-ui:tabs
	names="new-import-process,all-import-processes"
	param="tabs2"
	refresh="<%= false %>"
>
	<liferay-ui:section>
		<div id="<portlet:namespace />exportImportOptions">
			<c:choose>
				<c:when test="<%= (tempFileEntryNames.length > 0) && !validate %>">
					<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_resources.jsp" />
				</c:when>
				<c:otherwise>
					<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_validation.jsp" />
				</c:otherwise>
			</c:choose>
		</div>
	</liferay-ui:section>

	<liferay-ui:section>
		<liferay-util:include page="/html/portlet/layouts_admin/import_layouts_processes.jsp" />
	</liferay-ui:section>
</liferay-ui:tabs>
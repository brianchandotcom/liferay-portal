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
long lcsProjectId = ParamUtil.getLong(request, "lcsProjectId");

LCSProject lcsProject = new LCSProjectImpl();

for (LCSProject currentLCSProject : LCSProjectServiceUtil.getUserManageableLCSProjects()) {
	if (currentLCSProject.getLcsProjectId() == lcsProjectId) {
		lcsProject = currentLCSProject;
	}
}
%>

<h3><liferay-ui:message arguments="<%= HtmlUtil.escape(lcsProject.getName()) %>" key="create-new-environment-for-x" /></h3>

<aui:input autoFocus="<%= true %>" label="name" name="lcsClusterEntryName" />

<aui:input label="location" name="lcsClusterEntryLocation" />

<aui:input label="description" name="lcsClusterEntryDescription" />

<aui:button-row>
	<aui:button cssClass="btn-success" disabled="<%= true %>" name="saveLCSClusterEntry" value="save" />
</aui:button-row>
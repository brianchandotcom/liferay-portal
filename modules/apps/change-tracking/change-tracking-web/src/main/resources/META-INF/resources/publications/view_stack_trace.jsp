<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<clay:container-fluid
	cssClass="container-view"
>
	<div class="sheet">
		<h2 class="sheet-title">
			<liferay-ui:message arguments='<%= HtmlUtil.escape(renderRequest.getParameter("ctCollectionName")) %>' key="x-scheduled-publication-failed-with-an-unexpected-system-error" />
		</h2>

		<div class="sheet-section">

			<%
			long backgroundTaskId = GetterUtil.getLong(renderRequest.getParameter("backgroundTaskId"));

			BackgroundTask backgroundTask = BackgroundTaskLocalServiceUtil.getBackgroundTask(backgroundTaskId);
			%>

			<pre class="bg-light border p-2"><%= backgroundTask.getStatusMessage() %></pre>
		</div>
</clay:container-fluid>
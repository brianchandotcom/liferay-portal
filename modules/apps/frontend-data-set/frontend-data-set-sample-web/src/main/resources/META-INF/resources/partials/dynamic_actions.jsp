<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
MyRoleTasksFDSDisplayContext myRoleTasksFDSDisplayContext = new MyRoleTasksFDSDisplayContext(request);
MyUserTasksFDSDisplayContext myUserTasksFDSDisplayContext = new MyUserTasksFDSDisplayContext(request);
%>

<div class="row">
	<div class="col-md-6 fds-role-tasks">
		<div class="p-2">
			<h2>My Role Tasks</h2>
		</div>

		<frontend-data-set:headless-display
			apiURL="<%= myRoleTasksFDSDisplayContext.getAPIURL() %>"
			id="<%= FDSSampleFDSNames.MY_ROLE_TASKS %>"
			style="fluid"
		/>
	</div>

	<div class="col-md-6 fds-user-tasks">
		<div class="p-2">
			<h2>My User Tasks</h2>
		</div>

		<frontend-data-set:headless-display
			apiURL="<%= myUserTasksFDSDisplayContext.getAPIURL() %>"
			id="<%= FDSSampleFDSNames.MY_USER_TASKS %>"
			style="fluid"
		/>
	</div>
</div>
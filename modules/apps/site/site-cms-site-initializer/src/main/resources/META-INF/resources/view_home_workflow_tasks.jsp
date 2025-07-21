<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewWorkflowTasksDisplayContext viewWorkflowTasksDisplayContext = (ViewWorkflowTasksDisplayContext)request.getAttribute(ViewWorkflowTasksDisplayContext.class.getName());
%>

<div class="cms-section">
    <div>
		<react:component
			module="{ViewWorkflowTasks} from site-cms-site-initializer"
			props="<%= viewWorkflowTasksDisplayContext.getReactData() %>"
		/>
	</div>
</div>
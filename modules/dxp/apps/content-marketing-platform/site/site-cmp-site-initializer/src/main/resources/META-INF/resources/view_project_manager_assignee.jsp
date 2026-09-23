<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewProjectManagerAssigneeSectionDisplayContext viewProjectManagerAssigneeSectionDisplayContext = (ViewProjectManagerAssigneeSectionDisplayContext)request.getAttribute(ViewProjectManagerAssigneeSectionDisplayContext.class.getName());
%>

<div class="cms-section cms-tabs-fluid">
	<react:component
		module="{CustomAssignee} from site-cmp-site-initializer"
		props="<%= viewProjectManagerAssigneeSectionDisplayContext.getProperties() %>"
	/>
</div>
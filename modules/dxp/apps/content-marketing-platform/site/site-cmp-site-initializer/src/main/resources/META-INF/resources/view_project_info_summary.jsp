<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewProjectInfoSummarySectionDisplayContext viewProjectInfoSummarySectionDisplayContext = (ViewProjectInfoSummarySectionDisplayContext)request.getAttribute(ViewProjectInfoSummarySectionDisplayContext.class.getName());
%>

<div class="cms-section cms-tabs-fluid">
	<react:component
		module="{ProjectInfoSummary} from site-cmp-site-initializer"
		props="<%= viewProjectInfoSummarySectionDisplayContext.getProperties() %>"
	/>
</div>
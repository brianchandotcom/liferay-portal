<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ViewOnPageInsightDetailsDisplayContext viewOnPageInsightDetailsDisplayContext = (ViewOnPageInsightDetailsDisplayContext)request.getAttribute(ViewOnPageInsightDetailsDisplayContext.class.getName());
%>

<div class="seo-studio-insight-detail">
	<react:component
		module="{InsightDetailView} from seo-studio-web"
		props="<%= viewOnPageInsightDetailsDisplayContext.getReactData() %>"
	/>
</div>

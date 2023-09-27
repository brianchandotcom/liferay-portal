<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/process_summary/init.jsp" %>

<clay:container-fluid>
	<c:if test="<%= GroupCapabilityUtil.isSupportsPages(liveGroup) %>">
		<%@ include file="/process_summary/process_summary_pages.jspf" %>
	</c:if>

	<%@ include file="/process_summary/process_summary_dates.jspf" %>

	<%@ include file="/process_summary/process_summary_portlets.jspf" %>

	<%@ include file="/process_summary/process_summary_deletions.jspf" %>

	<%@ include file="/process_summary/process_summary_permissions.jspf" %>
</clay:container-fluid>
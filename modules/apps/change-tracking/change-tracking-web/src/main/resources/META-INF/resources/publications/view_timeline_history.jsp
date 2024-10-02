<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewTimelineHistoryDisplayContext viewTimelineHistoryDisplayContext = (ViewTimelineHistoryDisplayContext)request.getAttribute(CTWebKeys.VIEW_TIMELINE_HISTORY_DISPLAY_CONTEXT);
%>

<clay:container-fluid
	id="publication-timeline-history-table"
>
	<frontend-data-set:headless-display
		apiURL="<%= viewTimelineHistoryDisplayContext.getAPIURL() %>"
		fdsActionDropdownItems="<%= viewTimelineHistoryDisplayContext.getFDSActionDropdownItems() %>"
		fdsFilters="<%= viewTimelineHistoryDisplayContext.getFDSFilters() %>"
		id="<%= PublicationsFDSNames.PUBLICATIONS_TIMELINE_HISTORY %>"
		itemsPerPage="<%= 10 %>"
		selectedItemsKey="id"
		showPagination="<%= true %>"
	/>
</clay:container-fluid>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewScheduledDisplayContext viewScheduledDisplayContext = (ViewScheduledDisplayContext)request.getAttribute(CTWebKeys.VIEW_SCHEDULED_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	navigationItems="<%= viewScheduledDisplayContext.getViewNavigationItems() %>"
/>

<clay:container-fluid>
	<frontend-data-set:headless-display
		apiURL="<%= viewScheduledDisplayContext.getAPIURL() %>"
		fdsActionDropdownItems="<%= viewScheduledDisplayContext.getFDSActionDropdownItems() %>"
		id="<%= PublicationsFDSNames.PUBLICATIONS_SCHEDULED %>"
	/>
</clay:container-fluid>
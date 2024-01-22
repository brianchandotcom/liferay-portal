<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewHistoryDisplayContext viewHistoryDisplayContext = (ViewHistoryDisplayContext)request.getAttribute(CTWebKeys.VIEW_HISTORY_DISPLAY_CONTEXT);
%>

<clay:navigation-bar
	navigationItems="<%= viewHistoryDisplayContext.getViewNavigationItems() %>"
/>

<clay:container-fluid>
	<frontend-data-set:headless-display
		additionalProps='<%=
			HashMapBuilder.<String, Object>put(
				"getPublicationStatusURL",
				ResourceURLBuilder.createResourceURL(
					renderResponse
				).setResourceID(
					"/change_tracking/get_publication_status"
				).buildString()
			).build()
		%>'
		apiURL="<%= viewHistoryDisplayContext.getAPIURL() %>"
		fdsActionDropdownItems="<%= viewHistoryDisplayContext.getFDSActionDropdownItems() %>"
		id="<%= PublicationsFDSNames.PUBLICATIONS_HISTORY %>"
		propsTransformer="{PublicationHistoryPropsTransformer} from change-tracking-web"
	/>
</clay:container-fluid>
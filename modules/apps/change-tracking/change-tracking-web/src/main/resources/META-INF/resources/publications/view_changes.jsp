<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

if (!user.isOnDemandUser()) {
	portletDisplay.setURLBack(viewChangesDisplayContext.getBackURL());
	portletDisplay.setShowBackIcon(true);
}
else {
	portletDisplay.setBeta(true);
	portletDisplay.setShowBackIcon(false);
}

renderResponse.setTitle(LanguageUtil.get(request, "review-changes"));
%>

<div class="publications-view-changes-wrapper">
	<div>
		<react:component
			module="publications/js/views/ChangeTrackingChangesToolbar"
			props="<%= viewChangesDisplayContext.getToolbarReactData() %>"
		/>
	</div>

	<clay:navigation-bar
		navigationItems="<%= viewChangesDisplayContext.getViewNavigationItems() %>"
	/>

	<clay:container-fluid>
		<frontend-data-set:headless-display
			apiURL="<%= viewChangesDisplayContext.getAPIURL() %>"
			fdsActionDropdownItems="<%= viewChangesDisplayContext.getFDSActionDropdownItems() %>"
			fdsFilters="<%= viewChangesDisplayContext.getFDSFilters() %>"
			fdsSortItemList="<%= viewChangesDisplayContext.getFDSSortItemList() %>"
			id="<%= PublicationsFDSNames.PUBLICATIONS_CHANGES %>"
			style="stacked"
		/>
	</clay:container-fluid>
</div>
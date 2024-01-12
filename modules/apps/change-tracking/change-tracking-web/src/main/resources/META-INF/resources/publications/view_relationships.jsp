<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

portletDisplay.setURLBack(viewChangesDisplayContext.getBackURL());

portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(LanguageUtil.get(request, "review-changes"));
%>

<div class="publications-view-changes-wrapper">
	<div>
		<react:component
			module="{ChangeTrackingChangesToolbar} from change-tracking-web"
			props="<%= viewChangesDisplayContext.getToolbarReactData() %>"
		/>
	</div>

	<clay:navigation-bar
		navigationItems="<%= viewChangesDisplayContext.getViewNavigationItems() %>"
	/>

	<clay:container-fluid>
		<react:component
			module="{ChangeTrackingRelationshipsView} from change-tracking-web"
			props="<%= viewChangesDisplayContext.getToolbarReactData() %>"
		/>
	</clay:container-fluid>
</div>
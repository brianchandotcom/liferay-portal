<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

renderResponse.setTitle(LanguageUtil.get(request, "review-changes"));

portletDisplay.setURLBack(viewChangesDisplayContext.getBackURL());
portletDisplay.setShowBackIcon(true);
%>

<div class="publications-view-changes-wrapper">
	<div>
		<react:component
			module="publications/js/views/ChangeTrackingChangesToolbar"
			props="<%= viewChangesDisplayContext.getReactData() %>"
		/>
	</div>

	<div class="sidenav-content">
		<react:component
			module="publications/js/views/ChangeTrackingChangesView"
			props="<%= viewChangesDisplayContext.getReactData() %>"
		/>
	</div>
</div>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

ViewChangesDisplayContext viewChangesDisplayContext = (ViewChangesDisplayContext)request.getAttribute(CTWebKeys.VIEW_CHANGES_DISPLAY_CONTEXT);

portletDisplay.setURLBack(redirect);
portletDisplay.setShowBackIcon(true);

renderResponse.setTitle(LanguageUtil.get(request, "review-change"));
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
			module="publications/js/views/ChangeTrackingChangeView"
			props="<%= viewChangesDisplayContext.getReactData() %>"
		/>
	</div>
</div>
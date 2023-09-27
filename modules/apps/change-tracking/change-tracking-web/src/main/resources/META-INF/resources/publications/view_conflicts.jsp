<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/publications/init.jsp" %>

<%
ViewConflictsDisplayContext viewConflictsDisplayContext = (ViewConflictsDisplayContext)request.getAttribute(CTWebKeys.VIEW_CONFLICTS_DISPLAY_CONTEXT);

CTCollection ctCollection = viewConflictsDisplayContext.getCtCollection();

boolean schedule = ParamUtil.getBoolean(request, "schedule");

renderResponse.setTitle(StringBundler.concat(LanguageUtil.get(request, schedule ? "schedule-to-publish-later" : "publish"), ": ", ctCollection.getName()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(viewConflictsDisplayContext.getRedirect());
%>

<clay:container-fluid
	cssClass="container-form-lg publications-conflicts-container"
>
	<react:component
		module="publications/js/views/ChangeTrackingConflictsView"
		props="<%= viewConflictsDisplayContext.getReactData() %>"
	/>
</clay:container-fluid>
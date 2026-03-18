<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "manage-user-views"));
%>

<aui:style type="text/css">
	.manage-user-views .management-bar-wrapper {
		background: #fff;
		margin-left: -100%;
		margin-right: -100%;
		padding-left: 100%;
		padding-right: 100%;
	}

	.portlet-body {
		overflow: hidden;
	}
</aui:style>

<div class="container-fluid container-fluid-max-xxxl">
	<react:component
		module="{ManageUserViews} from frontend-data-set-admin-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"currentURL", themeDisplay.getURLCurrent()
			).put(
				"getUserViewsDataSetsURL", fdsAdminDisplayContext.getUserViewsDataSetsURL()
			).put(
				"namespace", liferayPortletResponse.getNamespace()
			).put(
				"portletId", portletDisplay.getId()
			).put(
				"systemDataSetEntries", fdsAdminDisplayContext.getSystemFDSEntryJSONArray()
			).build()
		%>'
	/>
</div>
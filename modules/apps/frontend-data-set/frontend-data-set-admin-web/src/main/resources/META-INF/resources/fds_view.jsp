<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String fdsViewsURL = fdsAdminDisplayContext.getFDSViewsURL(ParamUtil.getString(request, "fdsEntryId"), ParamUtil.getString(request, "fdsEntryLabel"));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fdsViewsURL);

renderResponse.setTitle(ParamUtil.getString(request, "fdsViewLabel"));
%>

<react:component
	module="{DataSet} from frontend-data-set-admin-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"backURL", fdsViewsURL
		).put(
			"fdsClientExtensionCellRenderers", fdsAdminDisplayContext.getFDSCellRendererCETsJSONArray()
		).put(
			"fdsFilterClientExtensions", fdsAdminDisplayContext.getFDSFilterCETsJSONArray()
		).put(
			"fdsViewId", ParamUtil.getString(request, "fdsViewId")
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"restApplications", fdsAdminDisplayContext.getRESTApplicationsJSONArray()
		).put(
			"saveFDSFieldsURL", fdsAdminDisplayContext.getSaveFDSFieldsURL()
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
/>
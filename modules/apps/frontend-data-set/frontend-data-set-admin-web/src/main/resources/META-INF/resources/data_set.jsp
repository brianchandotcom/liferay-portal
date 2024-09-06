<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL");

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(ParamUtil.getString(request, "backURL"));

renderResponse.setTitle(ParamUtil.getString(request, "dataSetLabel"));
%>

<react:component
	module="{DataSet} from frontend-data-set-admin-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"backURL", backURL
		).put(
			"dataSetERC", ParamUtil.getString(request, "dataSetERC")
		).put(
			"fdsClientExtensionCellRenderers", fdsAdminDisplayContext.getFDSCellRendererCETsJSONArray()
		).put(
			"fdsFilterClientExtensions", fdsAdminDisplayContext.getFDSFilterCETsJSONArray()
		).put(
			"learnResources", LearnMessageUtil.getReactDataJSONObject("frontend-data-set-admin-web")
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"resolvedRESTSchemas", fdsAdminDisplayContext.getRESTApplicationResolvedSchemasJSONArray()
		).put(
			"restApplications", fdsAdminDisplayContext.getRESTApplicationsJSONArray()
		).put(
			"saveFDSFieldsURL", fdsAdminDisplayContext.getSaveFDSFieldsURL()
		).put(
			"saveFDSSortURL", fdsAdminDisplayContext.getSaveFDSSortURL()
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
/>
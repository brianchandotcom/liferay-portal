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
	module="{DataSet} from frontend-data-set-views-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"backURL", backURL
		).put(
			"dataSetERC", ParamUtil.getString(request, "dataSetERC")
		).put(
			"fdsClientExtensionCellRenderers", fdsViewsDisplayContext.getFDSCellRendererCETsJSONArray()
		).put(
			"fdsFilterClientExtensions", fdsViewsDisplayContext.getFDSFilterCETsJSONArray()
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"saveFDSFieldsURL", fdsViewsDisplayContext.getSaveFDSFieldsURL()
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
/>
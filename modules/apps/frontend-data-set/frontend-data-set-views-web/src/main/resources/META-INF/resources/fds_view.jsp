<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String fdsViewsURL = fdsViewsDisplayContext.getFDSViewsURL(ParamUtil.getString(request, "fdsEntryId"), ParamUtil.getString(request, "fdsEntryLabel"));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fdsViewsURL);

renderResponse.setTitle(ParamUtil.getString(request, "fdsViewLabel"));
%>

<react:component
	module="js/FDSView"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"fdsClientExtensionCellRenderers", fdsViewsDisplayContext.getFDSCellRendererCETsJSONArray()
		).put(
			"fdsFilterClientExtensions", fdsViewsDisplayContext.getFDSFilterCETsJSONArray()
		).put(
			"fdsViewId", ParamUtil.getString(request, "fdsViewId")
		).put(
			"fdsViewsURL", fdsViewsURL
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"saveFDSFieldsURL", fdsViewsDisplayContext.getSaveFDSFieldsURL()
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
/>
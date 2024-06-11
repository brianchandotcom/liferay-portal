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
	module="{DataSet} from frontend-data-set-views-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"backURL", fdsViewsURL
		).put(
			"clientExtensionCellRenderers", fdsViewsDisplayContext.getFDSCellRendererCETsJSONArray()
		).put(
			"fdsViewId", ParamUtil.getString(request, "fdsViewId")
		).put(
			"filterClientExtensions", fdsViewsDisplayContext.getFDSFilterCETsJSONArray()
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"restApplications", fdsViewsDisplayContext.getRESTApplicationsJSONArray()
		).put(
			"saveTableSectionsURL", fdsViewsDisplayContext.getSaveTableSectionsURL()
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
/>
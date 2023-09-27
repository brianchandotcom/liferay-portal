<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(fdsViewsDisplayContext.getFDSEntriesURL());

renderResponse.setTitle(ParamUtil.getString(request, "fdsEntryLabel"));
%>

<react:component
	module="js/FDSViews"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"fdsEntryId", ParamUtil.getString(request, "fdsEntryId")
		).put(
			"fdsEntryLabel", ParamUtil.getString(request, "fdsEntryLabel")
		).put(
			"fdsViewURL", fdsViewsDisplayContext.getFDSViewURL()
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
/>
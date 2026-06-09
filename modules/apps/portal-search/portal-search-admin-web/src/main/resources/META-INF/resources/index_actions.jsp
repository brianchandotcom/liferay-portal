<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/react" prefix="react" %>

<%@ page import="com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder" %><%@
page import="com.liferay.portal.kernel.util.HashMapBuilder" %><%@
page import="com.liferay.portal.search.admin.web.internal.constants.SearchAdminWebKeys" %><%@
page import="com.liferay.portal.search.admin.web.internal.display.context.IndexActionsDisplayContext" %>

<portlet:defineObjects />

<%
IndexActionsDisplayContext indexActionsDisplayContext = (IndexActionsDisplayContext)request.getAttribute(SearchAdminWebKeys.INDEX_ACTIONS_DISPLAY_CONTEXT);
%>

<span class="hide" id="<portlet:namespace />classNameToBackgroundTaskMap">
	<%= indexActionsDisplayContext.getClassNameToBackgroundTaskJSONString() %>
</span>
<span class="hide" id="<portlet:namespace />failedReindexBackgroundTasksCount">
	<%= indexActionsDisplayContext.getFailedReindexBackgroundTasksCount() %>
</span>

<div>
	<react:component
		module="{IndexActions} from portal-search-admin-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"data", indexActionsDisplayContext.getData()
			).put(
				"redirectURL",
				PortletURLBuilder.createRenderURL(
					renderResponse
				).setMVCRenderCommandName(
					"/portal_search_admin/view"
				).setTabs1(
					"index-actions"
				).buildString()
			).put(
				"reindexURL",
				PortletURLBuilder.createActionURL(
					renderResponse
				).setActionName(
					"/portal_search_admin/edit"
				).buildString()
			).build()
		%>'
	/>
</div>
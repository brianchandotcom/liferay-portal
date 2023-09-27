<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/common/init.jsp" %>

<%
KBAdminNavigationDisplayContext kbAdminNavigationDisplayContext = new KBAdminNavigationDisplayContext(request, renderRequest, renderResponse);
%>

<div class="knowledge-base-vertical-bar <%= kbAdminNavigationDisplayContext.isProductMenuOpen() ? StringPool.BLANK : "expanded" %>" id="<portlet:namespace />verticalBarId">
	<liferay-portlet:actionURL name="/knowledge_base/move_kb_object" var="moveKBObjectURL" />

	<react:component
		componentId="verticalBarId"
		module="admin/js/components/VerticalBar"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"items", kbAdminNavigationDisplayContext.getVerticalNavigationJSONObjects()
			).put(
				"moveKBObjectURL", moveKBObjectURL
			).put(
				"parentContainerId", liferayPortletResponse.getNamespace() + "verticalBarId"
			).put(
				"productMenuOpen", kbAdminNavigationDisplayContext.isProductMenuOpen()
			).build()
		%>'
	/>
</div>
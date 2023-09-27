<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
KBAdminNavigationDisplayContext kbAdminNavigationDisplayContext = new KBAdminNavigationDisplayContext(request, renderRequest, renderResponse);
%>

<div>
	<react:component
		componentId="moveObjectModalId"
		module="admin/js/components/MoveModal"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"items", kbAdminNavigationDisplayContext.getKBFolderDataJSONArray()
			).put(
				"moveParentKBObjectId", kbAdminNavigationDisplayContext.getMoveParentKBObjectId()
			).build()
		%>'
	/>
</div>
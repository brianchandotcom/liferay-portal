<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test='<%= ParamUtil.getBoolean(request, "showGroupSelector") %>'>
		<liferay-item-selector:group-selector />
	</c:when>
	<c:otherwise>

		<%
		LocalizedItemSelectorRendering localizedItemSelectorRendering = LocalizedItemSelectorRendering.get(liferayPortletRequest);

		ItemSelectorViewRenderer itemSelectorViewRenderer = localizedItemSelectorRendering.getSelectedItemSelectorViewRenderer();

		itemSelectorViewRenderer.renderHTML(pageContext);
		%>

	</c:otherwise>
</c:choose>
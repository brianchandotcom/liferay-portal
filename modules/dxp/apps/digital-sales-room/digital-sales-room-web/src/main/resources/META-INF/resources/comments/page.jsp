<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/comments/init.jsp" %>

<c:if test="<%= digitalSalesRoomId != 0 %>">
	<react:component
		module="{DSRComments} from digital-sales-room-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"digitalSalesRoomId", digitalSalesRoomId
			).build()
		%>'
	/>
</c:if>
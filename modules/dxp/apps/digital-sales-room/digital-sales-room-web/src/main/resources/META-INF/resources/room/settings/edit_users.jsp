<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditDigitalSalesRoomRoomSettingsDisplayContext editDigitalSalesRoomRoomSettingsDisplayContext = (EditDigitalSalesRoomRoomSettingsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="dsr-template-management portlet-digital-sales-room-management">
	<react:component
		module="{DSRRoomSettingsInitializer} from digital-sales-room-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"cancelURL", editDigitalSalesRoomRoomSettingsDisplayContext.getCancelURL(renderResponse)
			).put(
				"digitalSalesRoomId", editDigitalSalesRoomRoomSettingsDisplayContext.getDigitalSalesRoomId()
			).put(
				"step", "users"
			).build()
		%>'
	/>
</div>
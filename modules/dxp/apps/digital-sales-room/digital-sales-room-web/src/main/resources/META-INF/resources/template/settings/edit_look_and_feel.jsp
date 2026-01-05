<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditDigitalSalesRoomTemplateSettingsDisplayContext editDigitalSalesRoomTemplateSettingsDisplayContext = (EditDigitalSalesRoomTemplateSettingsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<div class="dsr-template-management portlet-digital-sales-room-management">
	<react:component
		module="{DSRTemplateSettingsInitializer} from digital-sales-room-web"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"cancelURL", editDigitalSalesRoomTemplateSettingsDisplayContext.getCancelURL(renderResponse)
			).put(
				"digitalSalesRoomTemplateId", editDigitalSalesRoomTemplateSettingsDisplayContext.getDigitalSalesRoomTemplateId()
			).put(
				"step", "lookAndFeel"
			).build()
		%>'
	/>
</div>
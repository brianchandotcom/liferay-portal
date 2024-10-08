<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/fragment/renderer/inline_editable_order_field/init.jsp" %>

<react:component
	module="{InlineEditableOrderField} from commerce-order-content-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"field", field
		).put(
			"fieldHelpMessage", fieldHelpMessage
		).put(
			"fieldValue", HtmlUtil.escape(fieldValue)
		).put(
			"hasPermission", hasPermission
		).put(
			"isOpenOrder", open
		).put(
			"label", HtmlUtil.escape(label)
		).put(
			"namespace", namespace
		).put(
			"orderId", commerceOrderId
		).put(
			"spritemap", themeDisplay.getPathThemeSpritemap()
		).build()
	%>'
/>
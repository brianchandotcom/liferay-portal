<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<aui:style>
	.inactive-message {
		border: 1px solid #FF0000;
		margin: auto;
		max-width: 600px;
		padding: 20px;
		position: relative;
		text-align: center;
		top: 50%;
		vertical-align: middle;
	}
</aui:style>

<div class="inactive-message">
	<liferay-ui:message key="<%= (String)request.getAttribute(WebKeys.PORTAL_INACTIVE_REQUEST_HANDLER_MESSAGE) %>" />
</div>
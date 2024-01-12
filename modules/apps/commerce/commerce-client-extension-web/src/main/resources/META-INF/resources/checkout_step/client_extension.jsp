<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String renderURL = (String)request.getAttribute(WebKeys.RENDER_URL);
%>

<div class="form-group form-group-item input-String-wrapper has-error" id="commerceCheckoutStepContainer"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"renderURL", renderURL
		).build()
	%>'
	module="js/commerce_checkout_step_client_extension"
/>
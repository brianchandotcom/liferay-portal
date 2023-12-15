<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String renderURL = (String)request.getAttribute("renderURL");
%>

<div class="form-group form-group-item input-String-wrapper has-error" id="customCheckoutStepContainer"></div>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"renderURL", renderURL
		).build()
	%>'
	module="js/customCheckoutStep"
/>
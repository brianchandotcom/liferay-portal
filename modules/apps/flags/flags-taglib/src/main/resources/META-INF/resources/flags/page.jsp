<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/flags/init.jsp" %>

<%
String elementClasses = (String)request.getAttribute("liferay-flags:flags:elementClasses");
%>

<div class="d-inline-block taglib-flags <%= Validator.isNotNull(elementClasses) ? elementClasses : "" %>" id="<%= StringUtil.randomId() %>_id">
	<c:choose>
		<c:when test='<%= GetterUtil.getBoolean(request.getAttribute("liferay-flags:flags:onlyIcon")) %>'>
			<clay:button
				borderless="<%= true %>"
				cssClass="lfr-portal-tooltip"
				disabled="<%= true %>"
				displayType="secondary"
				icon="flag-empty"
				small="<%= true %>"
				title='<%= (String)request.getAttribute("liferay-flags:flags:message") %>'
			/>
		</c:when>
		<c:otherwise>
			<clay:button
				borderless="<%= true %>"
				disabled="<%= true %>"
				displayType="secondary"
				icon="flag-empty"
				label="message"
				small="<%= true %>"
			/>
		</c:otherwise>
	</c:choose>

	<react:component
		module="flags/js/index.es"
		props='<%= (Map<String, Object>)request.getAttribute("liferay-flags:flags:data") %>'
	/>
</div>
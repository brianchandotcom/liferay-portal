<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/common/themes/init.jsp" %>

<%
String jspPath = (String)PortalMessages.get(request, PortalMessages.KEY_JSP_PATH);
String message = (String)PortalMessages.get(request, PortalMessages.KEY_MESSAGE);
%>

<c:if test="<%= Validator.isNotNull(jspPath) || Validator.isNotNull(message) %>">

	<%
	String portletId = (String)PortalMessages.get(request, PortalMessages.KEY_PORTLET_ID);
	%>

	<liferay-util:buffer
		var="alertMessage"
	>
		<c:choose>
			<c:when test="<%= Validator.isNotNull(jspPath) %>">
				<liferay-util:include page="<%= jspPath %>" portletId="<%= portletId %>" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="<%= message %>" />
			</c:otherwise>
		</c:choose>
	</liferay-util:buffer>

	<aui:script>
		Liferay.Util.openToast({
			message: '<%= HtmlUtil.escapeJS(alertMessage) %>',
			renderData: {
				portletId: <%= portletId %>
			},
			toastProps: {
				autoClose: <%= GetterUtil.getInteger(PortalMessages.get(request, PortalMessages.KEY_TIMEOUT), 10000) %>,
				className: '<%= GetterUtil.getString(PortalMessages.get(request, PortalMessages.KEY_CSS_CLASS), "alert-info") %>',
				style: {top: 0},
			}
		});
	</aui:script>
</c:if>
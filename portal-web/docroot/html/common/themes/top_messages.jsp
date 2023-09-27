<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/html/taglib/ui/empty_result_message/init.jsp" %>

<%
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:empty-result-message:message"));
%>

<c:choose>
	<c:when test="<%= compact %>">
		<p class="text-secondary">
			<liferay-ui:message key="<%= message %>" />
		</p>
	</c:when>
	<c:otherwise>
		<div class="mt-4 taglib-empty-result-message">
			<div class="<%= (String)request.getAttribute("liferay-ui:empty-result-message:cssClass") %>"></div>

			<c:if test="<%= Validator.isNotNull(message) %>">
				<div class="mt-2 text-center text-secondary">
					<liferay-ui:message key="<%= message %>" />
				</div>
			</c:if>
	</c:otherwise>
</c:choose>
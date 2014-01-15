<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
String alternativeLayoutFriendlyURL = (String)SessionMessages.get(request, "alternativeLayoutFriendlyURL");
%>

<c:if test="<%= Validator.isNotNull(alternativeLayoutFriendlyURL) %>">
	<button class="close" type="button">&times;</button>

	<liferay-util:buffer var="redirectedLink">
		<aui:a href="<%= PortalUtil.getCurrentCompleteURL(request) %>">
			<%= PortalUtil.getCurrentCompleteURL(request) %>
		</aui:a>
	</liferay-util:buffer>

	<p class="redirected-to-message">
		<liferay-ui:message arguments="<%= redirectedLink %>" key="you-were-redirected-to-x" translateArguments="<%= false %>" />
	</p>

	<liferay-util:buffer var="originalLink">
		<aui:a href="<%= alternativeLayoutFriendlyURL %>">
			<liferay-ui:message key="link" />
		</aui:a>
	</liferay-util:buffer>

	<p class="original-url">
		<liferay-ui:message arguments="<%= originalLink %>" key="click-the-following-link-if-you-want-to-dismiss-this-redirect-and-access-the-original-url-x" translateArguments="<%= false %>" />
	</p>
</c:if>
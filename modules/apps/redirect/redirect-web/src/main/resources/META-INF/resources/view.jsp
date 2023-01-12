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

<%@ include file="/init.jsp" %>

<%
RedirectDisplayContext redirectDisplayContext = (RedirectDisplayContext)request.getAttribute(RedirectDisplayContext.class.getName());
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= redirectDisplayContext.getNavigationItems() %>"
/>

<c:choose>
	<c:when test="<%= redirectDisplayContext.isShowRedirectEntries() %>">
		<liferay-util:include page="/view_redirect_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:when test="<%= redirectDisplayContext.isShowRedirectNotFoundEntries() %>">
		<liferay-util:include page="/view_redirect_not_found_entries.jsp" servletContext="<%= application %>" />
	</c:when>
	<c:otherwise>

		<%
		RedirectPatternConfigurationDisplayContext redirectPatternConfigurationDisplayContext = (RedirectPatternConfigurationDisplayContext)request.getAttribute(RedirectPatternConfigurationDisplayContext.class.getName());
		%>

		<div>
			<react:component
				module="js/RedirectPatterns"
				props="<%= redirectPatternConfigurationDisplayContext.getRedirectPatterns() %>"
			/>
		</div>

		<c:if test="<%= SessionErrors.contains(renderRequest, ConfigurationModelListenerException.class) %>">
			<aui:script>
				Liferay.Util.openToast({
					message:
						'<liferay-ui:message key="patterns-must-be-valid-regular-expressions" />',
					title: '<liferay-ui:message key="error" />',
					toastProps: {
						autoClose: 5000,
					},
					type: 'danger',
				});
			</aui:script>
		</c:if>
	</c:otherwise>
</c:choose>
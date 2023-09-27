<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		<c:if test='<%= SessionErrors.contains(renderRequest, "redirectPatternInvalid") %>'>
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
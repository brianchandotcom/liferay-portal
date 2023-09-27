<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-layout:render-layout-utility-page-entry
	type="<%= LayoutUtilityPageEntryConstants.TYPE_STATUS %>"
>

	<%
	int status = GetterUtil.getInteger(request.getAttribute("status_code"));

	if (status == 0) {
		status = ParamUtil.getInteger(request, "status");
	}

	if (status > 0) {
		response.setStatus(status);
	}
	%>

	<c:choose>
		<c:when test="<%= SessionErrors.contains(request, PrincipalException.getNestedClasses()) %>">
			<clay:alert
				displayType="danger"
				message="forbidden"
			/>

			<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />

			<br /><br />

			<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>
		</c:when>
		<c:when test="<%= SessionErrors.contains(request, PortalException.class.getName()) || SessionErrors.contains(request, SystemException.class.getName()) %>">
			<clay:alert
				displayType="danger"
				message="internal-server-error"
			/>

			<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

			<br /><br />

			<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>
		</c:when>
		<c:when test="<%= SessionErrors.contains(request, TransformException.class.getName()) %>">
			<clay:alert
				displayType="danger"
				message="internal-server-error"
			/>

			<liferay-ui:message key="an-error-occurred-while-processing-the-requested-resource" />

			<br /><br />

			<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>

			<br /><br />

			<%
			TransformException te = (TransformException)SessionErrors.get(request, TransformException.class.getName());
			%>

			<div>
				<%= StringUtil.replace(HtmlUtil.escape(te.getMessage()), '\n', "<br />\n") %>
			</div>
		</c:when>
		<c:when test="<%= statusDisplayContext.isNoSuchResourceException() %>">
			<liferay-layout:render-layout-utility-page-entry
				type="<%= LayoutUtilityPageEntryConstants.TYPE_SC_NOT_FOUND %>"
			>
				<div class="container pb-3 pt-3">
					<clay:alert
						displayType="danger"
						message="not-found"
					/>

					<liferay-ui:message key="the-requested-resource-could-not-be-found" />

					<br /><br />

					<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>
				</div>
			</liferay-layout:render-layout-utility-page-entry>
		</c:when>
		<c:when test="<%= status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR %>">
			<liferay-layout:render-layout-utility-page-entry
				type="<%= LayoutUtilityPageEntryConstants.TYPE_SC_INTERNAL_SERVER_ERROR %>"
			>
				<clay:alert
					displayType="danger"
					message="internal-server-error"
				/>

				<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

				<br /><br />

				<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>

				<%
				statusDisplayContext.logSessionErrors();
				%>

			</liferay-layout:render-layout-utility-page-entry>
		</c:when>
		<c:otherwise>
			<clay:alert
				displayType="danger"
				message="internal-server-error"
			/>

			<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

			<br /><br />

			<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>

			<%
			statusDisplayContext.logSessionErrors();
			%>

		</c:otherwise>
	</c:choose>

	<hr class="separator" />

	<a href="javascript:history.go(-1);">&laquo; <liferay-ui:message key="back" /></a>
</liferay-layout:render-layout-utility-page-entry>
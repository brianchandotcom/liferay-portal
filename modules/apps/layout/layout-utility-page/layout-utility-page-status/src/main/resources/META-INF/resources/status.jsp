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
			<h3 class="alert alert-danger">
				<liferay-ui:message key="forbidden" />
			</h3>

			<liferay-ui:message key="you-do-not-have-permission-to-access-the-requested-resource" />

			<br /><br />

			<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>
		</c:when>
		<c:when test="<%= SessionErrors.contains(request, PortalException.class.getName()) || SessionErrors.contains(request, SystemException.class.getName()) %>">
			<h3 class="alert alert-danger">
				<liferay-ui:message key="internal-server-error" />
			</h3>

			<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

			<br /><br />

			<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>
		</c:when>
		<c:when test="<%= SessionErrors.contains(request, TransformException.class.getName()) %>">
			<h3 class="alert alert-danger">
				<liferay-ui:message key="internal-server-error" />
			</h3>

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
					<h3 class="alert alert-danger">
						<liferay-ui:message key="not-found" />
					</h3>

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
				<h3 class="alert alert-danger">
					<liferay-ui:message key="internal-server-error" />
				</h3>

				<liferay-ui:message key="an-error-occurred-while-accessing-the-requested-resource" />

				<br /><br />

				<code class="lfr-url-error"><%= statusDisplayContext.getEscapedURL(themeDisplay) %></code>

				<%
				statusDisplayContext.logSessionErrors();
				%>

			</liferay-layout:render-layout-utility-page-entry>
		</c:when>
		<c:otherwise>
			<h3 class="alert alert-danger">
				<liferay-ui:message key="internal-server-error" />
			</h3>

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
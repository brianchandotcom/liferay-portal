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

<%@ include file="/html/portal/init.jsp" %>

<c:choose>
	<c:when test="<%= Validator.isNotNull(request.getAttribute(NoSuchLayoutException.class.getName())) %>">
		<div class="container pb-3 pt-3">
			<h3 class="alert alert-danger">
				<liferay-ui:message key="not-found" />
			</h3>

			<liferay-ui:message key="the-requested-resource-could-not-be-found" />

			<br /><br />

			<%
			String url = ParamUtil.getString(request, "previousURL");

			if (Validator.isNull(url)) {
				url = PortalUtil.getCurrentURL(request);
			}

			url = HttpComponentsUtil.decodeURL(themeDisplay.getPortalURL() + url);
			%>

			<code class="lfr-url-error"><%= HtmlUtil.escape(url) %></code>
		</div>
	</c:when>
	<c:otherwise>

		<%
		StringBundler sb = (StringBundler)request.getAttribute(WebKeys.LAYOUT_CONTENT);

		sb.writeTo(out);

		request.removeAttribute(WebKeys.LAYOUT_CONTENT);
		%>

	</c:otherwise>
</c:choose>
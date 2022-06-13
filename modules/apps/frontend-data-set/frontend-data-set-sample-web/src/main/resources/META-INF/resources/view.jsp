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
String tabs1 = ParamUtil.getString(request, "tabs1", "customized");

String tabs1Names = "classic,controlled,customized,minimum,react";

PortletURL portletURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setTabs1(
	tabs1
).buildPortletURL();
%>

<clay:container-fluid>
	<liferay-ui:tabs
		names="<%= tabs1Names %>"
		url="<%= portletURL.toString() %>"
		value="<%= tabs1 %>"
	>

		<%
		String[] sections = tabs1Names.split(StringPool.COMMA);

		for (int i = 0; i < sections.length; i++) {
		%>

			<liferay-ui:section>
				<c:if test="<%= tabs1.equals(sections[i]) %>">
					<liferay-util:include page='<%= "/partials/" + tabs1 + ".jsp" %>' servletContext="<%= pageContext.getServletContext() %>" />
				</c:if>
			</liferay-ui:section>

		<%
		}
		%>

	</liferay-ui:tabs>
</clay:container-fluid>
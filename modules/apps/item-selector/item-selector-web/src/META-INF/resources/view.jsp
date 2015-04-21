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
String[] tabs1Names = ItemSelectorUtil.getTabs1Names(request);
%>

<c:choose>
	<c:when test="<%= !ArrayUtil.isEmpty(tabs1Names) && (tabs1Names.length > 1) %>">
		<liferay-ui:tabs names="<%= StringUtil.merge(tabs1Names) %>" param="tabs1" refresh="<%= false %>" type="pills">

			<%
			for (String tabs1Name : tabs1Names) {
			%>

				<liferay-ui:section>
					<div>
						<liferay-util:include page='<%= "/" + tabs1Name + ".jsp" %>' servletContext="<%= application %>" />
					</div>
				</liferay-ui:section>

			<%
			}
			%>

		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<liferay-util:include page='<%= "/" + tabs1Names[0] + ".jsp" %>' servletContext="<%= application %>" />
	</c:otherwise>
</c:choose>
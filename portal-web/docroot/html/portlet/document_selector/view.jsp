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

<%@ include file="/html/portlet/document_selector/init.jsp" %>

<%
String tabsString = ParamUtil.getString(renderRequest, "tabs", "documents,pages");

String[] tabs = StringUtil.split(tabsString);
%>

<c:choose>
	<c:when test="<%= !ArrayUtil.isEmpty(tabs) && (tabs.length > 1) %>">
		<liferay-ui:tabs names="<%= tabsString %>" param="tabs1" refresh="<%= false %>" type="pills">

			<%
			for (String tab : tabs) {
			%>

				<liferay-ui:section>
					<div>
						<liferay-util:include page='<%= "/html/portlet/document_selector/" + tab + ".jsp" %>'/>
					</div>
				</liferay-ui:section>

			<%
			}
			%>

		</liferay-ui:tabs>
	</c:when>
	<c:otherwise>
		<liferay-util:include page='<%= "/html/portlet/document_selector/" + tabs[0] + ".jsp" %>'/>
	</c:otherwise>
</c:choose>
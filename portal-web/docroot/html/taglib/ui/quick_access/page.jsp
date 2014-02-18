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

<%@ include file="/html/taglib/ui/quick_access/init.jsp" %>

<%
String randomId = StringUtil.randomId();
%>

<nav id="<%= randomId %>" class="quick-access-nav">
	<h1 class="hide-accessible"><liferay-ui:message key="navigation" /></h1>

	<ul>
		<li><a href="#main-content"><liferay-ui:message key="skip-to-content" /></a></li>

		<%
		if ((quickAccessEntries != null) && !quickAccessEntries.isEmpty()) {
			for (QuickAccessEntry quickAccessEntry : quickAccessEntries) {
		%>

			<li>
				<a href="<%= quickAccessEntry.getURL() %>" id="<%= quickAccessEntry.getId() %>"><%= HtmlUtil.escape(quickAccessEntry.getLabel()) %></a>
			</li>

		<%
			}
		}
		%>
	</ul>
</nav>

<c:if test="<%= (quickAccessEntries != null) && !quickAccessEntries.isEmpty() %>">
	<aui:script use="node-base">
		A.one('#<%= randomId %>').delegate(
			'click',
			function(event) {

				<%
				for (QuickAccessEntry quickAccessEntry : quickAccessEntries) {
					String onClick = quickAccessEntry.getOnClick();

					if (Validator.isNotNull(onClick)) {
				%>
						if (event.currentTarget.getAttribute('id') === '<%= quickAccessEntry.getId() %>') {
							<%= onClick %>
						}
				<%
					}
				}
				%>

			},
			'li a'
		);
	</aui:script>
</c:if>
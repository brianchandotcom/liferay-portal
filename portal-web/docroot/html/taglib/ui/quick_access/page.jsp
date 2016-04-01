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

<%@ include file="/html/taglib/ui/quick_access/init.jsp" %>

<%
String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;
%>

<c:if test="<%= ((quickAccessEntries != null) && !quickAccessEntries.isEmpty()) || Validator.isNotNull(contentId) %>">
	<nav class="quick-access-nav" id="<%= randomNamespace + "quickAccessNav" %>">
		<h1 class="hide-accessible"><liferay-ui:message key="navigation" /></h1>

		<ul>
			<c:if test="<%= Validator.isNotNull(contentId) %>">
				<li><a href="<%= contentId %>"><liferay-ui:message key="skip-to-content" /></a></li>
			</c:if>

			<%
			if ((quickAccessEntries != null) && !quickAccessEntries.isEmpty()) {
				for (QuickAccessEntry quickAccessEntry : quickAccessEntries) {
			%>

					<li>
						<a href="<%= quickAccessEntry.getURL() %>" id="<%= randomNamespace + quickAccessEntry.getId() %>"><%= quickAccessEntry.getContent() %></a>
					</li>

			<%
				}
			}
			%>

		</ul>
	</nav>

	<c:if test="<%= (quickAccessEntries != null) && !quickAccessEntries.isEmpty() %>">
		<aui:script sandbox="<%= true %>">
			var callbacks = {};

			<%
			for (QuickAccessEntry quickAccessEntry : quickAccessEntries) {
				String onClick = quickAccessEntry.getOnClick();

				if (Validator.isNotNull(onClick)) {
			%>

					callbacks['<%= randomNamespace + quickAccessEntry.getId() %>'] = function() {
						<%= onClick %>
					};

			<%
				}
			}
			%>

			$('#<%= randomNamespace %>quickAccessNav').on(
				'click',
				'li a',
				function(event) {
					var callbackFn = callbacks[$(event.currentTarget).attr('id')];

					if (_.isFunction(callbackFn)) {
						callbackFn();
					}
				}
			);
		</aui:script>
	</c:if>
</c:if>
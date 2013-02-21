<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
boolean merge = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:prototype-merge-alert:merge"));
int mergeFailCount = GetterUtil.getInteger((String)request.getAttribute("liferay-ui:prototype-merge-alert:mergeFailCount"));
PortletURL portletURL = (PortletURL)request.getAttribute("liferay-ui:prototype-merge-alert:portletURL");
String type = (String)request.getAttribute("liferay-ui:prototype-merge-alert:type");
%>

<c:if test="<%= portletURL != null %>">
	<span class="portlet-msg-alert">
		<liferay-ui:message arguments="<%= new Object[] {mergeFailCount, type} %>" key="the-propagation-of-changes-from-the-x-has-been-disabled-temporarily-after-x-errors" />

		<liferay-ui:message arguments="<%= new Object[] {type} %>" key='<%= merge ? "click-reset-and-propagate-to-reset-the-failure-count-and-propagate-changes-from-the-x" : "click-reset-to-reset-the-failure-count-and-reenable-propagation" %>' />

		<aui:button name="resetButton" value='<%= merge ? "reset-and-propagate" : "reset" %>' />
	</span>

	<aui:script use="aui-base">
		var resetButton= A.one('#<%= namespace %>resetButton');

		resetButton.on(
			'click',
			function(event) {
				submitForm(document.hrefFm, '<%= portletURL.toString() %>');
			}
		);
	</aui:script>
</c:if>
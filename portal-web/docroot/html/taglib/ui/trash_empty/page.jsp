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

<%@ page import="com.liferay.portlet.trash.util.TrashUtil" %>

<%
String portletURL = (String)request.getAttribute("liferay-ui:trash-empty:portletURL");

String emptyMessage = (String)request.getAttribute("liferay-ui:trash-empty:emptyMessage");
String confirmMessage = (String)request.getAttribute("liferay-ui:trash-empty:confirmMessage");

int totalEntries = GetterUtil.getInteger(request.getAttribute("liferay-ui:trash-empty:totalEntries"));

Group group = themeDisplay.getScopeGroup();
%>

<div class="lfr-message-info taglib-trash-empty">
	<liferay-ui:message arguments="<%= TrashUtil.getMaxAge(group) %>" key="entries-that-have-been-in-recycle-bin-for-more-than-x-days-will-be-automatically-deleted" />

	<c:if test="<%= totalEntries > 0 %>">
		<a class="trash-empty-link" href="javascript:;" id="<%= namespace %>empty"><liferay-ui:message key="<%= emptyMessage %>" /></a>

		<aui:form action="<%= portletURL %>" cssClass="trash-empty-form" name="emptyForm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EMPTY_TRASH %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<aui:button type="submit" value="empty" />
		</aui:form>
	</c:if>
</div>

<aui:script use="aui-base">
	var emptyLink = A.one('#<%= namespace %>empty');

	if (emptyLink) {
		emptyLink.on(
			'click',
			function(event) {
				if (confirm('<%= UnicodeLanguageUtil.get(pageContext, confirmMessage) %>')) {
					submitForm(document.<portlet:namespace />emptyForm);
				}
			}
		);
	}
</aui:script>
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String confirmMessage = (String)request.getAttribute("liferay-ui:trash-empty:confirmMessage");
String emptyMessage = (String)request.getAttribute("liferay-ui:trash-empty:emptyMessage");
String infoMessage = (String)request.getAttribute("liferay-ui:trash-empty:infoMessage");
String portletURL = (String)request.getAttribute("liferay-ui:trash-empty:portletURL");
int totalEntries = GetterUtil.getInteger(request.getAttribute("liferay-ui:trash-empty:totalEntries"));
%>

<c:if test="<%= totalEntries > 0 %>">
	<div class="alert alert-info taglib-trash-empty">
		<aui:form action="<%= portletURL %>" name="emptyForm">

			<%
			String trashEntriesMaxAgeTimeDescription = LanguageUtil.getTimeDescription(locale, TrashUtil.getMaxAge(themeDisplay.getScopeGroup()) * Time.MINUTE, true);
			%>

			<liferay-ui:message arguments="<%= StringUtil.toLowerCase(trashEntriesMaxAgeTimeDescription) %>" key="<%= infoMessage %>" translateArguments="<%= false %>" />

			<a class="trash-empty-link" href="javascript:;" id="<%= namespace %>empty"><liferay-ui:message key="<%= emptyMessage %>" /></a>

			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.EMPTY_TRASH %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />

			<aui:button cssClass="trash-empty-button" type="submit" value="<%= emptyMessage %>" />
		</aui:form>
	</div>
</c:if>

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
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

<%--<%@ include file="/html/portlet/dictionary/init.jsp" %>

<form name="<portlet:namespace />fm" onSubmit="window.open(document.<portlet:namespace />fm.<portlet:namespace />type[document.<portlet:namespace />fm.<portlet:namespace />type.selectedIndex].value + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />word.value)); return false;">

<input autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" name="<portlet:namespace />word" size="30" type="text" />

<select name="<portlet:namespace />type">
	<option value="http://dictionary.reference.com/search?q="><liferay-ui:message key="dictionary" /></option>
	<option value="http://thesaurus.reference.com/search?q="><liferay-ui:message key="thesaurus" /></option>
</select>

<input type="submit" value="<liferay-ui:message key="find" />" />

</form>--%>

<%@ include file="/html/portlet/dictionary/init.jsp" %>


<aui:form name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "dictionary();" %>'>

<aui:field-wrapper>

<aui:input name="word" autoFocus="<%= windowState.equals(WindowState.MAXIMIZED) %>" />

<aui:select name="type">
	<option value="http://dictionary.reference.com/browse/" /><liferay-ui:message key="dictionary" /></option>
	<option value="http://thesaurus.reference.com/browse/" /><liferay-ui:message key="thesaurus" /></option>
</aui:select>

<aui:button name="submit" type="submit" value="find" />

</aui:field-wrapper>

</aui:form>

<aui:script>
	Liferay.provide(
		window,
		'<portlet:namespace />dictionary',
		function() {
			window.open(document.<portlet:namespace />fm.<portlet:namespace />type[document.<portlet:namespace />fm.<portlet:namespace />type.selectedIndex].value + encodeURIComponent(document.<portlet:namespace />fm.<portlet:namespace />word.value));
			return false;
		},
		['liferay-util-list-fields']
	);
</aui:script>
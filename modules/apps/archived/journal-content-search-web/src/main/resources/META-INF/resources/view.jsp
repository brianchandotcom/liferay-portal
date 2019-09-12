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

<portlet:renderURL var="backURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:renderURL>

<portlet:renderURL var="searchURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcPath" value="/search.jsp" />
	<portlet:param name="backURL" value="<%= backURL %>" />

	<c:if test="<%= Validator.isNotNull(journalContentSearchPortletInstanceConfiguration.targetPortletId()) %>">
		<portlet:param name="targetPortletId" value="<%= journalContentSearchPortletInstanceConfiguration.targetPortletId() %>" />
	</c:if>
</portlet:renderURL>

<aui:form action="<%= searchURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "search(); event.preventDefault();" %>'>
	<div class="form-search">
		<liferay-ui:input-search
			name="keywords"
			placeholder='<%= LanguageUtil.get(request, "keywords") %>'
		/>
	</div>
</aui:form>

<aui:script>
	function <portlet:namespace />search() {
		var keywords =
			document.<portlet:namespace />fm.<portlet:namespace />keywords.value;

		keywords = keywords.replace(/^\s+|\s+$/, '');

		if (keywords != '') {
			submitForm(document.<portlet:namespace />fm);
		}
	}
</aui:script>
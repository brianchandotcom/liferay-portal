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

<portlet:renderURL var="searchURL" windowState="<%= WindowState.MAXIMIZED.toString() %>">
	<portlet:param name="mvcPath" value="/search.jsp" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
	<portlet:param name="showListed" value="<%= String.valueOf(showListed) %>" />

	<c:if test="<%= Validator.isNotNull(targetPortletId) %>">
		<portlet:param name="targetPortletId" value="<%= targetPortletId %>" />
	</c:if>
</portlet:renderURL>

<aui:form action="<%= searchURL %>" method="post" name="fm">
	<div class="form-search">
		<liferay-ui:input-search name="keywords" placeholder='<%= LanguageUtil.get(request, "keywords") %>' />
	</div>
</aui:form>
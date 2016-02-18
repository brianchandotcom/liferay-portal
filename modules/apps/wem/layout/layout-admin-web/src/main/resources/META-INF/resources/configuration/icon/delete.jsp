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

<liferay-ui:icon
	id="delete"
	message="delete"
	url="javascript:;"
/>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();

PortletURL deleteRedirectURL = PortletURLUtil.clone(layoutsAdminDisplayContext.getRedirectURL(), renderResponse);

deleteRedirectURL.setParameter("selPlid", String.valueOf(selLayout.getParentPlid()));
%>

<portlet:actionURL name="deleteLayout" var="deleteLayoutURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
	<portlet:param name="redirect" value="<%= deleteRedirectURL.toString() %>" />
	<portlet:param name="selPlid" value="<%= String.valueOf(layoutsAdminDisplayContext.getSelPlid()) %>" />
</portlet:actionURL>

<aui:script use="aui-base">
	A.one('#<portlet:namespace />delete').on(
		'click',
		function() {
			if (confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
				submitForm(document.hrefFm, '<%= deleteLayoutURL %>');
			}
		}
	);
</aui:script>
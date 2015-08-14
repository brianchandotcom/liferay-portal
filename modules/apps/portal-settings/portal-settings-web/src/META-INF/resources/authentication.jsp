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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<liferay-ui:error-marker key="errorSection" value="authentication" />

<h3><liferay-ui:message key="authentication" /></h3>

<liferay-ui:tabs
	names="<%= StringUtil.merge(PropsValues.COMPANY_SETTINGS_FORM_AUTHENTICATION) %>"
	refresh="<%= false %>"
>

	<%
	for (String section : PropsValues.COMPANY_SETTINGS_FORM_AUTHENTICATION) {
	%>

		<liferay-ui:section>
			<liferay-util:include page='<%= "/html/portlet/portal_settings/authentication/" + _getSectionJsp(section) + ".jsp" %>' portletId="<%= portletDisplay.getRootPortletId() %>" />
		</liferay-ui:section>

	<%
	}
	%>

</liferay-ui:tabs>

<%!
private String _getSectionJsp(String name) {
	return TextFormatter.format(name, TextFormatter.N);
}
%>
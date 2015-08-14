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

<%
String adminAnalyticsTypes = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_ANALYTICS_TYPES);
%>

<h3><liferay-ui:message key="analytics" /></h3>

<aui:fieldset>
	<aui:input label="enter-one-analytics-system-name-per-line" name='<%= "settings--" + PropsKeys.ADMIN_ANALYTICS_TYPES + "--" %>' type="textarea" value="<%= adminAnalyticsTypes %>" />
</aui:fieldset>
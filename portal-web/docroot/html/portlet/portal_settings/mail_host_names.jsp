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

<%@ include file="/html/portlet/portal_settings/init.jsp" %>

<%
String adminMailHostNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_MAIL_HOST_NAMES);
%>

<h3><liferay-ui:message key="mail-host-names" /></h3>

<aui:fieldset>
	<aui:input label='<%= LanguageUtil.format(pageContext, "enter-one-mail-host-name-per-line-for-all-additional-mail-host-names-besides-x", company.getMx()) %>' name='<%= "settings--" + PropsKeys.ADMIN_MAIL_HOST_NAMES + "--" %>' type="textarea" value="<%= adminMailHostNames %>" />
</aui:fieldset>
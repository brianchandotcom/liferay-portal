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

<%
String adminEmailFromName = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
String adminEmailFromAddress = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />

	<aui:input cssClass="lfr-input-text-container" label="name" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_FROM_NAME + "--" %>' type="text" value="<%= adminEmailFromName %>" />

	<liferay-ui:error exception="<%= EmailAddressException.class %>" message="please-enter-a-valid-email-address" />

	<aui:input cssClass="lfr-input-text-container" label="address" name='<%= "settings--" + PropsKeys.ADMIN_EMAIL_FROM_ADDRESS + "--" %>' type="text" value="<%= adminEmailFromAddress %>">
		<aui:validator name="email" />
	</aui:input>
</aui:fieldset>
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
String adminReservedScreenNames = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_SCREEN_NAMES);
String adminReservedEmailAddresses = PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES);
%>

<aui:fieldset>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<aui:input helpMessage="enter-one-screen-name-per-line-to-reserve-the-screen-name" label="screen-names" name='<%= "settings--" + PropsKeys.ADMIN_RESERVED_SCREEN_NAMES + "--" %>' type="textarea" value="<%= adminReservedScreenNames %>" />

	<aui:input helpMessage="enter-one-user-email-address-per-line-to-reserve-the-user-email-address" label="email-addresses" name='<%= "settings--" + PropsKeys.ADMIN_RESERVED_EMAIL_ADDRESSES + "--" %>' type="textarea" value="<%= adminReservedEmailAddresses %>" />
</aui:fieldset>
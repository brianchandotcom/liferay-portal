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

<%@ include file="/html/taglib/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.exception.ContactNameException" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameDefinition" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameDefinitionFactory" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameField" %><%@
page import="com.liferay.portal.kernel.util.CamelCaseUtil" %>

<%
Object bean = request.getAttribute("liferay-ui:user-name-fields:bean");
Contact selContact = (Contact)request.getAttribute("liferay-ui:user-name-fields:contact");
User selUser = (User)request.getAttribute("liferay-ui:user-name-fields:user");

String languageId = request.getParameter("languageId");

if (Validator.isNull(languageId)) {
	if (selUser != null) {
		languageId = selUser.getLanguageId();
	}
	else {
		languageId = themeDisplay.getLanguageId();
	}
}

Locale userLocale = LocaleUtil.fromLanguageId(languageId);
%>
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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.mail.reader.configuration.MailGroupServiceConfiguration" %><%@
page import="com.liferay.mail.reader.constants.MailConstants" %><%@
page import="com.liferay.mail.reader.constants.MailPortletKeys" %><%@
page import="com.liferay.mail.reader.model.Account" %><%@
page import="com.liferay.mail.reader.model.Attachment" %><%@
page import="com.liferay.mail.reader.model.Folder" %><%@
page import="com.liferay.mail.reader.model.MailFile" %><%@
page import="com.liferay.mail.reader.model.Message" %><%@
page import="com.liferay.mail.reader.model.MessageDisplay" %><%@
page import="com.liferay.mail.reader.model.MessagesDisplay" %><%@
page import="com.liferay.mail.reader.service.AccountLocalServiceUtil" %><%@
page import="com.liferay.mail.reader.service.FolderLocalServiceUtil" %><%@
page import="com.liferay.mail.reader.service.MessageLocalServiceUtil" %><%@
page import="com.liferay.mail.reader.web.internal.util.MailManager" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.json.JSONArray" %><%@
page import="com.liferay.portal.kernel.json.JSONObject" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.kernel.upload.UploadPortletRequest" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %>

<%@ page import="java.io.File" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %>

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
MailGroupServiceConfiguration mailGroupServiceConfiguration = ConfigurationProviderUtil.getCompanyConfiguration(MailGroupServiceConfiguration.class, themeDisplay.getCompanyId());

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);
%>
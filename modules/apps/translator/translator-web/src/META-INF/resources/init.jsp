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

<%@ taglib uri="/META-INF/aui.tld" prefix="aui" %>
<%@ taglib uri="/META-INF/c.tld" prefix="c" %>
<%@ taglib uri="/META-INF/liferay-portlet-ext.tld" prefix="liferay-portlet" %>
<%@ taglib uri="/META-INF/liferay-portlet_2_0.tld" prefix="portlet" %>
<%@ taglib uri="/META-INF/liferay-theme.tld" prefix="liferay-theme" %>
<%@ taglib uri="/META-INF/liferay-ui.tld" prefix="liferay-ui" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.microsofttranslator.MicrosoftTranslatorException" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.translator.web.configuration.TranslatorConfiguration" %><%@
page import="com.liferay.translator.web.model.Translation" %><%@
page import="com.liferay.translator.web.util.TranslatorUtil" %>

<%@ page import="javax.portlet.WindowState" %>

<%@ page import="java.util.Locale" %><%@
page import="java.util.Map" %>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<%
TranslatorConfiguration translatorConfiguration = (TranslatorConfiguration)request.getAttribute(TranslatorConfiguration.class.getName());

WindowState windowState = liferayPortletRequest.getWindowState();
%>

<%@ include file="/init-ext.jsp" %>
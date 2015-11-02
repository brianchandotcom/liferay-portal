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

<%@ page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration" %><%@
page import="com.liferay.portal.ldap.configuration.LDAPServerConfiguration" %><%@
page import="com.liferay.portal.ldap.constants.LDAPConstants" %><%@
page import="com.liferay.portal.ldap.exportimport.configuration.LDAPExportConfiguration" %><%@
page import="com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration" %><%@
page import="com.liferay.portal.ldap.internal.portal.settings.web.portlet.util.ConfigurationProviderUtil" %><%@
page import="com.liferay.portlet.PortletURLUtil" %>

<%@ page import="java.util.List" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletURL" %>

<portlet:defineObjects/>

<liferay-theme:defineObjects />

<%
PortletURL currentURLObj = PortletURLUtil.getCurrent(liferayPortletRequest, liferayPortletResponse);

String currentURL = currentURLObj.toString();

LDAPAuthConfiguration ldapAuthConfiguration = ConfigurationProviderUtil.getLDAPAuthConfigurationProvider().getConfiguration(themeDisplay.getCompanyId());
LDAPExportConfiguration ldapExportConfiguration = ConfigurationProviderUtil.getLDAPExportConfigurationProvider().getConfiguration(themeDisplay.getCompanyId());
LDAPImportConfiguration ldapImportConfiguration = ConfigurationProviderUtil.getLDAPImportConfigurationProvider().getConfiguration(themeDisplay.getCompanyId());
List<LDAPServerConfiguration> ldapServerConfigurations = ConfigurationProviderUtil.getLDAPServerConfigurationProvider().getConfigurations(themeDisplay.getCompanyId(), false);
%>
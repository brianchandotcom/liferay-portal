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
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil" %><%@
page import="com.liferay.portal.kernel.ldap.DuplicateLDAPServerNameException" %><%@
page import="com.liferay.portal.kernel.ldap.LDAPFilterException" %><%@
page import="com.liferay.portal.kernel.ldap.LDAPServerNameException" %><%@
page import="com.liferay.portal.kernel.ldap.LDAPUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameDefinition" %><%@
page import="com.liferay.portal.kernel.security.auth.FullNameDefinitionFactory" %><%@
page import="com.liferay.portal.kernel.security.ldap.PortalLDAPUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PropertiesUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.ldap.authenticator.configuration.LDAPAuthConfiguration" %><%@
page import="com.liferay.portal.ldap.configuration.ConfigurationProvider" %><%@
page import="com.liferay.portal.ldap.configuration.LDAPServerConfiguration" %><%@
page import="com.liferay.portal.ldap.constants.LDAPConstants" %><%@
page import="com.liferay.portal.ldap.exportimport.configuration.LDAPExportConfiguration" %><%@
page import="com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration" %><%@
page import="com.liferay.portal.ldap.internal.portal.settings.web.portlet.util.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.util.Portal" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PropsValues" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.List" %><%@
page import="java.util.Properties" %>

<%@ page import="javax.naming.directory.Attribute" %><%@
page import="javax.naming.directory.Attributes" %><%@
page import="javax.naming.directory.SearchResult" %><%@
page import="javax.naming.ldap.LdapContext" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
ConfigurationProvider<LDAPAuthConfiguration> ldapAuthConfigurationProvider = ConfigurationProviderUtil.getLDAPAuthConfigurationProvider();

LDAPAuthConfiguration ldapAuthConfiguration = ldapAuthConfigurationProvider.getConfiguration(themeDisplay.getCompanyId());

ConfigurationProvider<LDAPExportConfiguration> ldapExportConfigurationProvider = ConfigurationProviderUtil.getLDAPExportConfigurationProvider();

LDAPExportConfiguration ldapExportConfiguration = ldapExportConfigurationProvider.getConfiguration(themeDisplay.getCompanyId());

ConfigurationProvider<LDAPImportConfiguration> ldapImportConfigurationProvider = ConfigurationProviderUtil.getLDAPImportConfigurationProvider();

LDAPImportConfiguration ldapImportConfiguration = ldapImportConfigurationProvider.getConfiguration(themeDisplay.getCompanyId());

ConfigurationProvider<LDAPServerConfiguration> ldapServerConfigurationProvider = ConfigurationProviderUtil.getLDAPServerConfigurationProvider();

List<LDAPServerConfiguration> ldapServerConfigurations = ldapServerConfigurationProvider.getConfigurations(themeDisplay.getCompanyId(), false);
%>
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.AccountNameException" %><%@
page import="com.liferay.portal.CompanyMxException" %><%@
page import="com.liferay.portal.CompanyVirtualHostException" %><%@
page import="com.liferay.portal.kernel.facebook.FacebookConnectUtil" %><%@
page import="com.liferay.portal.kernel.ldap.DuplicateLDAPServerNameException" %><%@
page import="com.liferay.portal.kernel.ldap.LDAPFilterException" %><%@
page import="com.liferay.portal.kernel.ldap.LDAPServerNameException" %><%@
page import="com.liferay.portal.security.auth.AuthSettingsUtil" %><%@
page import="com.liferay.portal.security.ldap.LDAPSettingsUtil" %><%@
page import="com.liferay.portal.security.ldap.PortalLDAPUtil" %><%@
page import="com.liferay.portal.servlet.filters.sso.opensso.OpenSSOUtil" %><%@
page import="com.liferay.portlet.documentlibrary.NoSuchFileException" %><%@
page import="com.liferay.util.ldap.LDAPUtil" %>

<%@ page import="java.net.HttpURLConnection" %><%@
page import="java.net.MalformedURLException" %><%@
page import="java.net.URL" %>

<%@ page import="javax.naming.directory.Attribute" %><%@
page import="javax.naming.directory.Attributes" %><%@
page import="javax.naming.directory.SearchResult" %><%@
page import="javax.naming.ldap.LdapContext" %>

<%@ include file="/html/portlet/portal_settings/init-ext.jsp" %>
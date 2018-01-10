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
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@
page import="com.liferay.portal.util.PropsValues" %>
<%@ page import="com.liferay.portal.kernel.model.Group" %>
<%@ page
        import="com.liferay.commerce.user.web.internal.display.context.CommerceOrganizationDetailDisplayContext" %>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ page import="com.liferay.portal.kernel.model.Organization" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page
        import="com.liferay.portal.kernel.exception.DuplicateOrganizationException" %>
<%@ page
        import="com.liferay.portal.kernel.exception.OrganizationNameException" %>
<%@ page import="com.liferay.portal.kernel.model.OrganizationConstants" %>
<%@ page import="javax.portlet.WindowState" %>
<%@ page import="com.liferay.portal.kernel.model.ListTypeConstants" %>
<%@ page import="com.liferay.portal.kernel.exception.NoSuchListTypeException" %>
<%@ page import="com.liferay.portal.kernel.bean.BeanParamUtil" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.exception.NoSuchCountryException" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.util.PropsUtil" %>
<%@ page import="com.liferay.portal.kernel.util.PropsKeys" %>
<%@ page import="com.liferay.portal.kernel.configuration.Filter" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page
        import="com.liferay.users.admin.configuration.UserFileUploadsConfiguration" %>
<%@ page
        import="com.liferay.portal.kernel.webserver.WebServerServletTokenUtil" %>
<%@ page import="com.liferay.portal.kernel.exception.AddressCityException" %>
<%@ page import="com.liferay.portal.kernel.exception.AddressStreetException" %>
<%@ page import="com.liferay.portal.kernel.exception.AddressZipException" %>
<%@ page import="com.liferay.portal.kernel.exception.NoSuchRegionException" %>
<%@ page import="com.liferay.portal.kernel.model.Address" %>
<%@ page import="com.liferay.portal.kernel.exception.EmailAddressException" %>
<%@ page import="com.liferay.portal.kernel.model.EmailAddress" %>
<%@ page
        import="com.liferay.commerce.user.web.internal.servlet.taglib.ui.CommerceUserScreenNavigationConstants" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

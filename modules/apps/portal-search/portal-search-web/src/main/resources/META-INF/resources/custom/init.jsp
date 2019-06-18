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

<%@ page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetTermDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.custom.facet.portlet.CustomFacetPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferences" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.CustomFilterPortletPreferencesImpl" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.ConfigurationDisplayContext" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.OccurEntriesHolder" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.OccurEntry" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.QueryTypeEntriesHolder" %><%@
page import="com.liferay.portal.search.web.internal.custom.filter.portlet.action.QueryTypeEntry" %><%@
page import="com.liferay.portal.search.web.internal.util.PortletPreferencesJspUtil" %>

<%@ page import="java.util.Objects" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />
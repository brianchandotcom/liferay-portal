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

<%@ page import="com.liferay.portal.search.web.internal.custom.facet.display.context.CustomFacetDisplayContext" %><%@
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

<%@ taglib uri="http://liferay.com/tld/clay" prefix="clay" %>
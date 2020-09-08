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

<%@ taglib uri="http://liferay.com/tld/expando" prefix="liferay-expando" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.util.AssetHelper" %><%@
page import="com.liferay.petra.reflect.ReflectionUtil" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.portal.kernel.portlet.PortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.service.ServiceContextFactory" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.URLCodec" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.wiki.constants.WikiPortletKeys" %><%@
page import="com.liferay.wiki.constants.WikiWebKeys" %><%@
page import="com.liferay.wiki.model.WikiPage" %><%@
page import="com.liferay.wiki.model.WikiPageDisplay" %><%@
page import="com.liferay.wiki.service.WikiPageLocalServiceUtil" %>

<%@ page import="java.util.function.Supplier" %>

<%@ page import="javax.portlet.ActionRequest" %><%@
page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %><%@
page import="javax.portlet.WindowState" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />
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
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.application.list.constants.ApplicationListWebKeys" %><%@
page import="com.liferay.application.list.constants.PanelCategoryKeys" %><%@
page import="com.liferay.application.list.display.context.logic.PanelCategoryHelper" %><%@
page import="com.liferay.marketplace.exception.FileExtensionException" %><%@
page import="com.liferay.marketplace.model.App" %><%@
page import="com.liferay.marketplace.service.AppLocalServiceUtil" %><%@
page import="com.liferay.marketplace.util.comparator.PluginComparator" %><%@
page import="com.liferay.portal.kernel.deploy.DeployManagerUtil" %><%@
page import="com.liferay.portal.kernel.plugin.RequiredPluginPackageException" %><%@
page import="com.liferay.portal.kernel.servlet.ServletContextPool" %><%@
page import="com.liferay.portal.kernel.upload.UploadException" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.model.LayoutTemplate" %><%@
page import="com.liferay.portal.model.Plugin" %><%@
page import="com.liferay.portal.model.PluginSetting" %><%@
page import="com.liferay.portal.model.Portlet" %><%@
page import="com.liferay.portal.model.Theme" %><%@
page import="com.liferay.portal.service.CompanyLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PluginSettingLocalServiceUtil" %><%@
page import="com.liferay.portal.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.util.PortalUtil" %><%@
page import="com.liferay.portal.util.PortletKeys" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Iterator" %><%@
page import="java.util.List" %>

<%@ page import="javax.portlet.PortletURL" %>

<%@ page import="javax.servlet.ServletContext" %>

<portlet:defineObjects />

<liferay-theme:defineObjects />
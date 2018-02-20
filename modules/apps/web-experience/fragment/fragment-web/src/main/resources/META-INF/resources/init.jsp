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
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/security" prefix="liferay-security" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<%@ page import="com.liferay.document.library.configuration.DLConfiguration" %><%@
page import="com.liferay.fragment.constants.FragmentActionKeys" %><%@
page import="com.liferay.fragment.exception.DuplicateFragmentCollectionException" %><%@
page import="com.liferay.fragment.exception.DuplicateFragmentCollectionKeyException" %><%@
page import="com.liferay.fragment.exception.DuplicateFragmentEntryKeyException" %><%@
page import="com.liferay.fragment.exception.FragmentCollectionNameException" %><%@
page import="com.liferay.fragment.exception.FragmentEntryContentException" %><%@
page import="com.liferay.fragment.exception.RequiredFragmentEntryException" %><%@
page import="com.liferay.fragment.model.FragmentCollection" %><%@
page import="com.liferay.fragment.model.FragmentEntry" %><%@
page import="com.liferay.fragment.web.internal.constatns.ExportImportConstants" %><%@
page import="com.liferay.fragment.web.internal.display.context.FragmentDisplayContext" %><%@
page import="com.liferay.fragment.web.internal.security.permission.resource.FragmentCollectionPermission" %><%@
page import="com.liferay.fragment.web.internal.security.permission.resource.FragmentEntryPermission" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.upload.UploadServletRequestConfigurationHelperUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.text.DecimalFormatSymbols" %>

<%@ page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.Map" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
FragmentDisplayContext fragmentDisplayContext = new FragmentDisplayContext(renderRequest, renderResponse, request);
%>

<%@ include file="/init-ext.jsp" %>
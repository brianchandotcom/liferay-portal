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

<%@ taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>

<%@ page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.fragment.model.FragmentEntryLink" %><%@
page import="com.liferay.fragment.util.FragmentEntryRenderUtil" %><%@
page import="com.liferay.layout.type.controller.asset.display.internal.constants.AssetDisplayLayoutTypeControllerWebKeys" %><%@
page import="com.liferay.portal.kernel.io.unsync.UnsyncStringWriter" %><%@
page import="com.liferay.portal.kernel.template.StringTemplateResource" %><%@
page import="com.liferay.portal.kernel.template.Template" %><%@
page import="com.liferay.portal.kernel.template.TemplateConstants" %><%@
page import="com.liferay.portal.kernel.template.TemplateManager" %><%@
page import="com.liferay.portal.kernel.template.TemplateManagerUtil" %><%@
page import="com.liferay.portal.kernel.template.TemplateResource" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.StringBundler" %>

<%@ page import="java.util.List" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<%
AssetEntry assetEntry = (AssetEntry)request.getAttribute(AssetDisplayLayoutTypeControllerWebKeys.ASSET_ENTRY);

List<FragmentEntryLink> fragmentEntryLinks = (List<FragmentEntryLink>)request.getAttribute(AssetDisplayLayoutTypeControllerWebKeys.LAYOUT_FRAGMENTS);
%>
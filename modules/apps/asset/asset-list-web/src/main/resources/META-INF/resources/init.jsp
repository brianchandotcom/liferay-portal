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

<%@ taglib uri="http://liferay.com/tld/asset" prefix="liferay-asset" %><%@
taglib uri="http://liferay.com/tld/aui" prefix="aui" %><%@
taglib uri="http://liferay.com/tld/clay" prefix="clay" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/soy" prefix="soy" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.exception.DuplicateQueryRuleException" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.asset.kernel.model.ClassType" %><%@
page import="com.liferay.asset.kernel.model.ClassTypeField" %><%@
page import="com.liferay.asset.kernel.model.ClassTypeReader" %><%@
page import="com.liferay.asset.kernel.service.AssetEntryServiceUtil" %><%@
page import="com.liferay.asset.kernel.util.comparator.AssetRendererFactoryTypeNameComparator" %><%@
page import="com.liferay.asset.list.constants.AssetListEntryTypeConstants" %><%@
page import="com.liferay.asset.list.constants.AssetListFormConstants" %><%@
page import="com.liferay.asset.list.model.AssetListEntry" %><%@
page import="com.liferay.asset.list.model.AssetListEntryAssetEntryRel" %><%@
page import="com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel" %><%@
page import="com.liferay.asset.list.web.internal.display.context.AssetListDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.display.context.AssetListEntryUsagesDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.display.context.AssetListEntryUsagesManagementToolbarDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.display.context.AssetListManagementToolbarDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.display.context.EditAssetListDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.display.context.SelectAssetListDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.display.context.SelectAssetListManagementToolbarDisplayContext" %><%@
page import="com.liferay.asset.list.web.internal.security.permission.resource.AssetListEntryPermission" %><%@
page import="com.liferay.asset.list.web.internal.servlet.taglib.util.AssetEntryListActionDropdownItems" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.dynamic.data.mapping.storage.Field" %><%@
page import="com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchModelException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.model.ClassName" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.UnicodeFormatter" %><%@
page import="com.liferay.portal.kernel.util.UnicodeProperties" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.segments.constants.SegmentsEntryConstants" %><%@
page import="com.liferay.segments.model.SegmentsEntry" %><%@
page import="com.liferay.taglib.search.ResultRow" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
AssetListDisplayContext assetListDisplayContext = new AssetListDisplayContext(renderRequest, renderResponse);

UnicodeProperties properties = new UnicodeProperties();

AssetListEntry curAssetListEntry = assetListDisplayContext.getAssetListEntry();

if (curAssetListEntry != null) {
	properties.load(curAssetListEntry.getTypeSettings(assetListDisplayContext.getSegmentsEntryId()));
}

EditAssetListDisplayContext editAssetListDisplayContext = new EditAssetListDisplayContext(renderRequest, renderResponse, properties);
%>
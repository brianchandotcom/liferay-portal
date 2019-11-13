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
taglib uri="http://liferay.com/tld/comment" prefix="liferay-comment" %><%@
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/flags" prefix="liferay-flags" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/react" prefix="react" %><%@
taglib uri="http://liferay.com/tld/rss" prefix="liferay-rss" %><%@
taglib uri="http://liferay.com/tld/site" prefix="liferay-site" %><%@
taglib uri="http://liferay.com/tld/social-bookmarks" prefix="liferay-social-bookmarks" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.constants.AssetWebKeys" %><%@
page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.exception.DuplicateQueryRuleException" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.asset.kernel.model.AssetVocabulary" %><%@
page import="com.liferay.asset.kernel.model.ClassType" %><%@
page import="com.liferay.asset.kernel.model.ClassTypeField" %><%@
page import="com.liferay.asset.kernel.model.ClassTypeReader" %><%@
page import="com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.asset.list.model.AssetListEntry" %><%@
page import="com.liferay.asset.publisher.constants.AssetPublisherConstants" %><%@
page import="com.liferay.asset.publisher.constants.AssetPublisherPortletKeys" %><%@
page import="com.liferay.asset.publisher.constants.AssetPublisherWebKeys" %><%@
page import="com.liferay.asset.publisher.util.AssetEntryResult" %><%@
page import="com.liferay.asset.publisher.util.AssetPublisherHelper" %><%@
page import="com.liferay.asset.publisher.web.internal.display.context.AssetPublisherDisplayContext" %><%@
page import="com.liferay.asset.publisher.web.internal.display.context.AssetPublisherViewContentDisplayContext" %><%@
page import="com.liferay.asset.publisher.web.internal.display.context.ItemSelectorViewDisplayContext" %><%@
page import="com.liferay.asset.publisher.web.internal.servlet.taglib.util.AssetEntryActionDropdownItemsProvider" %><%@
page import="com.liferay.asset.publisher.web.internal.util.AssetPublisherWebUtil" %><%@
page import="com.liferay.asset.publisher.web.internal.util.AssetRendererUtil" %><%@
page import="com.liferay.asset.util.AssetHelper" %><%@
page import="com.liferay.asset.util.AssetPublisherAddItemHolder" %><%@
page import="com.liferay.asset.util.comparator.AssetRendererFactoryTypeNameComparator" %><%@
page import="com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem" %><%@
page import="com.liferay.info.list.provider.InfoListProvider" %><%@
page import="com.liferay.item.selector.ItemSelector" %><%@
page import="com.liferay.item.selector.ItemSelectorCriterion" %><%@
page import="com.liferay.petra.string.StringBundler" %><%@
page import="com.liferay.petra.string.StringPool" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchGroupException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchModelException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.model.ClassName" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil" %><%@
page import="com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandler" %><%@
page import="com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatConstants" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portal.util.PropsValues" %><%@
page import="com.liferay.portlet.usersadmin.search.GroupSearch" %><%@
page import="com.liferay.rss.util.RSSUtil" %><%@
page import="com.liferay.site.item.selector.criteria.SiteItemSelectorReturnType" %><%@
page import="com.liferay.site.item.selector.criterion.SiteItemSelectorCriterion" %><%@
page import="com.liferay.taglib.aui.AUIUtil" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.Date" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
AssetHelper assetHelper = (AssetHelper)request.getAttribute(AssetWebKeys.ASSET_HELPER);
AssetPublisherDisplayContext assetPublisherDisplayContext = (AssetPublisherDisplayContext)request.getAttribute(AssetPublisherWebKeys.ASSET_PUBLISHER_DISPLAY_CONTEXT);
AssetPublisherHelper assetPublisherHelper = (AssetPublisherHelper)request.getAttribute(AssetPublisherWebKeys.ASSET_PUBLISHER_HELPER);
AssetPublisherWebUtil assetPublisherWebUtil = (AssetPublisherWebUtil)request.getAttribute(AssetPublisherWebKeys.ASSET_PUBLISHER_WEB_UTIL);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(FastDateFormatConstants.LONG, FastDateFormatConstants.SHORT, locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>
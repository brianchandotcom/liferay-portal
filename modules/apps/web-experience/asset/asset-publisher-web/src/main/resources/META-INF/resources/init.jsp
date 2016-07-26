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
taglib uri="http://liferay.com/tld/ddm" prefix="liferay-ddm" %><%@
taglib uri="http://liferay.com/tld/flags" prefix="liferay-flags" %><%@
taglib uri="http://liferay.com/tld/frontend" prefix="liferay-frontend" %><%@
taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %><%@
taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %><%@
taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %><%@
taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil" %><%@
page import="com.liferay.asset.kernel.exception.DuplicateQueryRuleException" %><%@
page import="com.liferay.asset.kernel.model.AssetCategory" %><%@
page import="com.liferay.asset.kernel.model.AssetEntry" %><%@
page import="com.liferay.asset.kernel.model.AssetRenderer" %><%@
page import="com.liferay.asset.kernel.model.AssetRendererFactory" %><%@
page import="com.liferay.asset.kernel.model.AssetVocabulary" %><%@
page import="com.liferay.asset.kernel.model.ClassType" %><%@
page import="com.liferay.asset.kernel.model.ClassTypeField" %><%@
page import="com.liferay.asset.kernel.model.ClassTypeReader" %><%@
page import="com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.asset.kernel.util.AssetEntryQueryProcessor" %><%@
page import="com.liferay.asset.kernel.util.comparator.AssetRendererFactoryTypeNameComparator" %><%@
page import="com.liferay.asset.publisher.web.constants.AssetPublisherPortletKeys" %><%@
page import="com.liferay.asset.publisher.web.display.context.AssetEntryResult" %><%@
page import="com.liferay.asset.publisher.web.display.context.AssetPublisherDisplayContext" %><%@
page import="com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfigurationValues" %><%@
page import="com.liferay.asset.publisher.web.util.AssetPublisherHelper" %><%@
page import="com.liferay.asset.publisher.web.util.AssetPublisherUtil" %><%@
page import="com.liferay.document.library.kernel.util.DLUtil" %><%@
page import="com.liferay.dynamic.data.mapping.model.DDMStructure" %><%@
page import="com.liferay.petra.content.ContentUtil" %><%@
page import="com.liferay.portal.kernel.dao.search.ResultRow" %><%@
page import="com.liferay.portal.kernel.dao.search.SearchContainer" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchGroupException" %><%@
page import="com.liferay.portal.kernel.exception.NoSuchModelException" %><%@
page import="com.liferay.portal.kernel.language.LanguageUtil" %><%@
page import="com.liferay.portal.kernel.log.Log" %><%@
page import="com.liferay.portal.kernel.log.LogFactoryUtil" %><%@
page import="com.liferay.portal.kernel.model.ClassName" %><%@
page import="com.liferay.portal.kernel.model.Group" %><%@
page import="com.liferay.portal.kernel.model.Layout" %><%@
page import="com.liferay.portal.kernel.model.Portlet" %><%@
page import="com.liferay.portal.kernel.portlet.LiferayWindowState" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProvider" %><%@
page import="com.liferay.portal.kernel.portlet.PortletProviderUtil" %><%@
page import="com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager" %><%@
page import="com.liferay.portal.kernel.security.permission.ActionKeys" %><%@
page import="com.liferay.portal.kernel.security.permission.ResourceActionsUtil" %><%@
page import="com.liferay.portal.kernel.service.ClassNameLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.GroupLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.PortletLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.service.permission.PortletPermissionUtil" %><%@
page import="com.liferay.portal.kernel.servlet.SessionErrors" %><%@
page import="com.liferay.portal.kernel.servlet.SessionMessages" %><%@
page import="com.liferay.portal.kernel.util.ArrayUtil" %><%@
page import="com.liferay.portal.kernel.util.Constants" %><%@
page import="com.liferay.portal.kernel.util.DocumentConversionUtil" %><%@
page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil" %><%@
page import="com.liferay.portal.kernel.util.GetterUtil" %><%@
page import="com.liferay.portal.kernel.util.HtmlUtil" %><%@
page import="com.liferay.portal.kernel.util.HttpUtil" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePair" %><%@
page import="com.liferay.portal.kernel.util.KeyValuePairComparator" %><%@
page import="com.liferay.portal.kernel.util.ListUtil" %><%@
page import="com.liferay.portal.kernel.util.LocaleUtil" %><%@
page import="com.liferay.portal.kernel.util.LocalizationUtil" %><%@
page import="com.liferay.portal.kernel.util.MapUtil" %><%@
page import="com.liferay.portal.kernel.util.ParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PortalUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsParamUtil" %><%@
page import="com.liferay.portal.kernel.util.PrefsPropsUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.RSSUtil" %><%@
page import="com.liferay.portal.kernel.util.SetUtil" %><%@
page import="com.liferay.portal.kernel.util.StringPool" %><%@
page import="com.liferay.portal.kernel.util.StringUtil" %><%@
page import="com.liferay.portal.kernel.util.TextFormatter" %><%@
page import="com.liferay.portal.kernel.util.Validator" %><%@
page import="com.liferay.portal.kernel.util.WebKeys" %><%@
page import="com.liferay.portal.kernel.workflow.WorkflowConstants" %><%@
page import="com.liferay.portlet.asset.util.AssetUtil" %><%@
page import="com.liferay.taglib.servlet.PipingServletResponse" %>

<%@ page import="java.io.Serializable" %>

<%@ page import="java.text.Format" %>

<%@ page import="java.util.ArrayList" %><%@
page import="java.util.Arrays" %><%@
page import="java.util.HashMap" %><%@
page import="java.util.HashSet" %><%@
page import="java.util.List" %><%@
page import="java.util.Map" %><%@
page import="java.util.Objects" %><%@
page import="java.util.Set" %>

<%@ page import="javax.portlet.PortletMode" %><%@
page import="javax.portlet.PortletRequest" %><%@
page import="javax.portlet.PortletURL" %>

<liferay-frontend:defineObjects />

<liferay-theme:defineObjects />

<portlet:defineObjects />

<%
AssetPublisherDisplayContext assetPublisherDisplayContext = new AssetPublisherDisplayContext(liferayPortletRequest, liferayPortletResponse, portletPreferences);

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/init-ext.jsp" %>
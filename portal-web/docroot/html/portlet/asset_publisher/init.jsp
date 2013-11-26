<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portlet/init.jsp" %>

<%@ page import="com.liferay.portal.NoSuchModelException" %><%@
page import="com.liferay.portal.kernel.util.DateFormatFactoryUtil" %><%@
page import="com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil" %><%@
page import="com.liferay.portlet.asset.DuplicateQueryRuleException" %><%@
page import="com.liferay.portlet.asset.NoSuchTagException" %><%@
page import="com.liferay.portlet.asset.NoSuchTagPropertyException" %><%@
page import="com.liferay.portlet.asset.model.AssetTagProperty" %><%@
page import="com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil" %><%@
page import="com.liferay.portlet.assetpublisher.search.AssetDisplayTerms" %><%@
page import="com.liferay.portlet.assetpublisher.search.AssetSearch" %><%@
page import="com.liferay.portlet.assetpublisher.util.AssetPublisherHelperUtil" %><%@
page import="com.liferay.portlet.assetpublisher.util.AssetPublisherUtil" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMImpl" %><%@
page import="com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil" %><%@
page import="com.liferay.portlet.messageboards.model.MBDiscussion" %><%@
page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateConstants" %><%@
page import="com.liferay.util.RSSUtil" %>

<%
String portletResource = ParamUtil.getString(request, "portletResource");

String selectionStyle = GetterUtil.getString(portletPreferences.getValue("selectionStyle", null), "dynamic");

long[] groupIds = AssetPublisherUtil.getGroupIds(portletPreferences, scopeGroupId, layout);

long[] classTypeIds = GetterUtil.getLongValues(portletPreferences.getValues("classTypeIds", null));

boolean showOnlyLayoutAssets = GetterUtil.getBoolean(portletPreferences.getValue("showOnlyLayoutAssets", null));

boolean mergeUrlTags = GetterUtil.getBoolean(portletPreferences.getValue("mergeUrlTags", null), true);
boolean mergeLayoutTags = GetterUtil.getBoolean(portletPreferences.getValue("mergeLayoutTags", null), false);

String displayStyle = GetterUtil.getString(portletPreferences.getValue("displayStyle", PropsValues.ASSET_PUBLISHER_DISPLAY_STYLE_DEFAULT));
long displayStyleGroupId = GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId());

boolean showAddContentButton = GetterUtil.getBoolean(portletPreferences.getValue("showAddContentButton", null), true);
boolean showAssetTitle = GetterUtil.getBoolean(portletPreferences.getValue("showAssetTitle", null), true);
boolean showContextLink = GetterUtil.getBoolean(portletPreferences.getValue("showContextLink", null), true);
int abstractLength = GetterUtil.getInteger(portletPreferences.getValue("abstractLength", null), 200);
String orderByColumn1 = GetterUtil.getString(portletPreferences.getValue("orderByColumn1", "modifiedDate"));
String orderByColumn2 = GetterUtil.getString(portletPreferences.getValue("orderByColumn2", "title"));
String orderByType1 = GetterUtil.getString(portletPreferences.getValue("orderByType1", "DESC"));
String orderByType2 = GetterUtil.getString(portletPreferences.getValue("orderByType2", "ASC"));
boolean excludeZeroViewCount = GetterUtil.getBoolean(portletPreferences.getValue("excludeZeroViewCount", null));

int delta = GetterUtil.getInteger(portletPreferences.getValue("delta", null), SearchContainer.DEFAULT_DELTA);

if (portletName.equals(PortletKeys.RECENT_CONTENT)) {
	delta = PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS;
}

String paginationType = GetterUtil.getString(portletPreferences.getValue("paginationType", "none"));

boolean showAvailableLocales = GetterUtil.getBoolean(portletPreferences.getValue("showAvailableLocales", null));
boolean showMetadataDescriptions = GetterUtil.getBoolean(portletPreferences.getValue("showMetadataDescriptions", null), true);

boolean enableRelatedAssets = GetterUtil.getBoolean(portletPreferences.getValue("enableRelatedAssets", null), true);
boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null));
boolean enableComments = GetterUtil.getBoolean(portletPreferences.getValue("enableComments", null));
boolean enableCommentRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableCommentRatings", null));
boolean enableTagBasedNavigation = GetterUtil.getBoolean(portletPreferences.getValue("enableTagBasedNavigation", null));

String[] extensions = portletPreferences.getValues("extensions", new String[0]);
boolean openOfficeServerEnabled = PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED);
boolean enablePrint = GetterUtil.getBoolean(portletPreferences.getValue("enablePrint", null));
boolean enableFlags = GetterUtil.getBoolean(portletPreferences.getValue("enableFlags", null));

String[] metadataFields = StringUtil.split(portletPreferences.getValue("metadataFields", StringPool.BLANK));

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/asset_publisher/init-ext.jsp" %>
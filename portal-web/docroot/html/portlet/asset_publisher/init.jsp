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

long[] availableClassNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(company.getCompanyId(), true);

long[] classNameIds = AssetPublisherUtil.getClassNameIds(portletPreferences, availableClassNameIds);

long[] classTypeIds = GetterUtil.getLongValues(portletPreferences.getValues("classTypeIds", null));

AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

long[] allAssetCategoryIds = new long[0];
String[] allAssetTagNames = new String[0];

String ddmStructureDisplayFieldValue = StringPool.BLANK;
String ddmStructureFieldLabel = StringPool.BLANK;
String ddmStructureFieldName = StringPool.BLANK;
Serializable ddmStructureFieldValue = null;

if (selectionStyle.equals("dynamic")) {
	if (!ArrayUtil.contains(groupIds, scopeGroupId)) {
		assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(portletPreferences, ArrayUtil.append(groupIds, scopeGroupId));
	}
	else {
		assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(portletPreferences, groupIds);
	}

	allAssetTagNames = AssetPublisherUtil.getAssetTagNames(portletPreferences, scopeGroupId);

	assetEntryQuery.setClassTypeIds(classTypeIds);

	boolean subtypeFieldsFilterEnabled = GetterUtil.getBoolean(portletPreferences.getValue("subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));

	if (subtypeFieldsFilterEnabled && (classNameIds.length == 1) && (classTypeIds.length == 1)) {
		AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(classNameIds[0]));

		ddmStructureDisplayFieldValue = GetterUtil.getString(portletPreferences.getValue("ddmStructureDisplayFieldValue", StringPool.BLANK));
		ddmStructureFieldName = GetterUtil.getString(portletPreferences.getValue("ddmStructureFieldName", StringPool.BLANK));
		ddmStructureFieldValue = portletPreferences.getValue("ddmStructureFieldValue", StringPool.BLANK);

		if (Validator.isNotNull(ddmStructureFieldName) && Validator.isNotNull(ddmStructureFieldValue)) {
			Tuple classTypeFieldName = assetRendererFactory.getClassTypeFieldName(classTypeIds[0], ddmStructureFieldName, locale);

			ddmStructureFieldLabel = (String)classTypeFieldName.getObject(0);

			long ddmStructureId = GetterUtil.getLong(classTypeFieldName.getObject(3));

			assetEntryQuery.setAttribute("ddmStructureFieldName", DDMIndexerUtil.encodeName(ddmStructureId, ddmStructureFieldName, locale));
			assetEntryQuery.setAttribute("ddmStructureFieldValue", ddmStructureFieldValue);
		}
	}

	AssetPublisherUtil.processAssetEntryQuery(user, portletPreferences, assetEntryQuery);
}

long assetCategoryId = ParamUtil.getLong(request, "categoryId");

if (assetCategoryId > 0) {
	if (selectionStyle.equals("dynamic")) {
		allAssetCategoryIds = assetEntryQuery.getAllCategoryIds();

		if (!ArrayUtil.contains(allAssetCategoryIds, assetCategoryId)) {
			assetEntryQuery.setAllCategoryIds(ArrayUtil.append(allAssetCategoryIds, assetCategoryId));
		}
	}
	else if (selectionStyle.equals("manual")) {
		allAssetCategoryIds = ArrayUtil.append(allAssetCategoryIds, assetCategoryId);
	}

	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.getCategory(assetCategoryId);

	assetCategory = assetCategory.toEscapedModel();

	PortalUtil.setPageKeywords(assetCategory.getTitle(locale), request);
}

String assetTagName = ParamUtil.getString(request, "tag");

if (Validator.isNotNull(assetTagName)) {
	allAssetTagNames = new String[] {assetTagName};

	long[] assetTagIds = AssetTagLocalServiceUtil.getTagIds(groupIds, allAssetTagNames);

	assetEntryQuery.setAnyTagIds(assetTagIds);

	PortalUtil.setPageKeywords(assetTagName, request);
}

boolean showOnlyLayoutAssets = GetterUtil.getBoolean(portletPreferences.getValue("showOnlyLayoutAssets", null));

if (showOnlyLayoutAssets) {
	assetEntryQuery.setLayout(layout);
}

if (portletName.equals(PortletKeys.RELATED_ASSETS)) {
	AssetEntry layoutAssetEntry = (AssetEntry)request.getAttribute(WebKeys.LAYOUT_ASSET_ENTRY);

	if (layoutAssetEntry != null) {
		assetEntryQuery.setLinkedAssetEntryId(layoutAssetEntry.getEntryId());
	}
}

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

assetEntryQuery.setPaginationType(paginationType);

boolean showAvailableLocales = GetterUtil.getBoolean(portletPreferences.getValue("showAvailableLocales", null));
boolean showMetadataDescriptions = GetterUtil.getBoolean(portletPreferences.getValue("showMetadataDescriptions", null), true);

assetEntryQuery.setEnablePermissions(AssetUtil.isEnablePermissions(portletPreferences, portletName));

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
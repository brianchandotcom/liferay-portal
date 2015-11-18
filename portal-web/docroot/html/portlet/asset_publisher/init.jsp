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
page import="com.liferay.portal.kernel.util.PredicateFilter" %><%@
page import="com.liferay.portlet.asset.DuplicateQueryRuleException" %><%@
page import="com.liferay.portlet.asset.NoSuchTagException" %><%@
page import="com.liferay.portlet.asset.NoSuchTagPropertyException" %><%@
page import="com.liferay.portlet.asset.model.AssetTagProperty" %><%@
page import="com.liferay.portlet.asset.service.AssetTagPropertyLocalServiceUtil" %><%@
page import="com.liferay.portlet.assetpublisher.search.AssetDisplayTerms" %><%@
page import="com.liferay.portlet.assetpublisher.search.AssetSearch" %><%@
page import="com.liferay.portlet.assetpublisher.util.AssetPublisherHelperImpl" %><%@
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
long[] siteGroupIds = _getSiteGroupIds(groupIds);

long[] availableClassNameIds = AssetRendererFactoryRegistryUtil.getClassNameIds(company.getCompanyId());

availableClassNameIds = ArrayUtil.filter(
	availableClassNameIds,
	new PredicateFilter<Long>() {

		public boolean filter(Long classNameId) {
			AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(classNameId));

			return assetRendererFactory.isSelectable();
		}

	});

boolean anyAssetType = GetterUtil.getBoolean(portletPreferences.getValue("anyAssetType", null), true);

long[] classNameIds = AssetPublisherUtil.getClassNameIds(portletPreferences, availableClassNameIds);

long[] classTypeIds = GetterUtil.getLongValues(portletPreferences.getValues("classTypeIds", null));

AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

long[] allAssetCategoryIds = new long[0];
String[] allAssetTagNames = new String[0];

String ddmStructureDisplayFieldValue = StringPool.BLANK;
String ddmStructureFieldLabel = StringPool.BLANK;
String ddmStructureFieldName = StringPool.BLANK;
Serializable ddmStructureFieldValue = null;

boolean subtypeFieldsFilterEnabled = GetterUtil.getBoolean(portletPreferences.getValue("subtypeFieldsFilterEnabled", Boolean.FALSE.toString()));

if (selectionStyle.equals("dynamic")) {
	assetEntryQuery = AssetPublisherUtil.getAssetEntryQuery(portletPreferences, siteGroupIds);

	allAssetCategoryIds = AssetPublisherUtil.getAssetCategoryIds(portletPreferences);
	allAssetTagNames = AssetPublisherUtil.getAssetTagNames(portletPreferences, scopeGroupId);

	assetEntryQuery.setClassTypeIds(classTypeIds);

	if ((classNameIds.length == 1) && (classTypeIds.length == 1)) {
		AssetRendererFactory assetRendererFactory = AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(PortalUtil.getClassName(classNameIds[0]));

		ddmStructureDisplayFieldValue = GetterUtil.getString(portletPreferences.getValue("ddmStructureDisplayFieldValue", StringPool.BLANK));
		ddmStructureFieldName = GetterUtil.getString(portletPreferences.getValue("ddmStructureFieldName", StringPool.BLANK));
		ddmStructureFieldValue = portletPreferences.getValue("ddmStructureFieldValue", StringPool.BLANK);

		if (Validator.isNotNull(ddmStructureFieldName) && Validator.isNotNull(ddmStructureFieldValue)) {
			List<Tuple> classTypeFieldNames = assetRendererFactory.getClassTypeFieldNames(classTypeIds[0], locale, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (Tuple classTypeFieldName : classTypeFieldNames) {
				String fieldName = (String)classTypeFieldName.getObject(1);

				if (fieldName.equals(ddmStructureFieldName)) {
					ddmStructureFieldLabel = (String)classTypeFieldName.getObject(0);

					if (subtypeFieldsFilterEnabled) {
						long ddmStructureId = GetterUtil.getLong(classTypeFieldName.getObject(3));

						assetEntryQuery.setAttribute("ddmStructureFieldName", DDMIndexerUtil.encodeName(ddmStructureId, ddmStructureFieldName, locale));
						assetEntryQuery.setAttribute("ddmStructureFieldValue", ddmStructureFieldValue);
					}

					break;
				}
			}
		}
	}

	AssetPublisherUtil.processAssetEntryQuery(user, portletPreferences, assetEntryQuery);
}

long assetVocabularyId = GetterUtil.getLong(portletPreferences.getValue("assetVocabularyId", StringPool.BLANK));

long assetCategoryId = ParamUtil.getLong(request, "categoryId");

String assetCategoryTitle = null;
String assetVocabularyTitle = null;

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

	AssetCategory assetCategory = AssetCategoryLocalServiceUtil.fetchCategory(assetCategoryId);

	if (assetCategory != null) {
		assetCategory = assetCategory.toEscapedModel();

		assetCategoryTitle = assetCategory.getTitle(locale);

		AssetVocabulary assetVocabulary = AssetVocabularyLocalServiceUtil.getAssetVocabulary(assetCategory.getVocabularyId());

		assetVocabulary = assetVocabulary.toEscapedModel();

		assetVocabularyTitle = assetVocabulary.getTitle(locale);

		PortalUtil.setPageKeywords(assetCategoryTitle, request);
	}
}

String assetTagName = ParamUtil.getString(request, "tag");

if (Validator.isNotNull(assetTagName)) {
	allAssetTagNames = new String[] {assetTagName};

	long[] assetTagIds = AssetTagLocalServiceUtil.getTagIds(siteGroupIds, allAssetTagNames);

	if (assetTagIds.length == 0) {
		assetTagIds = new long[] {ResourceConstants.PRIMKEY_DNE};
	}

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
String assetLinkBehavior = GetterUtil.getString(portletPreferences.getValue("assetLinkBehavior", "showFullContent"));
String orderByColumn1 = GetterUtil.getString(portletPreferences.getValue("orderByColumn1", "modifiedDate"));
String orderByColumn2 = GetterUtil.getString(portletPreferences.getValue("orderByColumn2", "title"));
String orderByType1 = GetterUtil.getString(portletPreferences.getValue("orderByType1", "DESC"));
String orderByType2 = GetterUtil.getString(portletPreferences.getValue("orderByType2", "ASC"));
boolean excludeZeroViewCount = GetterUtil.getBoolean(portletPreferences.getValue("excludeZeroViewCount", null));

int pageDelta = GetterUtil.getInteger(portletPreferences.getValue("pageDelta", portletPreferences.getValue("delta", null)), SearchContainer.DEFAULT_DELTA);

if (portletName.equals(PortletKeys.RECENT_CONTENT)) {
	pageDelta = PropsValues.RECENT_CONTENT_MAX_DISPLAY_ITEMS;
}

String paginationType = GetterUtil.getString(portletPreferences.getValue("paginationType", "none"));

assetEntryQuery.setPaginationType(paginationType);

boolean showAvailableLocales = GetterUtil.getBoolean(portletPreferences.getValue("showAvailableLocales", null));
boolean showMetadataDescriptions = GetterUtil.getBoolean(portletPreferences.getValue("showMetadataDescriptions", null), true);

String defaultAssetPublisherPortletId = AssetUtil.getDefaultAssetPublisherId(layout);

boolean defaultAssetPublisher = AssetUtil.isDefaultAssetPublisher(defaultAssetPublisherPortletId, portletDisplay.getId(), portletResource);

boolean enablePermissions = _isEnablePermissions(portletName, portletPreferences);

assetEntryQuery.setEnablePermissions(enablePermissions);

boolean enableRelatedAssets = GetterUtil.getBoolean(portletPreferences.getValue("enableRelatedAssets", null), true);
boolean enableRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableRatings", null));
boolean enableComments = GetterUtil.getBoolean(portletPreferences.getValue("enableComments", null));
boolean enableCommentRatings = GetterUtil.getBoolean(portletPreferences.getValue("enableCommentRatings", null));
boolean enableTagBasedNavigation = GetterUtil.getBoolean(portletPreferences.getValue("enableTagBasedNavigation", null));
boolean enableViewCountIncrement = GetterUtil.getBoolean(portletPreferences.getValue("enableViewCountIncrement", null), true);

String[] conversions = DocumentConversionUtil.getConversions("html");
String[] extensions = portletPreferences.getValues("extensions", new String[0]);
boolean openOfficeServerEnabled = PrefsPropsUtil.getBoolean(PropsKeys.OPENOFFICE_SERVER_ENABLED, PropsValues.OPENOFFICE_SERVER_ENABLED);
boolean enableConversions = openOfficeServerEnabled && (extensions != null) && (extensions.length > 0);
boolean enablePrint = GetterUtil.getBoolean(portletPreferences.getValue("enablePrint", null));
boolean enableFlags = GetterUtil.getBoolean(portletPreferences.getValue("enableFlags", null));
boolean enableSocialBookmarks = GetterUtil.getBoolean(portletPreferences.getValue("enableSocialBookmarks", null), true);
String socialBookmarksDisplayStyle = portletPreferences.getValue("socialBookmarksDisplayStyle", "horizontal");
String socialBookmarksDisplayPosition = portletPreferences.getValue("socialBookmarksDisplayPosition", "bottom");

String defaultMetadataFields = StringPool.BLANK;
String allMetadataFields = "create-date,modified-date,publish-date,expiration-date,priority,author,view-count,categories,tags";

String[] metadataFields = StringUtil.split(portletPreferences.getValue("metadataFields", defaultMetadataFields));

boolean enableRSS = !PortalUtil.isRSSFeedsEnabled() ? false : GetterUtil.getBoolean(portletPreferences.getValue("enableRss", null));
int rssDelta = GetterUtil.getInteger(portletPreferences.getValue("rssDelta", StringPool.BLANK), SearchContainer.DEFAULT_DELTA);
String rssDisplayStyle = portletPreferences.getValue("rssDisplayStyle", RSSUtil.DISPLAY_STYLE_ABSTRACT);
String rssFeedType = portletPreferences.getValue("rssFeedType", RSSUtil.FEED_TYPE_DEFAULT);
String rssName = portletPreferences.getValue("rssName", portletDisplay.getTitle());

String[] assetEntryXmls = portletPreferences.getValues("assetEntryXml", new String[0]);

boolean viewInContext = assetLinkBehavior.equals("viewInPortlet");

boolean showPortletWithNoResults = false;
boolean groupByClass = (assetVocabularyId == -1);

Map<String, PortletURL> addPortletURLs = null;

Format dateFormatDate = FastDateFormatFactoryUtil.getDate(locale, timeZone);
%>

<%@ include file="/html/portlet/asset_publisher/init-ext.jsp" %>

<%!
private Set<Group> _getAncestorSiteGroups(long groupId, boolean checkContentSharingWithChildrenEnabled) throws PortalException, SystemException {
	Set<Group> groups = new LinkedHashSet<Group>();

	long siteGroupId = PortalUtil.getSiteGroupId(groupId);

	Group siteGroup = GroupLocalServiceUtil.getGroup(siteGroupId);

	for (Group group : siteGroup.getAncestors()) {
		if (checkContentSharingWithChildrenEnabled && !SitesUtil.isContentSharingWithChildrenEnabled(group)) {
			continue;
		}

		groups.add(group);
	}

	if (!siteGroup.isCompany()) {
		groups.add(GroupLocalServiceUtil.getCompanyGroup(siteGroup.getCompanyId()));
	}

	return groups;
}

private long[] _getCurrentAndAncestorSiteGroupIds(long[] groupIds, boolean checkContentSharingWithChildrenEnabled) throws PortalException, SystemException {
	List<Group> groups = _getCurrentAndAncestorSiteGroups(groupIds, checkContentSharingWithChildrenEnabled);

	long[] currentAndAncestorSiteGroupIds = new long[groups.size()];

	for (int i = 0; i < groups.size(); i++) {
		Group group = groups.get(i);

		currentAndAncestorSiteGroupIds[i] = group.getGroupId();
	}

	return currentAndAncestorSiteGroupIds;
}

private List<Group> _getCurrentAndAncestorSiteGroups(long groupId, boolean checkContentSharingWithChildrenEnabled) throws PortalException, SystemException {
	Set<Group> groups = new LinkedHashSet<Group>();

	Group siteGroup = _getCurrentSiteGroup(groupId);

	if (siteGroup != null) {
		groups.add(siteGroup);
	}

	groups.addAll(_getAncestorSiteGroups(groupId, checkContentSharingWithChildrenEnabled));

	return new ArrayList<Group>(groups);
}

private List<Group> _getCurrentAndAncestorSiteGroups(long[] groupIds, boolean checkContentSharingWithChildrenEnabled) throws PortalException, SystemException {
	Set<Group> groups = new LinkedHashSet<Group>();

	for (int i = 0; i < groupIds.length; i++) {
		groups.addAll(_getCurrentAndAncestorSiteGroups(groupIds[i], checkContentSharingWithChildrenEnabled));
	}

	return new ArrayList<Group>(groups);
}

private Group _getCurrentSiteGroup(long groupId) throws PortalException, SystemException {
	long siteGroupId = PortalUtil.getSiteGroupId(groupId);

	Group siteGroup = GroupLocalServiceUtil.getGroup(siteGroupId);

	if (!siteGroup.isLayoutPrototype()) {
		return siteGroup;
	}

	return null;
}

private long[] _getSiteGroupIds(long[] groupIds) throws PortalException, SystemException {
	Set<Long> siteGroupIds = new HashSet<Long>();

	for (long groupId : groupIds) {
		siteGroupIds.add(PortalUtil.getSiteGroupId(groupId));
	}

	return ArrayUtil.toLongArray(siteGroupIds);
}

private boolean _isEnablePermissions(String portletName, PortletPreferences portletPreferences) {
	if (!portletName.equals(PortletKeys.HIGHEST_RATED_ASSETS) &&
		!portletName.equals(PortletKeys.MOST_VIEWED_ASSETS) &&
		PropsValues.ASSET_PUBLISHER_SEARCH_WITH_INDEX) {

		return true;
	}

	if (!PropsValues.ASSET_PUBLISHER_PERMISSION_CHECKING_CONFIGURABLE) {
		return true;
	}

	return GetterUtil.getBoolean(portletPreferences.getValue("enablePermissions", null));
}
%>
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

<%@ page import="com.liferay.portlet.asset.NoSuchVocabularyException" %>

<%
List<AssetVocabulary> assetVocabularies = AssetVocabularyServiceUtil.getGroupsVocabularies(new long[] {scopeGroupId, themeDisplay.getCompanyGroupId()});

long[] availableAssetVocabularyIds = new long[assetVocabularies.size()];

for (int i = 0; i < assetVocabularies.size(); i++) {
	AssetVocabulary assetVocabulary = assetVocabularies.get(i);

	availableAssetVocabularyIds[i] = assetVocabulary.getVocabularyId();
}

boolean allAssetVocabularies = GetterUtil.getBoolean(portletPreferences.getValue("allAssetVocabularies", Boolean.TRUE.toString()));

long[] assetVocabularyIds = availableAssetVocabularyIds;

if (!allAssetVocabularies && (portletPreferences.getValues("assetVocabularyIds", null) != null)) {
	assetVocabularyIds = StringUtil.split(portletPreferences.getValue("assetVocabularyIds", null), 0L);
}

String displayStyle = portletPreferences.getValue("displayStyle", StringPool.BLANK);
long displayStyleGroupId = GetterUtil.getLong(portletPreferences.getValue("displayStyleGroupId", null), themeDisplay.getScopeGroupId());
%>

<%@ include file="/html/portlet/asset_categories_navigation/init-ext.jsp" %>
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

<%@ page import="com.liferay.portal.kernel.template.TemplateHandler" %><%@
page import="com.liferay.portal.kernel.template.TemplateHandlerRegistryUtil" %><%@
page import="com.liferay.portlet.asset.NoSuchVocabularyException" %><%@
page import="com.liferay.portlet.asset.model.AssetCategory" %><%@
page import="com.liferay.portlet.asset.model.AssetVocabulary" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil" %><%@
page import="com.liferay.portlet.asset.service.AssetVocabularyServiceUtil" %><%@
page import="com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil" %>

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

String displayTemplate = portletPreferences.getValue("displayTemplate", StringPool.BLANK);
%>

<%@ include file="/html/portlet/asset_categories_navigation/init-ext.jsp" %>
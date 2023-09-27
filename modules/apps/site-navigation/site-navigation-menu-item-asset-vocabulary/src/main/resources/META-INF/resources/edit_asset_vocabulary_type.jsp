<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AssetVocabularySiteNavigationMenuTypeDisplayContext assetVocabularySiteNavigationMenuTypeDisplayContext = (AssetVocabularySiteNavigationMenuTypeDisplayContext)request.getAttribute(AssetVocabularySiteNavigationMenuTypeConstants.ASSET_VOCABULARY_SITE_NAVIGATION_MENU_TYPE_DISPLAY_CONTEXT);
%>

<div>
	<react:component
		module="js/AssetVocabularyContextualSidebar"
		props="<%= assetVocabularySiteNavigationMenuTypeDisplayContext.getAssetVocabularyContextualSidebarContext() %>"
	/>
</div>
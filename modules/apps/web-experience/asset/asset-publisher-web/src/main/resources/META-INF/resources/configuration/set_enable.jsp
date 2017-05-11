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

<%@ include file="/init.jsp" %>

<c:if test="<%= assetPublisherDisplayContext.isShowEnableAddContentButton() %>">
	<aui:input helpMessage="show-add-content-button-help" name="preferences--showAddContentButton--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowAddContentButton() %>" />
</c:if>

<%
String helpMessage1 = "<em>" + LanguageUtil.format(request, "content-related-to-x", StringPool.DOUBLE_PERIOD, false) + "</em>";
String helpMessage2 = "<em>" + LanguageUtil.format(request, "content-with-tag-x", StringPool.DOUBLE_PERIOD, false) + "</em>";
%>

<aui:input helpMessage='<%= LanguageUtil.format(request, "such-as-x-or-x", new Object[] {helpMessage1, helpMessage2}, false) %>' name="preferences--showMetadataDescriptions--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowMetadataDescriptions() %>" />

<aui:input name="preferences--showAvailableLocales--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isShowAvailableLocales() %>" />

<c:if test="<%= assetPublisherDisplayContext.isEnableSetAsDefaultAssetPublisher() %>">
	<aui:input helpMessage="set-as-the-default-asset-publisher-for-this-page-help" label="set-as-the-default-asset-publisher-for-this-page" name="defaultAssetPublisher" type="toggle-switch" value="<%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) %>" />
</c:if>

<aui:field-wrapper helpMessage='<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() ? "enabling-openoffice-integration-provides-document-conversion-functionality" : StringPool.BLANK %>' label="enable-conversion-to">
	<div class="field-row">

		<%
		String[] conversions = DocumentConversionUtil.getConversions("html");

		for (String conversion : conversions) {
		%>

			<aui:input checked="<%= ArrayUtil.contains(assetPublisherDisplayContext.getExtensions(), conversion) %>" disabled="<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() %>" id='<%= "extensions" + conversion %>' inlineField="<%= true %>" label="<%= StringUtil.toUpperCase(conversion) %>" name="extensions" type="toggle-switch" value="<%= conversion %>" />

		<%
		}
		%>

	</div>
</aui:field-wrapper>

<aui:input name="preferences--enablePrint--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnablePrint() %>" />

<aui:input name="preferences--enableFlags--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableFlags() %>" />

<c:choose>
	<c:when test="<%= !assetPublisherDisplayContext.isShowEnablePermissions() %>">
		<aui:input name="preferences--enablePermissions--" type="hidden" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
	</c:when>
	<c:otherwise>
		<aui:input name="preferences--enablePermissions--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
	</c:otherwise>
</c:choose>

<c:choose>
	<c:when test="<%= !assetPublisherDisplayContext.isShowEnableRelatedAssets() %>">
		<aui:input name="preferences--enableRelatedAssets--" type="hidden" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
	</c:when>
	<c:otherwise>
		<aui:input name="preferences--enableRelatedAssets--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
	</c:otherwise>
</c:choose>

<aui:input name="preferences--enableRatings--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableRatings() %>" />

<aui:input name="preferences--enableComments--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableComments() %>" />

<aui:input name="preferences--enableCommentRatings--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>" />

<liferay-ui:social-bookmarks-settings
	displayPosition="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayPosition() %>"
	displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
	enabled="<%= assetPublisherDisplayContext.isEnableSocialBookmarks() %>"
/>

<c:if test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
	<aui:input helpMessage="enable-tag-based-navigation-help" label="enable-tag-based-navigation" name="preferences--enableTagBasedNavigation--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableTagBasedNavigation() %>" />
</c:if>

<aui:input name="preferences--enableViewCountIncrement--" type="toggle-switch" value="<%= assetPublisherDisplayContext.isEnableViewCountIncrement() %>" />
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

<h4 class="sheet-tertiary-title">
	<liferay-ui:message key="show-and-set" />
</h4>

<c:if test="<%= assetPublisherDisplayContext.isShowEnableAddContentButton() %>">
	<aui:input helpMessage="show-add-content-button-help" name="preferences--showAddContentButton--" type="checkbox" value="<%= assetPublisherDisplayContext.isShowAddContentButton() %>" />
</c:if>

<%
String helpMessage1 = "<em>" + LanguageUtil.format(request, "content-related-to-x", StringPool.DOUBLE_PERIOD, false) + "</em>";
String helpMessage2 = "<em>" + LanguageUtil.format(request, "content-with-tag-x", StringPool.DOUBLE_PERIOD, false) + "</em>";
%>

<aui:input helpMessage='<%= LanguageUtil.format(request, "such-as-x-or-x", new Object[] {helpMessage1, helpMessage2}, false) %>' name="preferences--showMetadataDescriptions--" type="checkbox" value="<%= assetPublisherDisplayContext.isShowMetadataDescriptions() %>" />

<aui:input name="preferences--showAvailableLocales--" type="checkbox" value="<%= assetPublisherDisplayContext.isShowAvailableLocales() %>" />

<c:if test="<%= assetPublisherDisplayContext.isEnableSetAsDefaultAssetPublisher() %>">
	<aui:input helpMessage="set-as-the-default-asset-publisher-for-this-page-help" label="set-as-the-default-asset-publisher-for-this-page" name="defaultAssetPublisher" type="checkbox" value="<%= assetPublisherWebUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) %>" />
</c:if>

<aui:input label='<%= LanguageUtil.format(request, "show-only-assets-with-x-as-its-display-page", HtmlUtil.escape(layout.getName(locale)), false) %>' name="preferences--showOnlyLayoutAssets--" type="checkbox" value="<%= assetPublisherDisplayContext.isShowOnlyLayoutAssets() %>" />

<aui:input label="include-tags-specified-in-the-url" name="preferences--mergeUrlTags--" type="checkbox" value="<%= assetPublisherDisplayContext.isMergeURLTags() %>" />

<h4 class="sheet-tertiary-title">
	<liferay-ui:message key="enable" />
</h4>

<aui:row>
	<aui:col width="<%= 50 %>">
		<aui:input label="print" name="preferences--enablePrint--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnablePrint() %>" />

		<aui:input label="flags" name="preferences--enableFlags--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableFlags() %>" />

		<aui:input label="ratings" name="preferences--enableRatings--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableRatings() %>" />

		<c:choose>
			<c:when test="<%= !assetPublisherDisplayContext.isShowEnableRelatedAssets() %>">
				<aui:input label="related-assets" name="preferences--enableRelatedAssets--" type="hidden" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
			</c:when>
			<c:otherwise>
				<aui:input label="related-assets" name="preferences--enableRelatedAssets--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
			</c:otherwise>
		</c:choose>
	</aui:col>

	<aui:col width="<%= 50 %>">
		<aui:input label="comments" name="preferences--enableComments--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableComments() %>" />

		<aui:input label="comment-ratings" name="preferences--enableCommentRatings--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>" />

		<aui:input label="view-count-increment" name="preferences--enableViewCountIncrement--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableViewCountIncrement() %>" />

		<c:if test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
			<aui:input helpMessage="enable-tag-based-navigation-help" label="tag-based-navigation" name="preferences--enableTagBasedNavigation--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableTagBasedNavigation() %>" />
		</c:if>

		<c:choose>
			<c:when test="<%= !assetPublisherDisplayContext.isShowEnablePermissions() %>">
				<aui:input label="permissions" name="preferences--enablePermissions--" type="hidden" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
			</c:when>
			<c:otherwise>
				<aui:input label="permissions" name="preferences--enablePermissions--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
			</c:otherwise>
		</c:choose>
	</aui:col>
</aui:row>

<c:if test="<%= assetPublisherDisplayContext.isOpenOfficeServerEnabled() %>">

	<%
	String[] conversions = DocumentConversionUtil.getConversions("html");
	%>

	<h4 class="sheet-tertiary-title">
		<liferay-ui:message key="enable-conversion-to" />

		<liferay-ui:icon-help message="enabling-openoffice-integration-provides-document-conversion-functionality" />
	</h4>

	<aui:row>
		<aui:col width="<%= 50 %>">

			<%
			for (int i = 0; i < (conversions.length / 2); i++) {
				String conversion = conversions[i];
			%>

				<aui:input checked="<%= ArrayUtil.contains(assetPublisherDisplayContext.getExtensions(), conversion) %>" id='<%= "extensions" + conversion %>' label="<%= StringUtil.toUpperCase(conversion) %>" name="extensions" type="checkbox" value="<%= conversion %>" />

			<%
			}
			%>

		</aui:col>

		<aui:col width="<%= 50 %>">

			<%
			for (int i = (conversions.length/ 2); i < conversions.length; i++) {
				String conversion = conversions[i];
			%>

				<aui:input checked="<%= ArrayUtil.contains(assetPublisherDisplayContext.getExtensions(), conversion) %>" id='<%= "extensions" + conversion %>' label="<%= StringUtil.toUpperCase(conversion) %>" name="extensions" type="checkbox" value="<%= conversion %>" />

			<%
			}
			%>

		</aui:col>
	</aui:row>
</c:if>

<h4 class="sheet-tertiary-title">
	<liferay-ui:message key="social-bookmarks" />
</h4>

<liferay-social-bookmarks:bookmarks-settings
	displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
	types="<%= assetPublisherDisplayContext.getSocialBookmarksTypes() %>"
/>
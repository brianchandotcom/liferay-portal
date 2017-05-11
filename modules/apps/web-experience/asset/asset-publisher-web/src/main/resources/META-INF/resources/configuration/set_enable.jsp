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

<aui:row cssClass="panel-group">
	<aui:col width="<%= 50 %>">
		<liferay-ui:panel collapsible="<%= false %>" markupView="lexicon" title="show-and-set">
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
				<aui:input helpMessage="set-as-the-default-asset-publisher-for-this-page-help" label="set-as-the-default-asset-publisher-for-this-page" name="defaultAssetPublisher" type="checkbox" value="<%= AssetUtil.isDefaultAssetPublisher(layout, portletDisplay.getId(), assetPublisherDisplayContext.getPortletResource()) %>" />
			</c:if>
		</liferay-ui:panel>
	</aui:col>

	<aui:col width="<%= 50 %>">
		<liferay-ui:panel collapsible="<%= false %>" extended="" markupView="lexicon" title="enable">
			<aui:col width="<%= 50 %>">
				<aui:input name="preferences--enablePrint--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnablePrint() %>" />

				<aui:input name="preferences--enableFlags--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableFlags() %>" />

				<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableRatings() %>" />

				<c:choose>
					<c:when test="<%= !assetPublisherDisplayContext.isShowEnableRelatedAssets() %>">
						<aui:input name="preferences--enableRelatedAssets--" type="hidden" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
					</c:when>
					<c:otherwise>
						<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableRelatedAssets() %>" />
					</c:otherwise>
				</c:choose>
			</aui:col>

			<aui:col width="<%= 50 %>">
				<aui:input name="preferences--enableComments--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableComments() %>" />

				<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableCommentRatings() %>" />

				<aui:input name="preferences--enableViewCountIncrement--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableViewCountIncrement() %>" />

				<c:if test="<%= assetPublisherDisplayContext.isSelectionStyleManual() %>">
					<aui:input helpMessage="enable-tag-based-navigation-help" label="enable-tag-based-navigation" name="preferences--enableTagBasedNavigation--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnableTagBasedNavigation() %>" />
				</c:if>

				<c:choose>
					<c:when test="<%= !assetPublisherDisplayContext.isShowEnablePermissions() %>">
						<aui:input name="preferences--enablePermissions--" type="hidden" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
					</c:when>
					<c:otherwise>
						<aui:input name="preferences--enablePermissions--" type="checkbox" value="<%= assetPublisherDisplayContext.isEnablePermissions() %>" />
					</c:otherwise>
				</c:choose>
			</aui:col>
		</liferay-ui:panel>
	</aui:col>
</aui:row>

<aui:row cssClass="panel-group">
	<aui:col width="<%= 100 %>">

		<%
		String[] conversions = DocumentConversionUtil.getConversions("html");

		int half = conversions.length / 2;
		%>

		<liferay-ui:panel collapsible="<%= false %>" extended="" helpMessage='<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() ? "enabling-openoffice-integration-provides-document-conversion-functionality" : StringPool.BLANK %>' markupView="lexicon" title="enable-conversion-to">
			<aui:col width="<%= 50 %>">

				<%
				for (int k = 0; k < half; k++) {
					String conversion = conversions[k];
				%>

					<aui:row>
						<aui:input checked="<%= ArrayUtil.contains(assetPublisherDisplayContext.getExtensions(), conversion) %>" disabled="<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() %>" id='<%= "extensions" + conversion %>' inlineField="<%= true %>" label="<%= StringUtil.toUpperCase(conversion) %>" name="extensions" type="checkbox" value="<%= conversion %>" />
					</aui:row>

				<%
				}
				%>

			</aui:col>

			<aui:col width="<%= 50 %>">

				<%
				for (int k = half; k < conversions.length; k++) {
					String conversion = conversions[k];
				%>

					<aui:row>
						<aui:input checked="<%= ArrayUtil.contains(assetPublisherDisplayContext.getExtensions(), conversion) %>" disabled="<%= !assetPublisherDisplayContext.isOpenOfficeServerEnabled() %>" id='<%= "extensions" + conversion %>' inlineField="<%= true %>" label="<%= StringUtil.toUpperCase(conversion) %>" name="extensions" type="checkbox" value="<%= conversion %>" />
					</aui:row>

				<%
				}
				%>

			</aui:col>
		</liferay-ui:panel>
	</aui:col>
</aui:row>

<aui:row cssClass="panel-group">
	<liferay-ui:social-bookmarks-settings
		displayPosition="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayPosition() %>"
		displayStyle="<%= assetPublisherDisplayContext.getSocialBookmarksDisplayStyle() %>"
		enabled="<%= assetPublisherDisplayContext.isEnableSocialBookmarks() %>"
	/>
</aui:row>
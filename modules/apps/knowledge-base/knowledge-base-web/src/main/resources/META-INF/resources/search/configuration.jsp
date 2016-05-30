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

<%@ include file="/search/init.jsp" %>

<%
kbSearchPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBSearchPortletInstanceConfiguration.class, kbSearchPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");

String tabs2 = ParamUtil.getString(request, "tabs2", "general");

String tabs2Names = "general,display-settings";
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
</liferay-portlet:renderURL>

<liferay-ui:tabs
	names="<%= tabs2Names %>"
	param="tabs2"
	url="<%= configurationRenderURL %>"
/>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />

	<aui:fieldset>
		<c:choose>
			<c:when test='<%= tabs2.equals("general") %>'>
				<aui:input label="show-author-column" name="preferences--showKBArticleAuthorColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleAuthorColumn() %>" />

				<aui:input label="show-create-date-column" name="preferences--showKBArticleCreateDateColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleCreateDateColumn() %>" />

				<aui:input label="show-modified-date-column" name="preferences--showKBArticleModifiedDateColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleModifiedDateColumn() %>" />

				<aui:input label="show-views-column" name="preferences--showKBArticleViewsColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleViewsColumn() %>" />
			</c:when>
			<c:when test='<%= tabs2.equals("display-settings") %>'>
				<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleDescription() %>" />

				<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleRatings() %>" />

				<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

				<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleAttachments() %>" />

				<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

				<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

				<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

				<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleHistory() %>" />

				<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticlePrint() %>" />

				<liferay-ui:social-bookmarks-settings
					displayPosition="<%= kbSearchPortletInstanceConfiguration.socialBookmarksDisplayPosition() %>"
					displayStyle="<%= kbSearchPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
					enabled="<%= kbSearchPortletInstanceConfiguration.enableSocialBookmarks() %>"
					types="<%= kbSearchPortletInstanceConfiguration.socialBookmarksTypes() %>"
				/>
			</c:when>
		</c:choose>

		<aui:button-row cssClass="kb-submit-buttons">
			<aui:button type="submit" />
		</aui:button-row>
	</aui:fieldset>
</aui:form>
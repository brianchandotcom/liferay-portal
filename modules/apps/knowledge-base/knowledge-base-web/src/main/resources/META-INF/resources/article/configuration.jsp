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

<%@ include file="/article/init.jsp" %>

<%
KBArticleConfigurationDisplayContext kbArticleConfigurationDisplayContext = (KBArticleConfigurationDisplayContext)request.getAttribute(KBArticleConfigurationDisplayContext.class.getName());
%>

<liferay-frontend:edit-form
	action="<%= kbArticleConfigurationDisplayContext.getConfigurationActionURL() %>"
	cssClass="pt-0"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="preferences--resourcePrimKey--" type="hidden" value="<%= String.valueOf(kbArticleConfigurationDisplayContext.getResourcePrimKey()) %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				cssClass="mb-4"
				label="content-selection"
			>
				<div class="form-group">
					<aui:input label="article" name="configurationKBObject" type="resource" value="<%= kbArticleConfigurationDisplayContext.getKBArticleTitle() %>" />

					<aui:button name="selectKBArticleButton" value="select" />
				</div>
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				cssClass="mb-4"
				label="set-and-enable"
			>
				<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticleDescriptionEnabled() %>" />

				<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticleRatingsEnabled() %>" />

				<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isShowKBArticleAssetEntries() %>" />

				<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isShowKBArticleAttachments() %>" />

				<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticleAssetLinksEnabled() %>" />

				<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticleViewCountIncrementEnabled() %>" />

				<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticleSubscriptionsEnabled() %>" />

				<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticleHistoryEnabled() %>" />

				<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbArticleConfigurationDisplayContext.isKBArticlePrintEnabled() %>" />
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				label="social-bookmarks"
			>
				<liferay-social-bookmarks:bookmarks-settings
					displayStyle="<%= kbArticleConfigurationDisplayContext.getSocialBookmarksDisplayStyle() %>"
					types="<%= kbArticleConfigurationDisplayContext.getSocialBookmarksTypes() %>"
				/>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<liferay-frontend:component
	componentId="<%= kbArticleConfigurationDisplayContext.getComponentId() %>"
	context="<%= kbArticleConfigurationDisplayContext.getComponentContext() %>"
	module="article/js/PortletConfiguration"
	servletContext="<%= application %>"
/>
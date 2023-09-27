<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/search/init.jsp" %>

<%
kbSearchPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBSearchPortletInstanceConfiguration.class, kbSearchPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:tabs
			names="general,display-settings"
			refresh="<%= false %>"
		>
			<liferay-ui:section>
				<liferay-frontend:fieldset>
					<aui:input label="show-author-column" name="preferences--showKBArticleAuthorColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleAuthorColumn() %>" />

					<aui:input label="show-create-date-column" name="preferences--showKBArticleCreateDateColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleCreateDateColumn() %>" />

					<aui:input label="show-modified-date-column" name="preferences--showKBArticleModifiedDateColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleModifiedDateColumn() %>" />

					<aui:input label="show-views-column" name="preferences--showKBArticleViewsColumn--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleViewsColumn() %>" />
				</liferay-frontend:fieldset>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset>
					<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleDescription() %>" />

					<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleRatings() %>" />

					<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

					<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.showKBArticleAttachments() %>" />

					<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

					<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

					<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

					<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticleHistory() %>" />

					<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbSearchPortletInstanceConfiguration.enableKBArticlePrint() %>" />

					<h4 class="section-header">
						<liferay-ui:message key="social-bookmarks" />
					</h4>

					<liferay-social-bookmarks:bookmarks-settings
						displayStyle="<%= kbSearchPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
						types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(kbSearchPortletInstanceConfiguration.socialBookmarksTypes()) %>"
					/>
				</liferay-frontend:fieldset>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>
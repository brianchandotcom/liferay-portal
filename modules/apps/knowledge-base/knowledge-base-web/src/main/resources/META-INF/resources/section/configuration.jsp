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

<%@ include file="/section/init.jsp" %>

<%
kbSectionPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBSectionPortletInstanceConfiguration.class, kbSectionPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:error key="kbArticlesSections" message="please-select-at-least-one-section" />

		<liferay-ui:tabs
			names="general,display-settings"
			refresh="<%= false %>"
		>
			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input label="show-sections-title" name="preferences--showKBArticlesSectionsTitle--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticlesSectionsTitle() %>" />

						<aui:select ignoreRequestValue="<%= true %>" label="sections" multiple="<%= true %>" name="kbArticlesSections" required="<%= true %>">

							<%
							Map<String, String> sectionsMap = new TreeMap<String, String>();

							for (String section : kbSectionPortletInstanceConfiguration.adminKBArticleSections()) {
								sectionsMap.put(LanguageUtil.get(request, section), section);
							}

							for (Map.Entry<String, String> entry : sectionsMap.entrySet()) {
							%>

								<aui:option label="<%= HtmlUtil.escape(entry.getKey()) %>" selected="<%= ArrayUtil.contains(kbSectionPortletInstanceConfiguration.kbArticlesSections(), entry.getValue()) %>" value="<%= HtmlUtil.escape(entry.getValue()) %>" />

							<%
							}
							%>

						</aui:select>

						<aui:select label="article-display-style" name="preferences--kbArticleDisplayStyle--" value="<%= kbSectionPortletInstanceConfiguration.kbArticleDisplayStyle() %>">
							<aui:option label="title" />
							<aui:option label="abstract" />
						</aui:select>

						<aui:input label="show-pagination" name="preferences--showKBArticlesPagination--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticlesPagination() %>" />
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleDescription() %>" />

						<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleRatings() %>" />

						<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

						<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.showKBArticleAttachments() %>" />

						<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

						<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

						<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

						<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticleHistory() %>" />

						<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbSectionPortletInstanceConfiguration.enableKBArticlePrint() %>" />

						<h4 class="section-header">
							<liferay-ui:message key="social-bookmarks" />
						</h4>

						<liferay-social-bookmarks:bookmarks-settings
							displayStyle="<%= kbSectionPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
							types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(kbSectionPortletInstanceConfiguration.socialBookmarksTypes()) %>"
						/>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>
		</liferay-ui:tabs>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>
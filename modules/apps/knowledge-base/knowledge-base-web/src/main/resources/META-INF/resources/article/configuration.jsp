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
String tabsNames = "general,display-settings";

kbArticlePortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBArticlePortletInstanceConfiguration.class, kbArticlePortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="preferences--resourcePrimKey--" type="hidden" value="<%= kbArticlePortletInstanceConfiguration.resourcePrimKey() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-ui:tabs
			names="<%= tabsNames %>"
			refresh="<%= false %>"
		>
			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<div class="form-group">

							<%
							String title = StringPool.BLANK;

							KBArticle kbArticle = KBArticleServiceUtil.fetchLatestKBArticle(kbArticlePortletInstanceConfiguration.resourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

							if (kbArticle != null) {
								title = kbArticle.getTitle();
							}
							%>

							<aui:input label="article" name="configurationKBObject" type="resource" value="<%= title %>" />

							<aui:button name="selectKBArticleButton" value="select" />
						</div>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleDescription() %>" />

						<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleRatings() %>" />

						<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

						<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.showKBArticleAttachments() %>" />

						<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

						<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

						<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

						<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleHistory() %>" />

						<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticlePrint() %>" />

						<h4 class="section-header">
							<liferay-ui:message key="social-bookmarks" />
						</h4>

						<liferay-social-bookmarks:bookmarks-settings
							displayStyle="<%= kbArticlePortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
							types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(kbArticlePortletInstanceConfiguration.socialBookmarksTypes()) %>"
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

<aui:script>
	var <portlet:namespace />form = document.getElementById(
		'<portlet:namespace />fm'
	);

	if (<portlet:namespace />form) {
		document
			.getElementById('<portlet:namespace />selectKBArticleButton')
			.addEventListener('click', function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true,
							width: 600
						},
						id: '<portlet:namespace />selectKBObject',
						title: '<liferay-ui:message key="select-article" />',

						<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectKBObjectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value="/article/select_parent.jsp" />
							<portlet:param name="eventName" value='<%= liferayPortletResponse.getNamespace() + "selectKBObject" %>' />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(KBArticleConstants.getClassName())) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbArticlePortletInstanceConfiguration.resourcePrimKey()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(kbArticlePortletInstanceConfiguration.resourcePrimKey()) %>" />
							<portlet:param name="selectableClassNameIds" value="<%= String.valueOf(PortalUtil.getClassNameId(KBArticleConstants.getClassName())) %>" />
						</liferay-portlet:renderURL>

						uri: '<%= HtmlUtil.escapeJS(selectKBObjectURL) %>'
					},
					function(event) {
						var kbArticleData = {
							idString: 'resourcePrimKey',
							idValue: event.resourceprimkey,
							nameString: 'configurationKBObject',
							nameValue: event.title
						};

						Liferay.Util.selectFolder(
							kbArticleData,
							'<portlet:namespace />'
						);
					}
				);
			});
	}
</aui:script>
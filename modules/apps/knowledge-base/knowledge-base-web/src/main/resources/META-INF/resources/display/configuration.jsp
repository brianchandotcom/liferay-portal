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

<%@ include file="/display/init.jsp" %>

<%
String tabsNames = "general,display-settings";

kbDisplayPortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBDisplayPortletInstanceConfiguration.class, kbDisplayPortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

	<%
	resourceClassNameId = kbDisplayPortletInstanceConfiguration.resourceClassNameId();

	if (resourceClassNameId == 0) {
		resourceClassNameId = kbFolderClassNameId;
	}
	%>

	<aui:input name="preferences--resourceClassNameId--" type="hidden" value="<%= resourceClassNameId %>" />
	<aui:input name="preferences--resourcePrimKey--" type="hidden" value="<%= kbDisplayPortletInstanceConfiguration.resourcePrimKey() %>" />

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

							if (resourceClassNameId != kbFolderClassNameId) {
								KBArticle kbArticle = KBArticleServiceUtil.fetchLatestKBArticle(kbDisplayPortletInstanceConfiguration.resourcePrimKey(), WorkflowConstants.STATUS_APPROVED);

								if (kbArticle != null) {
									title = kbArticle.getTitle();
								}
							}
							else if (kbDisplayPortletInstanceConfiguration.resourcePrimKey() == KBFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
								title = LanguageUtil.get(resourceBundle, "home");
							}
							else {
								KBFolder kbFolder = KBFolderServiceUtil.fetchKBFolder(kbDisplayPortletInstanceConfiguration.resourcePrimKey());

								if (kbFolder != null) {
									title = kbFolder.getName();
								}
							}
							%>

							<aui:input label="article-or-folder" name="configurationKBObject" type="resource" value="<%= title %>" />

							<aui:button name="selectKBObjectButton" value="select" />
						</div>
					</liferay-frontend:fieldset>
				</liferay-frontend:fieldset-group>
			</liferay-ui:section>

			<liferay-ui:section>
				<liferay-frontend:fieldset-group>
					<liferay-frontend:fieldset>
						<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleDescription() %>" />

						<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleRatings() %>" />

						<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

						<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.showKBArticleAttachments() %>" />

						<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

						<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

						<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

						<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticleHistory() %>" />

						<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbDisplayPortletInstanceConfiguration.enableKBArticlePrint() %>" />

						<h4 class="section-header">
							<liferay-ui:message key="social-bookmarks" />
						</h4>

						<liferay-social-bookmarks:bookmarks-settings
							displayStyle="<%= kbDisplayPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
							types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(kbDisplayPortletInstanceConfiguration.socialBookmarksTypes()) %>"
						/>

						<aui:input label="content-root-prefix" name="preferences--contentRootPrefix--" type="input" value="<%= kbDisplayPortletInstanceConfiguration.contentRootPrefix() %>" />

						<aui:input label="maximum-nesting-level" name="preferences--maxNestingLevel--" type="input" value="<%= kbDisplayPortletInstanceConfiguration.maxNestingLevel() %>" />
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
			.getElementById('<portlet:namespace />selectKBObjectButton')
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
						title: '<liferay-ui:message key="select-entry" />',

						<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectKBObjectURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value="/display/select_parent.jsp" />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(resourceClassNameId) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbDisplayPortletInstanceConfiguration.resourcePrimKey()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(kbDisplayPortletInstanceConfiguration.resourcePrimKey()) %>" />
							<portlet:param name="eventName" value='<%= liferayPortletResponse.getNamespace() + "selectKBObject" %>' />
						</liferay-portlet:renderURL>

						uri: '<%= HtmlUtil.escapeJS(selectKBObjectURL) %>'
					},
					function(event) {
						document.getElementById(
							'<portlet:namespace />resourceClassNameId'
						).value = event.resourceclassnameid;

						var kbObjectData = {
							idString: 'resourcePrimKey',
							idValue: event.resourceprimkey,
							nameString: 'configurationKBObject',
							nameValue: event.title
						};

						Liferay.Util.selectFolder(
							kbObjectData,
							'<portlet:namespace />'
						);
					}
				);
			});
	}
</aui:script>
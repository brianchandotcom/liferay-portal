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
String tabsNames = Objects.equals(portletResource, KBPortletKeys.KNOWLEDGE_BASE_ARTICLE_DEFAULT_INSTANCE) ? "display-settings" : "general,display-settings";

kbArticlePortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBArticlePortletInstanceConfiguration.class, kbArticlePortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="preferences--resourcePrimKey--" type="hidden" value="<%= kbArticlePortletInstanceConfiguration.resourcePrimKey() %>" />

	<liferay-ui:tabs
		names="<%= tabsNames %>"
		refresh="<%= false %>"
		type="tabs nav-tabs-default"
	>
		<c:if test='<%= tabsNames.contains("general") %>'>
			<liferay-ui:section>
				<aui:fieldset-group markupView="lexicon">
					<aui:fieldset>
						<div class="form-group kb-field-wrapper">

							<%
							KBArticle kbArticle = KBArticleLocalServiceUtil.fetchLatestKBArticle(kbArticlePortletInstanceConfiguration.resourcePrimKey(), WorkflowConstants.STATUS_APPROVED);
							%>

							<aui:input label="article" name="configurationKBArticle" type="resource" value="<%= (kbArticle != null) ? kbArticle.getTitle() : StringPool.BLANK %>" />

							<aui:button name="selectKBArticleButton" value="select" />
						</div>
					</aui:fieldset>
				</aui:fieldset-group>
			</liferay-ui:section>
		</c:if>

		<liferay-ui:section>
			<aui:fieldset-group markupView="lexicon">
				<aui:fieldset>
					<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleDescription() %>" />

					<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleRatings() %>" />

					<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

					<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.showKBArticleAttachments() %>" />

					<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

					<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

					<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

					<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleHistory() %>" />

					<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticlePrint() %>" />

					<liferay-ui:social-bookmarks-settings
						displayPosition="<%= kbArticlePortletInstanceConfiguration.socialBookmarksDisplayPosition() %>"
						displayStyle="<%= kbArticlePortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
						enabled="<%= kbArticlePortletInstanceConfiguration.enableSocialBookmarks() %>"
						types="<%= kbArticlePortletInstanceConfiguration.socialBookmarksTypes() %>"
					/>
				</aui:fieldset>
			</aui:fieldset-group>
		</liferay-ui:section>
	</liferay-ui:tabs>

	<aui:button-row>
		<aui:button cssClass="btn btn-lg btn-primary" type="submit" />
	</aui:button-row>
</aui:form>

<c:if test='<%= tabsNames.contains("general") %>'>
	<aui:script>
		AUI.$('#<portlet:namespace />selectKBArticleButton').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							destroyOnHide: true,
							modal: true,
							width: 600
						},
						id: '<portlet:namespace />selectKBEntry',
						title: '<liferay-ui:message key="select-parent" />',

						<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectKBEntryURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="mvcPath" value="/article/select_parent.jsp" />
							<portlet:param name="eventName" value='<%= liferayPortletResponse.getNamespace() + "selectKBEntry" %>' />
							<portlet:param name="parentResourceClassNameId" value="<%= String.valueOf(PortalUtil.getClassNameId(KBArticleConstants.getClassName())) %>" />
							<portlet:param name="parentResourcePrimKey" value="<%= String.valueOf(kbArticlePortletInstanceConfiguration.resourcePrimKey()) %>" />
							<portlet:param name="originalParentResourcePrimKey" value="<%= String.valueOf(kbArticlePortletInstanceConfiguration.resourcePrimKey()) %>" />
							<portlet:param name="selectableClassNameIds" value="<%= String.valueOf(PortalUtil.getClassNameId(KBArticleConstants.getClassName())) %>" />
						</liferay-portlet:renderURL>

						uri: '<%= selectKBEntryURL %>'
					},
					function(event) {
						var kbArticleData = {
							idString: 'resourcePrimKey',
							idValue: event.resourceprimkey,
							nameString: 'configurationKBArticle',
							nameValue: event.title
						};

						Liferay.Util.selectFolder(kbArticleData, '<portlet:namespace />');
					}
				);
			}
		);
	</aui:script>
</c:if>
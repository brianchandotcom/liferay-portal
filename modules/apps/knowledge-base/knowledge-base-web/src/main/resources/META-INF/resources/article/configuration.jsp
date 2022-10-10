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
kbArticlePortletInstanceConfiguration = ParameterMapUtil.setParameterMap(KBArticlePortletInstanceConfiguration.class, kbArticlePortletInstanceConfiguration, request.getParameterMap(), "preferences--", "--");
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-frontend:edit-form
	action="<%= configurationActionURL %>"
	cssClass="pt-0"
	method="post"
	name="fm"
>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="preferences--resourcePrimKey--" type="hidden" value="<%= kbArticlePortletInstanceConfiguration.resourcePrimKey() %>" />

	<liferay-frontend:edit-form-body>
		<liferay-frontend:fieldset-group>
			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				cssClass="mb-4"
				label="content-selection"
			>
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

			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				cssClass="mb-4"
				label="set-and-enable"
			>
				<aui:input label="enable-description" name="preferences--enableKBArticleDescription--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleDescription() %>" />

				<aui:input label="enable-ratings" name="preferences--enableKBArticleRatings--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleRatings() %>" />

				<aui:input label="show-asset-entries" name="preferences--showKBArticleAssetEntries--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.showKBArticleAssetEntries() %>" />

				<aui:input label="show-attachments" name="preferences--showKBArticleAttachments--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.showKBArticleAttachments() %>" />

				<aui:input label="enable-related-assets" name="preferences--enableKBArticleAssetLinks--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleAssetLinks() %>" />

				<aui:input label="enable-view-count-increment" name="preferences--enableKBArticleViewCountIncrement--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleViewCountIncrement() %>" />

				<aui:input label="enable-subscriptions" name="preferences--enableKBArticleSubscriptions--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleSubscriptions() %>" />

				<aui:input label="enable-history" name="preferences--enableKBArticleHistory--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticleHistory() %>" />

				<aui:input label="enable-print" name="preferences--enableKBArticlePrint--" type="checkbox" value="<%= kbArticlePortletInstanceConfiguration.enableKBArticlePrint() %>" />
			</liferay-frontend:fieldset>

			<liferay-frontend:fieldset
				collapsed="<%= false %>"
				collapsible="<%= true %>"
				label="social-bookmarks"
			>
				<liferay-social-bookmarks:bookmarks-settings
					displayStyle="<%= kbArticlePortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
					types="<%= SocialBookmarksUtil.getSocialBookmarksTypes(kbArticlePortletInstanceConfiguration.socialBookmarksTypes()) %>"
				/>
			</liferay-frontend:fieldset>
		</liferay-frontend:fieldset-group>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<aui:button type="submit" />

		<aui:button type="cancel" />
	</liferay-frontend:edit-form-footer>
</liferay-frontend:edit-form>

<liferay-frontend:component
	componentId='<%= liferayPortletResponse.getNamespace() + "PortletConfigurationComponent" %>'
	context='<%=
		HashMapBuilder.<String, Object>put(
			"eventName", liferayPortletResponse.getNamespace() + "selectKBObject"
		).put(
			"namespace", liferayPortletResponse.getNamespace()
		).put(
			"selectKBObjectURL",
			PortletURLBuilder.createRenderURL(
				liferayPortletResponse, portletResource
			).setMVCPath(
				"/admin/common/select_parent.jsp"
			).setParameter(
				"eventName", liferayPortletResponse.getNamespace() + "selectKBObject"
			).setParameter(
				"originalParentResourcePrimKey", kbArticlePortletInstanceConfiguration.resourcePrimKey()
			).setParameter(
				"parentResourceClassNameId", PortalUtil.getClassNameId(KBArticleConstants.getClassName())
			).setParameter(
				"parentResourcePrimKey", kbArticlePortletInstanceConfiguration.resourcePrimKey()
			).setParameter(
				"selectableClassNameIds", PortalUtil.getClassNameId(KBArticleConstants.getClassName())
			).setWindowState(
				LiferayWindowState.POP_UP
			).buildString()
		).build()
	%>'
	module="article/js/PortletConfiguration"
	servletContext="<%= application %>"
/>
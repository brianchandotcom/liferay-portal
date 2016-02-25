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

<%@ include file="/blogs/init.jsp" %>

<%
blogsPortletInstanceConfiguration = ConfigurationProviderUtil.getConfiguration(BlogsPortletInstanceConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), new PortletInstanceSettingsLocator(themeDisplay.getLayout(), portletDisplay.getPortletResource())));
%>

<liferay-portlet:actionURL portletConfiguration="<%= true %>" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="<%= true %>" var="configurationRenderURL" />

<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<aui:fieldset-group markupView="lexicon">
		<aui:fieldset collapsible="<%= true %>" label="display-settings">
			<aui:select label="maximum-items-to-display" name="preferences--pageDelta--">

				<%
				for (int pageDeltaValue : PropsValues.BLOGS_ENTRY_PAGE_DELTA_VALUES) {
				%>

					<aui:option label="<%= pageDeltaValue %>" selected="<%= GetterUtil.getInteger(blogsPortletInstanceConfiguration.pageDelta()) == pageDeltaValue %>" />

				<%
				}
				%>

			</aui:select>

			<div class="display-template">

				<%
				List<String> displayStyles = new ArrayList<String>();

				displayStyles.add(BlogsUtil.DISPLAY_STYLE_ABSTRACT);
				displayStyles.add(BlogsUtil.DISPLAY_STYLE_FULL_CONTENT);
				displayStyles.add(BlogsUtil.DISPLAY_STYLE_TITLE);
				%>

				<liferay-ddm:template-selector
					className="<%= BlogsEntry.class.getName() %>"
					displayStyle="<%= blogsPortletInstanceConfiguration.displayStyle() %>"
					displayStyleGroupId="<%= blogsPortletInstanceSettingsHelper.getDisplayStyleGroupId() %>"
					displayStyles="<%= displayStyles %>"
					refreshURL="<%= configurationRenderURL %>"
				/>
			</div>

			<aui:input name="preferences--enableFlags--" type="checkbox" value="<%= blogsPortletInstanceConfiguration.enableFlags() %>" />

			<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= blogsPortletInstanceConfiguration.enableRelatedAssets() %>" />

			<aui:input name="preferences--enableRatings--" type="checkbox" value="<%= blogsPortletInstanceConfiguration.enableRatings() %>" />

			<c:if test="<%= PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED %>">
				<aui:input name="preferences--enableComments--" type="checkbox" value="<%= blogsPortletInstanceConfiguration.enableComments() %>" />

				<aui:input name="preferences--enableCommentRatings--" type="checkbox" value="<%= blogsPortletInstanceConfiguration.enableCommentRatings() %>" />
			</c:if>

			<aui:input name="preferences--enableViewCount--" type="checkbox" value="<%= blogsPortletInstanceConfiguration.enableViewCount() %>" />

			<liferay-ui:social-bookmarks-settings
				displayPosition="<%= blogsPortletInstanceConfiguration.socialBookmarksDisplayPosition() %>"
				displayStyle="<%= blogsPortletInstanceConfiguration.socialBookmarksDisplayStyle() %>"
				enabled="<%= blogsPortletInstanceConfiguration.enableSocialBookmarks() %>"
				types="<%= blogsPortletInstanceConfiguration.socialBookmarksTypes() %>"
			/>
		</aui:fieldset>
	</aui:fieldset-group>

	<aui:button-row>
		<aui:button cssClass="btn-lg" type="submit" />
	</aui:button-row>
</aui:form>
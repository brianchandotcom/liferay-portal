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

<%@ include file="/message_boards/init.jsp" %>

<%
String topLink = ParamUtil.getString(request, "topLink", "message-boards-home");

String redirect = ParamUtil.getString(request, "redirect");

MBCategory category = (MBCategory)request.getAttribute(WebKeys.MESSAGE_BOARDS_CATEGORY);

long categoryId = MBUtil.getCategoryId(request, category);

String displayStyle = BeanPropertiesUtil.getString(category, "displayStyle", MBCategoryConstants.DEFAULT_DISPLAY_STYLE);

MBCategoryDisplay categoryDisplay = new MBCategoryDisplayImpl(scopeGroupId, categoryId);

Set<Long> categorySubscriptionClassPKs = null;
Set<Long> threadSubscriptionClassPKs = null;

if (themeDisplay.isSignedIn()) {
	categorySubscriptionClassPKs = MBUtil.getCategorySubscriptionClassPKs(user.getUserId());
	threadSubscriptionClassPKs = MBUtil.getThreadSubscriptionClassPKs(user.getUserId());
}

long groupThreadsUserId = ParamUtil.getLong(request, "groupThreadsUserId");

String assetTagName = ParamUtil.getString(request, "tag");

boolean useAssetEntryQuery = Validator.isNotNull(assetTagName);

PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setParameter("mvcRenderCommandName", "/message_boards/view");
portletURL.setParameter("topLink", topLink);
portletURL.setParameter("mbCategoryId", String.valueOf(categoryId));

request.setAttribute("view.jsp-categoryDisplay", categoryDisplay);

request.setAttribute("view.jsp-categorySubscriptionClassPKs", categorySubscriptionClassPKs);
request.setAttribute("view.jsp-threadSubscriptionClassPKs", threadSubscriptionClassPKs);

request.setAttribute("view.jsp-viewCategory", Boolean.TRUE.toString());

request.setAttribute("view.jsp-portletURL", portletURL);
%>

<portlet:actionURL name="/message_boards/edit_category" var="restoreTrashEntriesURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.RESTORE %>" />
</portlet:actionURL>

<liferay-ui:trash-undo
	portletURL="<%= restoreTrashEntriesURL %>"
/>

<liferay-util:include page="/message_boards/top_links.jsp" servletContext="<%= application %>" />

<c:choose>
	<c:when test="<%= useAssetEntryQuery %>">
		<liferay-ui:categorization-filter
			assetType="threads"
			portletURL="<%= portletURL %>"
		/>

		<%@ include file="/message_boards/view_threads.jspf" %>

	</c:when>
	<c:when test='<%= topLink.equals("message-boards-home") %>'>
		<c:if test="<%= MBPermission.contains(permissionChecker, scopeGroupId, ActionKeys.PERMISSIONS) %>">
			<div class="category-buttons">

				<%
				String modelResource = "com.liferay.portlet.messageboards";
				String modelResourceDescription = themeDisplay.getScopeGroupName();
				String resourcePrimKey = String.valueOf(scopeGroupId);

				if (category != null) {
					modelResource = MBCategory.class.getName();
					modelResourceDescription = category.getName();
					resourcePrimKey = String.valueOf(category.getCategoryId());
				}
				%>

				<liferay-security:permissionsURL
					modelResource="<%= modelResource %>"
					modelResourceDescription="<%= HtmlUtil.escape(modelResourceDescription) %>"
					resourcePrimKey="<%= resourcePrimKey %>"
					var="permissionsURL"
					windowState="<%= LiferayWindowState.POP_UP.toString() %>"
				/>

				<aui:button href="<%= permissionsURL %>" useDialog="<%= true %>" value="permissions" />
			</div>

			<%@ include file="/message_boards/category_subscriptions.jspf" %>
		</c:if>

		<c:if test="<%= category != null %>">
			<div class="category-subscription category-subscription-types">
				<c:if test="<%= enableRSS %>">
					<liferay-ui:rss
						delta="<%= rssDelta %>"
						displayStyle="<%= rssDisplayStyle %>"
						feedType="<%= rssFeedType %>"
						url="<%= MBUtil.getRSSURL(plid, category.getCategoryId(), 0, 0, themeDisplay) %>"
					/>
				</c:if>

				<c:if test="<%= MBCategoryPermission.contains(permissionChecker, category, ActionKeys.SUBSCRIBE) && (mbGroupServiceSettings.isEmailMessageAddedEnabled() || mbGroupServiceSettings.isEmailMessageUpdatedEnabled()) %>">
					<c:choose>
						<c:when test="<%= (categorySubscriptionClassPKs != null) && categorySubscriptionClassPKs.contains(category.getCategoryId()) %>">
							<portlet:actionURL name="/message_boards/edit_category" var="unsubscribeURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UNSUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-remove-sign"
								label="<%= true %>"
								message="unsubscribe"
								url="<%= unsubscribeURL %>"
							/>
						</c:when>
						<c:otherwise>
							<portlet:actionURL name="/message_boards/edit_category" var="subscribeURL">
								<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.SUBSCRIBE %>" />
								<portlet:param name="redirect" value="<%= currentURL %>" />
								<portlet:param name="mbCategoryId" value="<%= String.valueOf(category.getCategoryId()) %>" />
							</portlet:actionURL>

							<liferay-ui:icon
								iconCssClass="icon-ok-sign"
								label="<%= true %>"
								message="subscribe"
								url="<%= subscribeURL %>"
							/>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>

			<%
			long parentCategoryId = category.getParentCategoryId();
			String parentCategoryName = LanguageUtil.get(request, "message-boards-home");

			if (!category.isRoot()) {
				MBCategory parentCategory = MBCategoryLocalServiceUtil.getCategory(parentCategoryId);

				parentCategoryId = parentCategory.getCategoryId();
				parentCategoryName = parentCategory.getName();
			}
			%>

			<portlet:renderURL var="backURL">
				<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
				<portlet:param name="mbCategoryId" value="<%= String.valueOf(parentCategoryId) %>" />
			</portlet:renderURL>

			<liferay-ui:header
				backLabel="<%= parentCategoryName %>"
				backURL="<%= backURL.toString() %>"
				localizeTitle="<%= false %>"
				title="<%= category.getName() %>"
			/>
		</c:if>

		<div class="displayStyle-<%= HtmlUtil.escapeAttribute(displayStyle) %>">
			<liferay-util:include page='<%= "/message_boards/view_category_" + displayStyle + ".jsp" %>' servletContext="<%= application %>" />
		</div>

		<%
		if (category != null) {
			PortalUtil.setPageSubtitle(category.getName(), request);
			PortalUtil.setPageDescription(category.getDescription(), request);

			MBUtil.addPortletBreadcrumbEntries(category, request, renderResponse);
		}
		%>

	</c:when>
	<c:when test='<%= topLink.equals("my-posts") || topLink.equals("my-subscriptions") || topLink.equals("recent-posts") %>'>

		<%
		if ((topLink.equals("my-posts") || topLink.equals("my-subscriptions")) && themeDisplay.isSignedIn()) {
			groupThreadsUserId = user.getUserId();
		}

		if (groupThreadsUserId > 0) {
			portletURL.setParameter("groupThreadsUserId", String.valueOf(groupThreadsUserId));
		}
		%>

		<c:if test='<%= topLink.equals("recent-posts") && (groupThreadsUserId > 0) %>'>
			<div class="alert alert-info">
				<liferay-ui:message key="filter-by-user" />: <%= HtmlUtil.escape(PortalUtil.getUserName(groupThreadsUserId, StringPool.BLANK)) %>
			</div>
		</c:if>

		<c:if test='<%= topLink.equals("my-subscriptions") %>'>
			<liferay-ui:search-container
				curParam="cur1"
				deltaConfigurable="<%= false %>"
				emptyResultsMessage="you-are-not-subscribed-to-any-categories"
				headerNames="category,categories,threads,posts"
				iteratorURL="<%= portletURL %>"
				total="<%= MBCategoryServiceUtil.getSubscribedCategoriesCount(scopeGroupId, user.getUserId()) %>"
			>
				<liferay-ui:search-container-results
					results="<%= MBCategoryServiceUtil.getSubscribedCategories(scopeGroupId, user.getUserId(), searchContainer.getStart(), searchContainer.getEnd()) %>"
				/>

				<liferay-ui:search-container-row
					className="com.liferay.portlet.messageboards.model.MBCategory"
					escapedModel="<%= true %>"
					keyProperty="categoryId"
					modelVar="curCategory"
				>
					<liferay-ui:search-container-row-parameter name="categorySubscriptionClassPKs" value="<%= categorySubscriptionClassPKs %>" />

					<liferay-portlet:renderURL varImpl="rowURL">
						<portlet:param name="mvcRenderCommandName" value="/message_boards/view" />
						<portlet:param name="mbCategoryId" value="<%= String.valueOf(curCategory.getCategoryId()) %>" />
					</liferay-portlet:renderURL>

					<%@ include file="/message_boards/subscribed_category_columns.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator type="more" />
			</liferay-ui:search-container>
		</c:if>

		<%@ include file="/message_boards/view_threads.jspf" %>

		<c:if test='<%= enableRSS && topLink.equals("recent-posts") %>'>
			<br />

			<liferay-ui:rss
				delta="<%= rssDelta %>"
				displayStyle="<%= rssDisplayStyle %>"
				feedType="<%= rssFeedType %>"
				message="subscribe-to-recent-posts"
				url="<%= MBUtil.getRSSURL(plid, 0, 0, groupThreadsUserId, themeDisplay) %>"
			/>
		</c:if>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= topLink.equals("statistics") %>'>
		<liferay-ui:panel-container cssClass="statistics-panel" extended="<%= false %>" id="messageBoardsStatisticsPanelContainer" persistState="<%= true %>">
			<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsGeneralStatisticsPanel" persistState="<%= true %>" title="general">
				<dl>
					<dt>
						<liferay-ui:message key="num-of-categories" />:
					</dt>
					<dd>
						<%= numberFormat.format(categoryDisplay.getAllCategoriesCount()) %>
					</dd>
					<dt>
						<liferay-ui:message key="num-of-posts" />:
					</dt>
					<dd>
						<%= numberFormat.format(MBStatsUserLocalServiceUtil.getMessageCountByGroupId(scopeGroupId)) %>
					</dd>
					<dt>
						<liferay-ui:message key="num-of-participants" />:
					</dt>
					<dd>
						<%= numberFormat.format(MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId)) %>
					</dd>
				</dl>
			</liferay-ui:panel>

			<liferay-ui:panel collapsible="<%= true %>" cssClass="statistics-panel-content" extended="<%= true %>" id="messageBoardsTopPostersPanel" persistState="<%= true %>" title="top-posters">
				<liferay-ui:search-container
					emptyResultsMessage="there-are-no-top-posters"
					iteratorURL="<%= portletURL %>"
					total="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupIdCount(scopeGroupId) %>"
				>
					<liferay-ui:search-container-results
						results="<%= MBStatsUserLocalServiceUtil.getStatsUsersByGroupId(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
					/>

					<liferay-ui:search-container-row
						className="com.liferay.portlet.messageboards.model.MBStatsUser"
						keyProperty="statsUserId"
						modelVar="statsUser"
					>
						<liferay-ui:search-container-column-jsp
							path="/message_boards/top_posters_user_display.jsp"
						/>
					</liferay-ui:search-container-row>

					<liferay-ui:search-iterator />
				</liferay-ui:search-container>
			</liferay-ui:panel>
		</liferay-ui:panel-container>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
	<c:when test='<%= topLink.equals("banned-users") %>'>
		<liferay-ui:search-container
			emptyResultsMessage="there-are-no-banned-users"
			headerNames="banned-user,banned-by,ban-date"
			iteratorURL="<%= portletURL %>"
			total="<%= MBBanLocalServiceUtil.getBansCount(scopeGroupId) %>"
		>
			<liferay-ui:search-container-results
				results="<%= MBBanLocalServiceUtil.getBans(scopeGroupId, searchContainer.getStart(), searchContainer.getEnd()) %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.portlet.messageboards.model.MBBan"
				keyProperty="banId"
				modelVar="ban"
			>

				<%
				String bannedUserDisplayURL = StringPool.BLANK;

				try {
					User bannedUser = UserLocalServiceUtil.getUser(ban.getBanUserId());

					bannedUserDisplayURL = bannedUser.getDisplayURL(themeDisplay);
				}
				catch (NoSuchUserException nsue) {
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= bannedUserDisplayURL %>"
					name="banned-user"
					value="<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getBanUserId(), StringPool.BLANK)) %>"
				/>

				<%
				String bannedByUserDisplayURL = StringPool.BLANK;

				try {
					User bannedByUser = UserLocalServiceUtil.getUser(ban.getUserId());

					bannedByUserDisplayURL = bannedByUser.getDisplayURL(themeDisplay);
				}
				catch (NoSuchUserException nsue) {
				}
				%>

				<liferay-ui:search-container-column-text
					href="<%= bannedByUserDisplayURL %>"
					name="banned-by"
					value="<%= HtmlUtil.escape(PortalUtil.getUserName(ban.getUserId(), StringPool.BLANK)) %>"
				/>

				<liferay-ui:search-container-column-date
					name="ban-date"
					value="<%= ban.getCreateDate() %>"
				/>

				<c:if test="<%= PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL > 0 %>">
					<liferay-ui:search-container-column-date
						name="unban-date"
						value="<%= MBUtil.getUnbanDate(ban, PropsValues.MESSAGE_BOARDS_EXPIRE_BAN_INTERVAL) %>"
					/>
				</c:if>

				<liferay-ui:search-container-column-jsp
					align="right"
					cssClass="entry-action"
					path="/message_boards/ban_user_action.jsp"
				/>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator />
		</liferay-ui:search-container>

		<%
		PortalUtil.setPageSubtitle(LanguageUtil.get(request, StringUtil.replace(topLink, StringPool.UNDERLINE, StringPool.DASH)), request);
		PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, TextFormatter.format(topLink, TextFormatter.O)), portletURL.toString());
		%>

	</c:when>
</c:choose>

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_message_boards_web.message_boards.view_jsp");
%>
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

<%@ include file="/init.jsp" %>

<liferay-ui:success key="membershipRequestSent" message="your-request-was-sent-you-will-receive-a-reply-by-email" />

<liferay-ui:error embed="<%= false %>" key="membershipAlreadyRequested" message="membership-was-already-requested" />

<clay:navigation-bar
	navigationItems="<%= siteMySitesDisplayContext.getNavigationItems() %>"
/>

<clay:management-toolbar
	displayContext="<%= new SiteMySitesManagementToolbarDisplayContext(liferayPortletRequest, liferayPortletResponse, request, siteMySitesDisplayContext) %>"
/>

<aui:form action="<%= siteMySitesDisplayContext.getPortletURL() %>" cssClass="container-fluid-1280" method="get" name="fm">
	<liferay-ui:search-container
		searchContainer="<%= siteMySitesDisplayContext.getGroupSearchContainer() %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.portal.kernel.model.Group"
			keyProperty="groupId"
			modelVar="group"
			rowIdProperty="friendlyURL"
		>

			<%
			String siteImageURL = group.getLogoURL(themeDisplay, false);

			String rowURL = StringPool.BLANK;

			if (group.getPublicLayoutsPageCount() > 0) {
				rowURL = group.getDisplayURL(themeDisplay, false);
			}
			else if (Objects.equals(siteMySitesDisplayContext.getTabs1(), "my-sites") && (group.getPrivateLayoutsPageCount() > 0)) {
				rowURL = group.getDisplayURL(themeDisplay, true);
			}
			%>

			<c:choose>
				<c:when test='<%= Objects.equals(siteMySitesDisplayContext.getDisplayStyle(), "descriptive") %>'>
					<c:choose>
						<c:when test="<%= Validator.isNotNull(siteImageURL) %>">
							<liferay-ui:search-container-column-image
								src="<%= siteImageURL %>"
							/>
						</c:when>
						<c:otherwise>
							<liferay-ui:search-container-column-icon
								icon="sites"
							/>
						</c:otherwise>
					</c:choose>

					<liferay-ui:search-container-column-text
						colspan="<%= 2 %>"
					>
						<h5>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(rowURL) %>">
									<a href="<%= rowURL %>" target="_blank">
										<strong><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></strong>
									</a>
								</c:when>
								<c:otherwise>
									<strong><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></strong>
								</c:otherwise>
							</c:choose>
						</h5>

						<c:if test='<%= !Objects.equals(siteMySitesDisplayContext.getTabs1(), "my-sites") && Validator.isNotNull(group.getDescription(locale)) %>'>
							<h6 class="text-default">
								<%= HtmlUtil.escape(group.getDescription(locale)) %>
							</h6>
						</c:if>

						<h6 class="text-default">
							<liferay-asset:asset-tags-summary
								className="<%= Group.class.getName() %>"
								classPK="<%= group.getGroupId() %>"
							/>
						</h6>

						<h6 class="text-default">
							<strong><liferay-ui:message key="members" /></strong>: <%= siteMySitesDisplayContext.getGroupUsersCounts(group.getGroupId()) %>
						</h6>

						<c:if test='<%= Objects.equals(siteMySitesDisplayContext.getTabs1(), "my-sites") && PropsValues.LIVE_USERS_ENABLED %>'>
							<h6 class="text-default">
								<strong><liferay-ui:message key="online-now" /></strong>: <%= String.valueOf(LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId())) %>
							</h6>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							defaultEventHandler="<%= MySitesWebKeys.SITES_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
							dropdownItems="<%= siteMySitesDisplayContext.getArticleActionDropdownItems(group) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(siteMySitesDisplayContext.getDisplayStyle(), "icon") %>'>

					<%
					row.setCssClass("entry-card lfr-asset-item");
					%>

					<liferay-ui:search-container-column-text>
						<clay:vertical-card
							verticalCard="<%= new SiteVerticalCard(group, renderRequest, renderResponse, siteMySitesDisplayContext.getTabs1(), siteMySitesDisplayContext.getGroupUsersCounts(group.getGroupId())) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
				<c:when test='<%= Objects.equals(siteMySitesDisplayContext.getDisplayStyle(), "list") %>'>
					<liferay-ui:search-container-column-text
						name="name"
						orderable="<%= true %>"
						truncate="<%= true %>"
					>
						<c:choose>
							<c:when test="<%= Validator.isNotNull(rowURL) %>">
								<a href="<%= rowURL %>" target="_blank">
									<strong><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></strong>
								</a>
							</c:when>
							<c:otherwise>
								<strong><%= HtmlUtil.escape(group.getDescriptiveName(locale)) %></strong>
							</c:otherwise>
						</c:choose>

						<c:if test='<%= !Objects.equals(siteMySitesDisplayContext.getTabs1(), "my-sites") && Validator.isNotNull(group.getDescription(locale)) %>'>
							<br />

							<em><%= HtmlUtil.escape(group.getDescription(locale)) %></em>
						</c:if>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text
						name="members"
						value="<%= String.valueOf(siteMySitesDisplayContext.getGroupUsersCounts(group.getGroupId())) %>"
					/>

					<c:if test='<%= Objects.equals(siteMySitesDisplayContext.getTabs1(), "my-sites") && PropsValues.LIVE_USERS_ENABLED %>'>
						<liferay-ui:search-container-column-text
							name="online-now"
							value="<%= String.valueOf(LiveUsers.getGroupUsersCount(company.getCompanyId(), group.getGroupId())) %>"
						/>
					</c:if>

					<liferay-ui:search-container-column-text
						name="tags"
					>
						<liferay-asset:asset-tags-summary
							className="<%= Group.class.getName() %>"
							classPK="<%= group.getGroupId() %>"
						/>
					</liferay-ui:search-container-column-text>

					<liferay-ui:search-container-column-text>
						<clay:dropdown-actions
							defaultEventHandler="<%= MySitesWebKeys.SITES_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
							dropdownItems="<%= siteMySitesDisplayContext.getArticleActionDropdownItems(group) %>"
						/>
					</liferay-ui:search-container-column-text>
				</c:when>
			</c:choose>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			displayStyle="<%= siteMySitesDisplayContext.getDisplayStyle() %>"
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</aui:form>

<liferay-frontend:component
	componentId="<%= MySitesWebKeys.SITES_DROPDOWN_DEFAULT_EVENT_HANDLER %>"
	module="js/SiteDropdownDefaultEventHandler.es"
/>
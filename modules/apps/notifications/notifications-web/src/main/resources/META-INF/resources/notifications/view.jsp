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

<%
String navigation = ParamUtil.getString(request, "navigation", "all");

boolean actionRequired = ParamUtil.getBoolean(request, "actionRequired");

if (actionRequired) {
	navigation = "unread";
}

SearchContainer<UserNotificationEvent> notificationsSearchContainer = new SearchContainer(renderRequest, currentURLObj, null, actionRequired ? "you-do-not-have-any-requests" : "you-do-not-have-any-notifications");

String searchContainerId = "userNotificationEvents";

if (actionRequired) {
	searchContainerId = "actionableUserNotificationEvents";
}

notificationsSearchContainer.setId(searchContainerId);

NotificationsManagementToolbarDisplayContext notificationsManagementToolbarDisplayContext = new NotificationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, currentURLObj);

NotificationsUtil.populateResults(themeDisplay.getUserId(), actionRequired, navigation, notificationsManagementToolbarDisplayContext.getOrderByType(), notificationsSearchContainer);
%>

<clay:navigation-bar
	inverted="<%= layout.isTypeControlPanel() %>"
	navigationItems='<%=
		new JSPNavigationItemList(pageContext) {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(!actionRequired);
						navigationItem.setHref(renderResponse.createRenderURL(), "actionRequired", StringPool.FALSE);
						navigationItem.setLabel(LanguageUtil.format(httpServletRequest, "notifications-list-x", UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, false)));
					});

				add(
					navigationItem -> {
						navigationItem.setActive(actionRequired);
						navigationItem.setHref(renderResponse.createRenderURL(), "actionRequired", StringPool.TRUE);
						navigationItem.setLabel(LanguageUtil.format(httpServletRequest, "requests-list-x", String.valueOf(UserNotificationEventLocalServiceUtil.getArchivedUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, true, false))));
					});
			}
		}
	%>'
/>

<portlet:actionURL name="deleteNotifications" var="deleteNotificationsURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="markNotificationsAsRead" var="markNotificationsAsReadURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<portlet:actionURL name="markNotificationsAsUnread" var="markNotificationsAsUnreadURL">
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<clay:management-toolbar
	actionDropdownItems="<%= notificationsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	additionalProps='<%=
		HashMapBuilder.<String, Object>put(
			"deleteNotificationsURL", deleteNotificationsURL.toString()
		).put(
			"markNotificationsAsReadURL", markNotificationsAsReadURL.toString()
		).put(
			"markNotificationsAsUnreadURL", markNotificationsAsUnreadURL.toString()
		).build()
	%>'
	clearResultsURL="<%= notificationsManagementToolbarDisplayContext.getClearResultsURL() %>"
	disabled="<%= NotificationsUtil.getAllNotificationsCount(themeDisplay.getUserId(), actionRequired) == 0 %>"
	filterDropdownItems="<%= notificationsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= notificationsManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= notificationsSearchContainer.getTotal() %>"
	propsTransformer="notifications/js/NotificationsManagementToolbarPropsTransformer"
	searchContainerId="<%= searchContainerId %>"
	selectable="<%= actionRequired ? false : true %>"
	showCreationMenu="<%= false %>"
	showInfoButton="<%= false %>"
	showSearch="<%= false %>"
	sortingOrder="<%= notificationsManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(notificationsManagementToolbarDisplayContext.getSortingURL()) %>"
/>

<clay:container-fluid>
	<aui:form action="<%= currentURL %>" method="get" name="fm">
		<div class="user-notifications">
			<liferay-ui:search-container
				rowChecker="<%= actionRequired ? null : new UserNotificationEventRowChecker(request, renderResponse) %>"
				searchContainer="<%= notificationsSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.UserNotificationEvent"
					keyProperty="userNotificationEventId"
					modelVar="userNotificationEvent"
				>

					<%
					UserNotificationFeedEntry userNotificationFeedEntry = UserNotificationManagerUtil.interpret(StringPool.BLANK, userNotificationEvent, ServiceContextFactory.getInstance(request));

					row.setData(
						HashMapBuilder.<String, Object>put(
							"actions", StringUtil.merge(notificationsManagementToolbarDisplayContext.getAvailableActions(userNotificationEvent, userNotificationFeedEntry))
						).put(
							"userNotificationFeedEntry", userNotificationFeedEntry
						).build());
					%>

					<%@ include file="/notifications/user_notification_entry.jspf" %>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					displayStyle="descriptive"
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>
	</aui:form>
</clay:container-fluid>

<aui:script use="aui-base">
	var form = A.one('#<portlet:namespace />fm');

	form.delegate(
		'click',
		(event) => {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			Liferay.Util.fetch(currentTarget.attr('href'), {
				method: 'POST',
			})
				.then((response) => {
					return response.json();
				})
				.then((response) => {
					if (response.success) {
						var notificationContainer = currentTarget.ancestor(
							'li.list-group-item'
						);

						if (notificationContainer) {
							var markAsReadURL = notificationContainer
								.one('a')
								.attr('href');

							form.attr('method', 'post');

							submitForm(form, markAsReadURL);

							notificationContainer.remove();
						}
					}
					else {
						Liferay.Util.openToast({
							message:
								'<liferay-ui:message key="an-unexpected-error-occurred" />',
							toastProps: {
								autoClose: 5000,
							},
							type: 'warning',
						});
					}
				});
		},
		'.user-notification-action'
	);
</aui:script>
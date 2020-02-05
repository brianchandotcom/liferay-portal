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

SearchContainer notificationsSearchContainer = new SearchContainer(renderRequest, currentURLObj, null, actionRequired ? "you-do-not-have-any-requests" : "you-do-not-have-any-notifications");

String searchContainerId = "userNotificationEvents";

if (actionRequired) {
	searchContainerId = "actionableUserNotificationEvents";
}

notificationsSearchContainer.setId(searchContainerId);

NotificationsManagementToolbarDisplayContext notificationsManagementToolbarDisplayContext = new NotificationsManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, currentURLObj);

NotificationsUtil.populateResults(themeDisplay.getUserId(), actionRequired, navigation, notificationsManagementToolbarDisplayContext.getOrderByType(), notificationsSearchContainer);

PortletURL navigationURL = PortletURLUtil.clone(currentURLObj, renderResponse);

navigationURL.setParameter(SearchContainer.DEFAULT_CUR_PARAM, "0");
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
						navigationItem.setLabel(LanguageUtil.format(request, "notifications-list-x", UserNotificationEventLocalServiceUtil.getDeliveredUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, false)));
					});

				add(
					navigationItem -> {

						navigationItem.setActive(actionRequired);
						navigationItem.setHref(renderResponse.createRenderURL(), "actionRequired", StringPool.TRUE);
						navigationItem.setLabel(LanguageUtil.format(request, "requests-list-x", String.valueOf(UserNotificationEventLocalServiceUtil.getArchivedUserNotificationEventsCount(themeDisplay.getUserId(), UserNotificationDeliveryConstants.TYPE_WEBSITE, true, true, false))));
					});
			}
		}
	%>'
/>

<clay:management-toolbar
	actionDropdownItems="<%= notificationsManagementToolbarDisplayContext.getActionDropdownItems() %>"
	clearResultsURL="<%= notificationsManagementToolbarDisplayContext.getClearResultsURL() %>"
	componentId="notificationsManagementToolbar"
	disabled="<%= NotificationsUtil.getAllNotificationsCount(themeDisplay.getUserId(), actionRequired) == 0 %>"
	filterDropdownItems="<%= notificationsManagementToolbarDisplayContext.getFilterDropdownItems() %>"
	filterLabelItems="<%= notificationsManagementToolbarDisplayContext.getFilterLabelItems() %>"
	itemsTotal="<%= notificationsSearchContainer.getTotal() %>"
	searchContainerId="<%= searchContainerId %>"
	selectable="<%= actionRequired ? false : true %>"
	showCreationMenu="<%= false %>"
	showInfoButton="<%= false %>"
	showSearch="<%= false %>"
	sortingOrder="<%= notificationsManagementToolbarDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(notificationsManagementToolbarDisplayContext.getSortingURL()) %>"
/>

<div class="container-fluid-1280 main-content-body">
	<aui:form action="<%= currentURL %>" method="get" name="fm">
		<div class="user-notifications">
			<liferay-ui:search-container
				rowChecker="<%= actionRequired ? null : new UserNotificationEventRowChecker(renderResponse) %>"
				searchContainer="<%= notificationsSearchContainer %>"
			>
				<liferay-ui:search-container-row
					className="com.liferay.portal.kernel.model.UserNotificationEvent"
					keyProperty="userNotificationEventId"
					modelVar="userNotificationEvent"
				>

					<%
					Map<String, Object> rowData = new HashMap<String, Object>();

					UserNotificationFeedEntry userNotificationFeedEntry = UserNotificationManagerUtil.interpret(StringPool.BLANK, userNotificationEvent, ServiceContextFactory.getInstance(request));

					rowData.put("actions", StringUtil.merge(notificationsManagementToolbarDisplayContext.getAvailableActions(userNotificationEvent, userNotificationFeedEntry)));
					rowData.put("userNotificationFeedEntry", userNotificationFeedEntry);

					row.setData(rowData);
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
</div>

<aui:script sandbox="<%= true %>">
	var deleteNotifications = function() {
		var form = document.getElementById('<portlet:namespace />fm');

		form.setAttribute('method', 'post');

		submitForm(
			form,
			'<portlet:actionURL name="deleteNotifications"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>'
		);
	};

	var markNotificationsAsRead = function() {
		var form = document.getElementById('<portlet:namespace />fm');

		form.setAttribute('method', 'post');

		submitForm(
			form,
			'<portlet:actionURL name="markNotificationsAsRead"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>'
		);
	};

	var markNotificationsAsUnread = function() {
		var form = document.getElementById('<portlet:namespace />fm');

		form.setAttribute('method', 'post');

		submitForm(
			form,
			'<portlet:actionURL name="markNotificationsAsUnread"><portlet:param name="redirect" value="<%= currentURL %>" /></portlet:actionURL>'
		);
	};

	var ACTIONS = {
		deleteNotifications: deleteNotifications,
		markNotificationsAsRead: markNotificationsAsRead,
		markNotificationsAsUnread: markNotificationsAsUnread
	};

	Liferay.componentReady('notificationsManagementToolbar').then(function(
		managementToolbar
	) {
		managementToolbar.on('actionItemClicked', function(event) {
			var itemData = event.data.item.data;

			if (itemData && itemData.action && ACTIONS[itemData.action]) {
				ACTIONS[itemData.action]();
			}
		});
	});
</aui:script>

<aui:script use="aui-base,liferay-notice">
	var form = A.one('#<portlet:namespace />fm');

	form.delegate(
		'click',
		function(event) {
			event.preventDefault();

			var currentTarget = event.currentTarget;

			Liferay.Util.fetch(currentTarget.attr('href'), {
				method: 'POST'
			})
				.then(function(response) {
					return response.json();
				})
				.then(function(response) {
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
						getNotice().show();
					}
				});
		},
		'.user-notification-action'
	);

	var notice;

	function getNotice() {
		if (!notice) {
			notice = new Liferay.Notice({
				closeText: false,
				content:
					'<liferay-ui:message key="an-unexpected-error-occurred" /><button aria-label="' +
					Liferay.Language.get('close') +
					'" class="close" type="button">&times;</button>',
				timeout: 5000,
				toggleText: false,
				type: 'warning',
				useAnimation: false
			});
		}

		return notice;
	}
</aui:script>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long userId = ParamUtil.getLong(request, "userId");

User user2 = null;

if (userId > 0) {
	user2 = UserLocalServiceUtil.getUser(userId);
}

boolean showAddAsConnectionButton = false;
boolean showBlockButton = false;
boolean showFollowButton = false;
boolean showRemoveAsConnectionButton = false;
boolean showUnBlockButton = false;
boolean showUnFollowButton = false;
boolean viewRelationActions = true;

if (user2 != null) {
	if (SocialRelationLocalServiceUtil.hasRelation(user2.getUserId(), themeDisplay.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY)) {
		viewRelationActions = false;
	}
	else if (SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY)) {
		viewRelationActions = false;
	}

	if (viewRelationActions) {
		showAddAsConnectionButton = !SocialRequestLocalServiceUtil.hasRequest(themeDisplay.getUserId(), User.class.getName(), themeDisplay.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION, user2.getUserId(), SocialRequestConstants.STATUS_PENDING) && SocialRelationLocalServiceUtil.isRelatable(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION);
		showRemoveAsConnectionButton = SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_BI_CONNECTION);
		showFollowButton = SocialRelationLocalServiceUtil.isRelatable(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER);
		showUnFollowButton = SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_FOLLOWER);
	}

	showBlockButton = SocialRelationLocalServiceUtil.isRelatable(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY);
	showUnBlockButton = SocialRelationLocalServiceUtil.hasRelation(themeDisplay.getUserId(), user2.getUserId(), SocialRelationConstants.TYPE_UNI_ENEMY);
}

String userDisplayURL = StringPool.BLANK;

if (user2 != null) {
	userDisplayURL = user2.getDisplayURL(themeDisplay);
}
%>

<div class="lfr-button-column" id="<portlet:namespace />buttonColumn">
	<div class="lfr-button-column-content">
		<aui:button-row cssClass="btn-group edit-toolbar" id='<%= liferayPortletResponse.getNamespace() + "userToolbar" %>' />

		<div class="btn view-more-button">
			<liferay-ui:icon
				icon="ellipsis-h"
				markupView="lexicon"
			/>

			<liferay-ui:message key="more" />
		</div>
	</div>
</div>

<aui:script position="inline" use="aui-dialog-iframe-deprecated,aui-io-plugin-deprecated,aui-toolbar,liferay-util-window">
	var buttonRow = A.one('#<portlet:namespace />userToolbar');

	var contactsToolbarChildren = [];

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: '<%= showAddAsConnectionButton ? "" : "hide" %>',
			id: '<portlet:namespace />addConnectionButton',
			label: '<%= UnicodeLanguageUtil.get(request, "connect") %>',
			on: {
				click: function (event) {
					<portlet:namespace />relationAction(
						event,
						'<portlet:actionURL name="requestSocialRelation" windowState="<%= LiferayWindowState.NORMAL.toString() %>"><portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_BI_CONNECTION) %>" /></portlet:actionURL>'
					);
				},
			},
			render: true,
		})
	);

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: '<%= showRemoveAsConnectionButton ? "" : "hide" %>',
			id: '<portlet:namespace />removeConnectionButton',
			label: '<%= UnicodeLanguageUtil.get(request, "disconnect") %>',
			on: {
				click: function (event) {
					<portlet:namespace />relationAction(
						event,
						'<portlet:actionURL name="deleteSocialRelation" windowState="<%= LiferayWindowState.NORMAL.toString() %>"><portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_BI_CONNECTION) %>" /></portlet:actionURL>'
					);
				},
			},
			render: true,
		})
	);

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: 'more <%= showFollowButton ? "" : "hide" %>',
			id: '<portlet:namespace />followButton',
			label: '<%= UnicodeLanguageUtil.get(request, "follow") %>',
			on: {
				click: function (event) {
					<portlet:namespace />relationAction(
						event,
						'<portlet:actionURL name="addSocialRelation" windowState="<%= LiferayWindowState.NORMAL.toString() %>"><portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_FOLLOWER) %>" /></portlet:actionURL>'
					);
				},
			},
			render: true,
		})
	);

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: 'more <%= showUnFollowButton ? "" : "hide" %>',
			id: '<portlet:namespace />unfollowButton',
			label: '<%= UnicodeLanguageUtil.get(request, "unfollow") %>',
			on: {
				click: function (event) {
					<portlet:namespace />relationAction(
						event,
						'<portlet:actionURL name="deleteSocialRelation" windowState="<%= LiferayWindowState.NORMAL.toString() %>"><portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_FOLLOWER) %>" /></portlet:actionURL>'
					);
				},
			},
			render: true,
		})
	);

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: 'more <%= showBlockButton ? "" : "hide" %>',
			id: '<portlet:namespace />blockButton',
			label: '<%= UnicodeLanguageUtil.get(request, "block") %>',
			on: {
				click: function (event) {
					<portlet:namespace />relationAction(
						event,
						'<portlet:actionURL name="addSocialRelation" windowState="<%= LiferayWindowState.NORMAL.toString() %>"><portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_ENEMY) %>" /></portlet:actionURL>'
					);
				},
			},
			render: true,
		})
	);

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: 'more <%= showUnBlockButton ? "" : "hide" %>',
			id: '<portlet:namespace />unblockButton',
			label: '<%= UnicodeLanguageUtil.get(request, "unblock") %>',
			on: {
				click: function (event) {
					<portlet:namespace />relationAction(
						event,
						'<portlet:actionURL name="deleteSocialRelation" windowState="<%= LiferayWindowState.NORMAL.toString() %>"><portlet:param name="type" value="<%= String.valueOf(SocialRelationConstants.TYPE_UNI_ENEMY) %>" /></portlet:actionURL>'
					);
				},
			},
			render: true,
		})
	);

	contactsToolbarChildren.push(
		new A.Button({
			cssClass: 'more',
			id: '<portlet:namespace />exportButton',
			label: '<%= UnicodeLanguageUtil.get(request, "vcard") %>',
			on: {
				click: function (event) {
					<c:choose>
						<c:when test="<%= user2 != null %>">
							location.href =
								'<liferay-portlet:resourceURL id="exportVCard"><portlet:param name="userId" value="<%= String.valueOf(user2.getUserId()) %>" /></liferay-portlet:resourceURL>';
						</c:when>
						<c:otherwise>
							location.href =
								'<liferay-portlet:resourceURL id="exportVCards" />&<portlet:namespace />userIds=' +
								A.all('.lfr-contact-grid-item input').val();
						</c:otherwise>
					</c:choose>
				},
			},
		})
	);

	var contactsToolbar = new A.Toolbar({
		activeState: false,
		boundingBox: buttonRow,
		children: contactsToolbarChildren,
	}).render();

	var editToolbar = A.one('.edit-toolbar');

	editToolbar.toggleClass('hide-more-buttons', true);

	var buttonColumn = A.one('#<portlet:namespace />buttonColumn');

	var viewMoreButton = buttonColumn.one('.view-more-button');

	buttonColumn.delegate(
		'click',
		(event) => {
			editToolbar.toggleClass('hide-more-buttons', false);

			viewMoreButton.hide();
		},
		'.view-more-button'
	);

	function <portlet:namespace />relationAction(event, uri) {
		var end = <%= ContactsConstants.MAX_RESULT_COUNT %>;

		var lastNameAnchor = '';

		var node = A.one('.more-results a');

		if (node) {
			end = A.DataType.Number.parse(node.getAttribute('data-end'));

			lastNameAnchor = node.getAttribute('data-lastNameAnchor');
		}

		<c:choose>
			<c:when test="<%= user2 != null %>">
				var userIds = [<%= user2.getUserId() %>];
			</c:when>
			<c:otherwise>
				var userIds = A.all('.lfr-contact-grid-item input').val();
			</c:otherwise>
		</c:choose>

		var contactFilterSelect = A.one('#<portlet:namespace />filterBy');

		var contactFilerSelectValue = '<%= ContactsConstants.FILTER_BY_DEFAULT %>';

		if (contactFilterSelect) {
			contactFilerSelectValue = contactFilterSelect.get('value');
		}

		var searchInput = A.one('.contacts-portlet #<portlet:namespace />name');

		var data = new URLSearchParams({
			<portlet:namespace />end: end,
			<portlet:namespace />filterBy: contactFilerSelectValue,
			<portlet:namespace />jsonFormat: true,
			<portlet:namespace />keywords: searchInput.get('value'),
			<portlet:namespace />start: 0,
			<portlet:namespace />userIds: userIds.join(),
		});

		Liferay.Util.fetch(uri, {
			body: data,
			method: 'POST',
		})
			.then((response) => {
				return response.json();
			})
			.then((data) => {
				Liferay.component('contactsCenter').renderSelectedContacts(
					data,
					lastNameAnchor
				);
			})
			.catch(() => {
				Liferay.component('contactsCenter').showMessage(false);
			});
	}
</aui:script>
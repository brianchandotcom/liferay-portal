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
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

User selUser = PortalUtil.getSelectedUser(request);

SearchContainer accountEntryDisplaySearchContainer = AccountEntryDisplaySearchContainerFactory.create(selUser.getUserId(), liferayPortletRequest, liferayPortletResponse);

accountEntryDisplaySearchContainer.setRowChecker(null);

renderResponse.setTitle(LanguageUtil.format(request, "edit-user-x", HtmlUtil.escape(selUser.getFullName()), false));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<liferay-util:buffer
	var="removeAccountEntryIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="/account_admin/edit_account_user_account_entries" var="editAccountUserAccountEntriesURL">
	<portlet:param name="accountUserId" value="<%= String.valueOf(selUser.getUserId()) %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:form action="<%= editAccountUserAccountEntriesURL %>" method="post" name="fm">
	<aui:input name="addAccountEntryIds" type="hidden" />
	<aui:input name="deleteAccountEntryIds" type="hidden" />

	<div class="sheet sheet-lg">
		<h2 class="sheet-title">
			<%= LanguageUtil.get(request, "accounts") %>
		</h2>

		<h3 class="autofit-row sheet-subtitle">
			<span class="autofit-col autofit-col-expand">
				<span class="heading-text">
					<liferay-ui:message key="accounts" />
				</span>
			</span>
			<span class="autofit-col">
				<span class="heading-end">
					<liferay-ui:icon
						cssClass="modify-link"
						id="selectAccountLink"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="select"
						method="get"
						url="javascript:;"
					/>
				</span>
			</span>
		</h3>

		<div class="sheet-section">
			<liferay-ui:search-container
				compactEmptyResultsMessage="<%= true %>"
				emptyResultsMessage="this-user-does-not-belong-to-any-accounts"
				headerNames="name,roles,null"
				searchContainer="<%= accountEntryDisplaySearchContainer %>"
			>

				<%
				AccountUserDisplay accountUserDisplay = AccountUserDisplay.of(selUser);
				%>

				<liferay-ui:search-container-row
					className="com.liferay.account.admin.web.internal.display.AccountEntryDisplay"
					keyProperty="accountEntryId"
					modelVar="accountEntryDisplay"
				>
					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="name"
						property="name"
					/>

					<liferay-ui:search-container-column-text
						cssClass="table-cell-expand"
						name="roles"
						value="<%= accountUserDisplay.getAccountRoleNames(accountEntryDisplay.getAccountEntryId(), locale) %>"
					/>

					<liferay-ui:search-container-column-text>
						<a class="modify-link" data-rowId="<%= accountEntryDisplay.getAccountEntryId() %>" href="javascript:;"><%= removeAccountEntryIcon %></a>
					</liferay-ui:search-container-column-text>
				</liferay-ui:search-container-row>

				<liferay-ui:search-iterator
					markupView="lexicon"
				/>
			</liferay-ui:search-container>
		</div>

		<div class="sheet-footer">
			<aui:button type="submit" />

			<aui:button href="<%= backURL %>" type="cancel" />
		</div>
	</div>
</aui:form>

<aui:script use="liferay-search-container">
	var AArray = A.Array;
	var Util = Liferay.Util;

	var addAccountEntryIds = [];

	var deleteAccountEntryIds = [];

	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />accountEntries'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			var selectAccountEntry = Util.getWindow(
				'<portlet:namespace />selectAccountEntry'
			);

			if (selectAccountEntry) {
				var selectButton = selectAccountEntry.iframe.node
					.get('contentWindow.document')
					.one('.selector-button[data-entityid="' + rowId + '"]');

				Util.toggleDisabled(selectButton, false);
			}

			searchContainer.deleteRow(tr, rowId);

			AArray.removeItem(addAccountEntryIds, rowId);

			deleteAccountEntryIds.push(rowId);

			document.<portlet:namespace />fm.<portlet:namespace />addAccountEntryIds.value = addAccountEntryIds.join(
				','
			);
			document.<portlet:namespace />fm.<portlet:namespace />deleteAccountEntryIds.value = deleteAccountEntryIds.join(
				','
			);
		},
		'.modify-link'
	);

	var selectAccountLink = A.one('#<portlet:namespace />selectAccountLink');

	if (selectAccountLink) {
		selectAccountLink.on('click', function(event) {
			var searchContainerData = searchContainer.getData();

			if (!searchContainerData.length) {
				searchContainerData = [];
			}
			else {
				searchContainerData = searchContainerData.split(',');
			}

			Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
					},
					eventName: '<portlet:namespace />selectAccountEntry',
					id: '<portlet:namespace />selectAccountEntry',
					selectedData: searchContainerData,
					title:
						'<liferay-ui:message arguments="account" key="select-x" />',
					uri:
						'<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/account_users_admin/select_account_entry.jsp" /><portlet:param name="userId" value="<%= String.valueOf(selUser.getUserId()) %>" /></portlet:renderURL>',
				},
				function(event) {
					var entityId = event.entityid;

					var rowColumns = [];

					rowColumns.push(event.entityname);
					rowColumns.push(<%= StringPool.BLANK %>);
					rowColumns.push(
						'<a class="modify-link" data-rowId="' +
							entityId +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeAccountEntryIcon) %></a>'
					);

					searchContainer.addRow(rowColumns, entityId);

					searchContainer.updateDataStore();

					AArray.removeItem(deleteAccountEntryIds, entityId);

					addAccountEntryIds.push(entityId);

					document.<portlet:namespace />fm.<portlet:namespace />addAccountEntryIds.value = addAccountEntryIds.join(
						','
					);
					document.<portlet:namespace />fm.<portlet:namespace />deleteAccountEntryIds.value = deleteAccountEntryIds.join(
						','
					);
				}
			);
		});
	}
</aui:script>
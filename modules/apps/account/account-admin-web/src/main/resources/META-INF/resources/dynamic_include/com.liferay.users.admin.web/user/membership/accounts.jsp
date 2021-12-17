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
User selUser = PortalUtil.getSelectedUser(request, false);

SearchContainer<AccountEntryDisplay> accountEntryDisplaySearchContainer = AccountEntryDisplaySearchContainerFactory.createWithUserId(selUser.getUserId(), liferayPortletRequest, liferayPortletResponse);

accountEntryDisplaySearchContainer.setRowChecker(null);
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="accounts"
/>

<portlet:actionURL name="/account_admin/edit_account_user_account_entries" var="editAccountUserAccountEntriesURL">
	<portlet:param name="accountUserId" value="<%= String.valueOf(selUser.getUserId()) %>" />
	<portlet:param name="redirect" value="<%= currentURL %>" />
</portlet:actionURL>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/account_admin/update_account_memberships" />
<aui:input name="addAccountEntryIds" type="hidden" />
<aui:input name="deleteAccountEntryIds" type="hidden" />

<clay:content-row
	containerElement="h3"
	cssClass="sheet-subtitle"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<span class="heading-text"><liferay-ui:message key="accounts" /></span>
	</clay:content-col>

	<clay:content-col>
		<span class="heading-end">
			<liferay-ui:icon
				cssClass="modify-link"
				id="selectAccountLink"
				label="<%= true %>"
				linkCssClass="btn btn-secondary btn-sm"
				message="select"
				url="javascript:;"
			/>
		</span>
	</clay:content-col>
</clay:content-row>

<liferay-util:buffer
	var="removeAccountEntryIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<liferay-ui:search-container
	compactEmptyResultsMessage="<%= true %>"
	emptyResultsMessage="this-user-does-not-belong-to-any-accounts"
	headerNames="name,roles,null"
	searchContainer="<%= accountEntryDisplaySearchContainer %>"
>
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
			name="type"
			property="type"
			translate="<%= true %>"
		/>

		<liferay-ui:search-container-column-text>
			<c:if test="<%= AccountEntryPermission.contains(permissionChecker, accountEntryDisplay.getAccountEntryId(), ActionKeys.MANAGE_USERS) %>">
				<a class="modify-link" data-rowId="<%= accountEntryDisplay.getAccountEntryId() %>" href="javascript:;"><%= removeAccountEntryIcon %></a>
			</c:if>
		</liferay-ui:search-container-column-text>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator
		markupView="lexicon"
	/>
</liferay-ui:search-container>

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
		(event) => {
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
		selectAccountLink.on('click', (event) => {
			var searchContainerData = searchContainer.getData();

			if (!searchContainerData.length) {
				searchContainerData = [];
			}
			else {
				searchContainerData = searchContainerData.split(',');
			}

			Liferay.Util.openSelectionModal({
				buttonAddLabel: '<liferay-ui:message key="assign" />',
				id: '<portlet:namespace />selectAccountEntry',
				multiple: true,
				onSelect: function (selectedItems) {
					for (const selectedItem of selectedItems) {
						var entityId = selectedItem.entityid;

						var rowColumns = [];

						rowColumns.push(selectedItem.entityname);
						rowColumns.push(selectedItem.organizationname);
						rowColumns.push(<%= StringPool.BLANK %>);
						rowColumns.push(
							'<a class="modify-link" data-rowId="' +
								entityId +
								'" href="javascript:;"><%= UnicodeFormatter.toString(removeAccountEntryIcon) %></a>'
						);

						searchContainer.addRow(rowColumns, entityId);

						addAccountEntryIds.push(entityId);
					}

					searchContainer.updateDataStore();

					AArray.removeItem(deleteAccountEntryIds, entityId);

					document.<portlet:namespace />fm.<portlet:namespace />addAccountEntryIds.value = addAccountEntryIds.join(
						','
					);
					document.<portlet:namespace />fm.<portlet:namespace />deleteAccountEntryIds.value = deleteAccountEntryIds.join(
						','
					);
				},
				selectEventName: '<portlet:namespace />selectAccountEntry',
				selectedData: searchContainerData,
				title: '<liferay-ui:message arguments="account" key="select-x" />',
				url:
					'<liferay-portlet:renderURL portletName="<%= AccountPortletKeys.ACCOUNT_USERS_ADMIN %>" windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/account_users_admin/select_account_entry.jsp" /><portlet:param name="singleSelect" value="<%= Boolean.FALSE.toString() %>" /><portlet:param name="accountUserId" value="<%= String.valueOf(selUser.getUserId()) %>" /></liferay-portlet:renderURL>',
			});
		});
	}
</aui:script>
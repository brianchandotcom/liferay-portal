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
AccountEntryDisplay accountEntryDisplay = (AccountEntryDisplay)request.getAttribute(AccountWebKeys.ACCOUNT_ENTRY_DISPLAY);

AccountEntry parentAccountEntry = null;

if (accountEntryDisplay.getAccountEntryId() > 0) {
	AccountEntry accountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(accountEntryDisplay.getAccountEntryId());

	parentAccountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(accountEntry.getParentAccountEntryId());
}
%>

<liferay-util:buffer
	var="removeParentAccountEntryIcon"
>
	<liferay-ui:icon
		icon="times-circle"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="parent-account" /></span>
		</clay:content-col>

		<clay:content-col
			containerElement="span"
		>
			<span class="heading-end">
				<liferay-ui:icon
					cssClass="modify-link"
					id="selectParentAccountEntry"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message="select"
					method="get"
					url="javascript:;"
				/>
			</span>
		</clay:content-col>
	</clay:content-row>

	<aui:input name="parentAccountEntryId" type="hidden" value="<%= (parentAccountEntry != null) ? String.valueOf(parentAccountEntry.getAccountEntryId()) : AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT %>" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="this-account-does-not-have-a-parent-account"
		headerNames="title,null"
		id="parentAccountEntrySearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= 1 %>"
	>
		<liferay-ui:search-container-results
			results="<%= (parentAccountEntry == null) ? Collections.emptyList() : Arrays.asList(parentAccountEntry) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.account.model.AccountEntry"
			keyProperty="accountEntryId"
			modelVar="curParentAccountEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= curParentAccountEntry.getName() %>"
			/>

			<liferay-ui:search-container-column-text>
				<a class="modify-link pull-right" data-rowId="<%= curParentAccountEntry.getAccountEntryId() %>" href="javascript:;"><%= removeParentAccountEntryIcon %></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:sheet-section>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />parentAccountEntrySearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	var parentAccountEntryInput =
		document.<portlet:namespace />fm.<portlet:namespace />parentAccountEntryId;

	searchContainerContentBox.delegate(
		'click',
		function (event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, rowId);

			parentAccountEntryInput.value = '';
		},
		'.modify-link'
	);

	var selectParentAccountEntryIcon = document.getElementById(
		'<portlet:namespace />selectParentAccountEntry'
	);

	if (selectParentAccountEntryIcon) {
		selectParentAccountEntryIcon.addEventListener('click', function (event) {
			event.preventDefault();

			Liferay.Util.openModal({
				id:
					'<%= liferayPortletResponse.getNamespace() + "selectParentAccountEntry" %>',
				onSelect: function (selectedItem) {
					parentAccountEntryInput.value = selectedItem.accountentryid;

					var rowColumns = [];

					rowColumns.push(selectedItem.entityname);
					rowColumns.push(
						'<a class="modify-link" data-rowId="' +
							selectedItem.entityid +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeParentAccountEntryIcon) %></a>'
					);

					searchContainer.deleteRow(1, searchContainer.getData());

					searchContainer.addRow(rowColumns, selectedItem.entityid);

					searchContainer.updateDataStore(selectedItem.entityid);
				},
				selectEventName:
					'<%= liferayPortletResponse.getNamespace() + "selectAccountEntry" %>',
				selectedData: [
					'<%= (accountEntryDisplay.getAccountEntryId() > 0) ? accountEntryDisplay.getAccountEntryId() : "" %>',
					parentAccountEntryInput.value,
				],
				title: '<liferay-ui:message key="assign-parent-account" />',

				<%
				PortletURL selectParentAccountEntryURL = renderResponse.createRenderURL();

				selectParentAccountEntryURL.setParameter("mvcPath", "/account_users_admin/select_account_entry.jsp");
				selectParentAccountEntryURL.setWindowState(LiferayWindowState.POP_UP);
				%>

				url: '<%= selectParentAccountEntryURL.toString() %>',
			});
		});
	}
</aui:script>
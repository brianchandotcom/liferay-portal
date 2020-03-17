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

AccountEntry accountEntry = null;
long accountEntryId = AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
Long parentAccountEntryId = AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT;
String parentAccountEntryName = StringPool.BLANK;

AccountEntry parentAccountEntry = null;

if (accountEntryDisplay != null) {
	accountEntryId = accountEntryDisplay.getAccountEntryId();

	accountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(accountEntryId);

	parentAccountEntryId = accountEntry.getParentAccountEntryId();

	parentAccountEntryName = accountEntryDisplay.getParentAccountEntryName();

	parentAccountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(parentAccountEntryId);
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

<div class="sheet-section">
	<h3 class="autofit-row sheet-subtitle">
		<span class="autofit-col autofit-col-expand">
			<span class="heading-text"><liferay-ui:message key="parent-account" /></span>
		</span>
		<span class="autofit-col">
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
		</span>
	</h3>

	<aui:input name="parentAccountEntryId" type="hidden" value="<%= String.valueOf(parentAccountEntryId) %>" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="this-account-does-not-have-a-parent-account"
		headerNames="title,null"
		id="parentAccountEntrySearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= 1 %>"
	>
		<liferay-ui:search-container-results
			results="<%= (parentAccountEntryId == AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT) ? Collections.emptyList() : Arrays.asList(parentAccountEntry) %>"
		/>

		<liferay-ui:search-container-row
			className="com.liferay.account.model.AccountEntry"
			keyProperty="accountEntryId"
			modelVar="curParentAccountEntry"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= parentAccountEntryName %>"
			/>

			<liferay-ui:search-container-column-text>
				<a class="modify-link pull-right" data-rowId="<%= parentAccountEntryId %>" href="javascript:;"><%= removeParentAccountEntryIcon %></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />parentAccountEntrySearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	var parentAccountEntryInput =
		document.<portlet:namespace />fm.<portlet:namespace />parentAccountEntryId;

	var parentAccountEntry = parentAccountEntryInput.value
		.split(',')
		.filter(Boolean);

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, rowId);

			A.Array.removeItem(parentAccountEntry, rowId);

			parentAccountEntryInput.value = 0;
		},
		'.modify-link'
	);

	var selectParentAccountEntryIcon = document.getElementById(
		'<portlet:namespace />selectParentAccountEntry'
	);

	if (selectParentAccountEntryIcon) {
		selectParentAccountEntryIcon.addEventListener('click', function(event) {
			event.preventDefault();

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						modal: true,
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer',
					},
					eventName:
						'<%= liferayPortletResponse.getNamespace() + "selectAccountEntry" %>',
					id:
						'<%= liferayPortletResponse.getNamespace() + "selectParentAccountEntry" %>',
					selectedData: [
						'<%= accountEntryId %>',
						'<%= parentAccountEntryId %>',
					],
					title: '<liferay-ui:message key="assign-parent-account" />',

					<%
					PortletURL selectParentAccountEntryURL = renderResponse.createRenderURL();

					selectParentAccountEntryURL.setParameter("mvcPath", "/account_users_admin/select_account_entry.jsp");
					selectParentAccountEntryURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					uri: '<%= selectParentAccountEntryURL.toString() %>',
				},
				function(event) {
					var newParentAccountEntry = event.accountentryid;

					parentAccountEntryInput.value = newParentAccountEntry;

					var rowColumns = [];

					rowColumns.push(event.entityname);
					rowColumns.push(
						'<a class="modify-link" data-rowId="' +
							event.entityid +
							'" href="javascript:;"><%= UnicodeFormatter.toString(removeParentAccountEntryIcon) %></a>'
					);

					searchContainer.deleteRow(1, searchContainer.getData());
					searchContainer.addRow(rowColumns, event.entityid);
					searchContainer.updateDataStore(event.entityid);
				}
			);
		});
	}
</aui:script>
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

List<String> domains = Collections.emptyList();

if (accountEntryDisplay != null) {
	domains = accountEntryDisplay.getDomains();
}
%>

<liferay-ui:error exception="<%= AccountEntryDomainsException.class %>" message="please-enter-a-valid-mail-domain" />

<liferay-util:buffer
	var="removeDomainIcon"
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
			<span class="heading-text"><liferay-ui:message key="valid-domains" /></span>
		</span>
		<span class="autofit-col">
			<span class="heading-end">
				<liferay-ui:icon
					cssClass="modify-link"
					id="addDomains"
					label="<%= true %>"
					linkCssClass="btn btn-secondary btn-sm"
					message="add"
					method="get"
					url="javascript:;"
				/>
			</span>
		</span>
	</h3>

	<aui:input name="domains" type="hidden" value="<%= StringUtil.merge(domains) %>" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="this-account-does-not-have-a-valid-domain"
		headerNames="title,null"
		id="accountDomainsSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= domains.size() %>"
	>
		<liferay-ui:search-container-results
			results="<%= domains.subList(searchContainer.getStart(), searchContainer.getResultEnd()) %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="domain"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-content"
				name="name"
				value="<%= domain %>"
			/>

			<liferay-ui:search-container-column-text>
				<a class="modify-link pull-right" data-rowId="<%= domain %>" href="javascript:;"><%= removeDomainIcon %></a>
			</liferay-ui:search-container-column-text>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</div>

<aui:script use="liferay-search-container">
	var searchContainer = Liferay.SearchContainer.get(
		'<portlet:namespace />accountDomainsSearchContainer'
	);

	var searchContainerContentBox = searchContainer.get('contentBox');

	var domainsInput =
		document.<portlet:namespace />fm.<portlet:namespace />domains;

	var domains = domainsInput.value.split(',').filter(Boolean);

	searchContainerContentBox.delegate(
		'click',
		function(event) {
			var link = event.currentTarget;

			var rowId = link.attr('data-rowId');

			var tr = link.ancestor('tr');

			searchContainer.deleteRow(tr, rowId);

			A.Array.removeItem(domains, rowId);

			domainsInput.value = domains.join(',');
		},
		'.modify-link'
	);

	var addDomainsIcon = document.getElementById('<portlet:namespace />addDomains');

	if (addDomainsIcon) {
		addDomainsIcon.addEventListener('click', function(event) {
			event.preventDefault();

			Liferay.Util.selectEntity(
				{
					dialog: {
						constrain: true,
						destroyOnHide: true,
						height: 350,
						modal: true,
						width: 800
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id:
						'<%= liferayPortletResponse.getNamespace() + "addDomains" %>',
					title: '<liferay-ui:message key="add-domain" />',

					<%
					PortletURL addDomainsURL = renderResponse.createRenderURL();

					addDomainsURL.setParameter("mvcPath", "/account_entries_admin/account_entry/add_domains.jsp");
					addDomainsURL.setWindowState(LiferayWindowState.POP_UP);
					%>

					uri: '<%= addDomainsURL.toString() %>'
				},
				function(event) {
					var newDomains = event.data.split(',');

					newDomains.forEach(function(domain) {
						if (!domains.includes(domain)) {
							addRow(domain.trim());
						}
					});

					searchContainer.updateDataStore();

					domainsInput.value = domains.join(',');
				}
			);
		});
	}

	Liferay.provide(window, 'addRow', function(domain) {
		var rowColumns = [];

		rowColumns.push(Liferay.Util.escape(domain));
		rowColumns.push(
			'<a class="modify-link pull-right" data-rowId="' +
				domain +
				'" href="javascript:;"><%= UnicodeFormatter.toString(removeDomainIcon) %></a>'
		);

		searchContainer.addRow(rowColumns, domain);

		domains.push(domain);
	});
</aui:script>
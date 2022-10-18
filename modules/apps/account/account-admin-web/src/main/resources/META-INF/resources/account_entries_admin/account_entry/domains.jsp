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

boolean allowUpdateDomains = AccountEntryPermission.contains(permissionChecker, accountEntryDisplay.getAccountEntryId(), AccountActionKeys.MANAGE_DOMAINS);
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

<clay:sheet-section>
	<clay:content-row
		containerElement="h3"
		cssClass="sheet-subtitle"
	>
		<clay:content-col
			containerElement="span"
			expand="<%= true %>"
		>
			<span class="heading-text"><liferay-ui:message key="valid-domains" /></span>
		</clay:content-col>

		<c:if test="<%= allowUpdateDomains %>">
			<clay:content-col
				containerElement="span"
			>
				<span class="heading-end">
					<liferay-ui:icon
						cssClass="modify-link"
						id="addDomains"
						label="<%= true %>"
						linkCssClass="btn btn-secondary btn-sm"
						message="add"
						method="get"
						url="javascript:void(0);"
					/>
				</span>
			</clay:content-col>
		</c:if>
	</clay:content-row>

	<aui:input name="domains" type="hidden" value="<%= accountEntryDisplay.getDomains() %>" />

	<liferay-ui:search-container
		compactEmptyResultsMessage="<%= true %>"
		emptyResultsMessage="this-account-does-not-have-a-valid-domain"
		headerNames="title,null"
		id="accountDomainsSearchContainer"
		iteratorURL="<%= currentURLObj %>"
		total="<%= ArrayUtil.getLength(accountEntryDisplay.getDomainsArray()) %>"
	>
		<liferay-ui:search-container-results
			calculateStartAndEnd="<%= true %>"
			results="<%= ListUtil.fromArray(accountEntryDisplay.getDomainsArray()) %>"
		/>

		<liferay-ui:search-container-row
			className="java.lang.String"
			modelVar="domain"
		>
			<liferay-ui:search-container-column-text
				cssClass="table-cell-expand"
				name="name"
				value="<%= domain %>"
			/>

			<c:if test="<%= allowUpdateDomains %>">
				<liferay-ui:search-container-column-text>
					<a class="float-right modify-link" data-rowId="<%= domain %>" href="javascript:void(0);"><%= removeDomainIcon %></a>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>

	<c:if test="<%= allowUpdateDomains %>">
		<aui:field-wrapper cssClass="form-group lfr-input-text-container">
			<aui:input label="restrict-membership-to-domains" labelOff="not-restricted" labelOn="restricted" name="restrictMembership" type="toggle-switch" value="<%= accountEntryDisplay.isRestrictMembership() %>" />
		</aui:field-wrapper>
	</c:if>
</clay:sheet-section>

<c:if test="<%= allowUpdateDomains %>">
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
			(event) => {
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
			addDomainsIcon.addEventListener('click', (event) => {
				event.preventDefault();

				Liferay.Util.openModal({
					containerProps: {
						className: '',
					},
					customEvents: [
						{
							name:
								'<%= liferayPortletResponse.getNamespace() %>addDomains',
							onEvent: function (event) {
								var newDomains = event.data.split(',');

								newDomains.forEach((domain) => {
									domain = domain.trim().toLowerCase();

									if (!domains.includes(domain)) {
										var rowColumns = [];

										rowColumns.push(Liferay.Util.escape(domain));
										rowColumns.push(
											'<a class="float-right modify-link" data-rowId="' +
												domain +
												'" href="javascript:void(0);"><%= UnicodeFormatter.toString(removeDomainIcon) %></a>'
										);

										searchContainer.addRow(rowColumns, domain);

										domains.push(domain);
									}
								});

								searchContainer.updateDataStore();

								domainsInput.value = domains.join(',');
							},
						},
					],
					id: '<%= liferayPortletResponse.getNamespace() %>addDomains',
					iframeBodyCssClass: '',
					title: '<liferay-ui:message key="add-domain" />',
					url:
						'<%=
							PortletURLBuilder.createRenderURL(
								renderResponse
							).setMVCPath(
								"/account_entries_admin/account_entry/add_domains.jsp"
							).setWindowState(
								LiferayWindowState.POP_UP
							).buildPortletURL()
					%>',
				});
			});
		}
	</aui:script>
</c:if>
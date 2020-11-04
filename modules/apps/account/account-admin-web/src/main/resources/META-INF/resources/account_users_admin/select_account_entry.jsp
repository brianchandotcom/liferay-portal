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
SearchContainer<AccountEntryDisplay> accountEntryDisplaySearchContainer = AccountEntryDisplaySearchContainerFactory.create(liferayPortletRequest, liferayPortletResponse);

long accountGroupId = ParamUtil.getLong(request, "accountGroupId");

if (accountGroupId > 0) {
	accountEntryDisplaySearchContainer.setRowChecker(new AccountGroupAccountEntryRowChecker(liferayPortletResponse, accountGroupId));
}

SelectAccountEntryManagementToolbarDisplayContext selectAccountEntryManagementToolbarDisplayContext = new SelectAccountEntryManagementToolbarDisplayContext(request, liferayPortletRequest, liferayPortletResponse, accountEntryDisplaySearchContainer);

if (selectAccountEntryManagementToolbarDisplayContext.isSingleSelect()) {
	accountEntryDisplaySearchContainer.setRowChecker(null);
}
%>

<clay:management-toolbar-v2
	displayContext="<%= selectAccountEntryManagementToolbarDisplayContext %>"
/>

<clay:container-fluid
	id='<%= liferayPortletResponse.getNamespace() + "selectAccountEntry" %>'
>
	<liferay-ui:search-container
		searchContainer="<%= accountEntryDisplaySearchContainer %>"
	>
		<liferay-ui:search-container-row
			className="com.liferay.account.admin.web.internal.display.AccountEntryDisplay"
			keyProperty="accountEntryId"
			modelVar="accountEntryDisplay"
		>

			<%
			String cssClass = "table-cell-expand";

			Optional<User> userOptional = accountEntryDisplay.getPersonAccountEntryUserOptional();

			boolean disabled = userOptional.isPresent();

			if (disabled) {
				cssClass += " text-muted";
			}
			%>

			<liferay-ui:search-container-column-text
				cssClass='<%= cssClass + " table-title" %>'
				name="name"
				value="<%= HtmlUtil.escape(accountEntryDisplay.getName()) %>"
			/>

			<liferay-ui:search-container-column-text
				cssClass="<%= cssClass %>"
				name="type"
				translate="<%= true %>"
				value="<%= HtmlUtil.escape(accountEntryDisplay.getType()) %>"
			/>

			<c:if test="<%= selectAccountEntryManagementToolbarDisplayContext.isSingleSelect() %>">
				<liferay-ui:search-container-column-text>
					<aui:button
						cssClass="choose-account selector-button"
						data='<%=
							HashMapBuilder.<String, Object>put(
								"accountentryid", accountEntryDisplay.getAccountEntryId()
							).put(
								"entityid", accountEntryDisplay.getAccountEntryId()
							).put(
								"entityname", accountEntryDisplay.getName()
							).build()
						%>'
						disabled="<%= disabled %>"
						value="choose"
					/>
				</liferay-ui:search-container-column-text>
			</c:if>
		</liferay-ui:search-container-row>

		<liferay-ui:search-iterator
			markupView="lexicon"
		/>
	</liferay-ui:search-container>
</clay:container-fluid>

<c:choose>
	<c:when test="<%= selectAccountEntryManagementToolbarDisplayContext.isSingleSelect() %>">
		<aui:script>
			Liferay.Util.selectEntityHandler(
				'#<portlet:namespace />selectAccountEntry',
				'<%= HtmlUtil.escapeJS(liferayPortletResponse.getNamespace() + "selectAccountEntry") %>'
			);
		</aui:script>
	</c:when>
	<c:otherwise>
		<aui:script use="liferay-search-container">
			var searchContainer = Liferay.SearchContainer.get(
				'<portlet:namespace />accountEntries'
			);

			searchContainer.on('rowToggled', function (event) {
				var selectedItems = event.elements.allSelectedElements;

				var result = {};

				if (!selectedItems.isEmpty()) {
					result = {
						data: {
							value: selectedItems.get('value').join(','),
						},
					};
				}

				Liferay.Util.getOpener().Liferay.fire(
					'<%= HtmlUtil.escapeJS(liferayPortletResponse.getNamespace() + "selectAccountEntries") %>',
					result
				);
			});
		</aui:script>
	</c:otherwise>
</c:choose>
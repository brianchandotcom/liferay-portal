<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/account_selector/init.jsp" %>

<c:choose>
	<c:when test="<%= commerceChannelId == 0 %>">
		<div class="alert alert-info mx-auto">
			<liferay-ui:message key="this-site-does-not-have-a-channel" />
		</div>
	</c:when>
	<c:when test="<%= !user.isGuestUser() %>">
		<div class="account-selector-root" id="<%= accountSelectorId %>"></div>

		<aui:script require="commerce-frontend-js/components/account_selector/entry as accountSelector">
			accountSelector.default(
				'<%= accountSelectorId %>',
				'<%= accountSelectorId %>',
				{
					accountEntryAllowedTypes:
						'<%= jsonSerializer.serializeDeep(accountEntryAllowedTypes) %>',
					commerceChannelId: '<%= commerceChannelId %>',
					createNewOrderURL: '<%= createNewOrderURL %>',
					currentCommerceAccount: <%= Validator.isNotNull(currentCommerceAccount) ? jsonSerializer.serializeDeep(currentCommerceAccount) : null %>,
					currentCommerceOrder: <%= Validator.isNotNull(currentCommerceOrder) ? jsonSerializer.serializeDeep(currentCommerceOrder) : null %>,
					namespace: '<%= accountSelectorId %>',
					refreshPageOnAccountSelected: true,
					selectOrderURL: '<%= selectOrderURL %>',
					setCurrentAccountURL: '<%= setCurrentAccountURL %>',
					showOrderTypeModal: <%= showOrderTypeModal %>,
				}
			);
		</aui:script>
	</c:when>
</c:choose>
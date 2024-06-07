<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelAccountEntryRelDisplayContext commerceChannelAccountEntryRelDisplayContext = (CommerceChannelAccountEntryRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

AccountEntry accountEntry = commerceChannelAccountEntryRelDisplayContext.getAccountEntry();

CommerceChannelAccountEntryRel commerceChannelAccountEntryRel = commerceChannelAccountEntryRelDisplayContext.fetchCommerceChannelAccountEntryRel();

int type = commerceChannelAccountEntryRelDisplayContext.getType();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "save") %>'
	title="<%= commerceChannelAccountEntryRelDisplayContext.getModalTitle() %>"
>
	<portlet:actionURL name="/commerce_address/edit_account_entry_default_commerce_address" var="editAccountEntryDefaultCommerceAddressActionURL" />

	<aui:form action="<%= editAccountEntryDefaultCommerceAddressActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= accountEntry.getAccountEntryId() %>" />
		<aui:input name="commerceChannelAccountEntryRelId" type="hidden" value="<%= (commerceChannelAccountEntryRel == null) ? 0 : commerceChannelAccountEntryRel.getCommerceChannelAccountEntryRelId() %>" />
		<aui:input name="type" type="hidden" value="<%= type %>" />

		<liferay-ui:error exception="<%= DuplicateCommerceChannelAccountEntryRelException.class %>" message="there-is-already-an-address-defined-for-the-selected-channel" />

		<aui:model-context bean="<%= commerceChannelAccountEntryRelDisplayContext.fetchCommerceChannelAccountEntryRel() %>" model="<%= CommerceChannelAccountEntryRel.class %>" />

		<aui:select label="channel" name="commerceChannelId" />

		<aui:select label="<%= commerceChannelAccountEntryRelDisplayContext.getAddressSelectLabel(type) %>" name="classPK" required="<%= true %>" />
	</aui:form>
</commerce-ui:modal-content>

<aui:script use="liferay-dynamic-select">
	new Liferay.DynamicSelect([
		{
			select: '<portlet:namespace />commerceChannelId',
			selectData: function (callback) {
				function injectCountryPlaceholder(list) {
					var callbackList = [
						{
							commerceChannelId: '0',
							name: '<%= LanguageUtil.get(request, commerceChannelAccountEntryRelDisplayContext.getCommerceChannelsEmptyOptionKey()) %>',
						},
					];

					list.forEach((listElement) => {
						callbackList.push(listElement);
					});

					callback(callbackList);
				}

				Liferay.Service(
					'/commerce.commercechannel/get-eligible-commerce-channels',
					{
						accountEntryId: '<%= accountEntry.getAccountEntryId() %>',
						name: '',
						start: '-1',
						end: '-1',
					},
					injectCountryPlaceholder
				);
			},
			selectDesc: 'name',
			selectId: 'commerceChannelId',
			selectNullable: <%= false %>,
			selectSort: '<%= true %>',
			selectVal:
				'<%= (commerceChannelAccountEntryRel == null) ? '0' : commerceChannelAccountEntryRel.getCommerceChannelId() %>',
		},
		{
			select: '<portlet:namespace />classPK',
			selectData: function (callback, selectKey) {
				Liferay.Service(
					'<%= commerceChannelAccountEntryRelDisplayContext.getCommerceAddressAPIURL() %>',
					{
						channelId: selectKey,
						className: '<%= AccountEntry.class.getName() %>',
						classPK: '<%= accountEntry.getAccountEntryId() %>',
						start: -1,
						end: -1,
					},
					callback
				);
			},
			selectDesc: 'name',
			selectId: 'commerceAddressId',
			selectNullable: <%= false %>,
			selectVal:
				'<%= (commerceChannelAccountEntryRel == null) ? '0' : commerceChannelAccountEntryRel.getClassPK() %>',
		},
	]);
</aui:script>
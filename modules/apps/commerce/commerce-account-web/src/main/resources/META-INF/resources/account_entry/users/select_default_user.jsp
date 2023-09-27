<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceAccountDisplayContext commerceAccountDisplayContext = (CommerceAccountDisplayContext)request.getAttribute(CommerceAccountWebKeys.COMMERCE_ACCOUNT_DISPLAY_CONTEXT);

AccountEntry accountEntry = commerceAccountDisplayContext.getAccountEntry();
CommerceChannelAccountEntryRel commerceChannelAccountEntryRel = commerceAccountDisplayContext.fetchCommerceChannelAccountEntryRel();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "save") %>'
	title="<%= commerceAccountDisplayContext.getModalTitle() %>"
>
	<portlet:actionURL name="/account_entries_admin/edit_account_entry_user" var="editAccountEntrySalesAgentActionURL" />

	<aui:form action="<%= editAccountEntrySalesAgentActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="accountEntryId" type="hidden" value="<%= accountEntry.getAccountEntryId() %>" />
		<aui:input name="commerceChannelAccountEntryRelId" type="hidden" value="<%= (commerceChannelAccountEntryRel == null) ? 0 : commerceChannelAccountEntryRel.getCommerceChannelAccountEntryRelId() %>" />

		<liferay-ui:error exception="<%= DuplicateCommerceChannelAccountEntryRelException.class %>" message="there-is-already-a-user-defined-for-the-selected-channel" />

		<aui:select label="channel" name="commerceChannelId">
			<aui:option label="<%= LanguageUtil.get(request, commerceAccountDisplayContext.getCommerceChannelsEmptyOptionKey()) %>" selected="<%= commerceAccountDisplayContext.isCommerceChannelSelected(0) %>" value="0" />

			<%
			for (CommerceChannel commerceChannel : commerceAccountDisplayContext.getFilteredCommerceChannels()) {
			%>

				<aui:option label="<%= commerceChannel.getName() %>" selected="<%= commerceAccountDisplayContext.isCommerceChannelSelected(commerceChannel.getCommerceChannelId()) %>" value="<%= commerceChannel.getCommerceChannelId() %>" />

			<%
			}
			%>

		</aui:select>

		<aui:select label="user" name="classPK" required="<%= true %>">

			<%
			for (User allowedUser : commerceAccountDisplayContext.getAllowedUsers()) {
			%>

				<aui:option label="<%= allowedUser.getScreenName() %>" selected="<%= commerceAccountDisplayContext.isUserSelected(allowedUser.getUserId()) %>" value="<%= allowedUser.getUserId() %>" />

			<%
			}
			%>

		</aui:select>
	</aui:form>
</commerce-ui:modal-content>
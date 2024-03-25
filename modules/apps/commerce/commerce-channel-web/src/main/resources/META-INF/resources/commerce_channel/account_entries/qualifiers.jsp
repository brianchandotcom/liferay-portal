<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommerceChannelAccountEntryQualifiersDisplayContext commerceChannelAccountEntryQualifiersDisplayContext = (CommerceChannelAccountEntryQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceChannel commerceChannel = commerceChannelAccountEntryQualifiersDisplayContext.getCommerceChannel();
long commerceChannelId = commerceChannelAccountEntryQualifiersDisplayContext.getCommerceChannelId();

String accountQualifiers = ParamUtil.getString(request, "accountQualifiers", commerceChannelAccountEntryQualifiersDisplayContext.getActiveAccountEligibility());
%>

<aui:form cssClass="m-0 p-0" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceChannel == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceChannel.getExternalReferenceCode() %>" />
	<aui:input name="commerceChannelId" type="hidden" value="<%= commerceChannelId %>" />
	<aui:input name="accountQualifiers" type="hidden" value="<%= accountQualifiers %>" />

	<aui:model-context bean="<%= commerceChannel %>" model="<%= CommerceChannel.class %>" />

	<commerce-ui:panel
		bodyClasses="flex-fill"
		collapsed="<%= false %>"
		collapsible="<%= false %>"
		title='<%= LanguageUtil.get(request, "account-eligibility") %>'
	>
		<aui:fieldset markupView="lexicon">
			<aui:input checked='<%= Objects.equals(accountQualifiers, "all") %>' label="all-accounts" name="chooseAccountQualifiers" type="radio" value="all" />
			<aui:input checked='<%= Objects.equals(accountQualifiers, "accounts") %>' label="specific-accounts" name="chooseAccountQualifiers" type="radio" value="accounts" />
		</aui:fieldset>
	</commerce-ui:panel>

	<c:if test='<%= Objects.equals(accountQualifiers, "accounts") %>'>
		<%@ include file="/commerce_channel/account_entries/qualifier/account_entries.jspf" %>
	</c:if>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"currentURL", currentURL
			).build()
		%>'
		module="{qualifiers} from commerce-channel-web"
	/>
</aui:form>
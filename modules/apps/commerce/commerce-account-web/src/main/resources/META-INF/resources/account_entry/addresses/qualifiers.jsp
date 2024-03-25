<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AccountAddressQualifiersDisplayContext accountAddressQualifiersDisplayContext = (AccountAddressQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

Address address = accountAddressQualifiersDisplayContext.getAccountAddress();
long addressId = accountAddressQualifiersDisplayContext.getAccountAddressId();

long accountEntryId = ParamUtil.getLong(request, "accountEntryId");

String channelQualifiers = ParamUtil.getString(request, "channelQualifiers", accountAddressQualifiersDisplayContext.getActiveChannelEligibility());

String backURL = PortletURLBuilder.createRenderURL(
	renderResponse
).setMVCRenderCommandName(
	"/account_admin/edit_account_entry"
).setParameter(
	"accountEntryId", accountEntryId
).setParameter(
	"screenNavigationCategoryKey", "addresses"
).buildString();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "edit-address"));
%>

<portlet:actionURL name="/account_entry/edit_account_address_qualifiers" var="editAccountAddressQualifiersActionURL" />

<liferay-frontend:edit-form
	action="<%= editAccountAddressQualifiersActionURL %>"
>
	<liferay-frontend:edit-form-body>
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (address == null) ? Constants.ADD : Constants.UPDATE %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="externalReferenceCode" type="hidden" value="<%= address.getExternalReferenceCode() %>" />
		<aui:input name="addressId" type="hidden" value="<%= addressId %>" />
		<aui:input name="channelQualifiers" type="hidden" value="<%= channelQualifiers %>" />

		<aui:model-context bean="<%= address %>" model="<%= Address.class %>" />

		<commerce-ui:panel
			bodyClasses="flex-fill"
			collapsed="<%= false %>"
			collapsible="<%= false %>"
			title='<%= LanguageUtil.get(request, "channel-eligibility") %>'
		>
			<aui:fieldset markupView="lexicon">
				<aui:input checked='<%= Objects.equals(channelQualifiers, "all") %>' label="all-channels" name="chooseChannelQualifiers" type="radio" value="all" />
				<aui:input checked='<%= Objects.equals(channelQualifiers, "channels") %>' label="specific-channels" name="chooseChannelQualifiers" type="radio" value="channels" />
			</aui:fieldset>
		</commerce-ui:panel>

		<c:if test='<%= Objects.equals(channelQualifiers, "channels") %>'>
			<%@ include file="/account_entry/addresses/qualifier/channels.jspf" %>
		</c:if>
	</liferay-frontend:edit-form-body>

	<liferay-frontend:edit-form-footer>
		<liferay-frontend:edit-form-buttons
			redirect="<%= backURL %>"
		/>
	</liferay-frontend:edit-form-footer>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"currentURL", currentURL
			).build()
		%>'
		module="{qualifiers} from commerce-account-web"
	/>
</liferay-frontend:edit-form>
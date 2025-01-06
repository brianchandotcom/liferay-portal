<%--
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPConfigurationListQualifiersDisplayContext cpConfigurationListQualifiersDisplayContext = (CPConfigurationListQualifiersDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPConfigurationList cpConfigurationList = cpConfigurationListQualifiersDisplayContext.getCPConfigurationList();

String accountQualifiers = ParamUtil.getString(request, "accountQualifiers", cpConfigurationListQualifiersDisplayContext.getActiveAccountEligibility());
%>

<portlet:actionURL name="/cp_configuration_lists/edit_cp_configuration_list_qualifiers" var="editCPConfigurationListQualifiersActionURL" />

<aui:form action="<%= editCPConfigurationListQualifiersActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpConfigurationList == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="accountQualifiers" type="hidden" value="<%= accountQualifiers %>" />
	<aui:input name="cpConfigurationListId" type="hidden" value="<%= cpConfigurationListQualifiersDisplayContext.getCPConfigurationListId() %>" />

	<aui:model-context bean="<%= cpConfigurationList %>" model="<%= CPConfigurationList.class %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "account-eligibility") %>'
			>
				<div class="row">
					<aui:fieldset markupView="lexicon">
						<aui:input checked='<%= Objects.equals(accountQualifiers, "all") %>' label="all-accounts" name="qualifiers--account--" type="radio" value="all" />
						<aui:input checked='<%= Objects.equals(accountQualifiers, "accountGroups") %>' label="specific-account-groups" name="qualifiers--account--" type="radio" value="accountGroups" />
						<aui:input checked='<%= Objects.equals(accountQualifiers, "accounts") %>' label="specific-accounts" name="qualifiers--account--" type="radio" value="accounts" />
					</aui:fieldset>
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<c:if test='<%= Objects.equals(accountQualifiers, "accounts") %>'>
		<%@ include file="/configuration_list/qualifier/accounts.jspf" %>
	</c:if>

	<c:if test='<%= Objects.equals(accountQualifiers, "accountGroups") %>'>
		<%@ include file="/configuration_list/qualifier/account_groups.jspf" %>
	</c:if>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"currentURL", currentURL
		).put(
			"searchParam", "accountQualifiers"
		).put(
			"selector", "qualifiers--account--"
		).build()
	%>'
	module="{qualifiers} from commerce-product-definitions-web"
/>
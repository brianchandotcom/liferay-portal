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
CommerceTermEntryDisplayContext commerceTermEntryDisplayContext = (CommerceTermEntryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTermEntry commerceTermEntry = commerceTermEntryDisplayContext.getCommerceTermEntry();
long commerceTermEntryId = commerceTermEntryDisplayContext.getCommerceTermEntryId();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commerceTermEntry != null) && (commerceTermEntry.getExpirationDate() != null)) {
	neverExpire = false;
}


%>

<portlet:actionURL name="/commerce_term_entry/edit_commerce_term_entry" var="editCommerceTermEntryActionURL" />

<aui:form action="<%= editCommerceTermEntryActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceTermEntry == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceTermEntry.getExternalReferenceCode() %>" />
	<aui:input name="commerceTermEntryId" type="hidden" value="<%= commerceTermEntryId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= commerceTermEntry %>" model="<%= CommerceTermEntry.class %>" />

	<div class="row">
		<div class="col-12 col-xl-8">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				collapsed="<%= false %>"
				collapsible="<%= false %>"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<div class="row">
					<div class="col">
						<aui:input autoFocus="<%= true %>" label="name" name="name" localized="<%= true %>" required="<%= true %>" />
					</div>

					<div class="col-auto">
						<aui:select label="type" name="type" required="<%= true %>">

							<%
							for (CommerceTermEntryType commerceTermEntryType : commerceTermEntryDisplayContext.getCommerceTermEntryTypes()) {
							%>

								<aui:option label="<%= commerceTermEntryType.getLabel(locale) %>" value="<%= commerceTermEntryType.getKey() %>" />

							<%
							}
							%>

						</aui:select>
					</div>


				</div>

				<div class="row">
					<div class="col">
						<aui:input name="priority" />
					</div>

					<div class="col-auto">
						<aui:input label='<%= HtmlUtil.escape("active") %>' name="active" type="toggle-switch" value="<%= commerceTermEntry.isActive() %>" />
					</div>

				</div>

				<div class="row">
					<div class="col">
						<aui:input name="description" type="textarea" value="<%= commerceTermEntry.getDescription() %>" />
					</div>
				</div>

			</commerce-ui:panel>


		</div>

		<div class="col-12 col-xl-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "schedule") %>'
			>
				<liferay-ui:error exception="<%= CommerceTermEntryExpirationDateException.class %>" message="please-select-a-valid-expiration-date" />

				<aui:input formName="fm" label="publish-date" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
			</commerce-ui:panel>
		</div>
	</div>
</aui:form>

<liferay-frontend:component
	context='<%=
		HashMapBuilder.<String, Object>put(
			"commerceTermEntryId", commerceTermEntryId
		).put(
			"currentURL", currentURL
		).build()
	%>'
	module="js/details"
/>
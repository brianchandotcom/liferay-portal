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
CommerceOrderTypeDisplayContext commerceOrderTypeDisplayContext = (CommerceOrderTypeDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceOrderType commerceOrderType = commerceOrderTypeDisplayContext.getCommerceOrderType();
long commerceOrderTypeId = commerceOrderTypeDisplayContext.getCommerceOrderTypeId();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commerceOrderType != null) && (commerceOrderType.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<portlet:actionURL name="/commerce_order_type/edit_commerce_order_type" var="editCommerceOrderTypeActionURL" />

<aui:form action="<%= editCommerceOrderTypeActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceOrderType == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="externalReferenceCode" type="hidden" value="<%= commerceOrderType.getExternalReferenceCode() %>" />
	<aui:input name="commerceOrderTypeId" type="hidden" value="<%= commerceOrderTypeId %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<aui:model-context bean="<%= commerceOrderType %>" model="<%= CommerceOrderType.class %>" />

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
						<aui:input autoFocus="<%= true %>" label="name" localized="<%= true %>" name="name" required="<%= true %>" />
					</div>

					<div class="col-auto">
						<aui:input label='<%= HtmlUtil.escape("order") %>' name="displayOrder" value="<%= String.valueOf(commerceOrderType.getDisplayOrder()) %>" />
					</div>

					<div class="col-auto">
						<aui:input label='<%= HtmlUtil.escape("active") %>' name="active" type="toggle-switch" value="<%= commerceOrderType.isActive() %>" />
					</div>
				</div>

				<div class="row">
					<div class="col">
						<aui:input localized="<%= true %>" name="description" type="textarea" value="<%= commerceOrderType.getDescription(locale) %>" />
					</div>
				</div>
			</commerce-ui:panel>
		</div>

		<div class="col-12 col-xl-4">
			<commerce-ui:panel
				bodyClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "schedule") %>'
			>
				<liferay-ui:error exception="<%= CommerceOrderTypeExpirationDateException.class %>" message="please-select-a-valid-expiration-date" />

				<aui:input formName="fm" label="publish-date" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
			</commerce-ui:panel>
		</div>
	</div>

	<div class="row">
		<div class="col-12">
			<c:if test="<%= commerceOrderTypeDisplayContext.hasCustomAttributesAvailable() %>">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "custom-attributes") %>'
				>
					<liferay-expando:custom-attribute-list
						className="<%= CommerceOrderType.class.getName() %>"
						classPK="<%= commerceOrderTypeId %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</commerce-ui:panel>
			</c:if>
		</div>
	</div>
</aui:form>
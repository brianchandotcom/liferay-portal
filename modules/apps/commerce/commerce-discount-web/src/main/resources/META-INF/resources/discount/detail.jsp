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
CommerceDiscountDisplayContext commerceDiscountDisplayContext = (CommerceDiscountDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceDiscount commerceDiscount = commerceDiscountDisplayContext.getCommerceDiscount();
long commerceDiscountId = commerceDiscountDisplayContext.getCommerceDiscountId();
List<CommerceDiscountTarget> commerceDiscountTargets = commerceDiscountDisplayContext.getCommerceDiscountTargets();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((commerceDiscount != null) && (commerceDiscount.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<liferay-util:buffer
	var="removeCommerceAccountGroupIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="editCommerceDiscount" var="editCommerceDiscountActionURL" />

<aui:form action="<%= editCommerceDiscountActionURL %>" cssClass="container-fluid container-fluid-max-xl" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveCommerceDiscount();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceDiscount == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="addCommerceAccountGroupIds" type="hidden" />
	<aui:input name="commerceChannelIds" type="hidden" />
	<aui:input name="commerceDiscountId" type="hidden" value="<%= commerceDiscountId %>" />
	<aui:input name="deleteCommerceDiscountCommerceAccountGroupRelIds" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<c:if test="<%= (commerceDiscount != null) && !commerceDiscount.isNew() %>">
				<liferay-frontend:info-bar>
					<aui:workflow-status bean="<%= commerceDiscount %>" id="<%= String.valueOf(commerceDiscountId) %>" markupView="lexicon" model="<%= CommerceDiscount.class %>" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= commerceDiscount.getStatus() %>" />
				</liferay-frontend:info-bar>
			</c:if>

			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" bean="<%= commerceDiscount %>" model="<%= CommerceDiscount.class %>" name="title" />

				<aui:select bean="<%= commerceDiscount %>" model="<%= CommerceDiscount.class %>" name="target">

					<%
					for (CommerceDiscountTarget commerceDiscountTarget : commerceDiscountTargets) {
						String commerceDiscountTargetKey = commerceDiscountTarget.getKey();
					%>

						<aui:option label="<%= commerceDiscountTarget.getLabel(locale) %>" selected="<%= (commerceDiscount != null) && commerceDiscountTargetKey.equals(commerceDiscount.getTarget()) %>" value="<%= commerceDiscountTargetKey %>" />

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>

			<aui:fieldset collapsible="<%= true %>" label="channels">
				<%@ include file="/discount/detail_channels.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsible="<%= true %>" label="account-groups">
				<%@ include file="/discount/detail_account_group.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsible="<%= true %>" label="discount">
				<%@ include file="/discount/detail_discount.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="schedule">
				<aui:input bean="<%= commerceDiscount %>" formName="fm" model="<%= CommerceDiscount.class %>" name="displayDate" />

				<aui:input bean="<%= commerceDiscount %>" dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" model="<%= CommerceDiscount.class %>" name="expirationDate" />
			</aui:fieldset>

			<c:if test="<%= commerceDiscountDisplayContext.hasCustomAttributesAvailable() %>">
				<aui:fieldset collapsible="<%= true %>" label="custom-attribute">
					<liferay-expando:custom-attribute-list
						className="<%= CommerceDiscount.class.getName() %>"
						classPK="<%= commerceDiscountId %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</c:if>

			<aui:fieldset>

				<%
				boolean pending = false;

				if (commerceDiscount != null) {
					pending = commerceDiscount.isPending();
				}
				%>

				<c:if test="<%= pending %>">
					<div class="alert alert-info">
						<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
					</div>
				</c:if>

				<aui:button-row cssClass="commerce-discount-button-row">

					<%
					String saveButtonLabel = "save";

					if ((commerceDiscount == null) || commerceDiscount.isDraft() || commerceDiscount.isApproved() || commerceDiscount.isExpired() || commerceDiscount.isScheduled()) {
						saveButtonLabel = "save-as-draft";
					}

					String publishButtonLabel = "publish";

					if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, CommerceDiscount.class.getName())) {
						publishButtonLabel = "submit-for-publication";
					}
					%>

					<aui:button cssClass="btn-primary" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

					<aui:button name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

					<aui:button cssClass="btn-link" href="<%= redirect %>" type="cancel" />
				</aui:button-row>
			</aui:fieldset>
		</aui:fieldset-group>
	</div>
</aui:form>

<aui:script use="aui-base,event-input">
	var publishButton = A.one('#<portlet:namespace />publishButton');

	publishButton.on('click', function () {
		var workflowActionInput = A.one('#<portlet:namespace />workflowAction');

		if (workflowActionInput) {
			workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
		}
	});
</aui:script>

<aui:script>
	function <portlet:namespace />saveCommerceDiscount() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>
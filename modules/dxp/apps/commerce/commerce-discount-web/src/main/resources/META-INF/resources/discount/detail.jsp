<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	var="removeCommerceUserSegmentEntryIcon"
>
	<liferay-ui:icon
		icon="times"
		markupView="lexicon"
		message="remove"
	/>
</liferay-util:buffer>

<portlet:actionURL name="editCommerceDiscount" var="editCommerceDiscountActionURL" />

<aui:form action="<%= editCommerceDiscountActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveCommerceDiscount();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commerceDiscount == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="addCommerceUserSegmentEntryIds" type="hidden" />
	<aui:input name="commerceDiscountId" type="hidden" value="<%= commerceDiscountId %>" />
	<aui:input name="deleteCommerceDiscountUserSegmentRelIds" type="hidden" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceDiscount %>" model="<%= CommerceDiscount.class %>" />

		<c:if test="<%= (commerceDiscount != null) && !commerceDiscount.isNew() %>">
			<liferay-frontend:info-bar>
				<aui:workflow-status id="<%= String.valueOf(commerceDiscountId) %>" markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= commerceDiscount.getStatus() %>" />
			</liferay-frontend:info-bar>
		</c:if>

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input autoFocus="<%= true %>" name="title" />

				<aui:select name="target">

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

			<aui:fieldset collapsible="<%= true %>" label="user-segment">
				<%@ include file="/discount/detail_user_segment.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsible="<%= true %>" label="discount">
				<%@ include file="/discount/detail_discount.jspf" %>
			</aui:fieldset>

			<aui:fieldset collapsed="<%= true %>" collapsible="<%= true %>" label="schedule">
				<aui:input formName="fm" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
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

	publishButton.on(
		'click',
		function() {
			var workflowActionInput = A.one('#<portlet:namespace />workflowAction');

			if (workflowActionInput) {
				workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
			}
		}
	);
</aui:script>

<aui:script>
	function <portlet:namespace />saveCommerceDiscount() {
		submitForm(document.<portlet:namespace />fm);
	}
</aui:script>
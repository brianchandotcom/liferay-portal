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
CommercePriceListDisplayContext commercePriceListDisplayContext = (CommercePriceListDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePriceList commercePriceList = commercePriceListDisplayContext.getCommercePriceList();

long commercePriceListId = commercePriceListDisplayContext.getCommercePriceListId();
%>

<portlet:actionURL name="editCommercePriceList" var="editCommercePriceListActionURL" />

<aui:form action="<%= editCommercePriceListActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="addCommerceUserSegmentEntryIds" type="hidden" value="" />
	<aui:input name="commercePriceListId" type="hidden" value="<%= commercePriceListId %>" />
	<aui:input name="deleteCommercePriceListUserSegmentEntryRelIds" type="hidden" value="" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<c:if test="<%= (commercePriceList != null) && !commercePriceList.isNew() %>">
		<liferay-frontend:info-bar>
			<aui:workflow-status id="<%= String.valueOf(commercePriceListId) %>" markupView="lexicon" showHelpMessage="<%= false %>" showIcon="<%= false %>" showLabel="<%= false %>" status="<%= commercePriceList.getStatus() %>" />
		</liferay-frontend:info-bar>
	</c:if>

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= priceListsURL %>"
			formModelBean="<%= commercePriceList %>"
			id="<%= CommercePriceListFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRICE_LIST %>"
			markupView="lexicon"
			showButtons="<%= false %>"
		/>

		<%
		boolean pending = false;

		if (commercePriceList != null) {
			pending = commercePriceList.isPending();
		}
		%>

		<c:if test="<%= pending %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>
	</div>

	<aui:button-row cssClass="price-list-button-row">

		<%
		String saveButtonLabel = "save";

		if ((commercePriceList == null) || commercePriceList.isDraft() || commercePriceList.isApproved() || commercePriceList.isExpired() || commercePriceList.isScheduled()) {
			saveButtonLabel = "save-as-draft";
		}

		String publishButtonLabel = "publish";

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, CommercePriceList.class.getName())) {
			publishButtonLabel = "submit-for-publication";
		}
		%>

		<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

		<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

		<aui:button cssClass="btn-lg" href="<%= priceListsURL %>" type="cancel" />
	</aui:button-row>
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
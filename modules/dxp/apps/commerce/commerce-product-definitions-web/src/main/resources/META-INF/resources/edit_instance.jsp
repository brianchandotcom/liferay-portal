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
CPInstanceDisplayContext cpInstanceDisplayContext = (CPInstanceDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpInstanceDisplayContext.getCPDefinition();

CPInstance cpInstance = cpInstanceDisplayContext.getCPInstance();

long cpInstanceId = cpInstanceDisplayContext.getCPInstanceId();

PortletURL productSkusURL = renderResponse.createRenderURL();

productSkusURL.setParameter("mvcRenderCommandName", "viewProductInstances");
productSkusURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(productSkusURL.toString());

renderResponse.setTitle((cpInstance == null) ? LanguageUtil.get(request, "add-sku") : cpDefinition.getTitle(languageId) + " - " + cpInstance.getSku());
%>

<portlet:actionURL name="editProductInstance" var="editProductInstanceActionURL" />

<aui:form action="<%= editProductInstanceActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveInstance();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpInstance == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= productSkusURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= String.valueOf(cpInstanceId) %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= productSkusURL.toString() %>"
			formModelBean="<%= cpInstance %>"
			id="<%= CPInstanceFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_INSTANCE %>"
			markupView="lexicon"
			showButtons="<%= false %>"
		/>
	</div>

	<aui:button-row cssClass="product-instance-button-row">

		<%
		boolean pending = false;

		if (cpInstance != null) {
			pending = cpInstance.isPending();
		}

		String saveButtonLabel = "save";

		if ((cpInstance == null) || cpInstance.isDraft() || cpInstance.isApproved()) {
			saveButtonLabel = "save-as-draft";
		}

		String publishButtonLabel = "publish";

		if (WorkflowDefinitionLinkLocalServiceUtil.hasWorkflowDefinitionLink(themeDisplay.getCompanyId(), scopeGroupId, CPInstance.class.getName())) {
			publishButtonLabel = "submit-for-publication";
		}
		%>

		<aui:button cssClass="btn-lg" disabled="<%= pending %>" name="publishButton" type="submit" value="<%= publishButtonLabel %>" />

		<aui:button cssClass="btn-lg" name="saveButton" primary="<%= false %>" type="submit" value="<%= saveButtonLabel %>" />

		<aui:button cssClass="btn-lg" href="<%= productSkusURL.toString() %>" type="cancel" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />saveInstance(forceDisable) {
		var form = AUI.$(document.<portlet:namespace />fm);

		var cpDefinitionOptionsRenderDDMForm = Liferay.component("cpDefinitionOptionsRenderDDMForm");

		if (cpDefinitionOptionsRenderDDMForm) {
			form.fm('ddmFormValues').val(JSON.stringify(cpDefinitionOptionsRenderDDMForm.toJSON()));
		}

		submitForm(form);
	}
</aui:script>

<aui:script use="aui-base,event-input">
	var form = A.one('#<portlet:namespace />fm');

	var publishButton = form.one('#<portlet:namespace />publishButton');

	publishButton.on(
		'click',
		function() {
			var workflowActionInput = form.one('#<portlet:namespace />workflowAction');

			if (workflowActionInput) {
				workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
			}
		}
	);
</aui:script>
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

List<CPDefinitionOptionRel> cpDefinitionOptionRels = cpInstanceDisplayContext.getCPDefinitionOptionRels();

Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>> cpDefinitionOptionRelListMap = cpInstanceDisplayContext.parseCPInstanceDDMContent(cpInstanceDisplayContext.getCPInstanceId());

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((cpInstance != null) && (cpInstance.getExpirationDate() != null)) {
	neverExpire = false;
}

PortletURL productSkusURL = renderResponse.createRenderURL();

productSkusURL.setParameter("mvcRenderCommandName", "editProductDefinition");
productSkusURL.setParameter("cpDefinitionId", String.valueOf(cpDefinition.getCPDefinitionId()));
productSkusURL.setParameter("screenNavigationCategoryKey", cpInstanceDisplayContext.getScreenNavigationCategoryKey());
%>

<portlet:actionURL name="editProductInstance" var="editProductInstanceActionURL" />

<aui:form action="<%= editProductInstanceActionURL %>" cssClass="container-fluid-1280" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveInstance();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpInstance == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= String.valueOf(cpInstanceId) %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<div class="lfr-form-content">
		<liferay-ui:error-marker key="<%= WebKeys.ERROR_SECTION %>" value="details" />

		<aui:model-context bean="<%= cpInstance %>" model="<%= CPInstance.class %>" />

		<liferay-ui:error exception="<%= CPDefinitionIgnoreSKUCombinationsException.class %>" message="only-one-sku-can-be-approved" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="sku" />

				<aui:input helpMessage="gtin-help" label="global-trade-item-number" name="gtin" />

				<aui:input name="manufacturerPartNumber" />

				<aui:input name="purchasable" />
			</aui:fieldset>

			<c:if test="<%= !cpDefinition.getIgnoreSKUCombinations() %>">
				<aui:fieldset collapsible="<%= true %>" label="options">
					<c:choose>
						<c:when test="<%= cpInstance != null %>">

							<%
							for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
								List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

								StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);
							%>

								<h6 class="text-default">
									<strong><%= HtmlUtil.escape(cpDefinitionOptionRel.getTitle(languageId)) %></strong>

									<%
									for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
										stringJoiner.add(cpDefinitionOptionValueRel.getTitle(languageId));
									}
									%>

									<%= HtmlUtil.escape(stringJoiner.toString()) %>
								</h6>

							<%
							}
							%>

						</c:when>
						<c:otherwise>
							<%= cpInstanceDisplayContext.renderOptions(renderRequest, renderResponse) %>

							<aui:input name="ddmFormValues" type="hidden" />
						</c:otherwise>
					</c:choose>
				</aui:fieldset>
			</c:if>

			<aui:fieldset collapsible="<%= true %>" label="schedule">
				<aui:input name="published" />

				<aui:input formName="fm" name="displayDate" />

				<aui:input dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" name="expirationDate" />
			</aui:fieldset>
		</aui:fieldset-group>

		<c:if test="<%= cpInstanceDisplayContext.hasCustomAttributesAvailable() %>">
			<liferay-expando:custom-attribute-list
				className="<%= CPInstance.class.getName() %>"
				classPK="<%= (cpInstance != null) ? cpInstance.getCPInstanceId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</c:if>

		<%
		boolean pending = false;

		if (cpInstance != null) {
			pending = cpInstance.isPending();
		}
		%>

		<c:if test="<%= pending %>">
			<div class="alert alert-info">
				<liferay-ui:message key="there-is-a-publication-workflow-in-process" />
			</div>
		</c:if>
	</div>

	<aui:button-row cssClass="product-instance-button-row">

		<%
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

		var ddmForm = Liferay.component("<%= cpDefinition.getCPDefinitionId() %>DDMForm");

		if (ddmForm) {
			var fields = ddmForm.getImmediateFields();

			var fieldValues = [];

			fields.forEach(
				function(field) {
					var fieldValue = {};

					fieldValue.key = field.get('fieldName');

					var value = field.getValue();

					var arrValue = [];

					if (value instanceof Array) {
						arrValue = value;
					}
					else {
						arrValue.push(value);
					}

					fieldValue.value = arrValue;

					fieldValues.push(fieldValue);
				}
			);

			form.fm('ddmFormValues').val(JSON.stringify(fieldValues));
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
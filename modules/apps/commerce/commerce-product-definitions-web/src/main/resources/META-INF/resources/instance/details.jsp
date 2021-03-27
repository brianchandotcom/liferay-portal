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
String commerceCurrencyCode = cpInstanceDisplayContext.getCommerceCurrencyCode();

boolean neverExpire = ParamUtil.getBoolean(request, "neverExpire", true);

if ((cpInstance != null) && (cpInstance.getExpirationDate() != null)) {
	neverExpire = false;
}
%>

<portlet:actionURL name="/cp_definitions/edit_cp_instance" var="editProductInstanceActionURL" />

<aui:form action="<%= editProductInstanceActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + liferayPortletResponse.getNamespace() + "saveInstance();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (cpInstance == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinition.getCPDefinitionId() %>" />
	<aui:input name="cpInstanceId" type="hidden" value="<%= String.valueOf(cpInstanceId) %>" />
	<aui:input name="workflowAction" type="hidden" value="<%= String.valueOf(WorkflowConstants.ACTION_SAVE_DRAFT) %>" />

	<liferay-ui:error exception="<%= CommerceUndefinedBasePriceListException.class %>" message="there-is-no-base-price-list-associated-with-the-current-sku" />
	<liferay-ui:error exception="<%= CPDefinitionIgnoreSKUCombinationsException.class %>" message="only-one-sku-can-be-approved" />
	<liferay-ui:error exception="<%= CPInstanceJsonException.class %>" message="there-is-already-one-sku-with-the-selected-options" />
	<liferay-ui:error exception="<%= CPInstanceSkuException.class %>" message="please-enter-a-valid-sku" />

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "details") %>'
	>
		<div class="row">
			<div class="col-6">
				<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="sku" />

				<c:if test="<%= !cpDefinition.isIgnoreSKUCombinations() %>">
					<c:choose>
						<c:when test="<%= cpInstance != null %>">

							<%
							for (CPDefinitionOptionRel cpDefinitionOptionRel : cpDefinitionOptionRels) {
								List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels = cpInstanceDisplayContext.getCPDefinitionOptionValueRels(cpDefinitionOptionRel);

								StringJoiner stringJoiner = new StringJoiner(StringPool.COMMA);
							%>

								<h6 class="text-default">
									<strong><%= HtmlUtil.escape(cpDefinitionOptionRel.getName(languageId)) %></strong>

									<%
									for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel : cpDefinitionOptionValueRels) {
										stringJoiner.add(HtmlUtil.escape(cpDefinitionOptionValueRel.getName(languageId)));
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
				</c:if>

				<aui:input checked="<%= (cpInstance == null) ? false : cpInstance.isPurchasable() %>" name="purchasable" type="toggle-switch" />
			</div>

			<div class="col-6">
				<aui:input bean="<%= cpInstance %>" helpMessage="gtin-help" label="global-trade-item-number" model="<%= CPInstance.class %>" name="gtin" />

				<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="manufacturerPartNumber" />

				<aui:input bean="<%= cpInstance %>" label="unspsc" model="<%= CPInstance.class %>" name="unspsc" />
			</div>
		</div>
	</commerce-ui:panel>

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "pricing") %>'
	>
		<div class="row">
			<div class="col-4">
				<aui:input label="base-price" name="price" suffix="<%= HtmlUtil.escape(commerceCurrencyCode) %>" type="text" value="<%= cpInstanceDisplayContext.getPrice() %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>
			</div>

			<div class="col-4">
				<aui:input label="sale-price" name="promoPrice" suffix="<%= HtmlUtil.escape(commerceCurrencyCode) %>" type="text" value="<%= cpInstanceDisplayContext.getPromoPrice() %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>
			</div>

			<div class="col-4">
				<aui:input name="cost" suffix="<%= HtmlUtil.escape(commerceCurrencyCode) %>" type="text" value="<%= (cpInstance == null) ? StringPool.BLANK : cpInstanceDisplayContext.round(cpInstance.getCost()) %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>
			</div>
		</div>
	</commerce-ui:panel>

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "shipping-override") %>'
	>
		<div class="row">
			<div class="col-6">
				<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="width" suffix="<%= HtmlUtil.escape(cpInstanceDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_DIMENSION)) %>">
					<aui:validator name="min">0</aui:validator>
				</aui:input>

				<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="depth" suffix="<%= HtmlUtil.escape(cpInstanceDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_DIMENSION)) %>">
					<aui:validator name="min">0</aui:validator>
				</aui:input>
			</div>

			<div class="col-6">
				<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="height" suffix="<%= HtmlUtil.escape(cpInstanceDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_DIMENSION)) %>">
					<aui:validator name="min">0</aui:validator>
				</aui:input>

				<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="weight" suffix="<%= HtmlUtil.escape(cpInstanceDisplayContext.getCPMeasurementUnitName(CPMeasurementUnitConstants.TYPE_WEIGHT)) %>">
					<aui:validator name="min">0</aui:validator>
				</aui:input>
			</div>
		</div>
	</commerce-ui:panel>

	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "schedule") %>'
	>
		<aui:input bean="<%= cpInstance %>" model="<%= CPInstance.class %>" name="published" />

		<aui:input bean="<%= cpInstance %>" formName="fm" model="<%= CPInstance.class %>" name="displayDate" />

		<aui:input bean="<%= cpInstance %>" dateTogglerCheckboxLabel="never-expire" disabled="<%= neverExpire %>" formName="fm" model="<%= CPInstance.class %>" name="expirationDate" />
	</commerce-ui:panel>

	<c:if test="<%= cpInstanceDisplayContext.hasCustomAttributesAvailable() %>">
		<commerce-ui:panel
			title='<%= LanguageUtil.get(request, "custom-attribute") %>'
		>
			<liferay-expando:custom-attribute-list
				className="<%= CPInstance.class.getName() %>"
				classPK="<%= (cpInstance != null) ? cpInstance.getCPInstanceId() : 0 %>"
				editable="<%= true %>"
				label="<%= true %>"
			/>
		</commerce-ui:panel>
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
	</aui:button-row>
</aui:form>

<aui:script>
	var fieldValues = [];

	Liferay.componentReady(
		'ProductOptions<%= cpDefinition.getCPDefinitionId() %>'
	).then((ddmForm) => {
		ddmForm.unstable_onEvent((e) => {
			var eventName = e.type;
			if (eventName === 'field_change') {
				var key = e.payload.fieldInstance.fieldName;
				var updatedItem = {
					key: e.payload.fieldInstance.fieldName,
					value: e.payload.value,
				};

				var itemFound = fieldValues.find((item) => {
					return item.key === key;
				});

				if (itemFound) {
					fieldValues = fieldValues.reduce((acc, item) => {
						return acc.concat(item.key === key ? updatedItem : item);
					}, []);
				}
				else {
					fieldValues.push(updatedItem);
				}

				var form = window.document['<portlet:namespace />fm'];
				form['<portlet:namespace />ddmFormValues'].value = JSON.stringify(
					fieldValues
				);
			}
		});
	});

	function <portlet:namespace />saveInstance(forceDisable) {
		var form = window.document['<portlet:namespace />fm'];

		var ddmForm = Liferay.component(
			'ProductOptions<%= cpDefinition.getCPDefinitionId() %>DDMForm'
		);

		if (ddmForm) {
			var fields = ddmForm.getImmediateFields();

			var fieldValues = [];

			fields.forEach((field) => {
				var fieldValue = {};

				fieldValue.key = field.get('fieldName');

				var value = field.getValue();

				var arrValue = [];

				if (Array.isArray(value)) {
					arrValue = value;
				}
				else {
					arrValue.push(value);
				}

				fieldValue.value = arrValue;

				fieldValues.push(fieldValue);
			});

			form['<portlet:namespace />ddmFormValues'].value = JSON.stringify(
				fieldValues
			);
		}

		submitForm(form);
	}
</aui:script>

<aui:script use="aui-base,event-input">
	var form = A.one('#<portlet:namespace />fm');

	var publishButton = form.one('#<portlet:namespace />publishButton');

	publishButton.on('click', () => {
		var workflowActionInput = form.one('#<portlet:namespace />workflowAction');

		if (workflowActionInput) {
			workflowActionInput.val('<%= WorkflowConstants.ACTION_PUBLISH %>');
		}
	});
</aui:script>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CPInstanceUnitOfMeasureDisplayContext cpInstanceUnitOfMeasureDisplayContext = (CPInstanceUnitOfMeasureDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPInstance cpInstance = cpInstanceUnitOfMeasureDisplayContext.getCPInstance();
%>

<commerce-ui:modal-content
	submitButtonLabel='<%= LanguageUtil.get(request, "add") %>'
	title='<%= LanguageUtil.get(request, "add-unit-of-measure") %>'
>
	<portlet:actionURL name="/cp_definitions/edit_cp_instance_unit_of_measure" var="editCPInstanceUnitOfMeasureActionURL" />

	<aui:form action="<%= editCPInstanceUnitOfMeasureActionURL %>" method="post" name="fm">
		<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.ADD %>" />
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="cpDefinitionId" type="hidden" value="<%= cpInstance.getCPDefinitionId() %>" />
		<aui:input name="cpInstanceId" type="hidden" value="<%= cpInstance.getCPInstanceId() %>" />
		<aui:input name="sku" type="hidden" value="<%= cpInstance.getSku() %>" />

		<c:if test="<%= !cpInstanceUnitOfMeasureDisplayContext.hasCPInstanceUnitOfMeasure() %>">
			<div class="alert alert-info">
				<liferay-ui:message key="by-creating-the-first-unit-of-measure-for-this-sku,-all-existing-sku-stock-and-pricing-(if-any)-will-be-mapped-to-the-first-unit-of-measure-created" />
			</div>

			<aui:input name="primary" type="hidden" value="<%= true %>" />
		</c:if>

		<liferay-ui:error exception="<%= CPInstanceUnitOfMeasureIncrementalOrderQuantityException.class %>" message="decimals-allowed-cannot-be-less-than-the-number-of-decimals-in-the-base-unit-quantity" />
		<liferay-ui:error exception="<%= CPInstanceUnitOfMeasureRateException.class %>" message="conversion-rate-quantity-must-be-greater-than-zero" />
		<liferay-ui:error exception="<%= DuplicateCPInstanceUnitOfMeasureKeyException.class %>" message="there-is-another-unit-of-measure-with-the-same-key" />

		<c:if test="<%= cpInstanceUnitOfMeasureDisplayContext.hasCPInstanceUnitOfMeasure() %>">
			<div class="row">
				<div class="col-6">
					<aui:input checked="<%= true %>" helpMessage="set-as-primary-unit-of-measure-help" inlineLabel="right" label="set-as-primary-unit-of-measure" name="primary" type="toggle-switch" value="<%= false %>" />
				</div>
			</div>

			<div class="row">
				<div class="col-6">
					<label class="field-label"><liferay-ui:message key="primary-unit-of-measure" /></label>

					<div class="col-6 form-group">
						<%= cpInstanceUnitOfMeasureDisplayContext.getPrimaryCPInstanceUnitOfMeasureName() %>
					</div>
				</div>
			</div>
		</c:if>

		<div class="row">
			<div class="col-12">
				<aui:input defaultLanguageId="<%= cpInstanceUnitOfMeasureDisplayContext.getCatalogDefaultLanguageId() %>" label="unit-of-measure" localized="<%= true %>" name="name" required="<%= true %>" type="text" />
			</div>
		</div>

		<div class="row">
			<div class="col-12">
				<aui:input label="key" name="key" required="<%= true %>" type="text" />
			</div>
		</div>

		<c:if test="<%= cpInstanceUnitOfMeasureDisplayContext.hasCPInstanceUnitOfMeasure() %>">
			<div class="row">
				<div class="col-12">
					<aui:input label="conversion-rate-to-primary-unit-of-measure" name="rate" type="text">
						<aui:validator name="number" />

						<aui:validator errorMessage='<%= LanguageUtil.format(request, "please-enter-a-value-greater-than-x", 0) %>' name="custom">
							function(val) {
								if (Number(val) > 0) {
									return true;
								}

								return false;
							}
						</aui:validator>
					</aui:input>
				</div>
			</div>
		</c:if>

		<div class="row">
			<div class="col-6">
				<aui:input label="decimal-allowed-precision" name="precision" required="<%= true %>" type="text" value="0">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="digits" />
				</aui:input>
			</div>

			<div class="col-6">
				<aui:input helpMessage="base-unit-quantity-help" label="base-unit-quantity" name="incrementalOrderQuantity" required="<%= true %>" type="text" value="1">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>
			</div>
		</div>

		<div class="row">
			<div class="col-6">
				<aui:input label="base-price" name="basePrice" suffix="<%= HtmlUtil.escape(cpInstanceUnitOfMeasureDisplayContext.getCommerceCurrencyCode()) %>" type="text" value="<%= cpInstanceUnitOfMeasureDisplayContext.getPrice() %>">
					<aui:validator name="min">0</aui:validator>
					<aui:validator name="number" />
				</aui:input>
			</div>

			<div class="col-6">
				<aui:input label="base-promotion-price" name="promoPrice" suffix="<%= HtmlUtil.escape(cpInstanceUnitOfMeasureDisplayContext.getCommerceCurrencyCode()) %>" type="text" value="<%= cpInstanceUnitOfMeasureDisplayContext.getPromoPrice() %>">
					<aui:validator name="number" />
				</aui:input>
			</div>
		</div>

		<div class="row">
			<div class="col-6">
				<aui:input helpMessage="priority-help" label="priority" name="priority" type="text">
					<aui:validator name="number" />
				</aui:input>
			</div>
		</div>

		<div class="row">
			<div class="col-6">
				<aui:input checked="<%= true %>" inlineLabel="right" label="purchasable" name="active" type="checkbox" />
			</div>
		</div>
	</aui:form>

	<liferay-frontend:component
		context='<%=
			HashMapBuilder.<String, Object>put(
				"message", LanguageUtil.get(request, "exit-with-primary-unit-of-measure-changed-saving-help")
			).build()
		%>'
		module="js/add_cp_instance_unit_of_measure"
	/>
</commerce-ui:modal-content>
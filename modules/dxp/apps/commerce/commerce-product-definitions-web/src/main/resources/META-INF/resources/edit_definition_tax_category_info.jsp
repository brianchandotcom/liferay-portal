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
CPDefinitionTaxCategoryDisplayContext cpDefinitionTaxCategoryDisplayContext = (CPDefinitionTaxCategoryDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CPDefinition cpDefinition = cpDefinitionTaxCategoryDisplayContext.getCPDefinition();
long cpDefinitionId = cpDefinitionTaxCategoryDisplayContext.getCPDefinitionId();
List<CPTaxCategory> cpTaxCategories = cpDefinitionTaxCategoryDisplayContext.getCPTaxCategories();
%>

<portlet:actionURL name="editProductDefinition" var="editProductDefinitionTaxCategoryInfoActionURL" />

<aui:form action="<%= editProductDefinitionTaxCategoryInfoActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="updateTaxCategoryInfo" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= cpDefinition %>" model="<%= CPDefinition.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:select label="tax-category" name="cpTaxCategoryId" showEmptyOption="<%= true %>">

					<%
					for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
					%>

						<aui:option label="<%= cpTaxCategory.getName(locale) %>" selected="<%= (cpDefinition != null) && (cpDefinition.getCPTaxCategoryId() == cpTaxCategory.getCPTaxCategoryId()) %>" value="<%= cpTaxCategory.getCPTaxCategoryId() %>" />

					<%
					}
					%>

				</aui:select>

				<aui:input name="taxExempt" />

				<aui:input label="telecommunication-broadcasting-or-electronic-services" name="telcoOrElectronics" />
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>
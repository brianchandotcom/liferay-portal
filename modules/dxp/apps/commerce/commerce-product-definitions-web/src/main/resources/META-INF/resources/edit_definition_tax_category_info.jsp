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

						<aui:option
							label="<%= cpTaxCategory.getName(locale) %>"
							selected="<%= (cpDefinition != null) && (cpDefinition.getCPTaxCategoryId() == cpTaxCategory.getCPTaxCategoryId()) %>"
							value="<%= cpTaxCategory.getCPTaxCategoryId() %>"
						/>

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
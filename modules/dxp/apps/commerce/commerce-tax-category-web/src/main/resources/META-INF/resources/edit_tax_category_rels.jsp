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
CommerceTaxCategoryRelDisplayContext commerceTaxCategoryRelDisplayContext = (CommerceTaxCategoryRelDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceTaxCategoryRel commerceTaxCategoryRel = commerceTaxCategoryRelDisplayContext.getCommerceTaxCategoryRel();

long cpDefinitionId = ParamUtil.getLong(request, "cpDefinitionId");

String lifecycle = (String)request.getAttribute(LiferayPortletRequest.LIFECYCLE_PHASE);

PortletURL catalogURLObj = PortalUtil.getControlPanelPortletURL(request, CPPortletKeys.CP_DEFINITIONS, lifecycle);

String catalogURL = catalogURLObj.toString();
%>

<portlet:actionURL name="editCommerceTaxCategoryRel" var="editCommerceTaxCategoryRelURL" />

<aui:form action="<%= editCommerceTaxCategoryRelURL %>" cssClass="container-fluid-1280" method="post" name="fm1">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="cpDefinitionId" type="hidden" value="<%= cpDefinitionId %>" />

	<div class="lfr-form-content">
		<aui:model-context bean="<%= commerceTaxCategoryRel %>" model="<%= CommerceTaxCategoryRel.class %>" />

		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:select label="tax-category" name="commerceTaxCategoryId" showEmptyOption="<%= true %>">

					<%
					List<CommerceTaxCategory> commerceTaxCategories = commerceTaxCategoryRelDisplayContext.getCommerceTaxCategories(scopeGroupId);

					for (CommerceTaxCategory commerceTaxCategory : commerceTaxCategories) {
					%>

					<aui:option
						label="<%= commerceTaxCategory.getName(locale) %>"
						selected="<%= (commerceTaxCategoryRel != null) && (commerceTaxCategoryRel.getCommerceTaxCategoryId() == commerceTaxCategory.getCommerceTaxCategoryId()) %>"
						value="<%= commerceTaxCategory.getCommerceTaxCategoryId() %>"
					/>

					<%
					}
					%>

				</aui:select>
			</aui:fieldset>
		</aui:fieldset-group>

		<aui:button-row>
			<aui:button cssClass="btn-lg" type="submit" />

			<aui:button cssClass="btn-lg" href="<%= catalogURL %>" type="cancel" />
		</aui:button-row>
	</div>
</aui:form>
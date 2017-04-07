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

<%@ include file="/commerce_product_definitions/init.jsp" %>

<%
CommerceProductDefinition commerceProductDefinition = (CommerceProductDefinition)request.getAttribute(CommerceProductWebKeys.COMMERCE_PRODUCT_DEFINITION);

long commerceProductDefinitionId = BeanParamUtil.getLong(commerceProductDefinition, request, "commerceProductDefinitionId");

String redirect = ParamUtil.getString(request, "redirect");

String backURL = ParamUtil.getString(request, "backURL", redirect);

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);
%>

<portlet:actionURL name="/editProductDefinition" var="editProductDefinitionActionURL">
	<portlet:param name="commerceProductDefinitionId" value="<%= String.valueOf(commerceProductDefinitionId) %>" />
</portlet:actionURL>

<portlet:actionURL name="/editProductDefinition" var="editProductDefinitionActionURL">
	<portlet:param name="<%= Constants.CMD %>" value="<%= Constants.UPDATE %>" />
	<portlet:param name="commerceProductDefinitionId" value="<%= String.valueOf(commerceProductDefinitionId) %>" />
</portlet:actionURL>

<liferay-frontend:info-bar>
	<c:if test="<%= (commerceProductDefinition != null) && !commerceProductDefinition.isNew() %>">
		<liferay-frontend:info-bar>
			<aui:workflow-status
				id="<%= String.valueOf(commerceProductDefinitionId) %>"
				markupView="lexicon"
				showHelpMessage="<%= false %>"
				showIcon="<%= false %>"
				showLabel="<%= false %>"
				status="<%= commerceProductDefinition.getStatus() %>"
			/>
		</liferay-frontend:info-bar>
	</c:if>
</liferay-frontend:info-bar>

<aui:form action="<%= editProductDefinitionActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<div class="lfr-form-content">
		<liferay-ui:form-navigator
			backURL="<%= backURL %>"
			formModelBean="<%= commerceProductDefinition %>"
			id="<%= CommerceProductFormNavigatorConstants.FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION %>"
			markupView="lexicon"
		/>
	</div>
</aui:form>
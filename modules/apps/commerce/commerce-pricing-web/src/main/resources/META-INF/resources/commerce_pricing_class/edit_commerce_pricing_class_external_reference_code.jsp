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
CommercePricingClassDisplayContext commercePricingClassDisplayContext = (CommercePricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePricingClass commercePricingClass = commercePricingClassDisplayContext.getCommercePricingClass();
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class_external_reference_code" var="editCommercePricingClassExternalReferenceCodeURL" />

<commerce-ui:modal-content>
	<aui:form action="<%= editCommercePricingClassExternalReferenceCodeURL %>" cssClass="container-fluid container-fluid-max-xl p-0" method="post" name="fm">
		<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
		<aui:input name="commercePricingClassId" type="hidden" value="<%= commercePricingClass.getCommercePricingClassId() %>" />

		<aui:model-context bean="<%= commercePricingClass %>" model="<%= CommercePricingClass.class %>" />

		<aui:input name="externalReferenceCode" type="text" value="<%= commercePricingClass.getExternalReferenceCode() %>" wrapperCssClass="form-group-item" />
	</aui:form>
</commerce-ui:modal-content>
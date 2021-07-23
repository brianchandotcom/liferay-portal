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
CPDefinitionPricingClassDisplayContext cpDefinitionPricingClassDisplayContext = (CPDefinitionPricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePricingClass commercePricingClass = cpDefinitionPricingClassDisplayContext.getCommercePricingClass();
%>

<portlet:actionURL name="/cp_definitions/edit_cp_definition_pricing_class" var="editCommercePricingClassActionURL" />

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "edit-product-group") %>'
>
	<commerce-ui:panel
		title='<%= LanguageUtil.get(request, "detail") %>'
	>
		<aui:form action="<%= editCommercePricingClassActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
			<aui:input name="commercePricingClassId" type="hidden" value="<%= String.valueOf(commercePricingClass.getCommercePricingClassId()) %>" />

			<aui:input bean="<%= commercePricingClass %>" model="<%= CommercePricingClass.class %>" name="title" required="<%= true %>" />

			<aui:input localized="<%= true %>" name="description" type="textarea" value="<%= commercePricingClass.getDescription(locale) %>" />

			<aui:button-row>
				<aui:button cssClass="btn-lg" type="submit" value="save" />

				<aui:button cssClass="btn-lg" type="cancel" />
			</aui:button-row>

			<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(commercePricingClass.getCompanyId(), CommercePricingClass.class.getName(), commercePricingClass.getCommercePricingClassId(), null) %>">
				<commerce-ui:panel
					title='<%= LanguageUtil.get(request, "custom-attributes") %>'
				>
					<liferay-expando:custom-attribute-list
						className="<%= CommercePricingClass.class.getName() %>"
						classPK="<%= (commercePricingClass != null) ? commercePricingClass.getCommercePricingClassId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</commerce-ui:panel>
			</c:if>
		</aui:form>
	</commerce-ui:panel>
</liferay-frontend:side-panel-content>
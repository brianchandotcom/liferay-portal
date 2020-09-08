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

long commercePricingClassId = commercePricingClass.getCommercePricingClassId();
boolean isViewOnly = !commercePricingClassDisplayContext.hasPermission(ActionKeys.UPDATE);

boolean hasCustomAttributesAvailable = CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CommercePricingClass.class.getName(), commercePricingClassId, null);
%>

<portlet:actionURL name="editCommercePricingClass" var="editCommercePricingClassActionURL" />

<aui:form action="<%= editCommercePricingClassActionURL %>" cssClass="pt-4" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= (commercePricingClass == null) ? Constants.ADD : Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= backURL %>" />
	<aui:input name="commercePricingClassId" type="hidden" value="<%= (commercePricingClass == null) ? 0 : commercePricingClass.getCommercePricingClassId() %>" />

	<div class="row">
		<div class="col-12">
			<commerce-ui:panel
				elementClasses="flex-fill"
				title='<%= LanguageUtil.get(request, "details") %>'
			>
				<div class="col-12 lfr-form-content">
					<aui:input bean="<%= commercePricingClass %>" disabled="<%= isViewOnly %>" label="name" model="<%= CommercePricingClass.class %>" name="title" required="<%= true %>" />

					<aui:input localized="<%= true %>" name="description" type="textarea" value="<%= commercePricingClass.getDescription(locale) %>" />
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<div class="row">
		<div class="col-12">
			<c:if test="<%= hasCustomAttributesAvailable %>">
				<aui:fieldset>
					<liferay-expando:custom-attribute-list
						className="<%= CommercePricingClass.class.getName() %>"
						classPK="<%= (commercePricingClass != null) ? commercePricingClass.getCommercePricingClassId() : 0 %>"
						editable="<%= true %>"
						label="<%= true %>"
					/>
				</aui:fieldset>
			</c:if>
		</div>
	</div>
</aui:form>
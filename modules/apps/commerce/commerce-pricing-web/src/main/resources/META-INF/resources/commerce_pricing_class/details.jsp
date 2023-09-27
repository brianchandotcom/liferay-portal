<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
CommercePricingClassDisplayContext commercePricingClassDisplayContext = (CommercePricingClassDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommercePricingClass commercePricingClass = commercePricingClassDisplayContext.getCommercePricingClass();

long commercePricingClassId = commercePricingClass.getCommercePricingClassId();
%>

<portlet:actionURL name="/commerce_pricing_classes/edit_commerce_pricing_class" var="editCommercePricingClassActionURL" />

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
					<aui:input bean="<%= commercePricingClass %>" disabled="<%= !commercePricingClassDisplayContext.hasPermission(ActionKeys.UPDATE) %>" label="name" model="<%= CommercePricingClass.class %>" name="title" required="<%= true %>" />

					<aui:input localized="<%= true %>" name="description" type="textarea" value="<%= commercePricingClass.getDescription(locale) %>" />
				</div>
			</commerce-ui:panel>
		</div>
	</div>

	<div class="row">
		<div class="col-12">
			<c:if test="<%= CustomAttributesUtil.hasCustomAttributes(company.getCompanyId(), CommercePricingClass.class.getName(), commercePricingClassId, null) %>">
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
		</div>
	</div>
</aui:form>
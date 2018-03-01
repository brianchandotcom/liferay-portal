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
CommerceVatNumberDisplayContext commerceVatNumberDisplayContext = (CommerceVatNumberDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

CommerceVatNumber commerceVatNumber = commerceVatNumberDisplayContext.getCommerceVatNumber();

long commerceVatNumberId = commerceVatNumberDisplayContext.getCommerceVatNumberId();

String title = LanguageUtil.format(request, "edit-x", commerceVatNumber.getVatNumber(), false);

Map<String, Object> data = new HashMap<>();

data.put("direction-right", StringPool.TRUE);

PortalUtil.addPortletBreadcrumbEntry(request, LanguageUtil.get(request, "vat-numbers"), vatNumbersURL, data);
PortalUtil.addPortletBreadcrumbEntry(request, title, StringPool.BLANK, data);

renderResponse.setTitle(LanguageUtil.get(request, "vat-numbers"));
%>

<%@ include file="/navbar.jspf" %>

<%@ include file="/breadcrumb.jspf" %>

<portlet:actionURL name="editCommerceVatNumber" var="editCommerceVatNumberActionURL" />

<aui:form action="<%= editCommerceVatNumberActionURL %>" cssClass="container-fluid-1280" method="post" name="fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="commerceVatNumberId" type="hidden" value="<%= commerceVatNumberId %>" />

	<liferay-ui:error exception="<%= CommerceVatNumberCountryCodeException.class %>" message="there-is-no-country-defined-for-the-given-vat-number" />

	<aui:model-context bean="<%= commerceVatNumber %>" model="<%= CommerceVatNumber.class %>" />

	<div class="lfr-form-content">
		<aui:fieldset-group markupView="lexicon">
			<aui:fieldset>
				<aui:input name="vatNumber" />
			</aui:fieldset>
		</aui:fieldset-group>
	</div>

	<aui:button-row>
		<aui:button cssClass="btn-lg" primary="<%= true %>" type="submit" value="save" />

		<aui:button cssClass="btn-lg" href="<%= vatNumbersURL %>" type="cancel" />
	</aui:button-row>
</aui:form>
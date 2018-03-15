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

<%@ include file="/dynamic_include/init.jsp" %>

<%
OpenIdConfiguration openIdConfiguration = ConfigurationProviderUtil.getConfiguration(OpenIdConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), PortalSettingsOpenIdConstants.FORM_PARAMETER_NAMESPACE, new CompanyServiceSettingsLocator(company.getCompanyId(), OpenIdConstants.SERVICE_NAME)));

boolean enabled = openIdConfiguration.enabled();
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/openid" />

<aui:fieldset>
	<aui:input label="enabled" name='<%= PortalSettingsOpenIdConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= enabled %>" />

	<aui:button-row>
		<portlet:actionURL name="/portal_settings/openid_delete" var="resetValuesURL">
			<portlet:param name="tabs1" value="openid" />
		</portlet:actionURL>

		<%
		String taglibOnClick = "if (confirm('" + UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-reset-the-configured-values") + "')) {submitForm(document.hrefFm, '" + resetValuesURL.toString() + "');}";
		%>

		<aui:button onClick="<%= taglibOnClick %>" value="reset-values" />
	</aui:button-row>
</aui:fieldset>
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

<%@ include file="/com.liferay.portal.settings.web/init.jsp" %>

<%
OpenIdConfiguration openIdConfiguration = ConfigurationFactoryUtil.getConfiguration(OpenIdConfiguration.class, new ParameterMapSettingsLocator(request.getParameterMap(), "openid--", new CompanyServiceSettingsLocator(company.getCompanyId(), OpenIdConstants.SERVICE_NAME)));
boolean enabled = openIdConfiguration.enabled();
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/edit_company_openid_configuration" />

<aui:fieldset>
	<aui:input label="enabled" name="openid--enabled" type="checkbox" value="<%= enabled %>" />
</aui:fieldset>
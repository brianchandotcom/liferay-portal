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
VirtualHost virtualHost = null;

try {
	virtualHost = VirtualHostLocalServiceUtil.getVirtualHost(company.getCompanyId(), 0);
}
catch (Exception e) {
}
%>

<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />

<h4><liferay-ui:message key="main-configuration" /></h4>

<aui:model-context bean="<%= company %>" model="<%= Company.class %>" />

<clay:row>
	<clay:col
		md="6"
	>
		<liferay-ui:error exception="<%= CompanyNameException.class %>" message="please-enter-a-valid-name" />

		<aui:input name="name" />

		<liferay-ui:error exception="<%= CompanyMxException.class %>" message="please-enter-a-valid-mail-domain" />

		<aui:input disabled="<%= !PropsValues.MAIL_MX_UPDATE %>" label="mail-domain" name="mx" />

		<liferay-ui:error exception="<%= CompanyVirtualHostException.class %>" message="please-enter-a-valid-virtual-host" />

		<aui:input bean="<%= virtualHost %>" fieldParam="virtualHostname" label="virtual-host" model="<%= VirtualHost.class %>" name="hostname" />
	</clay:col>

	<clay:col
		md="6"
	>
		<aui:input label="cdn-host-http" name='<%= "settings--" + PropsKeys.CDN_HOST_HTTP + "--" %>' type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CDN_HOST_HTTP, PropsValues.CDN_HOST_HTTP) %>" />

		<aui:input label="cdn-host-https" name='<%= "settings--" + PropsKeys.CDN_HOST_HTTPS + "--" %>' type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.CDN_HOST_HTTPS, PropsValues.CDN_HOST_HTTPS) %>" />

		<aui:input label="enable-cdn-dynamic-resources" name='<%= "settings--" + PropsKeys.CDN_DYNAMIC_RESOURCES_ENABLED + "--" %>' type="checkbox" value="<%= PrefsPropsUtil.getBoolean(company.getCompanyId(), PropsKeys.CDN_DYNAMIC_RESOURCES_ENABLED, PropsValues.CDN_DYNAMIC_RESOURCES_ENABLED) %>" />
	</clay:col>
</clay:row>

<h4><liferay-ui:message key="navigation" /></h4>

<clay:row>
	<clay:col
		md="6"
	>
		<aui:input bean="<%= company %>" helpMessage="home-url-help" label="home-url" model="<%= Company.class %>" name="homeURL" />

		<aui:input helpMessage="default-landing-page-help" label="default-landing-page" name='<%= "settings--" + PropsKeys.DEFAULT_LANDING_PAGE_PATH + "--" %>' type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_LANDING_PAGE_PATH, PropsValues.DEFAULT_LANDING_PAGE_PATH) %>" />
	</clay:col>

	<clay:col
		md="6"
	>
		<aui:input helpMessage="default-logout-page-help" label="default-logout-page" name='<%= "settings--" + PropsKeys.DEFAULT_LOGOUT_PAGE_PATH + "--" %>' type="text" value="<%= PrefsPropsUtil.getString(company.getCompanyId(), PropsKeys.DEFAULT_LOGOUT_PAGE_PATH, PropsValues.DEFAULT_LOGOUT_PAGE_PATH) %>" />
	</clay:col>
</clay:row>

<h4><liferay-ui:message key="additional-information" /></h4>

<clay:row>
	<clay:col
		md="6"
	>
		<aui:input name="legalName" />

		<aui:input name="legalId" />

		<aui:input name="legalType" />

		<aui:input name="sicCode" />
	</clay:col>

	<clay:col
		md="6"
	>
		<aui:input name="tickerSymbol" />

		<aui:input name="industry" />

		<aui:input name="type" />
	</clay:col>
</clay:row>
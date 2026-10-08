<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
FriendlyURLSeparatorCompanyConfigurationDisplayContext friendlyURLSeparatorCompanyConfigurationDisplayContext = (FriendlyURLSeparatorCompanyConfigurationDisplayContext)request.getAttribute(FriendlyURLSeparatorCompanyConfigurationDisplayContext.class.getName());

JSONObject errorsJSONObject = friendlyURLSeparatorCompanyConfigurationDisplayContext.getErrorsJSONObject();

String errorMessage = errorsJSONObject.getString("errorMessage");
%>

<c:if test="<%= Validator.isNotNull(errorMessage) %>">
	<clay:alert
		cssClass="mt-4"
		displayType="danger"
		message="<%= errorMessage %>"
	/>
</c:if>

<p class="mt-4 sheet-subtitle text-secondary" id="<portlet:namespace />header">
	<liferay-ui:message key="url-separator" />
</p>

<clay:alert
	cssClass="mb-4"
	displayType="info"
	message="friendly-url-separator-info-message"
/>

<div aria-labelledby="<portlet:namespace />header" role="group">
	<react:component
		module="{SeparatorFields} from friendly-url-web"
		props="<%= friendlyURLSeparatorCompanyConfigurationDisplayContext.getSeparatorFieldsProps() %>"
	/>
</div>
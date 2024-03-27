<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:error exception="<%= CaptchaConfigurationException.class %>" message="a-captcha-error-occurred-please-contact-an-administrator" />
<liferay-ui:error exception="<%= CaptchaException.class %>" message="captcha-verification-failed" />
<liferay-ui:error exception="<%= CaptchaTextException.class %>" message="text-verification-failed" />

<%
long preferencesCompanyId = CompanyConstants.SYSTEM;

Function<String, String> defaultValueFunction = key -> PropsUtil.get(key);
%>

<div class="sheet">
	<div class="panel-group panel-group-flush">
		<%@ include file="/mail_fields.jspf" %>

		<liferay-captcha:captcha />

		<aui:button-row>
			<aui:button cssClass="save-server-button" data-cmd="updateMail" primary="<%= true %>" value="save" />
		</aui:button-row>
	</div>
</div>
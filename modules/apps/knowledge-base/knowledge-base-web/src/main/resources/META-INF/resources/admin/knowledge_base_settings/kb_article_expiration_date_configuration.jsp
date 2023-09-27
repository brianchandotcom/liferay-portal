<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/knowledge_base_settings/init.jsp" %>

<%
KBArticleCompanyConfigurationDisplayContext kbArticleCompanyConfigurationDisplayContext = (KBArticleCompanyConfigurationDisplayContext)request.getAttribute(KBArticleCompanyConfigurationDisplayContext.class.getName());
%>

<aui:form action="<%= kbArticleCompanyConfigurationDisplayContext.getEditKBArticleConfigurationURL() %>" method="post" name="fm">
	<clay:sheet>
		<clay:sheet-header>
			<h2>
				<liferay-ui:message key="knowledge-base-service-configuration-name" />
			</h2>

			<liferay-ui:error exception="<%= ConfigurationException.class %>" message="there-was-an-error-processing-one-or-more-of-the-configurations" />
		</clay:sheet-header>

		<clay:sheet-section>
			<aui:input label="check-interval" min="1" name="checkInterval" type="number" value="<%= kbArticleCompanyConfigurationDisplayContext.getCheckInterval() %>" />
		</clay:sheet-section>

		<clay:sheet-section>
			<h3 class="sheet-subtitle"><liferay-ui:message key="article-expiration-date-notification" /></h3>

			<p class="text-muted">
				<liferay-ui:message key="expiration-date-notification-date-weeks-description" />
			</p>

			<div>
				<aui:input helpMessage="expiration-date-notification-date-weeks-help" label="notification-date-weeks" min="0" name="expirationDateNotificationDateWeeks" type="number" value="<%= kbArticleCompanyConfigurationDisplayContext.getExpirationDateNotificationDateWeeks() %>" />
			</div>
		</clay:sheet-section>

		<clay:sheet-footer>
			<aui:button primary="<%= true %>" type="submit" />
		</clay:sheet-footer>
	</clay:sheet>
</aui:form>
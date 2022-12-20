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
<%--
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
LLMSGroupConfigurationDisplayContext llmsGroupConfigurationDisplayContext = (LLMSGroupConfigurationDisplayContext)request.getAttribute(LLMSGroupConfigurationDisplayContext.class.getName());
%>

<clay:content-row
	cssClass="c-mb-3"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<span>
			<liferay-ui:message key="llms-txt-description" />
		</span>
		<span>
			<clay:link
				href="https://www.llmstxt.org/"
				label='<%= LanguageUtil.format(request, "for-more-information,-visit-x", "www.llmstxt.org") %>'
				target="_blank"
			/>
		</span>
	</clay:content-col>
</clay:content-row>

<clay:sheet-section
	aria-labelledby='<%= liferayPortletResponse.getNamespace() + "llmsTxtContentTitle" %>'
	role="group"
>
	<clay:content-row
		containerElement="h3"
		cssClass="c-mb-3 sheet-subtitle"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<span class="heading-text text-secondary" id="<portlet:namespace />llmsTxtContentTitle"><liferay-ui:message key="llms-txt-content" /></span>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<clay:checkbox
				checked="<%= llmsGroupConfigurationDisplayContext.isEnabled() %>"
				id='<%= liferayPortletResponse.getNamespace() + "enabled" %>'
				label='<%= LanguageUtil.get(request, "enable-llms-txt") %>'
				name='<%= liferayPortletResponse.getNamespace() + "enabled" %>'
			/>
		</clay:content-col>
	</clay:content-row>

	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<p class="c-mb-3 small text-secondary"><liferay-ui:message key="llms-txt-content-help" /></p>

			<aui:input aria-label='<%= LanguageUtil.get(request, "llms-txt-content") %>' label="" name="content" type="textarea" value="<%= llmsGroupConfigurationDisplayContext.getContent() %>" />
		</clay:content-col>
	</clay:content-row>
</clay:sheet-section>
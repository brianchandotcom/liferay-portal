<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AICreatorOpenAICompanyConfigurationDisplayContext aiCreatorOpenAICompanyConfigurationDisplayContext = (AICreatorOpenAICompanyConfigurationDisplayContext)request.getAttribute(AICreatorOpenAICompanyConfigurationDisplayContext.class.getName());
%>

<clay:content-row
	cssClass="c-mt-5"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<clay:checkbox
			checked="<%= aiCreatorOpenAICompanyConfigurationDisplayContext.isEnabled() %>"
			id='<%= liferayPortletResponse.getNamespace() + "enableOpenAI" %>'
			label='<%= LanguageUtil.get(request, "enable-openai-to-create-content") %>'
			name='<%= liferayPortletResponse.getNamespace() + "enableOpenAI" %>'
		/>
	</clay:content-col>
</clay:content-row>

<clay:content-row
	cssClass="c-mt-2"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<aui:input label="api-key" name="apiKey" type="text" value="<%= aiCreatorOpenAICompanyConfigurationDisplayContext.getAPIKey() %>" />
	</clay:content-col>
</clay:content-row>

<clay:content-row
	cssClass="c-mb-5"
>
	<clay:content-col>
		<clay:link
			href="https://platform.openai.com/docs/api-reference/authentication"
			label="how-do-i-get-an-api-key"
			target="_blank"
		/>
	</clay:content-col>
</clay:content-row>

<%@ include file="/configuration/error_ai_creator_openai_client_exception.jspf" %>
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

<clay:content-row>
	<clay:content-col
		expand="<%= true %>"
	>
		<aui:input label="api-key" name="apiKey" type="text" value="<%= aiCreatorOpenAICompanyConfigurationDisplayContext.getApiKey() %>" />
	</clay:content-col>
</clay:content-row>

<clay:content-row
	cssClass="c-mb-5"
>
	<clay:content-col>
		<clay:link
			href="https://platform.openai.com/docs/api-reference/authentication"
			label="how-to-get-an-api-key"
			target="_blank"
		/>
	</clay:content-col>
</clay:content-row>
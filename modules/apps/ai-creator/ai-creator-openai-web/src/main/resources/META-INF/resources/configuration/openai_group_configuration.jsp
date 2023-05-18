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
AICreatorOpenAIGroupConfigurationDisplayContext aiCreatorOpenAIGroupConfigurationDisplayContext = (AICreatorOpenAIGroupConfigurationDisplayContext)request.getAttribute(AICreatorOpenAIGroupConfigurationDisplayContext.class.getName());
%>

<clay:content-row>
	<clay:content-col
		expand="<%= true %>"
	>
		<c:choose>
			<c:when test="<%= aiCreatorOpenAIGroupConfigurationDisplayContext.isCompanyEnabled() %>">
				<clay:checkbox
					checked="<%= aiCreatorOpenAIGroupConfigurationDisplayContext.isEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "enableOpenAI" %>'
					label='<%= LanguageUtil.get(request, "enable-openai-to-create-content") %>'
					name='<%= liferayPortletResponse.getNamespace() + "enableOpenAI" %>'
				/>
			</c:when>
			<c:otherwise>
				<clay:alert
					message='<%= LanguageUtil.get(request, "to-enable-openai-in-this-site,-it-must-also-be-enabled-from-instance-settings") %>'
				/>

				<clay:checkbox
					checked="<%= false %>"
					disabled="<%= true %>"
					id='<%= liferayPortletResponse.getNamespace() + "enableOpenAI" %>'
					label='<%= LanguageUtil.get(request, "enable-openai-to-create-content") %>'
					name='<%= liferayPortletResponse.getNamespace() + "enableOpenAI" %>'
				/>
			</c:otherwise>
		</c:choose>
	</clay:content-col>
</clay:content-row>

<clay:content-row
	cssClass="c-mt-2"
>
	<clay:content-col
		expand="<%= true %>"
	>
		<aui:input label="api-key" name="apiKey" type="text" value="<%= aiCreatorOpenAIGroupConfigurationDisplayContext.getApiKey() %>" />
	</clay:content-col>
</clay:content-row>

<clay:content-row>
	<clay:content-col>
		<clay:link
			href="https://platform.openai.com/docs/api-reference/authentication"
			label="how-to-get-an-api-key"
			target="_blank"
		/>
	</clay:content-col>
</clay:content-row>
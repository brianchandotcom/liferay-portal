<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					message="to-enable-openai-in-this-site,-it-must-also-be-enabled-from-instance-settings"
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
		<aui:input label="api-key" name="apiKey" type="text" value="<%= aiCreatorOpenAIGroupConfigurationDisplayContext.getAPIKey() %>" />
	</clay:content-col>
</clay:content-row>

<clay:content-row>
	<clay:content-col>
		<clay:link
			href="https://platform.openai.com/docs/api-reference/authentication"
			label="how-do-i-get-an-api-key"
			target="_blank"
		/>
	</clay:content-col>
</clay:content-row>

<%@ include file="/configuration/error_ai_creator_openai_client_exception.jspf" %>
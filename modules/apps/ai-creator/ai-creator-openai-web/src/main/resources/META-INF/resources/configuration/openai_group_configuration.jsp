<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
AICreatorOpenAIGroupConfigurationDisplayContext aiCreatorOpenAIGroupConfigurationDisplayContext = (AICreatorOpenAIGroupConfigurationDisplayContext)request.getAttribute(AICreatorOpenAIGroupConfigurationDisplayContext.class.getName());

boolean companyChatGTPEnabled = aiCreatorOpenAIGroupConfigurationDisplayContext.isCompanyChatGTPEnabled();
boolean companyDALLEEnabled = aiCreatorOpenAIGroupConfigurationDisplayContext.isCompanyDALLEEnabled();
%>

<clay:content-row>
	<clay:content-col
		expand="<%= true %>"
	>
		<c:choose>
			<c:when test="<%= !companyChatGTPEnabled && !companyDALLEEnabled %>">
				<clay:alert
					message="to-enable-openai-in-this-site,-it-must-also-be-enabled-from-instance-settings"
				/>
			</c:when>
			<c:when test="<%= !companyChatGTPEnabled && companyDALLEEnabled %>">
				<clay:alert
					message="to-enable-chatgpt-for-this-site,-first-enable-it-for-your-instance"
				/>
			</c:when>
			<c:when test='<%= FeatureFlagManagerUtil.isEnabled("LPS-196648") && companyChatGTPEnabled && !companyDALLEEnabled %>'>
				<clay:alert
					message="to-enable-dalle-for-this-site,-first-enable-it-for-your-instance"
				/>
			</c:when>
		</c:choose>
	</clay:content-col>
</clay:content-row>

<clay:content-row>
	<clay:content-col>
		<span>
			<liferay-ui:message key="set-the-api-key-for-authentication" />

			<clay:link
				href="https://platform.openai.com/docs/api-reference/authentication"
				label="how-do-i-get-an-api-key"
				target="_blank"
			/>
		</span>
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
	<clay:content-col
		expand="<%= true %>"
	>
		<c:choose>
			<c:when test="<%= companyChatGTPEnabled %>">
				<clay:checkbox
					checked="<%= aiCreatorOpenAIGroupConfigurationDisplayContext.isChatGTPEnabled() %>"
					id='<%= liferayPortletResponse.getNamespace() + "enableChatGTP" %>'
					label='<%= LanguageUtil.get(request, "enable-chatgtp-to-create-content") %>'
					name='<%= liferayPortletResponse.getNamespace() + "enableChatGTP" %>'
				/>
			</c:when>
			<c:otherwise>
				<clay:checkbox
					checked="<%= false %>"
					disabled="<%= true %>"
					id='<%= liferayPortletResponse.getNamespace() + "enableChatGTP" %>'
					label='<%= LanguageUtil.get(request, "enable-chatgtp-to-create-content") %>'
					name='<%= liferayPortletResponse.getNamespace() + "enableChatGTP" %>'
				/>
			</c:otherwise>
		</c:choose>
	</clay:content-col>
</clay:content-row>

<c:if test='<%= FeatureFlagManagerUtil.isEnabled("LPS-196648") %>'>
	<clay:content-row
		cssClass="c-mt-2"
	>
		<clay:content-col
			expand="<%= true %>"
		>
			<c:choose>
				<c:when test="<%= companyDALLEEnabled %>">
					<clay:checkbox
						checked="<%= aiCreatorOpenAIGroupConfigurationDisplayContext.isDALLEEnabled() %>"
						id='<%= liferayPortletResponse.getNamespace() + "enableDALLE" %>'
						label='<%= LanguageUtil.get(request, "enable-dalle-to-create-images") %>'
						name='<%= liferayPortletResponse.getNamespace() + "enableDALLE" %>'
					/>
				</c:when>
				<c:otherwise>
					<clay:checkbox
						checked="<%= false %>"
						disabled="<%= true %>"
						id='<%= liferayPortletResponse.getNamespace() + "enableDALLE" %>'
						label='<%= LanguageUtil.get(request, "enable-dalle-to-create-images") %>'
						name='<%= liferayPortletResponse.getNamespace() + "enableDALLE" %>'
					/>
				</c:otherwise>
			</c:choose>
		</clay:content-col>
	</clay:content-row>
</c:if>

<%@ include file="/configuration/error_ai_creator_openai_client_exception.jspf" %>
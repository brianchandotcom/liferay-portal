<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */
--%>

<%@ include file="/init.jsp" %>

<%
MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration = (MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration)request.getAttribute(MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<aui:input name="<%= ActionRequest.ACTION_NAME %>" type="hidden" value="/portal_settings/document_library_asset_auto_tagger_microsoft_cognitive_services" />

<aui:input id="enabled" label="enabled" name='<%= PortalSettingsMicrosoftCognitiveServicesAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "enabled" %>' type="checkbox" value="<%= MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration.enabled() %>" />

<aui:input helpMessage='<%= LanguageUtil.format(resourceBundle, "api-key-description", new String[] {MicrosoftCognitiveServicesAssetAutoTagProviderConstants.API_KEY_DOCS_URL}, false) %>' id="api-key" label="api-key" name='<%= PortalSettingsMicrosoftCognitiveServicesAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "apiKey" %>' value="<%= MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration.apiKey() %>" />

<aui:input helpMessage='<%= LanguageUtil.format(resourceBundle, "api-endpoint-description", new String[] {MicrosoftCognitiveServicesAssetAutoTagProviderConstants.SAMPLE_API_ENDPOINT, MicrosoftCognitiveServicesAssetAutoTagProviderConstants.API_KEY_DOCS_URL}, false) %>' id="api-endpoint" label="api-endpoint" name='<%= PortalSettingsMicrosoftCognitiveServicesAssetAutoTagProviderConstants.FORM_PARAMETER_NAMESPACE + "apiEndpoint" %>' value="<%= MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration.apiEndpoint() %>" />
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
String backURL = ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()));

ViewNotificationTemplatesDisplayContext viewNotificationTemplatesDisplayContext = (ViewNotificationTemplatesDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);

NotificationTemplate notificationTemplate = viewNotificationTemplatesDisplayContext.getNotificationTemplate();

String externalReferenceCode = StringPool.BLANK;
long notificationTemplateId = 0;

if (notificationTemplate != null) {
	externalReferenceCode = notificationTemplate.getExternalReferenceCode();
	notificationTemplateId = notificationTemplate.getNotificationTemplateId();
}

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(backURL);

renderResponse.setTitle(LanguageUtil.get(request, "notification-template"));
%>

<liferay-portlet:resourceURL copyCurrentRenderParameters="<%= false %>" var="baseResourceURL" />

<react:component
	module="{EditNotificationTemplate} from notification-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"backURL", ParamUtil.getString(request, "backURL", String.valueOf(renderResponse.createRenderURL()))
		).put(
			"baseResourceURL", String.valueOf(baseResourceURL)
		).put(
			"editorConfig", viewNotificationTemplatesDisplayContext.getEditorConfig()
		).put(
			"externalReferenceCode", externalReferenceCode
		).put(
			"learnResources", LearnMessageUtil.getReactDataJSONObject("notification-web")
		).put(
			"notificationTemplateId", notificationTemplateId
		).put(
			"notificationTemplateType", viewNotificationTemplatesDisplayContext.getNotificationTemplateType()
		).put(
			"portletNamespace", liferayPortletResponse.getNamespace()
		).build()
	%>'
/>
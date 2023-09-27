<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
EditConfigurationDisplayContext editConfigurationDisplayContext = (EditConfigurationDisplayContext)request.getAttribute(EditConfigurationDisplayContext.class.getName());
%>

<c:if test="<%= !editConfigurationDisplayContext.isDownloaded() %>">
	<clay:alert
		dismissible="<%= false %>"
		displayType='<%= editConfigurationDisplayContext.isDownloadFailed() ? "danger" : "info" %>'
	>
		<c:choose>
			<c:when test="<%= editConfigurationDisplayContext.isDownloadFailed() %>">
				<liferay-ui:message key="the-tensorflow-model-could-not-be-downloaded.-please-contact-your-administrator" />
			</c:when>
			<c:when test="<%= editConfigurationDisplayContext.isTensorFlowImageAssetAutoTagProviderEnabled() %>">
				<liferay-ui:message key="the-tensorflow-model-is-being-downloaded-in-the-background.-no-tags-will-be-created-until-the-model-is-fully-downloaded" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="the-tensorflow-model-will-be-downloaded-in-the-background.-no-tags-will-be-created-until-the-model-is-fully-downloaded" />
			</c:otherwise>
		</c:choose>
	</clay:alert>
</c:if>
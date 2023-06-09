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
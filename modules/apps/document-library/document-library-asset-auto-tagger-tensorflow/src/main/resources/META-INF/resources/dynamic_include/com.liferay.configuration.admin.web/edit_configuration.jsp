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
TensorFlowImageAssetAutoTagProviderCompanyConfiguration tensorFlowImageAssetAutoTagProviderCompanyConfiguration = (TensorFlowImageAssetAutoTagProviderCompanyConfiguration)request.getAttribute(TensorFlowImageAssetAutoTagProviderCompanyConfiguration.class.getName());
%>

<c:if test="<%= !InceptionModelUtil.isDownloaded() %>">
	<aui:alert closeable="<%= false %>" type='<%= InceptionModelUtil.isDownloadFailed() ? "danger" : "info" %>'>
		<c:choose>
			<c:when test="<%= InceptionModelUtil.isDownloadFailed() %>">
				<liferay-ui:message key="the-tensorflow-model-could-not-be-downloaded.-please-contact-your-administrator" />
			</c:when>
			<c:when test="<%= (tensorFlowImageAssetAutoTagProviderCompanyConfiguration != null) && tensorFlowImageAssetAutoTagProviderCompanyConfiguration.enabled() %>">
				<liferay-ui:message key="the-tensorflow-model-is-being-downloaded-in-the-background.-no-tags-will-be-created-until-the-model-is-fully-downloaded" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="the-tensorflow-model-will-be-downloaded-in-the-background.-no-tags-will-be-created-until-the-model-is-fully-downloaded" />
			</c:otherwise>
		</c:choose>
	</aui:alert>
</c:if>
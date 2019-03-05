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
List<AssetListProvider> assetListProviders = assetPublisherDisplayContext.getAssetListProviders();
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(assetListProviders) %>">
		<aui:select label="" name="preferences--assetListProviderClassName--">
			<aui:option label="none" value="" />

			<%
			for (AssetListProvider assetListProvider : assetListProviders) {
				Class<?> clazz = assetListProvider.getClass();
			%>

				<aui:option label="<%= assetListProvider.getLabel(themeDisplay.getLocale()) %>" value="<%= clazz.getName() %>" />

			<%
			}
			%>

		</aui:select>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="you-do-not-have-any-asset-list-provider" />
	</c:otherwise>
</c:choose>
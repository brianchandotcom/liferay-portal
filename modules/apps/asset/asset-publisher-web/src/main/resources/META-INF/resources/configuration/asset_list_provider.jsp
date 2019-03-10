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
List<InfoListProvider> infoListProviders = assetPublisherDisplayContext.getAssetInfoListProviders();
String curInfoListProviderClassName = PrefsParamUtil.getString(portletPreferences, request, "infoListProviderClassName", "");
%>

<c:choose>
	<c:when test="<%= ListUtil.isNotEmpty(infoListProviders) %>">
		<aui:select label="" name="preferences--infoListProviderClassName--">
			<aui:option label="none" value="" />

			<%
			for (InfoListProvider infoListProvider : infoListProviders) {
				Class<?> clazz = infoListProvider.getClass();
			%>

				<aui:option label="<%= infoListProvider.getLabel(themeDisplay.getLocale()) %>" value="<%= clazz.getName() %>" selected="<%= curInfoListProviderClassName.equals(clazz.getName()) %>"/>

			<%
			}
			%>

		</aui:select>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="you-do-not-have-any-asset-list-providers" />
	</c:otherwise>
</c:choose>
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
String className = (String)request.getAttribute("liferay-friendly-url:history:className");
long classPK = (long)request.getAttribute("liferay-friendly-url:history:classPK");
String elementId = (String)request.getAttribute("liferay-friendly-url:history:elementId");
%>

<div class="btn-url-history-wrapper">

	<%
	User defaultUser = company.getDefaultUser();

	String friendlyURLEntryURL = StringBundler.concat(themeDisplay.getPortalURL(), Portal.PATH_MODULE, "/friendly-url/", className, StringPool.SLASH, classPK);
	%>

	<react:component
		module="js/FriendlyURLHistory"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLanguageId", LocaleUtil.toLanguageId(defaultUser.getLocale())
			).put(
				"elementId", elementId
			).put(
				"friendlyURLEntryURL", friendlyURLEntryURL
			).build()
		%>'
	/>
</div>
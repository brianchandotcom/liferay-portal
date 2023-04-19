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

<%@ include file="/logo_selector/init.jsp" %>

<%
String defaultLogoURL = (String)request.getAttribute("liferay-frontend:logo-selector:defaultLogoURL");
String logoURL = (String)request.getAttribute("liferay-frontend:logo-selector:logoURL");
String randomNamespace = (String)request.getAttribute("liferay-frontend:logo-selector:randomNamespace");
String selectLogoURL = (String)request.getAttribute("liferay-frontend:logo-selector:selectLogoURL");

long fileEntryId = ParamUtil.getLong(request, "fileEntryId");
%>

<div>
	<react:component
		module="logo_selector/LogoSelector"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"defaultLogoURL", defaultLogoURL
			).put(
				"logoURL", logoURL
			).put(
				"selectLogoURL", selectLogoURL
			).build()
		%>'
	/>
</div>
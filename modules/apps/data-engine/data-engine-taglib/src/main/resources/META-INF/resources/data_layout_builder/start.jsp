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

<%@ include file="/data_layout_builder/init.jsp" %>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, "/o/dynamic-data-mapping-form-builder/css/main.css") %>" rel="stylesheet" type="text/css" />
</liferay-util:html-top>

<div class="container-fluid-1280 ddm-translation-manager">
	<liferay-frontend:translation-manager
		availableLocales="<%= availableLocales %>"
		changeableDefaultLanguage="<%= false %>"
		defaultLanguageId="<%= themeDisplay.getLanguageId() %>"
		id="translationManager"
	/>
</div>
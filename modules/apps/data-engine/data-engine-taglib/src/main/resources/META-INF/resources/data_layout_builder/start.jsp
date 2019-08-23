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

<liferay-editor:resources
	editorName="alloyeditor"
/>

<liferay-util:html-top>
	<link href="<%= PortalUtil.getStaticResourceURL(request, PortalUtil.getPathModule() + "/dynamic-data-mapping-form-renderer/css/main.css") %>" rel="stylesheet" />
</liferay-util:html-top>

<liferay-util:dynamic-include key="com.liferay.data.engine.taglib#/data_layout_builder/start.jsp#pre" />

<c:if test="<%= localizable %>">
	<div class="container-fluid-1280 ddm-translation-manager">
		<liferay-frontend:translation-manager
			availableLocales="<%= availableLocales %>"
			changeableDefaultLanguage="<%= false %>"
			defaultLanguageId="<%= themeDisplay.getLanguageId() %>"
			id="translationManager"
		/>
	</div>
</c:if>

<liferay-util:dynamic-include key="com.liferay.data.engine.taglib#/data_layout_builder/start.jsp#post" />
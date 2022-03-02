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
ObjectDefinitionsLayoutsDisplayContext objectDefinitionsLayoutsDisplayContext = (ObjectDefinitionsLayoutsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectLayout objectLayout = (ObjectLayout)request.getAttribute(ObjectWebKeys.OBJECT_LAYOUT);
%>

<liferay-frontend:side-panel-content
	title='<%= LanguageUtil.get(request, "layout") %>'
>
	<react:component
		module="js/components/Layout/index"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"isViewOnly", !objectDefinitionsLayoutsDisplayContext.hasUpdateObjectDefinitionPermission()
			).put(
				"objectFieldTypes", objectDefinitionsLayoutsDisplayContext.getObjectFieldBusinessTypeMaps(locale)
			).put(
				"objectLayoutId", objectLayout.getObjectLayoutId()
			).build()
		%>'
	/>
</liferay-frontend:side-panel-content>
<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinitionsLayoutsDisplayContext objectDefinitionsLayoutsDisplayContext = (ObjectDefinitionsLayoutsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectLayout objectLayout = (ObjectLayout)request.getAttribute(ObjectWebKeys.OBJECT_LAYOUT);
%>

<react:component
	module="{Layout} from object-web"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"isViewOnly", !objectDefinitionsLayoutsDisplayContext.hasUpdateObjectDefinitionPermission()
		).put(
			"objectFieldBusinessTypes", objectDefinitionsLayoutsDisplayContext.getObjectFieldBusinessTypeMaps(locale)
		).put(
			"objectLayoutId", objectLayout.getObjectLayoutId()
		).build()
	%>'
/>
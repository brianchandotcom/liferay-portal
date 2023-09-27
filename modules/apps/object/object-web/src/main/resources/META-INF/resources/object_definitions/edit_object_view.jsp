<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinition objectDefinition = (ObjectDefinition)request.getAttribute(ObjectWebKeys.OBJECT_DEFINITION);
ObjectDefinitionsViewsDisplayContext objectDefinitionsViewsDisplayContext = (ObjectDefinitionsViewsDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
ObjectView objectView = (ObjectView)request.getAttribute(ObjectWebKeys.OBJECT_VIEW);
%>

<react:component
	module="js/components/ObjectView/index"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"filterOperators", LocalizedJSONArrayUtil.getFilterOperatorsJSONObject(locale)
		).put(
			"isViewOnly", !objectDefinitionsViewsDisplayContext.hasUpdateObjectDefinitionPermission()
		).put(
			"objectDefinitionExternalReferenceCode", objectDefinition.getExternalReferenceCode()
		).put(
			"objectViewId", objectView.getObjectViewId()
		).put(
			"workflowStatusJSONArray", LocalizedJSONArrayUtil.getWorkflowStatusJSONArray(locale)
		).build()
	%>'
/>
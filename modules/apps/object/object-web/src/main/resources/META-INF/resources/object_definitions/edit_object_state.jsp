<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ObjectDefinitionsStateManagerDisplayContext objectDefinitionsStateManagerDisplayContext = (ObjectDefinitionsStateManagerDisplayContext)request.getAttribute(WebKeys.PORTLET_DISPLAY_CONTEXT);
%>

<react:component
	module="js/components/EditObjectStateField"
	props='<%=
		HashMapBuilder.<String, Object>put(
			"objectField", objectDefinitionsStateManagerDisplayContext.getObjectFieldJSONObject((ObjectField)request.getAttribute(ObjectWebKeys.OBJECT_FIELD))
		).build()
	%>'
/>
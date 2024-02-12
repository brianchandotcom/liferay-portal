<%--
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/resource_selector/init.jsp" %>

<%
String inputLabel = (String)request.getAttribute("liferay-frontend:resource-selector:inputLabel");
String inputName = (String)request.getAttribute("liferay-frontend:resource-selector:inputName");
String modalTitle = (String)request.getAttribute("liferay-frontend:resource-selector:modalTitle");
String resourceName = (String)request.getAttribute("liferay-frontend:resource-selector:resourceName");
String resourceNameKey = (String)request.getAttribute("liferay-frontend:resource-selector:resourceNameKey");
String resourceValue = (String)request.getAttribute("liferay-frontend:resource-selector:resourceValue");
String resourceValueKey = (String)request.getAttribute("liferay-frontend:resource-selector:resourceValueKey");
String selectEventName = (String)request.getAttribute("liferay-frontend:resource-selector:selectEventName");
String selectResourceURL = (String)request.getAttribute("liferay-frontend:resource-selector:selectResourceURL");
boolean showRemoveButton = (boolean)request.getAttribute("liferay-frontend:resource-selector:showRemoveButton");
String warningMessage = (String)request.getAttribute("liferay-frontend:resource-selector:warningMessage");
%>

<div>
	<span aria-hidden="true" class="loading-animation"></span>

	<react:component
		module="{ResourceSelector} from frontend-taglib"
		props='<%=
			HashMapBuilder.<String, Object>put(
				"inputLabel", inputLabel
			).put(
				"inputName", inputName
			).put(
				"modalTitle", modalTitle
			).put(
				"resourceName", resourceName
			).put(
				"resourceNameKey", resourceNameKey
			).put(
				"resourceValue", resourceValue
			).put(
				"resourceValueKey", resourceValueKey
			).put(
				"selectEventName", selectEventName
			).put(
				"selectResourceURL", selectResourceURL
			).put(
				"showRemoveButton", showRemoveButton
			).put(
				"warningMessage", warningMessage
			).build()
		%>'
	/>
</div>
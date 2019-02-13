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

<%@ include file="/asset_tags_selector/init.jsp" %>

<%
Map<String, Object> context = (Map<String, Object>)request.getAttribute("liferay-asset:asset-tags-selector:context");
String inputName = (String)request.getAttribute("liferay-asset:asset-tags-selector:inputName");
String tagNames = (String)request.getAttribute("liferay-asset:asset-tags-selector:tagNames");
%>

<h4>
	<liferay-ui:message key="tags" />
</h4>

<input id="<%= inputName %>" type="hidden" value="<%= tagNames %>" />

<soy:component-renderer
	context="<%= context %>"
	module="asset_tags_selector/js/TagSelector.es"
	templateNamespace="com.liferay.asset.taglib.TagSelector.render"
/>
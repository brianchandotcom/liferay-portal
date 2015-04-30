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
Group liveGroup = (Group)request.getAttribute("site.liveGroup");
%>

<liferay-ui:error-marker key="errorSection" value="categorization" />

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<h3><liferay-ui:message key="categorization" /></h3>

<liferay-ui:asset-categories-error />

<liferay-ui:asset-tags-error />

<aui:fieldset>
	<aui:input name="categories" type="assetCategories" />

	<aui:input name="tags" type="assetTags" />
</aui:fieldset>

<aui:script>
	function <portlet:namespace />getSuggestionsContent() {
		return document.<portlet:namespace />fm.<portlet:namespace />description.value;
	}
</aui:script>
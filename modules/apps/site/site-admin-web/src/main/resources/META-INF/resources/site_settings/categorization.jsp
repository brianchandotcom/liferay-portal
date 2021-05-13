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

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<liferay-asset:asset-categories-error />

<liferay-asset:asset-tags-error />

<h4 class="sheet-subtitle"><liferay-ui:message key="categories" /></h4>

<liferay-asset:asset-categories-selector
	className="<%= Group.class.getName() %>"
	classPK="<%= liveGroup.getGroupId() %>"
	visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
/>

<h4 class="sheet-subtitle"><liferay-ui:message key="tags" /></h4>

<liferay-asset:asset-tags-selector
	className="<%= Group.class.getName() %>"
	classPK="<%= liveGroup.getGroupId() %>"
/>
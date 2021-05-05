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
Group siteGroup = themeDisplay.getSiteGroup();

Group liveGroup = null;

if (siteGroup.isStagingGroup()) {
	liveGroup = siteGroup.getLiveGroup();
}
else {
	liveGroup = siteGroup;
}
%>

<aui:model-context bean="<%= liveGroup %>" model="<%= Group.class %>" />

<liferay-asset:asset-categories-error />

<liferay-asset:asset-tags-error />

<liferay-asset:asset-categories-selector
	className="<%= Group.class.getName() %>"
	classPK="<%= liveGroup.getGroupId() %>"
	visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
/>

<liferay-asset:asset-tags-selector
	className="<%= Group.class.getName() %>"
	classPK="<%= liveGroup.getGroupId() %>"
/>
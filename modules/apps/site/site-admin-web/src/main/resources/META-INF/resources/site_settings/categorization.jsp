<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
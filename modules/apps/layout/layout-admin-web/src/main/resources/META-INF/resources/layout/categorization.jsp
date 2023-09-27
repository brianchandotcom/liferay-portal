<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<liferay-ui:error-marker
	key="<%= WebKeys.ERROR_SECTION %>"
	value="categorization"
/>

<aui:model-context bean="<%= selLayout %>" model="<%= Layout.class %>" />

<liferay-asset:asset-categories-error />

<liferay-asset:asset-tags-error />

<liferay-asset:asset-categories-selector
	className="<%= Layout.class.getName() %>"
	classPK="<%= (selLayout != null) ? selLayout.getPrimaryKey() : 0 %>"
	visibilityTypes="<%= AssetVocabularyConstants.VISIBILITY_TYPES %>"
/>

<liferay-asset:asset-tags-selector
	className="<%= Layout.class.getName() %>"
	classPK="<%= (selLayout != null) ? selLayout.getPrimaryKey() : 0 %>"
/>
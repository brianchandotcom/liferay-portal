<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
long categoryId = ParamUtil.getLong(request, "categoryId");
%>

<liferay-ui:success key="categoryAdded" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "categoryAdded")) %>' />
<liferay-ui:success key="categoryUpdated" message='<%= GetterUtil.getString(MultiSessionMessages.get(renderRequest, "categoryUpdated")) %>' />

<liferay-frontend:screen-navigation
	context="<%= AssetCategoryLocalServiceUtil.fetchCategory(categoryId) %>"
	key="<%= AssetCategoriesConstants.CATEGORY_KEY_GENERAL %>"
	portletURL="<%= currentURLObj %>"
/>
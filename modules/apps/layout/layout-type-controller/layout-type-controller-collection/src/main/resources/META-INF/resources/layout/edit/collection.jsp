<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/layout/edit/init.jsp" %>

<liferay-ui:success key="collectionLayoutAdded" message="the-collection-page-was-created-successfully.-next-customize-how-the-collection-is-displayed-by-dropping-fragments-to-the-collection-display-already-added-to-the-page" />

<liferay-ui:success key="layoutPublished" message="the-page-was-published-successfully" />

<div class="layout-content portlet-layout" id="main-content" role="main">
	<liferay-portlet:runtime
		portletName="<%= ContentPageEditorPortletKeys.CONTENT_PAGE_EDITOR_PORTLET %>"
	/>
</div>

<liferay-layout:layout-common
	displaySessionMessages="<%= true %>"
/>
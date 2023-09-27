<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/admin/init.jsp" %>

<%
DDMFormViewFormInstanceRecordsDisplayContext ddmFormViewFormInstanceRecordsDisplayContext = ddmFormAdminDisplayContext.getDDMFormViewFormInstanceRecordsDisplayContext();

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= ddmFormViewFormInstanceRecordsDisplayContext.getNavigationItems() %>"
/>

<liferay-util:include page="/admin/form_instance_records_search_container.jsp" servletContext="<%= application %>" />
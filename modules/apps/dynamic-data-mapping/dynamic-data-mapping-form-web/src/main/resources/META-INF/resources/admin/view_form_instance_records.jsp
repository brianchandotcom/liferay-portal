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

<%@ include file="/admin/init.jsp" %>

<%
DDMFormViewFormInstanceRecordsDisplayContext ddmFormViewFormInstanceRecordsDisplayContext = ddmFormAdminDisplayContext.getDDMFormViewFormInstanceRecordsDisplayContext();

renderResponse.setTitle(LanguageUtil.get(request, "form-entries"));
%>

<clay:navigation-bar
	inverted="<%= true %>"
	navigationItems="<%= ddmFormViewFormInstanceRecordsDisplayContext.getNavigationItems() %>"
/>

<clay:alert
	message='<%= LanguageUtil.get(resourceBundle, "view-current-fields-warning-message") %>'
	style="info"
	title='<%= LanguageUtil.get(resourceBundle, "info") %>'
/>

<liferay-util:include page="/admin/form_instance_records_search_container.jsp" servletContext="<%= application %>" />
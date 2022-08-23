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
ResultRow row = (ResultRow)request.getAttribute(KBWebKeys.SEARCH_CONTAINER_RESULT_ROW);

KBTemplate kbTemplate = (KBTemplate)row.getObject();

KBDropdownItemsProvider kbDropdownItemsProvider = new KBDropdownItemsProvider(liferayPortletRequest, liferayPortletResponse);
%>

<clay:dropdown-actions
	dropdownItems="<%= kbDropdownItemsProvider.getKBTemplateDropdownItems(kbTemplate) %>"
	propsTransformer="admin/js/KBTemplateDropdownPropsTransformer"
/>
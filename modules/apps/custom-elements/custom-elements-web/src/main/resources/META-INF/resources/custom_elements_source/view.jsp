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

<%@ include file="/custom_elements_source/init.jsp" %>

<%
CustomElementsDisplayContext customElementsDisplayContext = (CustomElementsDisplayContext)renderRequest.getAttribute(CustomElementsWebKeys.CUSTOM_ELEMENTS_DISPLAY_CONTEXT);
%>

<clay:data-set-display
	actionParameterName="customElementsSourceEntryId"
	creationMenu="<%= customElementsDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CustomElementsClayDataSetDisplayNames.CUSTOM_ELEMENT_SOURCES %>"
	formId='<%= liferayPortletResponse.getNamespace() + "fm" %>'
	id="<%= CustomElementsClayDataSetDisplayNames.CUSTOM_ELEMENT_SOURCES %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= customElementsDisplayContext.getCurrentPortletURL() %>"
	selectedItemsKey="customElementsSourceEntryId"
	selectionType="multiple"
	style="fluid"
/>
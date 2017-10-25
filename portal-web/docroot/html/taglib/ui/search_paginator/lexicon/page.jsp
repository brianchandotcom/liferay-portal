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

<%@ include file="/html/taglib/init.jsp" %>

<%
SearchContainer searchContainer = (SearchContainer)request.getAttribute("liferay-ui:search:searchContainer");

String id = (String)request.getAttribute("liferay-ui:search:id");

if (Validator.isNull(id) && (searchContainer != null)) {
	id = searchContainer.getId(request, namespace);

	id = id.concat("PageIterator");
}

String markupView = (String)request.getAttribute("liferay-ui:search:markupView");
String type = (String)request.getAttribute("liferay-ui:search:type");

PortletURL iteratorURL = searchContainer.getIteratorURL();

if (iteratorURL != null) {
	iteratorURL.setParameter(searchContainer.getCurParam(), (String)null);
	iteratorURL.setParameter("resetCur", Boolean.FALSE.toString());
}
%>

<c:if test="<%= searchContainer.getTotal() > 0 %>">
	<liferay-ui:page-iterator
		cur="<%= searchContainer.getCur() %>"
		curParam="<%= searchContainer.getCurParam() %>"
		delta="<%= searchContainer.getDelta() %>"
		deltaConfigurable="<%= searchContainer.isDeltaConfigurable() %>"
		deltaParam="<%= searchContainer.getDeltaParam() %>"
		forcePost="<%= searchContainer.isForcePost() %>"
		id="<%= id %>"
		markupView="<%= markupView %>"
		maxPages="<%= PropsValues.SEARCH_CONTAINER_PAGE_ITERATOR_MAX_PAGES %>"
		portletURL="<%= iteratorURL %>"
		total="<%= searchContainer.getTotal() %>"
		type="<%= type %>"
	/>
</c:if>
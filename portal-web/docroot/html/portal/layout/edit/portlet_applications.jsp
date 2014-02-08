<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<%
String copyLayoutIdPrefix = ParamUtil.getString(request, "copyLayoutIdPrefix");
%>

<aui:select id='<%= copyLayoutIdPrefix + "copyLayoutId" %>' label="copy-from-page" name="copyLayoutId" showEmptyOption="<%= true %>">

	<%
	List<LayoutDescription> layoutDescriptions = (List<LayoutDescription>)request.getAttribute(WebKeys.LAYOUT_LISTER_LIST);

	for (LayoutDescription layoutDescription : layoutDescriptions) {
		Layout copiableLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

		if (copiableLayout != null) {
	%>

			<aui:option disabled="<%= (selLayout != null) && selLayout.getPlid() == copiableLayout.getPlid() %>" label="<%= layoutDescription.getDisplayName() %>" value="<%= copiableLayout.getLayoutId() %>" />

	<%
		}
	}
	%>

</aui:select>
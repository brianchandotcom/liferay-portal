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

<aui:input name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.getGroupId() %>" />
<aui:input name="TypeSettingsProperties--privateLayout--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.isPrivateLayout() %>" />

<%
long linkToLayoutId = 0;

if (selLayout != null) {
	linkToLayoutId = GetterUtil.getLong(selLayout.getTypeSettingsProperty("linkToLayoutId"));
}
%>

<aui:select label="link-to-layout" name="TypeSettingsProperties--linkToLayoutId--" showEmptyOption="<%= true %>">

	<%
	List layoutList = (List)request.getAttribute(WebKeys.LAYOUT_LISTER_LIST);

	for (int i = 0; i < layoutList.size(); i++) {

		// id | parentId | ls | obj id | name | img | depth

		String layoutDesc = (String)layoutList.get(i);

		String[] nodeValues = StringUtil.split(layoutDesc, '|');

		long objId = GetterUtil.getLong(nodeValues[3]);
		String name = nodeValues[4];

		int depth = 0;

		if (i != 0) {
			depth = GetterUtil.getInteger(nodeValues[6]);
		}

		name = HtmlUtil.escape(name);

		for (int j = 0; j < depth; j++) {
			name = "-&nbsp;" + name;
		}

		Layout linkableLayout = LayoutLocalServiceUtil.fetchLayout(objId);

		if (linkableLayout != null) {
	%>

			<aui:option disabled="<%= (selLayout != null) && (selLayout.getPlid() == linkableLayout.getPlid()) %>" label="<%= name %>" selected="<%= (linkToLayoutId == linkableLayout.getLayoutId()) %>" value="<%= linkableLayout.getLayoutId() %>" />

	<%
		}
	}
	%>

</aui:select>
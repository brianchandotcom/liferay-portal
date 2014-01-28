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
	List<LayoutDescription> layoutDescriptions = (List<LayoutDescription>)request.getAttribute(WebKeys.LAYOUT_LISTER_LIST);

	for (LayoutDescription layoutDescription : layoutDescriptions) {
		Layout linkableLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

		if (linkableLayout != null) {
	%>

			<aui:option disabled="<%= (selLayout != null) && (selLayout.getPlid() == linkableLayout.getPlid()) %>" label="<%= layoutDescription.getDisplayName() %>" selected="<%= (linkToLayoutId == linkableLayout.getLayoutId()) %>" value="<%= linkableLayout.getLayoutId() %>" />

	<%
		}
	}
	%>

</aui:select>
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

<%@ include file="/html/portal/layout/edit/init.jsp" %>

<aui:input name="TypeSettingsProperties--groupId--" type="hidden" value="<%= (selLayout == null) ? StringPool.BLANK : selLayout.getGroupId() %>" />

<%
long linkToLayoutId = 0;

if (selLayout != null) {
	linkToLayoutId = GetterUtil.getLong(selLayout.getTypeSettingsProperty("linkToLayoutId"));
}
%>

<aui:select label="link-to-layout" name="TypeSettingsProperties--linkToLayoutId--" showEmptyOption="<%= true %>">

	<%
	List<LayoutDescription> layoutDescriptions = (List<LayoutDescription>)request.getAttribute(WebKeys.LAYOUT_DESCRIPTIONS);

	for (LayoutDescription layoutDescription : layoutDescriptions) {
		Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

		if (layoutDescriptionLayout != null) {
	%>

			<aui:option disabled="<%= (selLayout != null) && (selLayout.getPlid() == layoutDescriptionLayout.getPlid()) %>" label="<%= layoutDescription.getDisplayName() %>" selected="<%= (linkToLayoutId == layoutDescriptionLayout.getLayoutId()) %>" value="<%= layoutDescriptionLayout.getLayoutId() %>" />

	<%
		}
	}
	%>

</aui:select>
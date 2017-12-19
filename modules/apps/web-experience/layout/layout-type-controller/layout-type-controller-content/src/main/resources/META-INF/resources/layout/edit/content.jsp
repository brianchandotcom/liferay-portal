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

<%@ include file="/layout/edit/init.jsp" %>

<%
long selectedLayoutPageTemplateEntry = 0;

if (selLayout != null) {
	selectedLayoutPageTemplateEntry = GetterUtil.getLong(selLayout.getTypeSettingsProperty("layoutPageTemplateEntryId"));
}
%>

<aui:select label="page-template" name="TypeSettingsProperties--layoutPageTemplateEntryId--">

	<%
	List<LayoutPageTemplateCollection> layoutPageTemplateCollections = LayoutPageTemplateCollectionLocalServiceUtil.getLayoutPageTemplateCollections(scopeGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

	for (LayoutPageTemplateCollection layoutPageTemplateCollection : layoutPageTemplateCollections) {
		List<LayoutPageTemplateEntry> layoutPageTemplateEntries = LayoutPageTemplateEntryLocalServiceUtil.getLayoutPageTemplateEntries(scopeGroupId, layoutPageTemplateCollection.getLayoutPageTemplateCollectionId());

		if (ListUtil.isNotEmpty(layoutPageTemplateEntries)) {
	%>

			<optgroup label="<%= layoutPageTemplateCollection.getName() %>">

				<%
				for (LayoutPageTemplateEntry layoutPageTemplateEntry : layoutPageTemplateEntries) {
				%>

					<aui:option label="<%= layoutPageTemplateEntry.getName() %>" selected="<%= selectedLayoutPageTemplateEntry == layoutPageTemplateEntry.getLayoutPageTemplateEntryId() %>" value="<%= layoutPageTemplateEntry.getLayoutPageTemplateEntryId() %>" />

				<%
				}
				%>

			</optgroup>

	<%
		}
	}
	%>

</aui:select>
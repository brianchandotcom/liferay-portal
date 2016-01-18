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

<%@ include file="/init.jsp" %>

<div class="tree-container tree-pages">
	<li class="tree-node">

		<%
		boolean selected = false;

		Layout selLayout = LayoutLocalServiceUtil.fetchFirstLayout(themeDisplay.getScopeGroupId(), true, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		if ((selLayout.getPlid() == plid) || Validator.equals(LayoutAdminPortletKeys.LAYOUT_PROTOTYPE_PAGE, themeDisplay.getPpid())) {
			selected = true;
		}
		%>

		<div class="tree-node-content <%= selected ? "tree-node-selected" : StringPool.BLANK %>">
			<span class="tree-label">

				<%
				Map<String, Object> data = new HashMap<String, Object>();

				data.put("url", selLayout.getFriendlyURL(locale));
				data.put("uuid", selLayout.getUuid());
				%>

				<aui:a cssClass="layout-tree" data="<%= data %>" href="<%= selLayout.getRegularURL(request) %>" label="<%= selLayout.getName(locale) %>" />

				<%
				PortletURL editLayoutURL = PortalUtil.getControlPanelPortletURL(request, LayoutAdminPortletKeys.LAYOUT_PROTOTYPE_PAGE, PortletRequest.RENDER_PHASE);

				editLayoutURL.setParameter("groupId", String.valueOf(themeDisplay.getScopeGroupId()));
				editLayoutURL.setParameter("selPlid", String.valueOf(selLayout.getPlid()));
				editLayoutURL.setParameter("privateLayout", Boolean.TRUE.toString());
				editLayoutURL.setParameter("viewLayout", Boolean.TRUE.toString());
				%>

				<liferay-ui:icon
					cssClass="layout-tree-edit"
					icon="cog"
					label="<%= false %>"
					markupView="lexicon"
					message='<%= LanguageUtil.format(request, "edit-x", selLayout.getName(locale)) %>'
					url="<%= editLayoutURL.toString() %>"
				/>
			</span>
		</div>
	</li>
</div>
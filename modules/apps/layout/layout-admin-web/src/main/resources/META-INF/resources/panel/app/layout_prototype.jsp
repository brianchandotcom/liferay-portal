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

<%
LayoutsPrototypeTreeDisplayContext layoutsTreeDisplayContext = new LayoutsPrototypeTreeDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<div class="tree-container tree-pages">
	<li class="tree-node">
		<div class="tree-node-content <%= layoutsTreeDisplayContext.isLayoutSelected() ? "tree-node-selected" : StringPool.BLANK %>">
			<span class="tree-label">
				<aui:a cssClass="layout-tree" href="<%= layoutsTreeDisplayContext.getLayoutURL() %>" label="<%= layoutsTreeDisplayContext.getLayoutName() %>" />

				<a class="layout-tree-edit" href="<%= layoutsTreeDisplayContext.getEditLayoutURL() %>" onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="edit" unicode="<%= true %>" />')"><aui:icon image="cog" markupView="lexicon" /><span class="hide-accessible"><liferay-ui:message arguments="<%= layoutsTreeDisplayContext.getLayoutName() %>" key="edit-x" /></span></a>
			</span>
		</div>
	</li>
</div>
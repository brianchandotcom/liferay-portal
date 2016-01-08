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
LayoutsTreeDisplayContext layoutsTreeDisplayContext = new LayoutsTreeDisplayContext(liferayPortletRequest, liferayPortletResponse);
%>

<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutSetBranchesSelector() %>">
	<ul class="nav nav-equal-height nav-nested">
		<li>
			<div class="nav-equal-height-heading">
				<span><%= HtmlUtil.escape(layoutsTreeDisplayContext.getLayoutSetBranchName()) %></span>

				<span class="nav-equal-height-heading-field">
					<liferay-ui:icon-menu direction="down" icon="cog" markupView="lexicon" message="" showArrow="<%= false %>">

						<%
						for (LayoutSetBranch curLayoutSetBranch : layoutsTreeDisplayContext.getLayoutSetBranches()) {
						%>

							<liferay-ui:icon
								cssClass="<%= layoutsTreeDisplayContext.getLayoutSetBranchCssClass(curLayoutSetBranch) %>"
								data="<%= layoutsTreeDisplayContext.getLayoutSetBranchURLData() %>"
								message="<%= HtmlUtil.escape(curLayoutSetBranch.getName()) %>"
								url="<%= layoutsTreeDisplayContext.getLayoutSetBranchURL(curLayoutSetBranch) %>"
							/>

						<%
						}
						%>

					</liferay-ui:icon-menu>
				</span>
			</div>
		</li>
	</ul>
</c:if>

<liferay-util:buffer var="linkTemplate">
	<a class="{cssClass}" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{regularURL}" id="{id}" title="{label}">{label}</a>

	<a class="layout-tree-add" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{addLayoutURL}" id="{id}" title="<liferay-ui:message arguments="{label}" key="add-child-page" />"><aui:icon image="plus" markupView="lexicon" /></a>

	<a class="layout-tree-edit" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{editLayoutURL}" id="{id}" title="<liferay-ui:message arguments="{label}" key="edit-x" />"><aui:icon image="cog" markupView="lexicon" /></a>
</liferay-util:buffer>

<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutTabs() %>">
	<div class="layout-set-tabs">
		<c:if test="<%= layoutsTreeDisplayContext.isShowPublicLayoutsTree() %>">
			<span class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? StringPool.BLANK : "selected-layout-set" %>">
				<aui:a cssClass="layout-set-link" href="<%= layoutsTreeDisplayContext.getPublicLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getLayoutSetName(false) %>" />

				<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">
					<a class="layout-set-tree-add" href="<%= layoutsTreeDisplayContext.getAddLayoutURL(false, LayoutConstants.DEFAULT_PLID) %>" title="<liferay-ui:message key="add-page" />"><aui:icon image="plus" markupView="lexicon" /></a>
				</c:if>

				<c:if test="<%= layoutsTreeDisplayContext.isShowEditLayoutSetButton() %>">
					<a class="layout-set-tree-edit" href="<%= layoutsTreeDisplayContext.getEditLayoutURL(false, LayoutConstants.DEFAULT_PLID) %>" title="<liferay-ui:message arguments="<%= layoutsTreeDisplayContext.getLayoutSetName(false) %>" key="edit-x" />"><aui:icon image="cog" markupView="lexicon" /></a>
				</c:if>
			</span>
		</c:if>

		<span class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? "selected-layout-set" : StringPool.BLANK %>">
			<aui:a cssClass="layout-set-link" href="<%= layoutsTreeDisplayContext.getPrivateLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getLayoutSetName(true) %>" />

			<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">
				<a class="layout-set-tree-add" href="<%= layoutsTreeDisplayContext.getAddLayoutURL(true, LayoutConstants.DEFAULT_PLID) %>" title="<liferay-ui:message key="add-page" />"><aui:icon image="plus" markupView="lexicon" /></a>
			</c:if>

			<c:if test="<%= layoutsTreeDisplayContext.isShowEditLayoutSetButton() %>">
				<a class="layout-set-tree-edit" href="<%= layoutsTreeDisplayContext.getEditLayoutURL(true, LayoutConstants.DEFAULT_PLID) %>" title="<liferay-ui:message arguments="<%= layoutsTreeDisplayContext.getLayoutSetName(true) %>" key="edit-x" />"><aui:icon image="cog" markupView="lexicon" /></a>
			</c:if>
		</span>
	</div>
</c:if>

<liferay-layout:layouts-tree
	expandFirstNode="<%= true %>"
	groupId="<%= layoutsTreeDisplayContext.getSelGroupId() %>"
	linkTemplate="<%= linkTemplate %>"
	portletURLs="<%= layoutsTreeDisplayContext.getPortletURLs() %>"
	privateLayout="<%= layoutsTreeDisplayContext.isPrivateLayout() %>"
	rootNodeName="<%= StringPool.BLANK %>"
	selPlid="<%= layoutsTreeDisplayContext.getCurSelPlid() %>"
	treeId="layoutsTree"
/>
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

	<a class="layout-tree-add" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{addLayoutURL}" id="{id}" onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="add-child-page" unicode="<%= true %>" />')"><aui:icon image="plus" markupView="lexicon" /><span class="hide-accessible"><liferay-ui:message key="add-child-page" /></span></a>

	<a class="layout-tree-edit" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{editLayoutURL}" id="{id}" onmouseover="Liferay.Portal.ToolTip.show(this, '<%= layoutsTreeDisplayContext.getJSSafeEditLayoutTitle() %>')"><aui:icon image="cog" markupView="lexicon" /><span class="hide-accessible"><liferay-ui:message arguments="{label}" key="edit-x" /></span></a>
</liferay-util:buffer>

<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutTabs() %>">

	<%
	Map<String, Object> data = new HashMap<>();
	%>

	<div class="layout-set-tabs">
		<c:if test="<%= layoutsTreeDisplayContext.isShowPublicLayoutsTree() %>">
			<span class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? StringPool.BLANK : "selected-layout-set" %>">

				<%
				data.put("qa-id", "goToPublicPages");
				%>

				<aui:a cssClass="layout-set-link" data="<%= data %>" href="<%= layoutsTreeDisplayContext.getPublicLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getLayoutSetName(false) %>" />

				<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">

					<%
					PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, false);

					data.put("qa-id", "addPublicPage");
					%>

					<liferay-ui:icon
						data="<%= data %>"
						icon="plus"
						label="<%= false %>"
						linkCssClass="layout-set-tree-add"
						markupView="lexicon"
						message="add-page"
						url="<%= addLayoutURL.toString() %>"
					/>
				</c:if>

				<c:if test="<%= layoutsTreeDisplayContext.isShowEditLayoutSetButton() %>">

					<%
					PortletURL editLayoutURL = layoutsTreeDisplayContext.getEditLayoutURL(LayoutConstants.DEFAULT_PLID, false);

					data.put("qa-id", "editPublicPages");
					%>

					<liferay-ui:icon
						data="<%= data %>"
						icon="cog"
						label="<%= false %>"
						linkCssClass="layout-set-tree-edit"
						markupView="lexicon"
						message='<%= LanguageUtil.format(request, "edit-x", layoutsTreeDisplayContext.getLayoutSetName(false)) %>'
						url="<%= editLayoutURL.toString() %>"
					/>
				</c:if>
			</span>
		</c:if>

		<span class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? "selected-layout-set" : StringPool.BLANK %>">

			<%
			data.put("qa-id", "goToPrivatePages");
			%>

			<aui:a cssClass="layout-set-link" data="<%= data %>" href="<%= layoutsTreeDisplayContext.getPrivateLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getLayoutSetName(true) %>" />

			<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">

				<%
				PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, true);

				data.put("qa-id", "addPrivatePage");
				%>

				<liferay-ui:icon
					data="<%= data %>"
					icon="plus"
					label="<%= false %>"
					linkCssClass="layout-set-tree-add"
					markupView="lexicon"
					message="add-page"
					url="<%= addLayoutURL.toString() %>"
				/>
			</c:if>

			<c:if test="<%= layoutsTreeDisplayContext.isShowEditLayoutSetButton() %>">

				<%
				PortletURL editLayoutURL = layoutsTreeDisplayContext.getEditLayoutURL(LayoutConstants.DEFAULT_PLID, true);

				data.put("qa-id", "editPrivatePages");
				%>

				<liferay-ui:icon
					data="<%= data %>"
					icon="cog"
					label="<%= false %>"
					linkCssClass="layout-set-tree-edit"
					markupView="lexicon"
					message='<%= LanguageUtil.format(request, "edit-x", layoutsTreeDisplayContext.getLayoutSetName(true)) %>'
					url="<%= editLayoutURL.toString() %>"
				/>
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
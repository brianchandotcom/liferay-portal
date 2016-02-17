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

<div class="<portlet:namespace/>layoutsTreeContainer">
	<liferay-ui:icon
		cssClass="expand-pages-link icon-monospaced"
		icon="expand"
		id="expandPagesLink"
		label="<%= false %>"
		markupView="lexicon"
		message="expand"
		url="javascript:;"
	/>

	<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutTabs() %>">

		<%
		Map<String, Object> data = new HashMap<>();
		%>

		<div class="layout-set-tabs">
			<c:if test="<%= layoutsTreeDisplayContext.isShowPublicLayoutsTree() %>">
				<span class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? StringPool.BLANK : "selected-layout-set" %>">

					<%
					data.put("navigation", Boolean.TRUE.toString());
					data.put("qa-id", "goToPublicPages");
					%>

					<aui:a cssClass="layout-set-link" data="<%= data %>" href="<%= layoutsTreeDisplayContext.getPublicLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getRootNodeName(false) %>" />

					<c:if test="<%= !layoutsTreeDisplayContext.isPrivateLayout() %>">
						<span class="pages-options pull-right">
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
									message='<%= LanguageUtil.format(request, "edit-x", layoutsTreeDisplayContext.getRootNodeName(false)) %>'
									url="<%= editLayoutURL.toString() %>"
								/>
							</c:if>
						</span>
					</c:if>
				</span>
			</c:if>

			<c:if test="<%= layoutsTreeDisplayContext.isShowPrivateLayoutsTree() %>">
				<span class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? "selected-layout-set" : StringPool.BLANK %>">

					<%
					data.put("qa-id", "goToPrivatePages");
					%>

					<aui:a cssClass="layout-set-link" data="<%= data %>" href="<%= layoutsTreeDisplayContext.getPrivateLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getRootNodeName(true) %>" />

					<c:if test="<%= layoutsTreeDisplayContext.isPrivateLayout() %>">
						<span class="pages-options pull-right">
							<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">

								<%
								PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, true);

								data.put("navigation", Boolean.TRUE.toString());
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
									message='<%= LanguageUtil.format(request, "edit-x", layoutsTreeDisplayContext.getRootNodeName(true)) %>'
									url="<%= editLayoutURL.toString() %>"
								/>
							</c:if>
						</span>
					</c:if>
				</span>
			</c:if>
		</div>
	</c:if>

	<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutSetBranchesSelector() %>">
		<ul class="nav nav-equal-height nav-nested">
			<li>
				<div class="scope-selector">
					<span class="scope-name"><%= HtmlUtil.escape(layoutsTreeDisplayContext.getLayoutSetBranchName()) %></span>

					<span class="nav-equal-height-heading-field">
						<liferay-ui:icon-menu direction="right" icon="cog" markupView="lexicon" message="" showArrow="<%= false %>">

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

		<a class="layout-tree-add" data-parentable="{parentable}" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{addLayoutURL}" id="{id}Add" onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="add-child-page" unicode="<%= true %>" />')"><aui:icon image="plus" markupView="lexicon" /><span class="hide-accessible"><liferay-ui:message arguments="{label}" key="add-child-page-of-x" /></span></a>

		<a class="layout-tree-edit" data-plid="{plid}" data-updateable="{updateable}" data-url="{url}" data-uuid="{uuid}" href="{editLayoutURL}" id="{id}Edit" onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="edit" unicode="<%= true %>" />')"><aui:icon image="cog" markupView="lexicon" /><span class="hide-accessible"><liferay-ui:message arguments="{label}" key="edit-x" /></span></a>

		<a class="layout-tree-delete" data-deleteable="{deleteable}" data-plid="{plid}" data-url="{url}" data-uuid="{uuid}" href="{deleteLayoutURL}" id="{id}Delete" onmouseover="Liferay.Portal.ToolTip.show(this, '<liferay-ui:message key="delete" unicode="<%= true %>" />')"><aui:icon image="trash" markupView="lexicon" /><span class="hide-accessible"><liferay-ui:message arguments="{label}" key="delete-x" /></span></a>
	</liferay-util:buffer>

	<liferay-ui:success key="layoutAdded" message='<%= LanguageUtil.get(resourceBundle, "the-page-has-been-created-succesfully") %>' targetNode="#controlMenuAlertsContainer" />

	<c:if test="<%= layoutsTreeDisplayContext.isShowStagingProcessMessage() %>">
		<div class="alert alert-default alert-dismissible" data-dismiss="alert" role="alert">
			<button class="close" type="button">
				<aui:icon image="times" markupView="lexicon" />

				<span class="sr-only"><liferay-ui:message key="close" /></span>
			</button>

			<liferay-ui:message key="publication-process-in-progress-help" />
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
</div>

<aui:script position="auto" use="aui-base,io-request">
	A.one('#<portlet:namespace />layoutsTreeOutput').delegate(
		'click',
		function(event) {
			event.preventDefault();

			if (confirm('<%= UnicodeLanguageUtil.get(resourceBundle, "are-you-sure-you-want-to-delete-the-selected-page") %>')) {
				var link = event.currentTarget;

				A.io.request(
					link.attr('href'),
					{
						after: {
							success: function(event, id, obj) {
								var response = this.get('responseData');

								var container = A.one('#<portlet:namespace/>layoutsTreeContainer');

								container.plug(A.Plugin.ParseContent);

								container.setContent(response);
							}
						}
					}
				);
			}
		},
		'.layout-tree-delete'
	);
</aui:script>

<liferay-portlet:renderURL portletName="<%= LayoutAdminPortletKeys.GROUP_PAGES %>" var="treeURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<portlet:param name="mvcPath" value="/panel/app/layouts_tree_expanded.jsp" />
</liferay-portlet:renderURL>

<aui:script use="liferay-url-preview">
	var expandedTreeDialog;

	var expandButton = A.one('#<portlet:namespace />expandPagesLink');

	expandButton.on(
		'click',
		function() {
			if (!expandedTreeDialog) {
				expandedTreeDialog = new Liferay.UrlPreview(
					{
						title: '<%= LanguageUtil.get(request, "pages") %>',
						url: '<%= treeURL.toString() %>'
					}
				);
			}

			expandedTreeDialog.open();
		}
	);
</aui:script>
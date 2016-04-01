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

<div class="layouts-tree-container" id="<portlet:namespace/>layoutsTreeContainer">
	<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutTabs() %>">

		<%
		Map<String, Object> data = new HashMap<>();
		%>

		<div class="layout-set-tabs">
			<c:if test="<%= layoutsTreeDisplayContext.isShowEmptyLayoutsTree() %>">
				<div class="layout-set-tab selected-layout-set">
					<aui:a cssClass="layout-set-link" href="<%= null %>" label="<%= layoutsTreeDisplayContext.getRootNodeName(false) %>" />

					<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">
						<div class="dropdown dropdown-menu-no-arrow layout-tree-options">
							<a aria-expanded="false" class="dropdown-toggle icon-monospaced" data-qa-id="pagesOptions" data-toggle="dropdown" href="javascript:;">
								<aui:icon image="ellipsis-v" markupView="lexicon" />
							</a>

							<ul class="dropdown-menu dropdown-menu-left-side">

								<%
								PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, false);
								%>

								<li>
									<a data-navigation="true" data-qa-id="addPublicPage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-public-page" /></a>
								</li>

								<%
								addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, true);
								%>

								<li>
									<a data-navigation="true" data-qa-id="addPrivatePage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-private-page" /></a>
								</li>
							</ul>
						</div>
					</c:if>
				</div>
			</c:if>

			<c:if test="<%= layoutsTreeDisplayContext.isShowPublicLayoutsTree() %>">
				<div class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? StringPool.BLANK : "selected-layout-set" %>">

					<%
					data.put("qa-id", "goToPublicPages");
					%>

					<aui:a cssClass="layout-set-link" data="<%= data %>" href="<%= layoutsTreeDisplayContext.getPublicLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getRootNodeName(false) %>" />

					<c:if test="<%= layoutsTreeDisplayContext.isShowPublicLayoutOptions() %>">
						<div class="dropdown dropdown-menu-no-arrow layout-tree-options">
							<a aria-expanded="false" class="dropdown-toggle icon-monospaced" data-qa-id="publicPagesOptions" data-toggle="dropdown" href="javascript:;">
								<aui:icon image="ellipsis-v" markupView="lexicon" />
							</a>

							<ul class="dropdown-menu dropdown-menu-left-side">
								<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">

									<%
									PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, false);
									%>

									<li>
										<a data-qa-id="addPublicPage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-page" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowAddBothRootLayoutButtons() %>">

									<%
									PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, false);
									%>

									<li>
										<a data-qa-id="addPublicPage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-public-page" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowEditLayoutSetButton() %>">

									<%
									PortletURL editLayoutURL = layoutsTreeDisplayContext.getEditLayoutURL(LayoutConstants.DEFAULT_PLID, false);
									%>

									<li>
										<a data-qa-id="editPublicPages" href="<%= editLayoutURL.toString() %>"><liferay-ui:message key="configuration" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowExpandLayoutSetButton(false) %>">
									<li>
										<a href="javascript:;" id="<portlet:namespace/>expandPagesLink"><liferay-ui:message key="expand-area" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowAddBothRootLayoutButtons() %>">
									<li class="divider"></li>

									<%
									PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, true);
									%>

									<li>
										<a data-qa-id="addPrivatePage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-private-page" /></a>
									</li>
								</c:if>
							</ul>
						</div>
					</c:if>
				</div>
			</c:if>

			<c:if test="<%= layoutsTreeDisplayContext.isShowPrivateLayoutsTree() %>">
				<div class="layout-set-tab <%= layoutsTreeDisplayContext.isPrivateLayout() ? "selected-layout-set" : StringPool.BLANK %>">

					<%
					data.put("qa-id", "goToPrivatePages");
					%>

					<aui:a cssClass="layout-set-link" data="<%= data %>" href="<%= layoutsTreeDisplayContext.getPrivateLayoutsURL() %>" label="<%= layoutsTreeDisplayContext.getRootNodeName(true) %>" />

					<c:if test="<%= layoutsTreeDisplayContext.isShowPrivateLayoutOptions() %>">
						<div class="dropdown dropdown-menu-no-arrow layout-tree-options">
							<a aria-expanded="false" class="dropdown-toggle icon-monospaced" data-qa-id="privatePagesOptions" data-toggle="dropdown" href="javascript:;">
								<aui:icon image="ellipsis-v" markupView="lexicon" />
							</a>

							<ul class="dropdown-menu dropdown-menu-left-side">
								<c:if test="<%= layoutsTreeDisplayContext.isShowAddRootLayoutButton() %>">

									<%
									PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, true);
									%>

									<li>
										<a data-qa-id="addPrivatePage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-page" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowAddBothRootLayoutButtons() %>">

									<%
									PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, true);
									%>

									<li>
										<a data-qa-id="addPrivatePage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-private-page" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowEditLayoutSetButton() %>">

									<%
									PortletURL editLayoutURL = layoutsTreeDisplayContext.getEditLayoutURL(LayoutConstants.DEFAULT_PLID, true);
									%>

									<li>
										<a data-qa-id="editPrivatePages" href="<%= editLayoutURL.toString() %>"><liferay-ui:message key="configuration" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowExpandLayoutSetButton(false) %>">
									<li>
										<a href="javascript:;" id="<portlet:namespace/>expandPagesLink"><liferay-ui:message key="expand-area" /></a>
									</li>
								</c:if>

								<c:if test="<%= layoutsTreeDisplayContext.isShowAddBothRootLayoutButtons() %>">
									<li class="divider"></li>

									<%
									PortletURL addLayoutURL = layoutsTreeDisplayContext.getAddLayoutURL(LayoutConstants.DEFAULT_PLID, false);
									%>

									<li>
										<a data-qa-id="addPublicPage" href="<%= addLayoutURL.toString() %>"><liferay-ui:message key="add-public-page" /></a>
									</li>
								</c:if>
							</ul>
						</div>
					</c:if>
				</div>
			</c:if>
		</div>
	</c:if>

	<c:if test="<%= layoutsTreeDisplayContext.isShowLayoutSetBranchesSelector() %>">
		<ul class="nav nav-equal-height nav-nested">
			<li>
				<div class="scope-selector">
					<span class="scope-name"><%= HtmlUtil.escape(layoutsTreeDisplayContext.getLayoutSetBranchName()) %></span>

					<span class="nav-equal-height-heading-field">
						<liferay-ui:icon-menu direction="left" icon="cog" markupView="lexicon" message="" showArrow="<%= false %>">

							<%
							for (LayoutSetBranch curLayoutSetBranch : layoutsTreeDisplayContext.getLayoutSetBranches()) {
							%>

								<liferay-ui:icon
									cssClass="<%= layoutsTreeDisplayContext.getLayoutSetBranchCssClass(curLayoutSetBranch) %>"
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

		<div class="dropdown dropdown-menu-no-arrow layout-tree-options" data-deleteable="{deleteable}" data-parentable="{parentable}" data-updateable="{updateable}">
			<a aria-expanded="false" class="dropdown-toggle icon-monospaced" data-qa-id="pageOptions" data-toggle="dropdown" href="javascript:;">
				<aui:icon image="ellipsis-v" markupView="lexicon" />
			</a>

			<ul class="dropdown-menu dropdown-menu-left-side">
				<li>
					<a class="layout-tree-add" data-parentable="{parentable}" data-plid="{plid}" data-qa-id="addChildPage" data-url="{url}" data-uuid="{uuid}" href="{addLayoutURL}" id="{id}Add">
						<span aria-hidden="true"><liferay-ui:message key="add-child-page" /></span>
						<span class="sr-only"><liferay-ui:message arguments="{label}" key="add-child-page-of-x" /></span>
					</a>
				</li>

				<li>
					<a class="layout-tree-edit" data-plid="{plid}" data-qa-id="editPage" data-updateable="{updateable}" data-url="{url}" data-uuid="{uuid}" href="{editLayoutURL}" id="{id}Edit">
						<span aria-hidden="true"><liferay-ui:message key="edit" /></span>
						<span class="sr-only"><liferay-ui:message arguments="{label}" key="edit-x" /></span>
					</a>
				</li>

				<li>
					<a class="layout-tree-delete" data-deleteable="{deleteable}" data-plid="{plid}" data-qa-id="deletePage" data-url="{url}" data-uuid="{uuid}" href="{deleteLayoutURL}" id="{id}Delete">
						<span aria-hidden="true"><liferay-ui:message key="delete" /></span>
						<span class="sr-only"><liferay-ui:message arguments="{label}" key="delete-x" /></span>
					</a>
				</li>
			</ul>
		</div>
	</liferay-util:buffer>

	<%
	Layout selLayout = layoutsTreeDisplayContext.getSelLayout();

	String targetNode = "#controlMenuAlertsContainer";
	%>

	<liferay-ui:success key="layoutAdded" message='<%= LanguageUtil.get(resourceBundle, "the-page-has-been-created-succesfully") %>' targetNode="<%= targetNode %>" />
	<liferay-ui:success key="layoutDeleted" message='<%= LanguageUtil.get(resourceBundle, "the-page-has-been-deleted-succesfully") %>' targetNode="<%= targetNode %>" />

	<%@ include file="/layout_exception.jspf" %>

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
	<portlet:param name="showLayoutTabs" value="<%= String.valueOf(Boolean.FALSE) %>" />
</liferay-portlet:renderURL>

<aui:script use="liferay-url-preview">
	var expandedTreeDialog;

	var expandButton = A.one('#<portlet:namespace />expandPagesLink');

	if (expandButton) {
		expandButton.on(
			'click',
			function() {
				if (!expandedTreeDialog) {
					expandedTreeDialog = new Liferay.UrlPreview(
						{
							title: '<%= LanguageUtil.get(request, "pages") %>',
							url: '<%= treeURL.toString() %>',
							width: Liferay.Util.isPhone() ? '100%' : '900px'
						}
					);
				}

				expandedTreeDialog.open();
			}
		);
	}
</aui:script>
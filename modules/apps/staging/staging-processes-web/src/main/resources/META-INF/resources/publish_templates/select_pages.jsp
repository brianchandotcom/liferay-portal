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
groupId = ParamUtil.getLong(request, "groupId");

group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");
%>

<aui:input name="layoutIds" type="hidden" value="<%= ExportImportHelperUtil.getSelectedLayoutsJSON(groupId, privateLayout, selectedLayoutIds) %>" />

<ul class="flex-container layout-selector" id="<portlet:namespace />pages">
	<li class="layout-selector-options">
		<aui:fieldset label="pages-options">
			<c:choose>
				<c:when test="<%= privateLayout %>">
					<li>
						<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="changeToPublicLayoutsURL">
							<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
							<portlet:param name="privateLayout" value="<%= Boolean.FALSE.toString() %>" />
						</liferay-portlet:renderURL>

						<aui:button href="<%= changeToPublicLayoutsURL %>" value="change-to-public-pages" />
					</li>
				</c:when>
				<c:otherwise>
					<li>
						<liferay-portlet:renderURL copyCurrentRenderParameters="<%= false %>" var="changeToPrivateLayoutsURL">
							<portlet:param name="mvcRenderCommandName" value="editPublishConfiguration" />
							<portlet:param name="privateLayout" value="<%= Boolean.TRUE.toString() %>" />
						</liferay-portlet:renderURL>

						<aui:button href="<%= changeToPrivateLayoutsURL %>" value="change-to-private-pages" />
					</li>
				</c:otherwise>
			</c:choose>

			<c:choose>
				<c:when test="<%= layoutSetBranchId > 0 %>">
					<aui:input name="layoutSetBranchId" type="hidden" value="<%= layoutSetBranchId %>" />
				</c:when>
				<c:otherwise>
					<c:if test="<%= LayoutStagingUtil.isBranchingLayoutSet(group, privateLayout) %>">

						<%
						List<LayoutSetBranch> layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(group.getGroupId(), privateLayout);
						%>

						<aui:select label="site-pages-variation" name="layoutSetBranchId">

							<%
							for (LayoutSetBranch layoutSetBranch : layoutSetBranches) {
								boolean selected = false;

								if (layoutSetBranch.isMaster()) {
									selected = true;
								}
							%>

							<aui:option label="<%= HtmlUtil.escape(layoutSetBranch.getName()) %>" selected="<%= selected %>" value="<%= layoutSetBranch.getLayoutSetBranchId() %>" />

							<%
							}
							%>

						</aui:select>
					</c:if>
				</c:otherwise>
			</c:choose>
		</aui:fieldset>
	</li>

	<li class="layout-selector-options">
		<aui:fieldset label="pages-to-export">

			<%
			long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
			String treeId = ParamUtil.getString(request, "treeId");
			%>

			<div class="pages-selector">
				<liferay-layout:layouts-tree
					defaultStateChecked="<%= true %>"
					draggableTree="<%= false %>"
					groupId="<%= groupId %>"
					incomplete="<%= false %>"
					portletURL="<%= renderResponse.createRenderURL() %>"
					privateLayout="<%= privateLayout %>"
					rootNodeName="<%= group.getLayoutRootNodeName(privateLayout, locale) %>"
					selectableTree="<%= true %>"
					selectedLayoutIds="<%= selectedLayoutIds %>"
					selPlid="<%= selPlid %>"
					treeId="<%= treeId %>"
				/>
			</div>
		</aui:fieldset>
	</li>

	<li class="layout-selector-options">
		<aui:fieldset label="look-and-feel">
			<aui:input helpMessage="export-import-theme-settings-help" label="theme-settings" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= true %>" />

			<aui:input label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= true %>" />

			<aui:input label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= true %>" />

			<aui:input helpMessage="delete-missing-layouts-staging-help" label="delete-missing-layouts" name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" type="checkbox" />
		</aui:fieldset>
	</li>
</ul>
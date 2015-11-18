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
String cmd = ParamUtil.getString(request, Constants.CMD);

long groupId = ParamUtil.getLong(request, "groupId");

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}

long layoutSetBranchId = ParamUtil.getLong(request, "layoutSetBranchId");

boolean privateLayout = ParamUtil.getBoolean(request, "privateLayout");
String selectedLayoutIds = ParamUtil.getString(request, "selectedLayoutIds");

Map<String, String[]> parameterMap = (Map<String, String[]>)GetterUtil.getObject(request.getAttribute("select_pages.jsp-parameterMap"), Collections.emptyMap());
%>

<aui:input name="layoutIds" type="hidden" value="<%= ExportImportHelperUtil.getSelectedLayoutsJSON(groupId, privateLayout, selectedLayoutIds) %>" />

<span class="selected-labels" id="<portlet:namespace />selectedPages"></span>

<aui:a cssClass="modify-link" href="javascript:;" id="pagesLink" label="change" method="get" />

<div class="hide" id="<portlet:namespace />pages">
	<aui:fieldset cssClass="portlet-data-section" label="pages-to-export">
		<div class="selected-pages" id="<portlet:namespace />pane">

			<%
			String treeId = ParamUtil.getString(request, "treeId");
			long selPlid = ParamUtil.getLong(request, "selPlid", LayoutConstants.DEFAULT_PLID);
			%>

			<liferay-ui:layouts-tree
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

		<c:if test="<%= cmd.equals(Constants.PUBLISH) %>">
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

			<aui:input helpMessage="delete-missing-layouts-staging-help" label="delete-missing-layouts" name="<%= PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.DELETE_MISSING_LAYOUTS) %>" />
		</c:if>

		<aui:input label="site-pages-settings" name="<%= PortletDataHandlerKeys.LAYOUT_SET_SETTINGS %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LAYOUT_SET_SETTINGS, true) %>" />
	</aui:fieldset>

	<aui:fieldset cssClass="portlet-data-section" label="look-and-feel">
		<aui:input helpMessage="export-import-theme-settings-help" label="theme-settings" name="<%= PortletDataHandlerKeys.THEME_REFERENCE %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.THEME_REFERENCE, true) %>" />

		<aui:input label="logo" name="<%= PortletDataHandlerKeys.LOGO %>" type="checkbox" value="<%= MapUtil.getBoolean(parameterMap, PortletDataHandlerKeys.LOGO, true) %>" />
	</aui:fieldset>
</div>
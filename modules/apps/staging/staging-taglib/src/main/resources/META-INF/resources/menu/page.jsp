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

<%@ include file="/menu/init.jsp" %>

<%
String cssClass = "staging-icon-menu " + GetterUtil.getString((String)request.getAttribute("liferay-staging:menu:cssClass"));
long layoutSetBranchId = GetterUtil.getLong((String)request.getAttribute("liferay-staging:menu:layoutSetBranchId"));
boolean onlyActions = GetterUtil.getBoolean((String)request.getAttribute("liferay-staging:menu:onlyActions"));
long selPlid = GetterUtil.getLong((String)request.getAttribute("liferay-staging:menu:selPlid"));
boolean showManageBranches = GetterUtil.getBoolean((String)request.getAttribute("liferay-staging:menu:showManageBranches"));

boolean branchingEnabled = GetterUtil.getBoolean((String)request.getAttribute(StagingProcessesWebKeys.BRANCHING_ENABLED));
boolean hasWorkflowTask = GetterUtil.getBoolean((String)request.getAttribute("view_layout_revision_details.jsp-hasWorkflowTask"));
LayoutRevision layoutRevision = (LayoutRevision)request.getAttribute("view_layout_revision_details.jsp-layoutRevision");

LayoutSetBranch layoutSetBranch = null;
List<LayoutSetBranch> layoutSetBranches = null;

String publishDialogTitle = null;

if (!group.isCompany()) {
	layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);
}

boolean localPublishing = group.isStaged() && !group.isStagedRemotely();

if (!localPublishing) {
	if ((layoutSetBranchId > 0) && (layoutSetBranches.size() > 1)) {
		publishDialogTitle = "publish-x-to-remote-live";
	}
	else {
		publishDialogTitle = "publish-to-remote-live";
	}
}
else {
	if ((layoutSetBranchId > 0) && (layoutSetBranches.size() > 1)) {
		publishDialogTitle = "publish-x-to-live";
	}
	else {
		publishDialogTitle = "publish-to-live";
	}
}

String publishMessage = LanguageUtil.get(request, publishDialogTitle);
%>

<liferay-portlet:renderURL plid="<%= plid %>" portletMode="<%= PortletMode.VIEW.toString() %>" portletName="<%= PortletKeys.EXPORT_IMPORT %>" varImpl="publishRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="mvcRenderCommandName" value="publishLayouts" />
	<liferay-portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
	<liferay-portlet:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
	<liferay-portlet:param name="closeRedirect" value="<%= currentURL %>" />
	<liferay-portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	<liferay-portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
</liferay-portlet:renderURL>

<c:if test="<%= stagingGroup != null %>">
	<c:choose>
		<c:when test="<%= onlyActions %>">
			<%@ include file="/menu/staging_actions.jspf" %>
		</c:when>
		<c:otherwise>
			<aui:nav-bar>
				<aui:nav cssClass="navbar-nav">
					<aui:nav-item dropdown="<%= true %>" label="staging">
						<aui:nav-item cssClass="<%= cssClass %>">
							<%@ include file="/menu/staging_actions.jspf" %>
						</aui:nav-item>
					</aui:nav-item>
				</aui:nav>
			</aui:nav-bar>
		</c:otherwise>
	</c:choose>
</c:if>
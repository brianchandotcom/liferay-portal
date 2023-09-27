<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	<liferay-portlet:param name="mvcRenderCommandName" value="/export_import/publish_layouts" />
	<liferay-portlet:param name="<%= Constants.CMD %>" value="<%= localPublishing ? Constants.PUBLISH_TO_LIVE : Constants.PUBLISH_TO_REMOTE %>" />
	<liferay-portlet:param name="tabs1" value='<%= privateLayout ? "private-pages" : "public-pages" %>' />
	<liferay-portlet:param name="closeRedirect" value="<%= currentURL %>" />
	<liferay-portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
	<liferay-portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
</liferay-portlet:renderURL>

<c:if test="<%= stagingGroup != null %>">
	<%@ include file="/menu/staging_actions.jspf" %>
</c:if>
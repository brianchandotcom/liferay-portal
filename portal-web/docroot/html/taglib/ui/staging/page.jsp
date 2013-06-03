<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

<%@ include file="/html/taglib/init.jsp" %>

<%
String cssClass = "staging-icon-menu " + GetterUtil.getString((String) request.getAttribute("liferay-ui:staging:cssClass"));
boolean extended = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:staging:extended"));
long groupId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:staging:groupId"));
String icon = GetterUtil.getString((String)request.getAttribute("liferay-ui:staging:icon"));
long layoutSetBranchId = GetterUtil.getLong((String)request.getAttribute("liferay-ui:staging:layoutSetBranchId"));
String message = GetterUtil.getString((String)request.getAttribute("liferay-ui:staging:message"));
boolean onlyActions = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:staging:onlyActions"));
boolean privateLayout = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:staging:privateLayout"));
long selPlid = GetterUtil.getLong((String)request.getAttribute("liferay-ui:staging:selPlid"));
boolean showManageBranches = GetterUtil.getBoolean((String)request.getAttribute("liferay-ui:staging:showManageBranches"));

if (Validator.isNotNull(icon)) {
	icon = themeDisplay.getPathThemeImages() + icon;
}

LayoutSetBranch layoutSetBranch = null;
List<LayoutSetBranch> layoutSetBranches = null;

Group group = null;

if (groupId > 0) {
	group = GroupLocalServiceUtil.getGroup(groupId);
}
else {
	group = themeDisplay.getScopeGroup();

	if (group.isLayout()) {
		group = layout.getGroup();
	}
}

Group liveGroup = null;
Group stagingGroup = null;

if (group.isCompany()) {
	stagingGroup = group;
}
else if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
	stagingGroup = group;
}
else if (group.isStaged()) {
	if (group.isStagedRemotely()) {
		liveGroup = group;
		stagingGroup = group;
	}
	else {
		liveGroup = group;
		stagingGroup = group.getStagingGroup();
	}
}

if (groupId <= 0) {
	privateLayout = layout.isPrivateLayout();
}

String publishDialogTitle = null;

if (group.isCompany()) {
	publishDialogTitle = "publish-to-remote-live";
}
else {
	layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);

	if (group.isStaged() && group.isStagedRemotely()) {
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
}

String publishMessage = LanguageUtil.get(pageContext, publishDialogTitle);
%>

<liferay-portlet:renderURL plid="<%= plid %>" portletMode="<%= PortletMode.VIEW.toString() %>" portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" varImpl="publishRenderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
	<liferay-portlet:param name="struts_action" value="/layouts_admin/publish_layouts" />
	<liferay-portlet:param name="<%= Constants.CMD %>" value='<%= (group.isCompany()) ? "publish_to_remote" : "publish_to_live" %>' />
	<liferay-portlet:param name="tabs1" value='<%= (privateLayout) ? "private-pages" : "public-pages" %>' />
	<liferay-portlet:param name="closeRedirect" value="<%= currentURL %>" />
	<liferay-portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
</liferay-portlet:renderURL>

<c:if test="<%= stagingGroup != null %>">
	<c:choose>
		<c:when test="<%= onlyActions %>">
			<%@ include file="/html/taglib/ui/staging/staging_actions.jspf" %>
		</c:when>
		<c:otherwise>
			<aui:nav-bar>
				<aui:nav>
					<aui:nav-item dropdown="<%= true %>" label="staging">
						<aui:nav-item cssClass="<%= cssClass %>" label="<%= extended ? message : StringPool.BLANK %>">
							<%@ include file="/html/taglib/ui/staging/staging_actions.jspf" %>
						</aui:nav-item>
					</aui:nav-item>
				</aui:nav>
			</aui:nav-bar>
		</c:otherwise>
	</c:choose>
</c:if>
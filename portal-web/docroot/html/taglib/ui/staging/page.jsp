<%--
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
boolean extended = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:staging:extended"));
long groupId = GetterUtil.getLong((String) request.getAttribute("liferay-ui:staging:groupId"));
String icon = GetterUtil.getString((String) request.getAttribute("liferay-ui:staging:icon"));
long layoutSetBranchId = GetterUtil.getLong((String) request.getAttribute("liferay-ui:staging:layoutSetBranchId"));
String message = GetterUtil.getString((String) request.getAttribute("liferay-ui:staging:message"));
boolean privateLayout = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:staging:privateLayout"));
long selPlid = GetterUtil.getLong((String) request.getAttribute("liferay-ui:staging:selPlid"));
boolean showManageBranches = GetterUtil.getBoolean((String) request.getAttribute("liferay-ui:staging:showManageBranches"));

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

String publishNowDialogTitle = null;
String publishScheduleDialogTitle = null;

Group liveGroup = null;
Group stagingGroup = null;

if (group.isStagingGroup()) {
	liveGroup = group.getLiveGroup();
	stagingGroup = group;
}
else if (group.isStaged()) {
	if (group.isStagedRemotely()) {
		stagingGroup = group;
	}
	else {
		liveGroup = group;
		stagingGroup = group.getStagingGroup();
	}
}
else if (group.isCompany()) {
	stagingGroup = group;
}

if (groupId <= 0) {
	privateLayout = layout.isPrivateLayout();
}

if (group.isCompany()) {
	publishNowDialogTitle = "publish-to-remote-live-now";
	publishScheduleDialogTitle = "schedule-publication-to-remote-live";
}
else {
	layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), privateLayout);

	if (group.isStaged() && group.isStagedRemotely()) {
		if ((layoutSetBranchId > 0) && (layoutSetBranches.size() > 1)) {
			publishNowDialogTitle = "publish-x-to-remote-live-now";
			publishScheduleDialogTitle = "schedule-publication-of-x-to-remote-live";
		}
		else {
			publishNowDialogTitle = "publish-to-remote-live-now";
			publishScheduleDialogTitle = "schedule-publication-to-remote-live";
		}
	}
	else {
		if ((layoutSetBranchId > 0) && (layoutSetBranches.size() > 1)) {
			publishNowDialogTitle = "publish-x-to-live-now";
			publishScheduleDialogTitle = "schedule-publication-of-x-to-live";
		}
		else {
			publishNowDialogTitle = "publish-to-live-now";
			publishScheduleDialogTitle = "schedule-publication-to-live";
		}
	}
}

String publishNowMessage = LanguageUtil.get(pageContext, publishNowDialogTitle);
String publishScheduleMessage = LanguageUtil.get(pageContext, publishScheduleDialogTitle);
%>

<liferay-portlet:renderURL plid="<%= plid %>" portletMode="<%= PortletMode.VIEW.toString() %>" portletName="<%= PortletKeys.LAYOUTS_ADMIN %>" varImpl="publishRenderUrl" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
	<liferay-portlet:param name="struts_action" value="/layouts_admin/publish_layouts" />
	<liferay-portlet:param name="<%= Constants.CMD %>" value='<%= (group.isCompany()) ? "publish_to_remote" : "publish_to_live" %>' />
	<liferay-portlet:param name="tabs1" value='<%= (privateLayout) ? "private-pages" : "public-pages" %>' />
	<liferay-portlet:param name="pagesRedirect" value="<%= currentURL %>" />
	<liferay-portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
	<liferay-portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
</liferay-portlet:renderURL>

<c:if test="<%= stagingGroup != null %>">
	<span class="staging-icon-menu-container">
		<liferay-ui:icon-menu align="auto" cssClass="<%= cssClass %>" direction="down" extended="<%= extended %>" icon='<%= extended ? icon : StringPool.BLANK %>' message='<%= extended ? message : StringPool.BLANK %>' showWhenSingleIcon="<%= true %>">
			<c:choose>
				<c:when test="<%= group.isCompany() && GroupPermissionUtil.contains(permissionChecker, group.getGroupId(), ActionKeys.PUBLISH_STAGING) %>">
					<liferay-ui:icon id='<%= groupId + "publishGlobalNowLink" %>' image="maximize" message='<%= publishNowDialogTitle %>' url="<%= publishRenderUrl.toString() %>" />

					<%
					publishRenderUrl.setParameter("schedule", String.valueOf(true));
					%>

					<liferay-ui:icon id='<%= groupId + "publishGlobalScheduleLink" %>' image="time" message="<%= publishScheduleMessage %>" url="<%= publishRenderUrl.toString() %>" />

					<aui:script use="aui-base">
						var publishGlobalNowLink = A.one('#<portlet:namespace /><%= groupId + "publishGlobalNowLink" %>');

						if (publishGlobalNowLink) {
							publishGlobalNowLink.detach('click');

							publishGlobalNowLink.on(
								'click',
								function(event) {
									event.preventDefault();

									Liferay.LayoutExporter.publishToLive(
										{
											title: '<%= UnicodeFormatter.toString(publishNowMessage) %>',
											url: event.currentTarget.attr('href')
									});

								}
							);
						}

						var publishGlobalScheduleLink = A.one('#<portlet:namespace /><%= groupId + "publishGlobalScheduleLink" %>');

						if (publishGlobalScheduleLink) {
							publishGlobalScheduleLink.detach('click');

							publishGlobalScheduleLink.on(
								'click',
								function(event) {
									event.preventDefault();

									Liferay.LayoutExporter.publishToLive(
										{
											title: '<%= UnicodeFormatter.toString(publishScheduleMessage) %>',
											url: event.currentTarget.attr('href')
									});
								}
							);
						}
					</aui:script>
				</c:when>
				<c:otherwise>
					<c:if test="<%= (stagingGroup.isStagedRemotely() || GroupPermissionUtil.contains(permissionChecker, liveGroup.getGroupId(), ActionKeys.PUBLISH_STAGING)) %>">
						<c:choose>
							<c:when test="<%= (layoutSetBranchId > 0) && (layoutSetBranches.size() > 1) %>">

								<%
								layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutSetBranchId);

								publishRenderUrl.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranchId));
								publishRenderUrl.setParameter("layoutSetBranchName", layoutSetBranch.getName());

								publishNowMessage = LanguageUtil.format(pageContext, publishNowDialogTitle, layoutSetBranch.getName());
								publishScheduleMessage = LanguageUtil.format(pageContext, publishScheduleDialogTitle, layoutSetBranch.getName());
								%>

							</c:when>
							<c:otherwise>
								<c:if test="<%= layoutSetBranches.size() == 1 %>">

									<%
									layoutSetBranch = layoutSetBranches.get(0);

									publishRenderUrl.setParameter("layoutSetBranchId", String.valueOf(layoutSetBranch.getLayoutSetBranchId()));
									%>

								</c:if>
							</c:otherwise>
						</c:choose>

						<liferay-ui:icon id='<%= layoutSetBranchId + "publishNowLink" %>' image="maximize" message="<%= publishNowMessage %>" url="<%= publishRenderUrl.toString() %>" />

						<%
						publishRenderUrl.setParameter("schedule", String.valueOf(true));
						%>

						<liferay-ui:icon id='<%= layoutSetBranchId + "publishScheduleLink" %>' image="time" message="<%= publishScheduleMessage %>" url="<%= publishRenderUrl.toString() %>" />

						<aui:script use="aui-base">
							var publishnowLink = A.one('#<portlet:namespace /><%= layoutSetBranchId + "publishNowLink" %>');

							if (publishnowLink) {
								publishnowLink.detach('click');

								publishnowLink.on(
									'click',
									function(event) {
										event.preventDefault();

										Liferay.LayoutExporter.publishToLive(
											{
												title: '<%= UnicodeFormatter.toString(publishNowMessage) %>',
												url: event.currentTarget.attr('href')
										});

									}
								);
							}

							var publishScheduleLink = A.one('#<portlet:namespace /><%= layoutSetBranchId + "publishScheduleLink" %>');

							if (publishScheduleLink) {
								publishScheduleLink.detach('click');

								publishScheduleLink.on(
									'click',
									function(event) {
										event.preventDefault();

										Liferay.LayoutExporter.publishToLive(
											{
												title: '<%= UnicodeFormatter.toString(publishScheduleMessage) %>',
												url: event.currentTarget.attr('href')
										});
									}
								);
							}
						</aui:script>

					</c:if>

					<c:if test="<%= showManageBranches && !layoutSetBranches.isEmpty() %>">
						<portlet:renderURL var="layoutSetBranchesURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
							<portlet:param name="struts_action" value="/staging_bar/view_layout_set_branches" />
							<portlet:param name="groupId" value="<%= String.valueOf(groupId) %>" />
							<portlet:param name="privateLayout" value="<%= String.valueOf(privateLayout) %>" />
							<portlet:param name="selPlid" value="<%= String.valueOf(selPlid) %>" />
						</portlet:renderURL>

						<liferay-ui:icon
							cssClass="manage-layout-set-branches"
							id="manageLayoutSetBranches"
							image="configuration"
							label="<%= true %>"
							message="manage-site-pages-variations"
							url="<%= layoutSetBranchesURL %>"
						/>

						<aui:script use="aui-base">
							var layoutSetBranchesLink = A.one('#<portlet:namespace />manageLayoutSetBranches');

							if (layoutSetBranchesLink) {
								layoutSetBranchesLink.detach('click');

								layoutSetBranchesLink.on(
									'click',
									function(event) {
										event.preventDefault();

										Liferay.Util.openWindow(
											{
												dialog:
													{
														width: 820
													},
												id: '<portlet:namespace />layoutSetBranches',
												title: '<%= UnicodeLanguageUtil.get(pageContext, "manage-site-pages-variations") %>',
												uri: event.currentTarget.attr('href')
											}
										);
									}
								);
							}
						</aui:script>
					</c:if>
				</c:otherwise>
			</c:choose>
		</liferay-ui:icon-menu>
	</span>
</c:if>
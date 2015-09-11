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
boolean branchingEnabled = false;

LayoutRevision layoutRevision = null;

LayoutSetBranch layoutSetBranch = null;

LayoutBranch layoutBranch = null;

Layout liveLayout = null;

if (layout != null) {
	layoutRevision = LayoutStagingUtil.getLayoutRevision(layout);

	if (layoutRevision != null) {
		branchingEnabled = true;

		layoutSetBranch = LayoutSetBranchLocalServiceUtil.getLayoutSetBranch(layoutRevision.getLayoutSetBranchId());

		layoutBranch = layoutRevision.getLayoutBranch();
	}
}
%>

<c:if test="<%= themeDisplay.isShowStagingIcon() %>">

	<%
	String liveFriendlyURL = null;

	if (liveGroup != null) {
		liveLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layout.getUuid(), liveGroup.getGroupId(), layout.isPrivateLayout());

		if (liveLayout != null) {
			liveFriendlyURL = PortalUtil.getLayoutFriendlyURL(liveLayout, themeDisplay);
		}
		else if ((layout.isPrivateLayout() && (liveGroup.getPrivateLayoutsPageCount() > 0)) || (layout.isPublicLayout() && (liveGroup.getPublicLayoutsPageCount() > 0))) {
			liveFriendlyURL = liveGroup.getDisplayURL(themeDisplay, layout.isPrivateLayout());
		}
	}

	String stagingFriendlyURL = null;

	if (stagingGroup != null) {
		Layout stagingLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layout.getUuid(), stagingGroup.getGroupId(), layout.isPrivateLayout());

		if (stagingLayout != null) {
			stagingFriendlyURL = PortalUtil.getLayoutFriendlyURL(stagingLayout, themeDisplay);
		}
		else {
			stagingFriendlyURL = stagingGroup.getDisplayURL(themeDisplay, layout.isPrivateLayout());
		}
	}

	List<LayoutSetBranch> layoutSetBranches = null;

	if (group.isStagingGroup() || group.isStagedRemotely()) {
		layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), layout.isPrivateLayout());
	}
	%>

	<c:if test="<%= liveGroup != null %>">
		<li class="control-menu-nav-item">
			<c:choose>
				<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
					<c:if test="<%= stagingGroup != null %>">
						<a class="active control-menu-icon sidenav-toggler" id="stagingLink" value="staging">
							<span class="icon-fb-radio icon-monospaced"></span>
							<span class="control-menu-icon-label">
								<liferay-ui:message key="staging" />
							</span>
						</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<a class='<%= ((layoutSetBranches != null) ? " active control-menu-icon sidenav-toggler" : StringPool.BLANK) %>' href="<%= (layoutSetBranches != null) ? null : stagingFriendlyURL %>" value="staging">
						<span class="icon-fb-radio icon-monospaced"></span>
						<span class="control-menu-icon-label">
							<liferay-ui:message key="staging" />
						</span>
					</a>
				</c:otherwise>
			</c:choose>
		</li>

		<li class="active control-menu-nav-item">
			<c:choose>
				<c:when test="<%= group.isStagedRemotely() %>">

					<%
					UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

					String remoteAddress = typeSettingsProperties.getProperty("remoteAddress");
					int remotePort = GetterUtil.getInteger(typeSettingsProperties.getProperty("remotePort"));
					String remotePathContext = typeSettingsProperties.getProperty("remotePathContext");
					boolean secureConnection = GetterUtil.getBoolean(typeSettingsProperties.getProperty("secureConnection"));
					long remoteGroupId = GetterUtil.getLong(typeSettingsProperties.getProperty("remoteGroupId"));

					String remoteURL = StagingUtil.buildRemoteURL(remoteAddress, remotePort, remotePathContext, secureConnection, remoteGroupId, layout.isPrivateLayout());
					%>

					<a href="<%= remoteURL %>" icon="icon-external-link-sign" value="go-to-remote-live">
						<liferay-ui:message key="go-to-remote-live" />
					</a>
				</c:when>
				<c:when test="<%= group.isStagingGroup() %>">
					<c:if test="<%= Validator.isNotNull(liveFriendlyURL) %>">
						<a class="control-menu-icon taglib-icon" href="<%= liveFriendlyURL %>" value="live">
							<i class="icon-circle-blank icon-monospaced"></i>

							<span class="hide-accessible taglib-text"></span>
							<span class="control-menu-icon-label">
								<liferay-ui:message key="live" />
							</span>
						</a>
					</c:if>
				</c:when>
				<c:otherwise>
					<a class="control-menu-icon taglib-icon" id="liveLink" value="live">
						<i class="icon-circle-blank icon-monospaced"></i>

						<span class="hide-accessible taglib-text"></span>
						<span class="control-menu-icon-label">
							<liferay-ui:message key="live" />
						</span>
					</a>
				</c:otherwise>
			</c:choose>
		</li>

		<c:if test="<%= (group.isStagingGroup() || group.isStagedRemotely()) && (stagingGroup != null) %>">
			<c:choose>
				<c:when test="<%= (group.isStagingGroup() || group.isStagedRemotely()) && branchingEnabled %>">

					<%
					request.setAttribute(WebKeys.PRIVATE_LAYOUT, privateLayout);
					request.setAttribute("view.jsp-layoutBranch", layoutBranch);
					request.setAttribute("view.jsp-layoutRevision", layoutRevision);
					request.setAttribute("view.jsp-layoutSetBranch", layoutSetBranch);
					request.setAttribute("view.jsp-layoutSetBranches", layoutSetBranches);
					request.setAttribute("view.jsp-stagingFriendlyURL", stagingFriendlyURL);
					%>

					<c:if test="<%= !layoutRevision.isIncomplete() %>">
						<liferay-util:include page="/view_layout_set_branch_details.jsp" servletContext="<%= application %>" />

						<liferay-util:include page="/view_layout_branch_details.jsp" servletContext="<%= application %>" />
					</c:if>

					<div class="layout-revision-details" id="<portlet:namespace />layoutRevisionDetails">
						<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

						<liferay-util:include page="/view_layout_revision_details.jsp" servletContext="<%= application %>" />
					</div>

					<liferay-staging:menu cssClass="branching-enabled col-md-4" extended="<%= false %>" layoutSetBranchId="<%= layoutRevision.getLayoutSetBranchId() %>" onlyActions="<%= true %>" />
				</c:when>

				<c:otherwise>
					<div class="staging-details">
						<c:choose>
							<c:when test="<%= liveLayout == null %>">
								<span class="last-publication-branch">
									<liferay-ui:message arguments='<%= "<strong>" + HtmlUtil.escape(layout.getName(locale)) + "</strong>" %>' key="page-x-has-not-been-published-to-live-yet" translateArguments="<%= false %>" />
								</span>
							</c:when>
							<c:otherwise>

								<%
								request.setAttribute("privateLayout", privateLayout);
								request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
								%>

								<liferay-util:include page="/last_publication_date_message.jsp" servletContext="<%= application %>" />
							</c:otherwise>
						</c:choose>
					</div>

					<c:if test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
						<liferay-staging:menu cssClass="publish-link" extended="<%= false %>" onlyActions="<%= true %>" />
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="<%= !group.isStagedRemotely() && !group.isStagingGroup() %>">
			<li class="control-menu-nav-item">
				<div class="alert alert-warning hide warning-content" id="<portlet:namespace />warningMessage">
					<liferay-ui:message key="an-inital-staging-publication-is-in-progress" />
				</div>

				<%
				request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
				%>

				<liferay-util:include page="/last_publication_date_message.jsp" servletContext="<%= application %>" />
			</li>
		</c:if>
	</c:if>

	<c:if test="<%= !branchingEnabled %>">
		<aui:script use="liferay-staging">
			Liferay.StagingBar.init(
				{
					namespace: '<portlet:namespace />',
					portletId: '<%= portletDisplay.getId() %>'
				}
			);
		</aui:script>
	</c:if>

	<aui:script use="aui-base">
		var stagingLink = A.one('#<portlet:namespace />stagingLink');
		var warningMessage = A.one('#<portlet:namespace />warningMessage');

		var checkBackgroundTasks = function() {
			Liferay.Service(
				'/backgroundtask.backgroundtask/get-background-tasks-count',
				{
					completed: false,
					groupId: '<%= liveGroup.getGroupId() %>',
					taskExecutorClassName: '<%= LayoutStagingBackgroundTaskExecutor.class.getName() %>'
				},
				function(obj) {
					var incomplete = obj > 0;

					if (stagingLink) {
						stagingLink.toggle(!incomplete);
					}

					if (warningMessage) {
						warningMessage.toggle(incomplete);
					}

					if (incomplete) {
						setTimeout(checkBackgroundTasks, 5000);
					}
				}
			);
		};

		checkBackgroundTasks();
	</aui:script>
</c:if>
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

<%@ include file="/html/portlet/staging_bar/init.jsp" %>

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
			liveFriendlyURL = PortalUtil.getGroupFriendlyURL(liveGroup, layout.isPrivateLayout(), themeDisplay);
		}

		if (Validator.isNotNull(liveFriendlyURL)) {
			liveFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, liveFriendlyURL);
		}
	}

	String stagingFriendlyURL = null;

	if (stagingGroup != null) {
		Layout stagingLayout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(layout.getUuid(), stagingGroup.getGroupId(), layout.isPrivateLayout());

		if (stagingLayout != null) {
			stagingFriendlyURL = PortalUtil.getLayoutFriendlyURL(stagingLayout, themeDisplay);
		}
		else {
			stagingFriendlyURL = PortalUtil.getGroupFriendlyURL(stagingGroup, layout.isPrivateLayout(), themeDisplay);
		}

		if (Validator.isNotNull(stagingFriendlyURL)) {
			stagingFriendlyURL = PortalUtil.addPreservedParameters(themeDisplay, stagingFriendlyURL);
		}
	}

	List<LayoutSetBranch> layoutSetBranches = null;

	if (group.isStagingGroup() || group.isStagedRemotely()) {
		layoutSetBranches = LayoutSetBranchLocalServiceUtil.getLayoutSetBranches(stagingGroup.getGroupId(), layout.isPrivateLayout());
	}
	%>

	<aui:nav collapsible="<%= false %>" cssClass="navbar-nav staging-bar" id="stagingBar">
		<c:if test="<%= (liveGroup != null) %>">
			<c:choose>
				<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
					<c:if test="<%= stagingGroup != null %>">
						<aui:nav-item anchorCssClass="staging-link" cssClass="active site-variations staging-toggle" dropdown="<%= true %>" id="stagingLink" label="staging" toggle="<%= true %>">
							<aui:nav-item cssClass="row">
								<c:choose>
									<c:when test="<%= (group.isStagingGroup() || group.isStagedRemotely()) && branchingEnabled %>">

										<%
										request.setAttribute("view.jsp-layoutBranch", layoutBranch);
										request.setAttribute("view.jsp-layoutRevision", layoutRevision);
										request.setAttribute("view.jsp-layoutSetBranch", layoutSetBranch);
										request.setAttribute("view.jsp-layoutSetBranches", layoutSetBranches);
										request.setAttribute("view.jsp-stagingFriendlyURL", stagingFriendlyURL);
										%>

										<c:if test="<%= !layoutRevision.isIncomplete() %>">
											<liferay-util:include page="/html/portlet/staging_bar/view_layout_set_branch_details.jsp" />

											<liferay-util:include page="/html/portlet/staging_bar/view_layout_branch_details.jsp" />
										</c:if>

										<portlet:actionURL var="editLayoutRevisionURL">
											<portlet:param name="struts_action" value="/staging_bar/edit_layouts" />
										</portlet:actionURL>

										<div class="layout-revision-details" id="<portlet:namespace />layoutRevisionDetails">
											<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

											<liferay-util:include page="/html/portlet/staging_bar/view_layout_revision_details.jsp" />
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
													request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
													%>

													<liferay-util:include page="/html/portlet/staging_bar/last_publication_date_message.jsp" />
												</c:otherwise>
											</c:choose>
										</div>

										<c:if test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
											<liferay-staging:menu cssClass="publish-link" extended="<%= false %>" onlyActions="<%= true %>" />
										</c:if>
									</c:otherwise>
								</c:choose>
							</aui:nav-item>
						</aui:nav-item>
					</c:if>
				</c:when>
				<c:otherwise>
					<aui:nav-item cssClass='<%= ((layoutSetBranches != null) ? " active" : StringPool.BLANK) + " staging-toggle" %>' href="<%= (layoutSetBranches != null) ? null : stagingFriendlyURL %>" label="staging" />
				</c:otherwise>
			</c:choose>

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

					<aui:nav-item cssClass="remote-live-link" href="<%= remoteURL %>" iconCssClass="icon-external-link-sign" label="go-to-remote-live" />
				</c:when>
				<c:when test="<%= group.isStagingGroup() %>">
					<c:if test="<%= Validator.isNotNull(liveFriendlyURL) %>">
						<aui:nav-item cssClass=" live-link staging-toggle" href="<%= liveFriendlyURL %>" label="live" />
					</c:if>
				</c:when>
				<c:otherwise>
					<aui:nav-item anchorCssClass="staging-link" cssClass="active live-link staging-toggle" dropdown="<%= true %>" id="liveLink" label="live" toggle="<%= true %>">
						<aui:nav-item cssClass="row">
							<div class="staging-details">
								<div class="alert alert-warning hide warning-content" id="<portlet:namespace />warningMessage">
									<liferay-ui:message key="an-inital-staging-publication-is-in-progress" />
								</div>

								<%
								request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
								%>

								<liferay-util:include page="/html/portlet/staging_bar/last_publication_date_message.jsp" />
							</div>
						</aui:nav-item>
					</aui:nav-item>
				</c:otherwise>
			</c:choose>
		</c:if>
	</aui:nav>

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
				'/backgroundtask/get-background-tasks-count',
				{
					groupId: '<%= liveGroup.getGroupId() %>',
					taskExecutorClassName: '<%= LayoutStagingBackgroundTaskExecutor.class.getName() %>',
					completed: false
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
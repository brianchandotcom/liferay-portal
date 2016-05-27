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

	UnicodeProperties typeSettingsProperties = group.getTypeSettingsProperties();

	String remoteAddress = typeSettingsProperties.getProperty("remoteAddress");
	int remotePort = GetterUtil.getInteger(typeSettingsProperties.getProperty("remotePort"));
	String remotePathContext = typeSettingsProperties.getProperty("remotePathContext");
	boolean secureConnection = GetterUtil.getBoolean(typeSettingsProperties.getProperty("secureConnection"));
	long remoteGroupId = GetterUtil.getLong(typeSettingsProperties.getProperty("remoteGroupId"));

	String remoteURL = StagingUtil.buildRemoteURL(remoteAddress, remotePort, remotePathContext, secureConnection, remoteGroupId, layout.isPrivateLayout());
	%>

	<c:if test="<%= liveGroup != null %>">
		<ul class="control-menu-nav">
			<li class="control-menu-nav-item dropdown staging-options-toggle visible-xs">
				<a class="control-menu-icon dropdown-toggle" href="javascript:;" data-toggle="dropdown" value="staging">
					<span class="control-menu-icon-label">
						<c:choose>
							<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
								<c:if test="<%= stagingGroup != null %>">
									<liferay-ui:message key="staging" />
								</c:if>
							</c:when>
							<c:otherwise>
								<liferay-ui:message key="live" />
							</c:otherwise>
						</c:choose>
					</span>

					<aui:icon image="caret-double-l" markupView="lexicon" />
				</a>

				<ul class="dropdown-menu">
					<li>
						<a href="#" id="viewPageStagingOptions">
							<liferay-ui:message key="view-page-staging-options" />
						</a>
					</li>

					<c:if test="<%= !group.isStagingGroup() && !group.isStagedRemotely() && (stagingGroup != null) %>">
						<li>
							<a href="<%= stagingFriendlyURL %>">
								<liferay-ui:message key="go-to-staging" />
							</a>
						</li>
					</c:if>

					<c:if test="<%= group.isStagingGroup() %>">
						<c:choose>
							<c:when test="<%= group.isStagedRemotely() %>">
								<li>
									<a href="<%= remoteURL %>">
										<liferay-ui:message key="go-to-remote-live" />
									</a>
								</li>
							</c:when>
							<c:when test="<%= group.isStagingGroup() && Validator.isNotNull(liveFriendlyURL) %>">
								<li>
									<a href="<%= liveFriendlyURL %>">
										<liferay-ui:message key="go-to-live" />
									</a>
								</li>
							</c:when>
						</c:choose>
					</c:if>
				</ul>
			</li>

			<c:choose>
				<c:when test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">
					<c:if test="<%= stagingGroup != null %>">
						<li class="active control-menu-link control-menu-nav-item hidden-xs staging-link">
							<a class="control-menu-icon" id="stagingLink" value="staging">
								<aui:icon image="staging" label="staging" markupView="lexicon" />
							</a>
						</li>
					</c:if>
				</c:when>
				<c:otherwise>
					<li class="control-menu-link control-menu-nav-item hidden-xs staging-link">
						<a class="control-menu-icon" href="<%= (layoutSetBranches != null) ? null : stagingFriendlyURL %>" value="staging">
							<aui:icon image="staging" label="staging" markupView="lexicon" />
						</a>
					</li>
				</c:otherwise>
			</c:choose>

			<c:if test="<%= !group.isStagingGroup() || Validator.isNotNull(liveFriendlyURL) %>">
				<li class="control-menu-nav-item hidden-xs staging-divider">
					<span> / </span>
				</li>
			</c:if>

			<c:choose>
				<c:when test="<%= group.isStagedRemotely() %>">
					<li class="control-menu-link control-menu-nav-item hidden-xs live-link">

						<%
						String remoteSiteURL = StringPool.BLANK;

						try {
							remoteSiteURL = StagingUtil.getRemoteSiteURL(group, layout.isPrivateLayout());
						%>

							<a class="control-menu-icon" href="<%= remoteSiteURL %>" value="go-to-remote-live">
								<aui:icon image="home" label="go-to-remote-live" markupView="lexicon" />
							</a>

						<%
						}
						catch (SystemException se) {
							_log.error(se, se);
						%>

							<a class="control-menu-icon" value="go-to-remote-live">
								<aui:icon image="home" label="go-to-remote-live" markupView="lexicon" />
							</a>

							<liferay-ui:icon icon="exclamation-full" markupView="lexicon" message="an-unexpected-error-occurred" toolTip="<%= true %>" />

						<%
						}
						%>

					</li>
				</c:when>
				<c:when test="<%= group.isStagingGroup() %>">
					<c:if test="<%= Validator.isNotNull(liveFriendlyURL) %>">
						<li class="control-menu-link control-menu-nav-item hidden-xs live-link">
							<a class="control-menu-icon" href="<%= liveFriendlyURL %>" value="live">
								<aui:icon image="live" label="live" markupView="lexicon" />
							</a>
						</li>
					</c:if>
				</c:when>
				<c:otherwise>
					<li class="active control-menu-link control-menu-nav-item hidden-xs live-link">
						<a class="control-menu-icon taglib-icon" id="liveLink" value="live">
							<aui:icon image="live" label="live" markupView="lexicon" />
						</a>
					</li>
				</c:otherwise>
			</c:choose>

			<li class="control-menu-nav-item staging-bar">
				<div class="control-menu-level-2">
					<div class="container-fluid-1280">
						<div class="control-menu-level-2-heading visible-xs">
							<liferay-ui:message key="staging-options" />

							<button aria-labelledby="Close" class="close" id="closeStagingOptions" type="button">
								<aui:icon image="times" markupView="lexicon" />
							</button>
						</div>

						<ul class="control-menu-level-2-nav control-menu-nav">
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

										<li class="staging-layout-revision-details" id="<portlet:namespace />layoutRevisionDetails">
											<aui:model-context bean="<%= layoutRevision %>" model="<%= LayoutRevision.class %>" />

											<liferay-util:include page="/view_layout_revision_details.jsp" servletContext="<%= application %>" />
										</li>
									</c:when>
									<c:otherwise>
										<c:if test="<%= group.isStagingGroup() || group.isStagedRemotely() %>">

											<%
											request.setAttribute(StagingProcessesWebKeys.BRANCHING_ENABLED, String.valueOf(false));
											%>

											<liferay-staging:menu cssClass="publish-link" onlyActions="<%= true %>" />
										</c:if>

										<li>
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
										</li>
									</c:otherwise>
								</c:choose>
							</c:if>

							<c:if test="<%= !group.isStagedRemotely() && !group.isStagingGroup() %>">
								<li class="control-menu-nav-item staging-message">
									<div class="alert alert-warning hide warning-content" id="<portlet:namespace />warningMessage">
										<liferay-ui:message key="an-inital-staging-publication-is-in-progress" />
									</div>

									<%
									request.setAttribute("view.jsp-typeSettingsProperties", liveLayout.getTypeSettingsProperties());
									%>

									<liferay-util:include page="/last_publication_date_message.jsp" servletContext="<%= application %>" />
								</li>
							</c:if>
						</ul>
					</div>
				</div>
			</li>
		</ul>
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
		$('#viewPageStagingOptions').on(
			'click',
			function(event) {
				event.preventDefault();

				$('.control-menu-level-2').addClass('open');
			}
		);

		$('#closeStagingOptions').on(
			'click',
			function(event) {
				event.preventDefault();

				$('.control-menu-level-2').removeClass('open');
			}
		);

		var stagingLink = A.one('#<portlet:namespace />stagingLink');
		var warningMessage = A.one('#<portlet:namespace />warningMessage');

		var checkBackgroundTasks = function() {
			Liferay.Service(
				'/backgroundtask.backgroundtask/get-background-tasks-count',
				{
					completed: false,
					groupId: '<%= liveGroup.getGroupId() %>',
					taskExecutorClassName: '<%= BackgroundTaskExecutorNames.LAYOUT_STAGING_BACKGROUND_TASK_EXECUTOR %>'
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

<%!
private static Log _log = LogFactoryUtil.getLog("com_liferay_staging_bar_web.view_jsp");
%>